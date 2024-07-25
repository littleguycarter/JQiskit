package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.circuitry.gates.*;
import com.cf.jqiskit.util.math.MathUtil;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public interface QuantumGate {
    QuantumGate H = new Hadamard();
    QuantumGate X = new XRotation(1);
    QuantumGate Y = new YRotation(1);
    QuantumGate Z = new ZRotation(1);
    QuantumGate I = new Identity(2);

    Matrix matrix();

    default int qubits() {
        return MathUtil.logBase2(matrix().rows());
    }
}
