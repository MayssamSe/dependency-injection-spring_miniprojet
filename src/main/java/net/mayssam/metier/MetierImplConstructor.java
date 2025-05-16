package net.mayssam.metier;

import net.mayssam.annotation.Component;
import net.mayssam.annotation.Inject;
import net.mayssam.annotation.PostConstruct;
import net.mayssam.dao.IDao;

@Component(id="metierCtorAnnot")
public class MetierImplConstructor implements IMetier {
    private IDao dao;
    private double initialValue;

    // **Constructeur annoté @Inject**
    @Inject
    public MetierImplConstructor(IDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void init() {
        initialValue = dao.getData() + 30;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }
}
