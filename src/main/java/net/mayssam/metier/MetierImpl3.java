package net.mayssam.metier;

import net.mayssam.dao.IDao;

public class MetierImpl3 implements IMetier {
    private IDao dao;
    private double initialValue;

    public MetierImpl3() {
        // Constructeur par défaut
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public void init() {
        initialValue = dao.getData() + 20;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }
}
