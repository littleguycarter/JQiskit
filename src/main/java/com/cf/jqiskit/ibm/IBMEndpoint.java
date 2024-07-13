package com.cf.jqiskit.ibm;

public enum IBMEndpoint {
    USER("users/me"),
    INSTANCES("instances");

    private static final String API_URL = "https://api.quantum-computing.ibm.com/runtime/";
    private final String urlPath;

    IBMEndpoint(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getRawURL() {
        return API_URL + urlPath;
    }
}
