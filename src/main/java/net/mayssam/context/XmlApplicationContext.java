package net.mayssam.context;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import net.mayssam.xml.BeanDefinition;
import net.mayssam.xml.Beans;
import net.mayssam.xml.ConstructorArg;
import net.mayssam.xml.Property;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlApplicationContext {
    private Map<String, Object> beans = new HashMap<>();
    private Map<String, BeanDefinition> definitions = new HashMap<>();

    public XmlApplicationContext(String xmlPath) throws Exception {
        // Lecture du fichier de configuration XML
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(xmlPath);
        if (is == null) {
            throw new IllegalArgumentException("Ressource introuvable : " + xmlPath);
        }

        // Parsing du fichier XML
        JAXBContext jaxbCtx = JAXBContext.newInstance(Beans.class);
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        Beans config = (Beans) um.unmarshal(is);

        // Stockage des définitions de beans
        for (BeanDefinition bd : config.getBeans()) {
            definitions.put(bd.getId(), bd);
        }

        // Création des instances de beans
        for (BeanDefinition bd : config.getBeans()) {
            if (!beans.containsKey(bd.getId())) {
                Object instance = createBean(bd);
                beans.put(bd.getId(), instance);
            }
        }
    }

    private Object createBean(BeanDefinition bd) throws Exception {
        Class<?> cls = Class.forName(bd.getClassName());

        // Gestion de l'injection par constructeur
        Object instance;
        if (bd.getConstructorArg() != null && !bd.getConstructorArg().isEmpty()) {
            List<ConstructorArg> argsList = bd.getConstructorArg();
            Object[] args = new Object[argsList.size()];
            Class<?>[] paramTypes = new Class<?>[argsList.size()];

            for (int i = 0; i < argsList.size(); i++) {
                String ref = argsList.get(i).getRef();
                Object dependency = beans.get(ref);
                if (dependency == null) {
                    BeanDefinition depDef = definitions.get(ref);
                    if (depDef == null) {
                        throw new RuntimeException("Bean introuvable pour ref: " + ref);
                    }
                    dependency = createBean(depDef);
                    beans.put(ref, dependency);
                }
                args[i] = dependency;
                Class<?>[] interfaces = dependency.getClass().getInterfaces();
                paramTypes[i] = (interfaces.length > 0) ? interfaces[0] : dependency.getClass();
            }

            Constructor<?> constructor = cls.getConstructor(paramTypes);
            instance = constructor.newInstance(args);
        } else {
            instance = cls.getDeclaredConstructor().newInstance();
        }

        // Gestion de l'injection par setter
        if (bd.getProperties() != null) {
            for (Property prop : bd.getProperties()) {
                String name = prop.getName();
                String ref = prop.getRef();
                Object dependency = beans.get(ref);
                if (dependency == null) {
                    BeanDefinition depDef = definitions.get(ref);
                    if (depDef == null) {
                        throw new RuntimeException("Bean introuvable pour ref: " + ref);
                    }
                    dependency = createBean(depDef);
                    beans.put(ref, dependency);
                }
                try {
                    Method setter = cls.getMethod("set" + capitalize(name), dependency.getClass().getInterfaces()[0]);
                    setter.invoke(instance, dependency);
                } catch (NoSuchMethodException e) {
                    Field field = cls.getDeclaredField(name);
                    field.setAccessible(true);
                    field.set(instance, dependency);
                }
            }
        }


        if (bd.getInitMethod() != null) {
            Method initMethod = cls.getMethod(bd.getInitMethod());
            initMethod.invoke(instance);
        }

        return instance;
    }

    public Object getBean(String id) {
        return beans.get(id);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
