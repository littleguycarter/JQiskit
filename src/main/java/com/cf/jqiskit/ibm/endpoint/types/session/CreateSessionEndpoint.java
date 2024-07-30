package com.cf.jqiskit.ibm.endpoint.types.session;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.io.RequestMethod;

public final class CreateSessionEndpoint implements IBMEndpoint {
    @Override
    public String urlPath() {
        return "sessions";
    }

    @Override
    public RequestMethod method() {
        return RequestMethod.POST;
    }
}
