package com.cf.jqiskit.circuitry;

import com.cf.jqiskit.assembly.QasmWriter;

public interface SerializableGate extends QuantumGate {
    void serialize(QasmWriter writer, int regIndex);
}
