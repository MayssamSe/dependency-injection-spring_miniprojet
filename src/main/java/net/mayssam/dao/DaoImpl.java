package net.mayssam.dao;


import net.mayssam.annotation.Component;
import org.springframework.stereotype.Repository;
/**
 * Implémentation simple de l'interface IDao.
 */
@Component(id="net.mayssam.dao.IDao")
@Repository("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        // Pour cet exemple, on renvoie une valeur fixe.
        return 80;
    }
}