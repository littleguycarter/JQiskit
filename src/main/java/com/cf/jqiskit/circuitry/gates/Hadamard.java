package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.assembly.QasmBuilder;
import com.cf.jqiskit.util.MathUtil;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;
import com.cf.jqiskit.circuitry.QuantumGate;

public class Hadamard implements QuantumGate {
    private static final Matrix OPERATION = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
        ComplexNumber.ofReal(1/ MathUtil.SQRT2), ComplexNumber.ofReal(1/MathUtil.SQRT2),
        ComplexNumber.ofReal(1/MathUtil.SQRT2), ComplexNumber.ofReal(-1/MathUtil.SQRT2)
    });

    @Override
    public Matrix matrix() {
        return OPERATION;
    }

    @Override
    public int acceptedQubits() {
        return 1;
    }

    @Override
    public void toQasmCommand(QasmBuilder script, int targetRegistry) {
        script.addStep("h q[" + targetRegistry + "];");
    }
}
