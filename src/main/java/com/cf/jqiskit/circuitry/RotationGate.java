package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.assembly.QasmWriter;
import com.cf.jqiskit.circuitry.circuits.QuantumCircuit;

public abstract class RotationGate implements SerializableGate {
    public enum Axis { X, Y, Z }

    protected final float piMultiplier;

    public RotationGate(float piMultiplier) {
        this.piMultiplier = piMultiplier;
    }

    protected abstract Axis axis();

    @Override
    public void serialize(QasmWriter writer, int regIndex) {
        writer.rotate(axis(), QuantumCircuit.QUANTUM_REGISTRY, regIndex, piMultiplier);
    }
}
