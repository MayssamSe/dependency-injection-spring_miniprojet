package net.mayssam.pres;

import net.mayssam.context.XmlApplicationContext;
import net.mayssam.metier.IMetier;

// injection directe via l'attribut
public class PresXmlAttr {
    public static void main(String[] args) throws Exception {
        XmlApplicationContext ctx = new XmlApplicationContext("beans-att.xml");
        IMetier metier = (IMetier) ctx.getBean("metierField");
        System.out.println("Résultat (field) = " + metier.calcul());
    }
}
