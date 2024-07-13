package com.cf.jqiskit.exceptions;

import com.cf.jqiskit.ibm.IBMError;

import java.util.Set;

public class IBMException extends RuntimeException {
    public IBMException(Set<IBMError> errors) {
        super(errors.stream().reduce("", (acc, error) -> acc + error.toString() + "\n", String::concat));
    }
}
