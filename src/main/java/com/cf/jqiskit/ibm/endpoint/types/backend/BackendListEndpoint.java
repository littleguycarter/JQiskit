package com.cf.jqiskit.ibm.endpoint.types.backend;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public final class BackendListEndpoint implements IBMEndpoint {
    @Override
    public String urlPath() {
        return "backends";
    }
}
