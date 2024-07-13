package com.cf.jqiskit.circuitry.circuits;

import com.cf.jqiskit.circuitry.CircuitStep;
import com.cf.jqiskit.circuitry.ClassicalRegistry;
import com.cf.jqiskit.util.MathUtil;
import com.cf.jqiskit.util.QuantumUtil;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;

public class CachedRunQuantumCircuit extends QuantumCircuit {
    private Matrix cachedResult;

    CachedRunQuantumCircuit(int qubits, CircuitStep[] steps, ClassicalRegistry classicalRegistry, String qasm) {
        super(qubits, steps, classicalRegistry, qasm);
    }

    @Override
    public Matrix run(Matrix initialState) {
        if (cachedResult == null) {
            this.cachedResult = super.run(initialState);
        }

        return cachedResult;
    }

    //TODO: probably want to move these expectation value calculations out of the circuit itself and into a separate utility class

    public ComplexNumber expectationValue(Matrix initial) {
        if (initial.rows() != MathUtil.powBase2(qubits())) {
            throw new RuntimeException("Initial state must have 2^qubits rows.");
        }

        Matrix intermediateState = run(initial);
        Matrix finalState = initial.adjoint().multiply(intermediateState);

        if (finalState.rows() != 1 || finalState.columns() != 1) {
            throw new RuntimeException("Something went wrong with the expectation value calculation, as the final state is not a 1x1 matrix.");
        }

        return finalState.get(0, 0);
    }

    public ComplexNumber expectationValue() {
        return expectationValue(QuantumUtil.buildZeroState(qubits()));
    }
}
