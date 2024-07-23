package com.cf.jqiskit.util.quantum;

import com.cf.jqiskit.util.math.linear_algebra.ComplexNumber;
import com.cf.jqiskit.util.math.linear_algebra.Matrix;
import com.cf.jqiskit.util.math.MathUtil;

public final class QuantumUtil {

    public static boolean isOne(int row, int column) {
        return (Math.floor(row / (double) MathUtil.powBase2(column)) % 2) != 0;
    }

    public static byte buildStateRow(int size, int row) {
        byte state = 0;

        for (int i = 0; i < size; i++) {
            state |= (byte) (isOne(row, i) ? 1 << i : 0);
        }

        return state;
    }

    public static Matrix buildZeroState(int qubits) {
        ComplexNumber[] data = new ComplexNumber[MathUtil.powBase2(qubits)];
        data[0] = ComplexNumber.ofReal(1);

        return Matrix.columnVector(data);
    }
}
