package com.cf.jqiskit.util.general;

import java.lang.reflect.InvocationTargetException;

public final class LazyInit<T> {
    private final Class<T> clazz;
    private T instance;

    public LazyInit(Class<T> qasmClass) {
        this.clazz = qasmClass;
        this.instance = null;
    }

    public T instance() {
        if (instance == null) {
            try {
                this.instance = clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException("Failed to build " + clazz + "!", e);
            }
        }

        return instance;
    }
}
