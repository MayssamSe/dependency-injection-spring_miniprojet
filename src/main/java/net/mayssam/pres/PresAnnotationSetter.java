package net.mayssam.pres;

import net.mayssam.context.AnnotationApplicationContext;
import net.mayssam.metier.IMetier;

public class PresAnnotationSetter {
    public static void main(String[] args) {
        try {
            AnnotationApplicationContext ctx =
                    new AnnotationApplicationContext("net.mayssam");
            // on récupère le bean dont l'id est "metierSetterAnnot"
            IMetier metier = (IMetier) ctx.getBean("metierSetterAnnot");
            System.out.println("Résultat (setter) = " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
