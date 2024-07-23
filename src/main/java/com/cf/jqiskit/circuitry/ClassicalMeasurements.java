package com.cf.jqiskit.circuitry;

public record ClassicalMeasurements(int size, byte[] registry) {
    public boolean getMeasured(int index) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        return (registry[byteIndex] >> bitIndex & 1) != 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            builder.append(getMeasured(i) ? "1" : "0");
        }

        return builder.toString();
    }

    public static class Builder {
        private final int size;
        private final byte[] bytes;

        public Builder(int size) {
            this.size = size;
            this.bytes = new byte[(size / 8) + 1];
        }

        public Builder on(int index) {
            bytes[index / 8] |= (byte) (1 << (index % 8));
            return this;
        }

        public ClassicalMeasurements build() {
            return new ClassicalMeasurements(size, bytes);
        }
    }
}
