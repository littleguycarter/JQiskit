package com.cf.jqiskit.io;

import com.cf.jqiskit.io.response.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public interface HttpRequest {
    String getRawURL();

    RequestMethod requestMethod();

    /**
     * Specify the request method in the requestMethod function.
     */
    void prepare(HttpURLConnection connection) throws IOException;

    default void populate(Response response) throws IOException {
        if (response.requiredMethod() != null && response.requiredMethod() != requestMethod()) {
            throw new IllegalArgumentException("Provided response cannot be populated by a " + requestMethod() + " request!");
        }

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) URI.create(getRawURL()).toURL().openConnection();
            connection.setRequestMethod(requestMethod().toString());
            prepare(connection);

            response.use(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
