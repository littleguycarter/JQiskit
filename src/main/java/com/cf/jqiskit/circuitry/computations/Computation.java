package com.cf.jqiskit.circuitry.computations;

import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public interface Computation<T> {
    T run(Matrix matrix);
}
