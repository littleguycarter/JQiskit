package com.cf.jqiskit.io.response;

import com.cf.jqiskit.io.RequestMethod;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Response {
    void use(HttpURLConnection connection) throws IOException;

    default RequestMethod requiredMethod() {
        return RequestMethod.GET;
    }
}
