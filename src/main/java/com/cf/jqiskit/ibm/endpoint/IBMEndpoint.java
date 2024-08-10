package com.cf.jqiskit.ibm.endpoint;

import com.cf.jqiskit.ibm.endpoint.types.InstancesEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.UserEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.backend.BackendListEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.session.CreateSessionEndpoint;
import com.cf.jqiskit.io.RequestMethod;

public interface IBMEndpoint {
    String API_URL = "https://api.quantum-computing.ibm.com/runtime/";

    IBMEndpoint USER = new UserEndpoint();
    IBMEndpoint INSTANCES = new InstancesEndpoint();
    IBMEndpoint CREATE_SESSION = new CreateSessionEndpoint();
    IBMEndpoint LIST_BACKENDS = new BackendListEndpoint();

    String urlPath();

    default RequestMethod method() {
        return RequestMethod.GET;
    }

    default String rawUrlPath() {
        return API_URL + urlPath();
    }
}
