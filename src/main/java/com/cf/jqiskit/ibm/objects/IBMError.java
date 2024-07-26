package com.cf.jqiskit.ibm.objects;

public record IBMError(String code, String message, String solution, String moreInfo) {

    @Override
    public String toString() {
        return "Error Code " + code + ": " + message + "\nSolution: " + solution + "\nMore Info: " + moreInfo + "\n";
    }
}
