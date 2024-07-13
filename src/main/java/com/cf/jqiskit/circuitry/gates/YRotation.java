package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.circuitry.RotationGate;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;
import com.cf.jqiskit.circuitry.QuantumGate;

public class YRotation extends RotationGate {
    private final Matrix operation;

    public YRotation(int piDivisor) {
        super(piDivisor);

        double angle = Math.PI / piDivisor;
        double cos = Math.cos(angle / 2);
        double sin = Math.sin(angle / 2);

        this.operation = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
            ComplexNumber.ofReal(cos), ComplexNumber.ofImaginary(-sin),
            ComplexNumber.ofImaginary(sin), ComplexNumber.ofReal(cos)
        });
    }

    @Override
    public Matrix matrix() {
        return operation;
    }

    @Override
    public String qasmIdentifier() {
        return "ry";
    }

    @Override
    public int acceptedQubits() {
        return 1;
    }
}
