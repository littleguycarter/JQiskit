package com.cf.jqiskit.gson_adapters;

import com.cf.jqiskit.util.general.TypeToken;

public interface JsonDeserializer<T> extends com.google.gson.JsonDeserializer<T> {
    TypeToken<T> token();
}
