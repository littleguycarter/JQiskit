package com.cf.jqiskit.circuitry;

import java.util.Arrays;

public final class ClassicalRegistry {
    private final int size;
    private final byte[] registry;

    public ClassicalRegistry(int size) {
        this.size = size;
        this.registry = new byte[(size / 8) + 1];
    }

    /**
     true = on = 1, false = off = 0
     */
    public void setState(int index, boolean value) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        if (value) {
            registry[byteIndex] |= (byte) (1 << bitIndex);
        } else {
            registry[byteIndex] &= (byte) ~(1 << bitIndex);
        }
    }

    public boolean getMeasured(int index) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        return (registry[byteIndex] >> bitIndex & 1) != 0;
    }

    public int size() {
        return registry.length * 8;
    }

    public void clear() {
        Arrays.fill(registry, (byte) 0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            builder.append(getMeasured(i) ? "1" : "0");
        }

        return builder.toString();
    }
}
