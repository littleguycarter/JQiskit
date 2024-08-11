package com.cf.jqiskit.ibm.objects;

import com.cf.jqiskit.JQiskitService;

import java.io.Closeable;
import java.io.IOException;

public final class Session implements Closeable {
    private final JQiskitService service;
    private final String backend;
    private final Instance instance;
    private final String id;

    public Session(JQiskitService service, String backend, Instance instance, String id) {
        this.service = service;
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
        service.closeSession(id);
    }
}
