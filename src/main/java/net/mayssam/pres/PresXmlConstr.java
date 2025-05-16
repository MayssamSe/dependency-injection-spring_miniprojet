package net.mayssam.pres;

import net.mayssam.context.XmlApplicationContext;
import net.mayssam.metier.IMetier;
// utilisation du constructeur
public class PresXmlConstr {
    public static void main(String[] args) throws Exception {
        XmlApplicationContext ctx = new XmlApplicationContext("beans-const.xml");
        // on récupère le bean qui utilise le constructeur
        IMetier metier = (IMetier) ctx.getBean("metierCtor");
        System.out.println("Résultat (constructeur) = " + metier.calcul());
    }
}
