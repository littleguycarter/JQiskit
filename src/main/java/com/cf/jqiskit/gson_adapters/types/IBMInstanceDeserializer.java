package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.objects.Instance;
import com.cf.jqiskit.util.general.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;

public final class IBMInstanceDeserializer implements JsonDeserializer<Instance> {
    @Override
    public Instance deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String name = obj.get("name").getAsString();
        Instance.Plan plan = Instance.Plan.valueOf(obj.get("plan").getAsString().toUpperCase());

        String[] split = name.split("/");
        String hub = split[0];
        String group = split[1];
        String project = split[2];

        return new Instance(name, hub, group, project, plan);
    }

    @Override
    public TypeToken<Instance> token() {
        return new TypeToken.Basic<>(Instance.class);
    }
}
