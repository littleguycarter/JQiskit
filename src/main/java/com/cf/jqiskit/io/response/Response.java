package com.cf.jqiskit.io.response;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Response {
    void use(HttpURLConnection connection) throws IOException;
}
