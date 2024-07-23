package com.cf.jqiskit.circuitry.circuits;

import com.cf.jqiskit.assembly.Qasm;
import com.cf.jqiskit.assembly.QasmWriter;
import com.cf.jqiskit.circuitry.QuantumGate;
import com.cf.jqiskit.circuitry.SerializableGate;
import com.cf.jqiskit.circuitry.gates.Identity;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class QuantumCircuit {
    public static final String QUANTUM_REGISTRY = "q";
    public static final String CLASSICAL_REGISTRY = "c";

    private final int qubits;
    private final Matrix instruction;
    private final String qasm;

    private QuantumCircuit(int qubits, Matrix instruction, String qasm) {
        this.qubits = qubits;
        this.instruction = instruction;
        this.qasm = qasm;
    }

    public String qasm() {
        return qasm;
    }

    public Matrix instruction() {
        return instruction;
    }

    public int qubits() {
        return qubits;
    }

    public static final class Compiler {
        private final int qubits;
        private final QasmWriter writer;

        private Matrix instruction;
        private Matrix step;
        private int currentQubit;

        public Compiler(int qubits, float qasmVersion) {
            this.qubits = qubits;
            this.writer = Qasm.VERSIONS.get(qasmVersion).instance()
                    .newWriter()
                    .initialize()
                    .qreg(QUANTUM_REGISTRY, qubits)
                    .creg(CLASSICAL_REGISTRY, qubits);

            this.instruction = null;
            resetStep();
        }

        private void resetStep() {
            this.step = null;
            this.currentQubit = 0;
        }

        public Compiler gate(QuantumGate gate) {
            if (currentQubit + gate.qubits() > qubits) {
                throw new IllegalStateException("Cannot append " + gate.getClass().getSimpleName() + " to QuantumCircuit: step exceeds " + qubits + " qubits!");
            }

            step = step == null ? gate.matrix() : step.tensor(gate.matrix());

            if (gate instanceof SerializableGate serializableGate) {
                serializableGate.serialize(writer, currentQubit);
            }

            currentQubit += gate.qubits();

            if (currentQubit == qubits) {
                this.instruction = instruction == null ? step : instruction.multiply(step);
                resetStep();
            }

            return this;
        }

        public Compiler empty() {
            return gate(new Identity(2));
        }

        public QuantumCircuit compile() {
            return new QuantumCircuit(qubits, instruction, writer.toString());
        }
    }
}
