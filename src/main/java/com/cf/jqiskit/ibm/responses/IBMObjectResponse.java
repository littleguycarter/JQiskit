package com.cf.jqiskit.ibm.responses;

import com.cf.jqiskit.JQiskitService;
import com.cf.jqiskit.util.general.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public class IBMObjectResponse<T> extends IBMResponse {
    private final Type type;
    private T object;

    public IBMObjectResponse(TypeToken<T> token) {
        this.type = token.type();
    }

    public IBMObjectResponse(Class<T> clazz) {
        this.type = clazz;
    }

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        super.use(connection);

        if (isErrored()) {
            return;
        }

        this.object = JQiskitService.GSON.fromJson(getJsonResponse(), type);
    }

    public T getResult() {
        return object;
    }
}
