package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.objects.Instance;
import com.cf.jqiskit.util.general.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class IBMInstancesDeserializer implements JsonDeserializer<Map<String, Instance>> {
    @Override
    public Map<String, Instance> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray array = object.getAsJsonArray("instances");

        Map<String, Instance> instances = new HashMap<>();
        for (JsonElement element : array) {
            Instance instance = jsonDeserializationContext.deserialize(element, Instance.class);
            instances.put(instance.name(), instance);
        }

        return instances;
    }

    @Override
    public TypeToken<Map<String, Instance>> token() {
        return new TypeToken<>() {};
    }
}
