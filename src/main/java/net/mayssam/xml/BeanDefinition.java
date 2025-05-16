package net.mayssam.xml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

public class BeanDefinition {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArg;
    private List<Property> properties;
    private String initMethod;

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "class")
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    @XmlElement(name = "constructor-arg")
    public List<ConstructorArg> getConstructorArg() {
        return constructorArg;
    }
    public void setConstructorArg(List<ConstructorArg> constructorArg) {
        this.constructorArg = constructorArg;
    }

    @XmlElement(name = "property")
    public List<Property> getProperties() {
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @XmlAttribute(name = "init-method")
    public String getInitMethod() {
        return initMethod;
    }
    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }
}
