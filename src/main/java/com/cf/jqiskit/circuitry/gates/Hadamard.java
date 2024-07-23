package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.assembly.QasmWriter;
import com.cf.jqiskit.circuitry.SerializableGate;
import com.cf.jqiskit.circuitry.circuits.QuantumCircuit;
import com.cf.jqiskit.util.math.MathUtil;
import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class Hadamard implements SerializableGate {
    private static final Matrix OPERATION = new Matrix((byte) 2, (byte) 2, new ComplexNumber[] {
        ComplexNumber.ofReal(1/ MathUtil.SQRT2), ComplexNumber.ofReal(1/MathUtil.SQRT2),
        ComplexNumber.ofReal(1/MathUtil.SQRT2), ComplexNumber.ofReal(-1/MathUtil.SQRT2)
    });

    @Override
    public Matrix matrix() {
        return OPERATION;
    }

    @Override
    public void serialize(QasmWriter writer, int regIndex) {
        writer.h(QuantumCircuit.QUANTUM_REGISTRY, regIndex);
    }
}
