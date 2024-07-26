package com.cf.jqiskit.ibm.endpoint.types;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public class UserEndpoint implements IBMEndpoint {
    @Override
    public String urlPath() {
        return "users/me";
    }
}
