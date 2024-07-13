package com.cf.jqiskit.ibm;

import com.cf.jqiskit.io.HttpRequest;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class IBMHttpRequest implements HttpRequest {
    private final String apiToken;
    private final IBMRequestInfo info;

    public IBMHttpRequest(IBMRequestInfo info, String apiToken) {
        this.apiToken = apiToken;
        this.info = info;
    }

    public IBMHttpRequest(IBMEndpoint endpoint, String apiToken) {
        this(new IBMRequestInfo(endpoint), apiToken);
    }

    @Override
    public String getRawURL() {
        return info.endpoint().getRawURL();
    }

    @Override
    public void prepare(HttpURLConnection connection) throws IOException {
        connection.setRequestProperty("Authorization", "Bearer " + apiToken);
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        JsonObject payload = info.payload();

        if (payload == null) {
            return;
        }

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(payload.toString().getBytes());
        }
    }
}
