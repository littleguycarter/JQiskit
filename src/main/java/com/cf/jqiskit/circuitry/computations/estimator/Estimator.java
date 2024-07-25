package com.cf.jqiskit.circuitry.computations.estimator;

import com.cf.jqiskit.circuitry.computations.Computation;
import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public interface Estimator extends Computation<ComplexNumber> {
    ComplexNumber run(Matrix state);
}
