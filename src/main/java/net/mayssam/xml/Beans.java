package net.mayssam.xml;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Classe JAXB représentant la racine du fichier de configuration XML.
 */
@XmlRootElement(name = "beans")
public class Beans {
    private List<BeanDefinition> beans;

    @XmlElement(name = "bean")
    public List<BeanDefinition> getBeans() {
        return beans;
    }

    public void setBeans(List<BeanDefinition> beans) {
        this.beans = beans;
    }
}