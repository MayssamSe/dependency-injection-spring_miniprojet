package net.mayssam.utils;

import java.util.Set;
import java.util.HashSet;

/**
 * Classe utilitaire pour scanner un package et retourner les classes annotées.
 * Cette implémentation est très simple et vous pourrez l'améliorer
 * afin de scanner dynamiquement tous les .class d’un package.
 */
public class ClassScanner {
    public static Set<Class<?>> scan(String basePackage) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        if ("net.mayssam".equals(basePackage)) {
            classes.add(Class.forName("net.mayssam.dao.DaoImpl"));
            classes.add(Class.forName("net.mayssam.metier.MetierImpl"));
        }
        return classes;
    }
}