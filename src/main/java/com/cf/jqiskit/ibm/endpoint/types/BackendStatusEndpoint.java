package com.cf.jqiskit.ibm.endpoint.types;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public final class BackendStatusEndpoint implements IBMEndpoint {
    private final String backendId;

    public BackendStatusEndpoint(String backendId) {
        this.backendId = backendId;
    }

    @Override
    public String urlPath() {
        return "backends/" + backendId + "/status";
    }
}
