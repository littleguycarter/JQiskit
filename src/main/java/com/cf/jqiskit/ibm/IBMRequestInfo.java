package com.cf.jqiskit.ibm;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public record IBMRequestInfo(IBMEndpoint endpoint, String payload) {
    public IBMRequestInfo(IBMEndpoint endpoint) {
        this(endpoint, null);
    }
}
