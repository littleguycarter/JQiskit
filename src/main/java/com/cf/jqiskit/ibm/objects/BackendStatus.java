package com.cf.jqiskit.ibm.objects;

public record BackendStatus(boolean state, Status status, String message, int queue, String version) {
    // not sure what the inactive state name is, but we'll go with this for now
    public enum Status { ACTIVE, INACTIVE }
}
