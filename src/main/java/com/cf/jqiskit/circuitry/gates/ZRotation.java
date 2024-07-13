package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.RotationGate;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;

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
    public String qasmIdentifier() {
        return "rz";
    }

    @Override
    public Matrix matrix() {
        return operation;
    }
}
