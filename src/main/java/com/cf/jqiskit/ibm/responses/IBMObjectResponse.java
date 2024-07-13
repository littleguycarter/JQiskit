package com.cf.jqiskit.ibm.responses;

import com.cf.jqiskit.JQiskit;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;

public class IBMObjectResponse<T> extends IBMResponse {
    private final TypeToken<T> token;
    private T object;

    public IBMObjectResponse(TypeToken<T> token) {
        this.token = token;
    }

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        super.use(connection);

        if (isErrored()) {
            return;
        }

        this.object = JQiskit.GSON.fromJson(getJsonResponse(), token);
    }

    public T getResult() {
        return object;
    }
}
