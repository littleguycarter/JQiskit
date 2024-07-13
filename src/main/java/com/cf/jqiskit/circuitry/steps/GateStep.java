package com.cf.jqiskit.circuitry.steps;

import com.cf.jqiskit.assembly.QasmBuilder;
import com.cf.jqiskit.util.MathUtil;
import com.cf.jqiskit.util.matrix.Matrix;
import com.cf.jqiskit.circuitry.CircuitStep;
import com.cf.jqiskit.circuitry.QuantumGate;

public final class GateStep implements CircuitStep {
    private final QuantumGate[] gates;
    private final Matrix cachedMatrix;

    public GateStep(QuantumGate... gates) {
        this.gates = gates;
        this.cachedMatrix = buildMatrix();
    }

    private Matrix buildMatrix() {
        Matrix result = null;

        for (QuantumGate gate : gates) {
            Matrix matrix = gate == null ? Matrix.identity((byte) 2) : gate.matrix();

            if (result == null) {
                result = matrix;
                continue;
            }

            result = result.tensor(matrix);
        }

        return result;
    }

    @Override
    public Matrix apply(Matrix quantumState) {
        return cachedMatrix.multiply(quantumState);
    }

    @Override
    public void validateCompatibility(int qubits) {
        if (MathUtil.powBase2(qubits) != cachedMatrix.columns()) {
            throw new RuntimeException("GateStep of matrix size " + cachedMatrix.columns() + " is not compatible with the number of qubits in the circuit (" + qubits + "). 2^(qubits) must match the size.");
        }
    }

    @Override
    public boolean isFixed() {
        return true;
    }

    @Override
    public void serialize(QasmBuilder script) {
        for (int i = 0; i < gates.length; i++) {
            QuantumGate gate = gates[i];

            if (gate == null) {
                continue;
            }

            gate.toQasmCommand(script, i);
        }
    }
}
