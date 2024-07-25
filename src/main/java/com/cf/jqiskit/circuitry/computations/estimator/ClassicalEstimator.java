package com.cf.jqiskit.circuitry.computations.estimator;

import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;

public class ClassicalEstimator implements Estimator {
    private final Matrix observable;

    public ClassicalEstimator(Matrix observable) {
        if (!observable.isSquare()) {
            throw new IllegalArgumentException("Must provide a square matrix observable!");
        }

        this.observable = observable;
    }

    @Override
    public ComplexNumber run(Matrix state) {
        if (!state.isColumnVector()) {
            throw new IllegalArgumentException("Must provide a valid quantum state (column vector)");
        }

        if (state.rows() != observable.columns()) {
            throw new IllegalArgumentException("Observable and state dimensions are incompatible: state rows = " + state.rows() + ", observable columns = " + observable.columns());
        }

        Matrix result = state.adjoint().multiply(observable.multiply(state));

        if (!result.isSingleton()) {
            throw new RuntimeException("Error occurred while estimating: singleton matrix not provided. This should never happen!");
        }

        return result.get(0, 0);
    }
}
