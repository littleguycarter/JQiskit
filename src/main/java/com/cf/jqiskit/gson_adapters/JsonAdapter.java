package com.cf.jqiskit.gson_adapters;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonAdapter<T> extends JsonDeserializer<T> {
    Class<T> getTargetClass();
}
