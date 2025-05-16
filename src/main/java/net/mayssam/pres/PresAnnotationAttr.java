package net.mayssam.pres;

import net.mayssam.context.AnnotationApplicationContext;
import net.mayssam.metier.IMetier;

public class PresAnnotationAttr {
    public static void main(String[] args) {
        try {
            // Création du contexte d'injection en scannant le package "net.mayssam"
            AnnotationApplicationContext context = new AnnotationApplicationContext("net.mayssam");

            // Récupération du bean métier dont l'id est "metierImpl" (défini dans l'annotation @Component)
            IMetier metier = (IMetier) context.getBean("metierImpl");

            // Exécution de la logique métier et affichage du résultat
            System.out.println("Résultat du calcul (injection via attribut ) : " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}