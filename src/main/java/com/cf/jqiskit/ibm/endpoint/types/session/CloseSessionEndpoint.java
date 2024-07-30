package com.cf.jqiskit.ibm.endpoint.types.session;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.io.RequestMethod;

public final class CloseSessionEndpoint implements IBMEndpoint {
    private final String id;

    public CloseSessionEndpoint(String id) {
        this.id = id;
    }

    @Override
    public String urlPath() {
        return "sessions/" + id + "/close";
    }

    @Override
    public RequestMethod method() {
        return RequestMethod.DELETE;
    }
}
