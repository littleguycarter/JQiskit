package com.cf.jqiskit.assembly;

import com.cf.jqiskit.circuitry.RotationGate;

public abstract class QasmWriter {
    protected final StringBuilder definitionsBuilder;
    protected final StringBuilder logicBuilder;

    public QasmWriter() {
        this.definitionsBuilder = new StringBuilder();
        this.logicBuilder = new StringBuilder();
    }

    public abstract QasmWriter initialize();

    public abstract QasmWriter include(String pkg);

    public abstract QasmWriter qreg(String name, int qubits);

    public abstract QasmWriter creg(String name, int qubits);

    public abstract QasmWriter x(String qreg, int qubit);

    public abstract QasmWriter y(String qreg, int qubit);

    public abstract QasmWriter z(String qreg, int qubit);

    public abstract QasmWriter rotate(RotationGate.Axis axis, String qreg, int qubit, float piMultiplier);

    public abstract QasmWriter h(String qreg, int qubit);

    public abstract QasmWriter s(String qreg, int qubit);

    public abstract QasmWriter sdg(String qreg, int qubit);

    public abstract QasmWriter t(String qreg, int qubit);

    public abstract QasmWriter tdg(String qreg, int qubit);

    public abstract QasmWriter cx(String qreg1, int qubit1, String qreg2, int qubit2);

    public abstract QasmWriter cz(String qreg1, int qubit1, String qreg2, int qubit2);

    public abstract QasmWriter measure(String qreg, int qubit, String creg, int bit);

    @Override
    public String toString() {
        return definitionsBuilder.toString() + logicBuilder;
    }
}
