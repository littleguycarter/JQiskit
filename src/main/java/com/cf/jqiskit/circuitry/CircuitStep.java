package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.assembly.QasmSerializable;
import com.cf.jqiskit.util.matrix.Matrix;

public interface CircuitStep extends QasmSerializable {
    Matrix apply(Matrix quantumState);

    void validateCompatibility(int qubits);

    boolean isFixed();
}
