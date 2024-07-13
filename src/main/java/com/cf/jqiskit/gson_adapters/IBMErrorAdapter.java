package com.cf.jqiskit.gson_adapters;

import com.cf.jqiskit.ibm.IBMError;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public class IBMErrorAdapter implements JsonAdapter<IBMError> {

    @Override
    public Class<IBMError> getTargetClass() {
        return IBMError.class;
    }

    @Override
    public IBMError deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int code = object.get("code").getAsInt();
        String message = object.get("message").getAsString();
        String solution = object.get("solution").getAsString();
        String moreInfo = object.get("more_info").getAsString();

        return new IBMError(code, message, solution, moreInfo);
    }

    @Override
    public JsonElement serialize(IBMError ibmError, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("code", ibmError.code());
        object.addProperty("message", ibmError.message());
        object.addProperty("solution", ibmError.solution());
        object.addProperty("more_info", ibmError.moreInfo());

        return object;
    }
}
