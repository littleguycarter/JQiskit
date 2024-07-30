package com.cf.jqiskit;

import com.cf.jqiskit.exceptions.IBMException;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.types.BackendStatusEndpoint;
import com.cf.jqiskit.ibm.objects.BackendStatus;
import com.cf.jqiskit.ibm.responses.IBMObjectResponse;

import java.io.IOException;

// TODO add means of querying circuits (and obtaining results)
public final class Backend {
    private final JQiskit instance;
    private final String id;

    private long lastStatusUpdateMillis;
    private BackendStatus status;

    public static Backend from(String id, JQiskit instance) throws IOException, IBMException {
        Backend backend = new Backend(instance, id);
        backend.update();

        return backend;
    }

    private Backend(JQiskit instance, String id) {
        this.instance = instance;
        this.id = id;
    }

    public void update() throws IOException, IBMException {
        IBMRequestInfo info = new IBMRequestInfo(new BackendStatusEndpoint(id));
        IBMObjectResponse<BackendStatus> response = new IBMObjectResponse<>(BackendStatus.class);

        instance.request(info, response);

        if (response.isErrored()) {
            throw new IBMException(response.getErrors());
        }

        this.status = response.getResult();
        this.lastStatusUpdateMillis = System.currentTimeMillis();
    }

    public JQiskit runtimeInstance() {
        return instance;
    }

    public String id() {
        return id;
    }

    public BackendStatus status() {
        return status;
    }

    public long lastUpdated() {
        return lastStatusUpdateMillis;
    }
}
