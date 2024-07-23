package com.cf.jqiskit.util.math.linear_algebra;

public final class ComplexNumber implements Cloneable {
    private static final double EPSILON = 1E-10;

    private final double real;
    private final double imaginary;

    public static ComplexNumber of(double real, double imaginary) {
        return new ComplexNumber(real, imaginary);
    }

    public static ComplexNumber ofReal(double real) {
        return new ComplexNumber(real, 0);
    }

    public static ComplexNumber ofImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    public static ComplexNumber ofPolar(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    public static ComplexNumber empty() {
        return new ComplexNumber(0, 0);
    }

    private ComplexNumber(double real, double imaginary) {
        this.real = removeUncertainty(real);
        this.imaginary = removeUncertainty(imaginary);
    }

    private double removeUncertainty(double value) {
        return Math.abs(value) < EPSILON ? 0 : value;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(real + other.real, imaginary + other.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(real - other.real, imaginary - other.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(real * other.real - imaginary * other.imaginary, real * other.imaginary + imaginary * other.real);
    }

    public ComplexNumber negate() {
        return new ComplexNumber(-real, -imaginary);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imaginary);
    }

    public double magnitude() {
        return Math.sqrt(squaredMagnitude());
    }

    public double squaredMagnitude() {
        return real * real + imaginary * imaginary;
    }

    public double angle() {
        return Math.atan2(imaginary, real);
    }

    public boolean isReal() {
        return imaginary == 0;
    }

    public boolean isImaginary() {
        return real == 0;
    }

    public boolean isEmpty() {
        return real == 0 && imaginary == 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ComplexNumber complexNumber)) {
            return false;
        }

        return real == complexNumber.real && imaginary == complexNumber.imaginary;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(real) + Double.hashCode(imaginary);
    }

    @Override
    public String toString() {
        if (real == 0 && imaginary == 0) {
            return "0";
        }

        if (real == 0) {
            return imaginary + "i";
        }

        if (imaginary == 0) {
            return real + "";
        }

        if (imaginary < 0) {
            return real + " - " + -imaginary + "i";
        }

        return real + " + " + imaginary + "i";
    }

    @Override
    public ComplexNumber clone() {
        try {
            return (ComplexNumber) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }
}
