package net.mayssam.pres;

import net.mayssam.context.AnnotationApplicationContext;
import net.mayssam.metier.IMetier;

public class PresAnnotationConstr {
    public static void main(String[] args) {
        try {
            AnnotationApplicationContext ctx =
                    new AnnotationApplicationContext("net.mayssam");
            // on récupère le bean dont l'id est "metierCtorAnnot"
            IMetier metier = (IMetier) ctx.getBean("metierCtorAnnot");
            System.out.println("Résultat (constructeur) = " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
