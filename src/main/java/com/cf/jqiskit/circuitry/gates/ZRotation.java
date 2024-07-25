package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.RotationGate;
import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class ZRotation extends RotationGate {
    private final Matrix operation;

    public ZRotation(float piMultiplier) {
        super(piMultiplier);

        double angle = Math.PI * piMultiplier;
        this.operation = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
                ComplexNumber.ofPolar(1, -angle/2), ComplexNumber.empty(),
                ComplexNumber.empty(), ComplexNumber.ofPolar(1, angle/2)
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
