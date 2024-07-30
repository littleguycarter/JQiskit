package com.cf.jqiskit.ibm.objects;

public record Instance(String name, Plan plan) {
    public enum Plan { OPEN, PREMIUM }
}
