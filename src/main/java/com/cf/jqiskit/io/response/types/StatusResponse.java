package com.cf.jqiskit.io.response.types;

import com.cf.jqiskit.io.response.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

public class StatusResponse implements Response {
    private int status;

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        status = connection.getResponseCode();
    }

    public int getStatus() {
        return status;
    }
}
