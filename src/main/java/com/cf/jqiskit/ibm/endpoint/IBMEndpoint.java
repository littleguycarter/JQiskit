package com.cf.jqiskit.ibm.endpoint;

import com.cf.jqiskit.ibm.endpoint.types.UserEndpoint;

public interface IBMEndpoint {
    String API_URL = "https://api.quantum-computing.ibm.com/runtime/";

    IBMEndpoint USER = new UserEndpoint();

    String urlPath();

    default String rawUrlPath() {
        return API_URL + urlPath();
    }
}
