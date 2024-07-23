package com.cf.jqiskit.assembly.qasm3;

import com.cf.jqiskit.assembly.Qasm;

public final class Qasm3 implements Qasm {
    @Override
    public Qasm3Writer newWriter() {
        return new Qasm3Writer();
    }
}
