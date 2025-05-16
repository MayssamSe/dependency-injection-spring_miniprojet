package net.mayssam.context;

import net.mayssam.annotation.Component;
import net.mayssam.annotation.Inject;
import net.mayssam.annotation.PostConstruct;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationApplicationContext {
    private Map<String, Object> beans = new HashMap<>();
    private Map<Class<?>, Class<?>> componentTypes = new HashMap<>();

    public AnnotationApplicationContext(String basePackage) throws Exception {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        // Indexer les types disponibles pour l’injection
        for (Class<?> cls : classes) {
            componentTypes.put(cls, cls);
            for (Class<?> interf : cls.getInterfaces()) {
                componentTypes.put(interf, cls); // Associer les interfaces aux implémentations
            }
        }

        // Instancier tous les beans
        for (Class<?> cls : classes) {
            createBean(cls);
        }

        // Injection par champs
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fieldType = field.getType();
                    Object dependency = getBean(fieldType);
                    if (dependency == null) {
                        throw new RuntimeException("Dépendance manquante: " + fieldType.getName());
                    }
                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }
        }

        // Injection par setter
        for (Object bean : beans.values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inject.class) && method.getParameterCount() == 1) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    Object dep = getBean(paramType);
                    if (dep == null) {
                        throw new RuntimeException("Dépendance manquante pour setter: " + method.getName());
                    }
                    method.setAccessible(true);
                    method.invoke(bean, dep);
                }
            }
        }

        // PostConstruct
        for (Object bean : beans.values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.setAccessible(true);
                    method.invoke(bean);
                }
            }
        }
    }

    private Object createBean(Class<?> cls) throws Exception {
        Component comp = cls.getAnnotation(Component.class);
        String beanId = comp.id().isEmpty() ? cls.getName() : comp.id();

        if (beans.containsKey(beanId)) return beans.get(beanId);

        // Recherche d’un constructeur annoté
        Constructor<?> constructor = Arrays.stream(cls.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElse(null);

        Object instance;
        if (constructor != null) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = getBean(paramTypes[i]);
                if (args[i] == null) {
                    // Crée la dépendance à la volée si elle n’existe pas
                    Class<?> depImpl = componentTypes.get(paramTypes[i]);
                    if (depImpl == null)
                        throw new RuntimeException("Dépendance non trouvée pour " + paramTypes[i].getName());
                    args[i] = createBean(depImpl);
                }
            }
            constructor.setAccessible(true);
            instance = constructor.newInstance(args);
        } else {
            instance = cls.getDeclaredConstructor().newInstance();
        }

        beans.put(beanId, instance);
        return instance;
    }

    public Object getBean(String id) {
        return beans.get(id);
    }

    public <T> T getBean(Class<T> cls) {
        for (Object bean : beans.values()) {
            if (cls.isAssignableFrom(bean.getClass())) {
                return cls.cast(bean);
            }
        }
        return null;
    }
}
