package com.cf.jqiskit.assembly;

public final class QasmBuilder {
    private final float version;
    private final int qubits;
    private final StringBuilder definitionBuilder;
    private final StringBuilder stepBuilder;

    public QasmBuilder(float version, int qubits) {
        this.version = version;
        this.qubits = qubits;
        this.definitionBuilder = new StringBuilder();
        this.stepBuilder = new StringBuilder();
    }

    public void addDefinition(String definition) {
        definitionBuilder.append(definition).append(" ");
    }

    public void addStep(String step) {
        stepBuilder.append(step).append(" ");
    }

    @Override
    public String toString() {
        return "OPENQASM " + version + "; " +
                "include \"qelib1.inc\"; " +
                "qreg q[" + qubits + "]; " +
                "creg c[" + qubits + "]; " +
                definitionBuilder +
                stepBuilder;
    }
}
