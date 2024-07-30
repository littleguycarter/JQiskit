package com.cf.jqiskit.util.general;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Lightweight alternative to Gson's TypeToken for simple type building
 */
public interface TypeToken<T> {
    default Type type() {
        return ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
