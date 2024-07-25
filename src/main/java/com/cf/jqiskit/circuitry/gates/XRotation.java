package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.RotationGate;
import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class XRotation extends RotationGate {
    private final Matrix operation;

    public XRotation(int piMultiplier) {
        super(piMultiplier);

        double angle = Math.PI * piMultiplier;
        double cos = Math.cos(angle / 2);
        double sin = Math.sin(angle / 2);

        this.operation = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
            ComplexNumber.ofReal(cos), ComplexNumber.ofImaginary(-sin),
            ComplexNumber.ofImaginary(-sin), ComplexNumber.ofReal(cos)
        });
    }

    @Override
    public Axis axis() {
        return Axis.X;
    }

    @Override
    public Matrix matrix() {
        return operation;
    }
}
