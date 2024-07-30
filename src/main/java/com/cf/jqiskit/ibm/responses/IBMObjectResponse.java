package com.cf.jqiskit.ibm.responses;

import com.cf.jqiskit.JQiskit;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public class IBMObjectResponse<T> extends IBMResponse {
    private final Type type;
    private T object;

    public IBMObjectResponse(Type type) {
        this.type = type;
    }

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        super.use(connection);

        if (isErrored()) {
            return;
        }

        this.object = JQiskit.GSON.fromJson(getJsonResponse(), type);
    }

    public T getResult() {
        return object;
    }
}
