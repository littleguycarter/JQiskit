package com.cf.jqiskit.assembly.qasm3;

import com.cf.jqiskit.assembly.QasmWriter;
import com.cf.jqiskit.circuitry.RotationGate;

public final class Qasm3Writer extends QasmWriter {
    @Override
    public QasmWriter initialize() {
        definitionsBuilder.append("OPENQASM 3.0; ");
        include("qelib1.inc");
        return this;
    }

    private void appendSingleIndexCmd(StringBuilder to, String action, String regName, int pos) {
        to
                .append(action)
                .append(" ")
                .append(regName)
                .append("[")
                .append(pos)
                .append("]; ");
    }

    private void appendDoubleIndexCmd(StringBuilder to, String action, String regName1, String regName2, int pos1, int pos2) {
        to
                .append(action)
                .append(" ")
                .append(regName1)
                .append("[")
                .append(pos1)
                .append("], ")
                .append(regName2)
                .append("[")
                .append(pos2)
                .append("]; ");
    }

    @Override
    public QasmWriter include(String pkg) {
        definitionsBuilder
                .append("include \"")
                .append(pkg)
                .append("\";")
                .append(" ");
        return this;
    }

    @Override
    public QasmWriter qreg(String name, int qubits) {
        appendSingleIndexCmd(definitionsBuilder, "qreg", name, qubits);
        return this;
    }

    @Override
    public QasmWriter creg(String name, int qubits) {
        appendSingleIndexCmd(definitionsBuilder, "creg", name, qubits);
        return this;
    }

    @Override
    public QasmWriter x(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "x", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter y(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "y", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter z(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "z", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter rotate(RotationGate.Axis axis, String qreg, int qubit, int piDivisor) {
        appendSingleIndexCmd(
                logicBuilder,
                "r" + axis.toString().toLowerCase() + "(pi/" + piDivisor + ")",
                qreg,
                qubit
        );
        return this;
    }

    @Override
    public QasmWriter h(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "h", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter s(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "s", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter sdg(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "sdg", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter t(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "t", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter tdg(String qreg, int qubit) {
        appendSingleIndexCmd(logicBuilder, "tdg", qreg, qubit);
        return this;
    }

    @Override
    public QasmWriter cx(String control, int cQubit, String target, int tQubit) {
        appendDoubleIndexCmd(logicBuilder, "cx", control, target, cQubit, tQubit);
        return this;
    }

    @Override
    public QasmWriter cz(String control, int cQubit, String target, int tQubit) {
        appendDoubleIndexCmd(logicBuilder, "cz", control, target, cQubit, tQubit);
        return this;
    }

    @Override
    public QasmWriter measure(String qreg, int qubit, String creg, int bit) {
        logicBuilder
                .append("measure ")
                .append(qreg)
                .append("[")
                .append(qubit)
                .append("] = ")
                .append(creg)
                .append("[")
                .append(bit)
                .append("];");
        return this;
    }
}
