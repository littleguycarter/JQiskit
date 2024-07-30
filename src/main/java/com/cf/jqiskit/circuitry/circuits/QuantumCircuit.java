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

    public Matrix transform(Matrix state) {
        if (state.rows() != instruction.rows()) {
            throw new IllegalArgumentException("Provided state's dimensions are incompatible with the circuit's instruction matrix dimensions.");
        }

        return instruction.multiply(state);
    }

    public static final class Compiler {
        private final int qubits;
        private final int bits;
        private final QasmWriter writer;

        private Matrix instruction;
        private Matrix step;
        private int currentQubit;

        public Compiler(int qubits, int bits, float qasmVersion) {
            this.qubits = qubits;
            this.bits = bits;
            this.writer = Qasm.VERSIONS.get(qasmVersion).name()
                    .newWriter()
                    .initialize()
                    .qreg(QUANTUM_REGISTRY, qubits)
                    .creg(CLASSICAL_REGISTRY, bits);

            this.instruction = null;
            nextStep();
        }

        private void nextStep() {
            this.step = null;
            this.currentQubit = 0;
        }

        public Compiler gate(QuantumGate gate) {
            int gateQubits = gate.qubits();

            if (gateQubits > qubits) {
                throw new IllegalArgumentException("Gate qubit requirement exceeds the size of the circuit!");
            }

            if (gateQubits != 1 && currentQubit + gateQubits > qubits) {
                for (int i = currentQubit; i < qubits; i++) {
                    step = step.tensor(QuantumGate.I.matrix());
                }
            } else {
                step = step == null ? gate.matrix() : step.tensor(gate.matrix());

                if (gate instanceof SerializableGate serializableGate) {
                    serializableGate.serialize(writer, currentQubit);
                }

                currentQubit += gateQubits;

                if (currentQubit != qubits) {
                    return this;
                }
            }

            this.instruction = instruction == null ? step : step.multiply(instruction);
            nextStep();

            return this;
        }

        public Compiler skip() {
            return gate(new Identity(2));
        }

        public Compiler measure(int qreg, int creg) {
            if (qreg < 0 || qreg >= qubits) {
                throw new IndexOutOfBoundsException("Specified qreg must be between 0 and" + qubits);
            }

            if (creg < 0 || creg >= bits) {
                throw new IndexOutOfBoundsException("Specified creg must be between 0 and" + bits);
            }

            writer.measure(QUANTUM_REGISTRY, qreg, CLASSICAL_REGISTRY, creg);
            return this;
        }

        public QuantumCircuit compile() {
            return new QuantumCircuit(qubits, instruction, writer.toString());
        }
    }
}
