package com.cf.jqiskit.ibm.endpoint.types;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public final class UserEndpoint implements IBMEndpoint {
    @Override
    public String urlPath() {
        return "users/me";
    }
}
