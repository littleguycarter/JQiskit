package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.RotationGate;
import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class ZRotation extends RotationGate {
    private final Matrix operation;

    public ZRotation(int piDivisor) {
        super(piDivisor);

        double angle = Math.PI / piDivisor;
        double cos = Math.cos(angle / 2);
        double sin = Math.sin(angle / 2);

        this.operation = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
            ComplexNumber.ofReal(cos), ComplexNumber.empty(),
            ComplexNumber.empty(), ComplexNumber.ofImaginary(sin)
        });
    }

    @Override
    public Axis axis() {
        return Axis.Z;
    }

    @Override
    public Matrix matrix() {
        return operation;
    }
}
