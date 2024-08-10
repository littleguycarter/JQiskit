package com.cf.jqiskit.gson_adapters.types;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.objects.IBMError;
import com.cf.jqiskit.util.general.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public final class IBMErrorDeserializer implements JsonDeserializer<IBMError> {
    @Override
    public IBMError deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        // who thought "not_found" as an error code was a good idea
        String code = object.get("code").getAsString();

        JsonElement msgObj = object.get("message");
        String message = "No message provided";

        if (msgObj != null) {
            message = msgObj.getAsString();
        }

        JsonElement solObj = object.get("message");
        String solution = "No solution provided";

        if (solObj != null) {
            solution = solObj.getAsString();
        }

        JsonElement infoObj = object.get("message");
        String info = "No additional info provided";

        if (infoObj != null) {
            info = infoObj.getAsString();
        }

        return new IBMError(code, message, solution, info);
    }

    @Override
    public TypeToken<IBMError> token() {
        return new TypeToken.Basic<>(IBMError.class);
    }
}
