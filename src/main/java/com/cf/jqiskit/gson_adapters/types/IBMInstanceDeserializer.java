package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.objects.Instance;
import com.google.gson.*;

import java.lang.reflect.Type;

public final class IBMInstanceDeserializer implements JsonDeserializer<Instance> {
    @Override
    public Instance deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String name = obj.get("name").getAsString();
        Instance.Plan plan = Instance.Plan.valueOf(obj.get("plan").getAsString().toUpperCase());

        return new Instance(name, plan);
    }

    @Override
    public Type type() {
        return Instance.class;
    }
}
