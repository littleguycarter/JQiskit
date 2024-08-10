package com.cf.jqiskit.ibm.objects;

import com.cf.jqiskit.ibm.IBMHttpRequest;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.types.session.CloseSessionEndpoint;
import com.cf.jqiskit.ibm.responses.IBMResponse;

import java.io.Closeable;
import java.io.IOException;

public final class Session implements Closeable {
    private final String apiToken;
    private final String backend;
    private final Instance instance;
    private final String id;

    public Session(String apiToken, String backend, Instance instance, String id) {
        this.apiToken = apiToken;
        this.backend = backend;
        this.instance = instance;
        this.id = id;
    }

    public String backend() {
        return backend;
    }

    public Instance instance() {
        return instance;
    }

    public String id() {
        return id;
    }

    @Override
    public void close() throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(new CloseSessionEndpoint(id));
        IBMResponse response = new IBMResponse();

        new IBMHttpRequest(info, apiToken).populate(response);
        response.throwErrors();
    }
}
