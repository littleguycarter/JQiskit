package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.QuantumGate;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class Identity implements QuantumGate {
    private final Matrix identity;

    public Identity(int size) {
        this.identity = Matrix.identity((byte) size);
    }

    @Override
    public Matrix matrix() {
        return identity;
    }
}
