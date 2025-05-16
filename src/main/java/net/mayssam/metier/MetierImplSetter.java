package net.mayssam.metier;

import net.mayssam.annotation.Component;
import net.mayssam.annotation.Inject;
import net.mayssam.annotation.PostConstruct;
import net.mayssam.dao.IDao;

@Component(id="metierSetterAnnot")
public class MetierImplSetter implements IMetier {
    private IDao dao;
    private double initialValue;

    public MetierImplSetter() { }

    // **Setter annoté @Inject**
    @Inject
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void init() {
        initialValue = dao.getData() + 40;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }
}
