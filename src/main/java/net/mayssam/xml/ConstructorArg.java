package net.mayssam.xml;

import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Classe pour mapper un argument de constructeur dans la configuration XML.
 */
public class ConstructorArg {
    private String ref;

    @XmlAttribute
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
}