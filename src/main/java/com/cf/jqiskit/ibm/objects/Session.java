package com.cf.jqiskit.ibm.objects;

import com.cf.jqiskit.Backend;
import com.cf.jqiskit.JQiskit;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.session.CloseSessionEndpoint;
import com.cf.jqiskit.ibm.responses.IBMResponse;
import com.cf.jqiskit.util.io.HttpUtil;
import com.cf.jqiskit.io.response.types.StatusResponse;

import java.io.Closeable;
import java.io.IOException;

public final class Session implements Closeable {
    public record Request(Backend backend, Instance instance) {
        public Session send() throws IOException {
            IBMRequestInfo info = new IBMRequestInfo(IBMEndpoint.CREATE_SESSION, JQiskit.GSON.toJson(this, Session.Request.class));
            IBMResponse response = new IBMResponse();

            backend.runtimeInstance().request(info, response);
            response.throwErrors();

            return new Session(backend, instance, response.getJsonResponse().get("id").getAsString());
        }
    }

    private final Backend backend;
    private final Instance instance;
    private final String id;
    private boolean closed;

    private Session(Backend backend, Instance instance, String id) {
        this.backend = backend;
        this.instance = instance;
        this.id = id;
        this.closed = false;
    }

    public Backend backend() {
        return backend;
    }

    public Instance instance() {
        return instance;
    }

    public String id() {
        return id;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;

        IBMRequestInfo info = new IBMRequestInfo(new CloseSessionEndpoint(id));
        StatusResponse response = new StatusResponse();

        backend.runtimeInstance().request(info, response);

        if (!HttpUtil.isSuccessful(response.getStatus())) {
            System.err.println("Error encountered while closing IBM Session: status code " + response.getStatus());
        }
    }
}
