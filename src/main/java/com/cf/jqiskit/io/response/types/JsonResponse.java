package com.cf.jqiskit.io.response.types;

import com.cf.jqiskit.io.HttpUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class JsonResponse extends StatusResponse {
    private JsonObject json;

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        super.use(connection);

        try (JsonReader reader = new JsonReader(new InputStreamReader(HttpUtil.isSuccessful(getStatus()) ? connection.getInputStream() : connection.getErrorStream()))) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    public JsonObject getJsonResponse() {
        return json;
    }
}
