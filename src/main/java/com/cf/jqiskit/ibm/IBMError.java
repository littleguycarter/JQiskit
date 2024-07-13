package com.cf.jqiskit.ibm;

public record IBMError(int code, String message, String solution, String moreInfo) {

    @Override
    public String toString() {
        return "Error Code " + code + ": " + message + "\nSolution: " + solution + "\nMore Info: " + moreInfo + "\n";
    }
}
