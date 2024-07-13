package com.cf.jqiskit.ibm;

import com.google.gson.JsonObject;

public record IBMRequestInfo(IBMEndpoint endpoint, JsonObject payload) {
    public IBMRequestInfo(IBMEndpoint endpoint) {
        this(endpoint, null);
    }
}
