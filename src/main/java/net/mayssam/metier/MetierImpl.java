package net.mayssam.metier;

import net.mayssam.annotation.Component;
import net.mayssam.annotation.Inject;
import net.mayssam.annotation.PostConstruct;
import net.mayssam.dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implémentation de la logique métier.
 */
@Component(id = "metierImpl")

public class MetierImpl implements IMetier {

    // Injection par champ
    @Inject
    private IDao dao;
    private double initialValue;

    public MetierImpl(@Qualifier("dao") IDao dao) {
        this.dao = dao;
    }

    public MetierImpl(){}

    @PostConstruct
    public void init() {
        // Initialisation après injection
        initialValue = dao.getData() * 10;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }

    // Méthode setter alternative pour l'injection
    @Inject
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}