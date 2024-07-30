package com.cf.jqiskit.ibm;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.io.HttpRequest;
import com.cf.jqiskit.io.RequestMethod;

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
        return info.endpoint().rawUrlPath();
    }

    @Override
    public RequestMethod requestMethod() {
        return info.endpoint().method();
    }

    @Override
    public void prepare(HttpURLConnection connection) throws IOException {
        connection.setRequestProperty("Authorization", "Bearer " + apiToken);
        connection.setRequestProperty("Accept", "application/json");

        String payload = info.payload();

        if (payload == null) {
            return;
        }

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(payload.getBytes());
        }
    }
}
