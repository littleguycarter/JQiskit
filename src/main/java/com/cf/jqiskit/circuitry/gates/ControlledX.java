package com.cf.jqiskit.circuitry.gates;

import com.cf.jqiskit.assembly.QasmBuilder;
import com.cf.jqiskit.util.MathUtil;
import com.cf.jqiskit.util.QuantumUtil;
import com.cf.jqiskit.util.matrix.ComplexNumber;
import com.cf.jqiskit.util.matrix.Matrix;
import com.cf.jqiskit.circuitry.QuantumGate;

public class ControlledX implements QuantumGate {
    private final int targetDistFromControl;
    private final int qubitRows;
    private final Matrix operation;

    public ControlledX(int targetDistFromControl) {
        this.targetDistFromControl = targetDistFromControl;

        int absDist = Math.abs(targetDistFromControl);
        this.qubitRows = absDist + 1;
        int size = MathUtil.powBase2(qubitRows);

        ComplexNumber[] data = new ComplexNumber[size * size];

        for (int i = 0; i < size; i++) {
            byte stateRow = QuantumUtil.buildStateRow(size, i);

            // keep in mind that in braket form, the least significant bit represents the last qubit, hence:
            // the target qubit is position of the control bit, and the control qubit is the position of the target bit
            // the condition of the below if statement is thus checking the control bit
            if ((stateRow & (1 << (targetDistFromControl < 0 ? 0 : absDist))) != 0) {
                // the bit shift here is thus shifting by the position of the target bit
                stateRow ^= (byte) (1 << (targetDistFromControl < 0 ? absDist : 0));
            }

            data[(i * size) + stateRow] = ComplexNumber.ofReal(1);
        }

        this.operation = new Matrix((byte) size, (byte) size, data);
    }

    @Override
    public Matrix matrix() {
        return operation;
    }

    @Override
    public int acceptedQubits() {
        return qubitRows;
    }

    @Override
    public void toQasmCommand(QasmBuilder script, int targetRegistry) {
        script.addStep("cx q[" + (targetRegistry - Math.min(targetDistFromControl, 0)) + "], q[" + (targetRegistry + Math.max(targetDistFromControl, 0)) + "];");
    }
}
