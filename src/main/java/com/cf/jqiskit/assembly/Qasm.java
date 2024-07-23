package com.cf.jqiskit.assembly;

import com.cf.jqiskit.assembly.qasm3.Qasm3;
import com.cf.jqiskit.util.general.LazyInit;
import com.google.common.collect.ImmutableMap;

public interface Qasm {
    ImmutableMap<Float, LazyInit<? extends Qasm>> VERSIONS = ImmutableMap.<Float, LazyInit<? extends Qasm>>builder()
            .put(3.0f, new LazyInit<>(Qasm3.class))
            .build();

    QasmWriter newWriter();
}
