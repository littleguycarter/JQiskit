package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.objects.BackendStatus;
import com.cf.jqiskit.util.general.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public final class IBMBackendStatusDeserializer implements JsonDeserializer<BackendStatus> {
    @Override
    public BackendStatus deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        boolean state = object.get("state").getAsBoolean();
        BackendStatus.Status status = BackendStatus.Status.valueOf(object.get("status").getAsString().toUpperCase());
        String message = object.get("message").getAsString();
        int queue = object.get("length_queue").getAsInt();
        String version = object.get("backend_version").getAsString();

        return new BackendStatus(state, status, message, queue, version);
    }

    @Override
    public TypeToken<BackendStatus> token() {
        return new TypeToken.Basic<>(BackendStatus.class);
    }
}
