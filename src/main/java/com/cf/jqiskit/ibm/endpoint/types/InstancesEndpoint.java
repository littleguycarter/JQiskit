package com.cf.jqiskit.ibm.endpoint.types;

import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;

public final class InstancesEndpoint implements IBMEndpoint {
    @Override
    public String urlPath() {
        return "instances";
    }
}
