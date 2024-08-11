package com.cf.jqiskit.ibm.objects;

// TODO implement job info params
public record JobInfo() {
    public enum Status {
        COMPLETED, CANCELLED
    }
}
