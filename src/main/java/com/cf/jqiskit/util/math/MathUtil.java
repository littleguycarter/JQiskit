package com.cf.jqiskit.util.math;

public final class MathUtil {
    public static final double SQRT2 = Math.sqrt(2);

    public static int powBase2(int exponent) {
        return 1 << exponent;
    }

    public static int logBase2(int whole) {
        int i = 0;
        while (true) {
            if (i > 50) {
                throw new IllegalArgumentException("logBase2 passed 50 iterations! Invalid whole number provided.");
            }

            if ((whole & ~(1 << i)) == 0) {
                return i;
            }

            i++;
        }
    }
}
