package net.mayssam.metier;

import net.mayssam.dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("metier")
public class MetierImpl2 implements IMetier {
    private IDao dao; // Couplage faible
    private double initialValue;
    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDO
     * au moment de l'instantiation
     */
    public MetierImpl2(@Qualifier("dao") IDao dao) {
        this.dao = dao;
    }


    public void init() {
        // Initialisation de votre bean, avant le calcul
        this.initialValue = dao.getData() + 10;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDO
     * après instantiation
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
