package com.cf.jqiskit.ibm.endpoint.types.jobs;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public class JobEndpoint implements IBMEndpoint {
    private final String jobId;

    public JobEndpoint(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String urlPath() {
        return "jobs/" + jobId;
    }
}
