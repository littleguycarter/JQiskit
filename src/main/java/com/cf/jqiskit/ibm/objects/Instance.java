package com.cf.jqiskit.ibm.objects;

public record Instance(String name, String hub, String group, String project, Plan plan) {
    public enum Plan { OPEN, PREMIUM }
}
