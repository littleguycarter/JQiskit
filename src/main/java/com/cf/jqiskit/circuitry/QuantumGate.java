package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.circuitry.gates.Hadamard;
import com.cf.jqiskit.circuitry.gates.XRotation;
import com.cf.jqiskit.circuitry.gates.YRotation;
import com.cf.jqiskit.circuitry.gates.ZRotation;
import com.cf.jqiskit.util.math.MathUtil;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public interface QuantumGate {
    QuantumGate H = new Hadamard();
    QuantumGate X = new XRotation(1);
    QuantumGate Y = new YRotation(1);
    QuantumGate Z = new ZRotation(2);

    Matrix matrix();

    default int qubits() {
        return MathUtil.logBase2(matrix().rows());
    }
}
