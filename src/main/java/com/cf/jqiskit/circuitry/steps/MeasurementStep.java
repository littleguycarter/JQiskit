package com.cf.jqiskit.circuitry.steps;

import com.cf.jqiskit.assembly.QasmBuilder;
import com.cf.jqiskit.circuitry.ClassicalRegistry;
import com.cf.jqiskit.util.QuantumUtil;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;
import com.cf.jqiskit.circuitry.CircuitStep;

import java.util.Random;

/*
    Wave collapse using Java's random
 */
public abstract class MeasurementStep implements CircuitStep {
    public record Details(ClassicalRegistry registry, int qIndex, int cIndex) {
        public Details {
            if (registry.size() <= cIndex) {
                throw new IllegalArgumentException("Classical registry is too small for the specified classical index!");
            }
        }
    }

    private static final Random MEASUREMENT_RANDOM = new Random();

    protected abstract Details details();

    @Override
    public Matrix apply(Matrix matrix) {
        Details details = details();
        int rows = matrix.rows();
        double percentage = MEASUREMENT_RANDOM.nextDouble();

        double sum = 0;
        for (int i = 0; i < rows; i++) {
            ComplexNumber element = matrix.get(i, 0);

            if (element.isEmpty()) {
                continue;
            }

            float probability = (float) element.squaredMagnitude();

            if (sum <= percentage && percentage < sum + probability) {
                ComplexNumber[] data = new ComplexNumber[rows];
                data[i] = ComplexNumber.ofReal(1);
                Matrix result = new Matrix((byte) rows, (byte) 1, data);

                details.registry().setState(details.cIndex(), QuantumUtil.isOne(i, details.qIndex()));

                return result;
            }

            sum += probability;
        }

        throw new RuntimeException("Quantum gate measurement failed...");
    }

    @Override
    public void validateCompatibility(int qubits) {
        int qIndex = details().qIndex();

        if (qIndex >= qubits) {
            throw new IllegalArgumentException("Measurement index is out of bounds of the number of qubits in the circuit! (" + qIndex + " >= " + qubits + ")");
        }
    }

    @Override
    public void serialize(QasmBuilder script) {
        //TODO: add support for openqasm 2.0
        //"measure q[" + details.qIndex() + "] -> c[" + details.cIndex() + "];"
        Details details = details();
        script.addStep("c[" + details.cIndex() + "] = measure q[" + details.qIndex() + "];");
    }

    @Override
    public boolean isFixed() {
        return false;
    }
}
