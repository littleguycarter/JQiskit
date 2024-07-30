package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonSerializer;
import com.cf.jqiskit.ibm.objects.Session;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public final class IBMSessionCreationPayloadSerializer implements JsonSerializer<Session.Request> {
    @Override
    public Type type() {
        return Session.Request.class;
    }

    @Override
    public JsonElement serialize(Session.Request request, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty("backend", request.backend().id());
        object.addProperty("instance", request.instance().name());

        return object;
    }
}
