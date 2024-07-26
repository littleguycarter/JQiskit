package com.cf.jqiskit.io;

import com.cf.jqiskit.io.response.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public interface HttpRequest {
    String getRawURL();

    void prepare(HttpURLConnection connection) throws IOException;

    default void populate(Response response) throws IOException {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) URI.create(getRawURL()).toURL().openConnection();
            prepare(connection);

            response.use(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
