package com.cf.jqiskit.circuitry.circuits;

import com.cf.jqiskit.assembly.QasmBuilder;
import com.cf.jqiskit.circuitry.CircuitStep;
import com.cf.jqiskit.circuitry.ClassicalRegistry;
import com.cf.jqiskit.circuitry.QuantumGate;
import com.cf.jqiskit.circuitry.steps.GateStep;
import com.cf.jqiskit.circuitry.steps.MeasurementStep;
import com.cf.jqiskit.util.MathUtil;
import com.cf.jqiskit.util.QuantumUtil;
import com.cf.jqiskit.util.matrix.Matrix;

public class QuantumCircuit {
    private final int qubits;
    private final CircuitStep[] steps;
    private final ClassicalRegistry classicalRegistry;
    private final String qasm;

    QuantumCircuit(int qubits, CircuitStep[] steps, ClassicalRegistry classicalRegistry, String qasm) {
        this.qubits = qubits;
        this.steps = steps;
        this.classicalRegistry = classicalRegistry;
        this.qasm = qasm;
    }

    public Matrix run(Matrix initialState) {
        if (initialState.rows() != MathUtil.powBase2(qubits)) {
            throw new RuntimeException("Initial state must have 2^qubits rows.");
        }

        Matrix netState = initialState;

        for (CircuitStep step : steps) {
            netState = step.apply(netState);
        }

        return netState;
    }

    public Matrix run() {
        return run(QuantumUtil.buildZeroState(qubits));
    }

    public int qubits() {
        return qubits;
    }

    public ClassicalRegistry classicalRegistry() {
        return classicalRegistry;
    }

    public String qasm() {
        return qasm;
    }

    public static final class Builder {
        private final int qubits;
        private final ClassicalRegistry registry;
        private final CircuitStep[] stepsBuilder;
        private final QasmBuilder qasmBuilder;
        private int currentStep;
        private boolean fixed;

        public Builder(int qubits, int steps, float qasmVersion) {
            this.qubits = qubits;
            this.registry = new ClassicalRegistry(qubits);
            this.stepsBuilder = new CircuitStep[steps];
            this.qasmBuilder = new QasmBuilder(qasmVersion, qubits);
            this.currentStep = 0;
            this.fixed = true;
        }

        public Builder addStep(CircuitStep step) {
            step.validateCompatibility(qubits);

            if (!step.isFixed()) {
                fixed = false;
            }

            stepsBuilder[currentStep] = step;
            currentStep++;
            return this;
        }

        public Builder addResizableGateStep(QuantumGate... gates) {
            int qubits = 0;

            for (QuantumGate gate : gates) {
                qubits += gate == null ? 1 : gate.acceptedQubits();
            }

            QuantumGate[] officialGates = new QuantumGate[gates.length + (this.qubits - qubits)];
            System.arraycopy(gates, 0, officialGates, 0, gates.length);

            return addGateStep(officialGates);
        }

        public Builder addGateStep(QuantumGate... gates) {
            return addStep(new GateStep(gates));
        }

        public Builder measureAll() {
            this.fixed = false;

            for (int i = 0; i < qubits; i++) {
                MeasurementStep.Details details = new MeasurementStep.Details(registry, i, i);

                stepsBuilder[currentStep] = new MeasurementStep() {
                    @Override
                    protected Details details() {
                        return details;
                    }
                };

                currentStep++;
            }

            return this;
        }

        public QuantumCircuit build() {
            for (CircuitStep step : stepsBuilder) {
                step.serialize(qasmBuilder);
            }

            String qasm = qasmBuilder.toString();
            return fixed ? new CachedRunQuantumCircuit(qubits, stepsBuilder, registry, qasm) : new QuantumCircuit(qubits, stepsBuilder, registry, qasm);
        }
    }
}
