package com.cf.jqiskit.util.math.linear_algebra;

public final class Matrix {
    private final byte rows;
    private final byte columns;
    private final ComplexNumber[] data;

    public static boolean isColumnVector(Matrix matrix) {
        return matrix.rows() != 1 && matrix.columns() == 1;
    }

    public static boolean isSingleton(Matrix matrix) {
        return matrix.columns() == 1 && matrix.rows() == 1;
    }

    public static Matrix identity(byte size) {
        ComplexNumber[] data = new ComplexNumber[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i * size + j] = i == j ? ComplexNumber.ofReal(1) : ComplexNumber.empty();
            }
        }

        return new Matrix(size, size, data);
    }

    public static Matrix empty(byte rows, byte columns) {
        ComplexNumber[] data = new ComplexNumber[rows * columns];
        return new Matrix(rows, columns, data);
    }

    public static Matrix columnVector(ComplexNumber... data) {
        return new Matrix((byte) data.length, (byte) 1, data);
    }

    public static Matrix singleton(ComplexNumber number) {
        return new Matrix((byte) 1, (byte) 1, new ComplexNumber[] { number });
    }

    public Matrix(byte rows, byte columns, ComplexNumber[] data) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Matrix must have at least 1 row and 1 column!");
        }

        if (data.length != (rows * columns)) {
            throw new IllegalArgumentException("Matrix length does not match specified size parameters!");
        }

        fillNulls(data);

        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }

    private void fillNulls(ComplexNumber[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                data[i] = ComplexNumber.empty();
            }
        }
    }

    public ComplexNumber get(int row, int column) {
        return data[row * columns + column];
    }

    public Matrix add(Matrix other) {
        if (rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to add!");
        }

        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i].add(other.data[i]);
        }

        return new Matrix(rows, columns, newData);
    }

    public Matrix subtract(Matrix other) {
        if (rows != other.rows || columns != other.columns) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to subtract!");
        }

        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i].subtract(other.data[i]);
        }

        return new Matrix(rows, columns, newData);
    }

    public Matrix multiply(Matrix other) {
        if (columns != other.rows) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication!");
        }

        ComplexNumber[] newData = new ComplexNumber[rows * other.columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.columns; j++) {
                ComplexNumber sum = ComplexNumber.empty();

                for (int k = 0; k < columns; k++) {
                    sum = sum.add(get(i, k).multiply(other.get(k, j)));
                }

                newData[i * other.columns + j] = sum;
            }
        }

        return new Matrix(rows, other.columns, newData);
    }

    public Matrix multiply(ComplexNumber scalar) {
        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i].multiply(scalar);
        }

        return new Matrix(rows, columns, newData);
    }

    public Matrix transpose() {
        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[j * rows + i] = get(i, j);
            }
        }

        return new Matrix(columns, rows, newData);
    }

    public Matrix conjugate() {
        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i].conjugate();
        }

        return new Matrix(rows, columns, newData);
    }

    public Matrix adjoint() {
        ComplexNumber[] newData = new ComplexNumber[data.length];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[j * rows + i] = get(i, j).conjugate();
            }
        }

        return new Matrix(columns, rows, newData);
    }

    public Matrix tensor(Matrix other) {
        ComplexNumber[] newData = new ComplexNumber[rows * other.rows * columns * other.columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < other.rows; k++) {
                    for (int l = 0; l < other.columns; l++) {
                        newData[(i * other.rows + k) * (columns * other.columns) + (j * other.columns + l)] = get(i, j).multiply(other.get(k, l));
                    }
                }
            }
        }

        return new Matrix((byte) (rows * other.rows), (byte) (columns * other.columns), newData);
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            builder.append(data[i]);

            if ((i + 1) % columns == 0) {
                builder.append("\n");
            } else {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Matrix matrix)) {
            return false;
        }

        if (rows != matrix.rows || columns != matrix.columns) {
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            if (!data[i].equals(matrix.data[i])) {
                return false;
            }
        }

        return true;
    }

    public Matrix cloneMatrix() {
        ComplexNumber[] newData = new ComplexNumber[data.length];
        System.arraycopy(data, 0, newData, 0, data.length);
        return new Matrix(rows, columns, newData);
    }
}
