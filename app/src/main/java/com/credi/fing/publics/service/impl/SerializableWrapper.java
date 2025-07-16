package com.credi.fing.publics.service.impl;

import java.io.IOException;
import java.io.Serializable;

public class SerializableWrapper<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient T instance;

    public SerializableWrapper(T instance) {
        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }

    // Implémentez ici la logique de sérialisation personnalisée si besoin
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // Vous pouvez éventuellement sérialiser d'autres données liées à l'instance
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Restaurez l'instance si nécessaire
    }
}

