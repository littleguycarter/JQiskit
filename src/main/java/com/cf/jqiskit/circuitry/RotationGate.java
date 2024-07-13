package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.assembly.QasmBuilder;

public abstract class RotationGate implements QuantumGate {
    protected final int piDivisor;

    public RotationGate(int piDivisor) {
        this.piDivisor = piDivisor;
    }

    public abstract String qasmIdentifier();

    @Override
    public int acceptedQubits() {
        return 1;
    }

    @Override
    public void toQasmCommand(QasmBuilder script, int targetRegistry) {
        script.addStep(qasmIdentifier() + "(pi/" + piDivisor + ")" + " q[" + targetRegistry + "];");
    }
}
