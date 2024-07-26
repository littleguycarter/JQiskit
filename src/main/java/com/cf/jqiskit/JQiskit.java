package com.cf.jqiskit;

import com.cf.jqiskit.assembly.Qasm;
import com.cf.jqiskit.circuitry.QuantumGate;
import com.cf.jqiskit.circuitry.circuits.QuantumCircuit;
import com.cf.jqiskit.circuitry.gates.ControlledX;
import com.cf.jqiskit.exceptions.IBMException;
import com.cf.jqiskit.ibm.IBMHttpRequest;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.BackendStatusEndpoint;
import com.cf.jqiskit.ibm.objects.BackendStatus;
import com.cf.jqiskit.ibm.objects.IBMError;
import com.cf.jqiskit.ibm.responses.IBMObjectResponse;
import com.cf.jqiskit.ibm.responses.IBMResponse;
import com.cf.jqiskit.io.response.Response;
import com.cf.jqiskit.gson_adapters.JsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;

public final class JQiskit {
    private static final String ADAPTER_PACKAGE = "com.cf.jqiskit.gson_adapters";
    public static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls();
        Reflections reflections = new Reflections(ADAPTER_PACKAGE);

        reflections.getSubTypesOf(JsonAdapter.class).forEach(clazz -> {
            try {
                JsonAdapter<?> adapter = clazz.getDeclaredConstructor().newInstance();
                builder.registerTypeAdapter(adapter.getTargetClass(), adapter);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        GSON = builder.create();
    }

    public static JQiskit from(String apiToken) {
        IBMResponse response = new IBMResponse();

        try {
            new IBMHttpRequest(new IBMRequestInfo(IBMEndpoint.USER), apiToken).populate(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.getStatus() == 200) {
            return new JQiskit(apiToken);
        }

        throw new IBMException(response.getErrors());
    }

    private final String apiToken;

    private JQiskit(String apiToken) {
        this.apiToken = apiToken;
    }

    public void request(IBMRequestInfo info, Response response) throws IOException {
        new IBMHttpRequest(info, apiToken).populate(response);
    }

    public <T extends Response> CompletableFuture<T> request(IBMRequestInfo info, T response, ThreadPoolExecutor executor) {
        CompletableFuture<T> future = new CompletableFuture<>();

        executor.submit(() -> {
            try {
                request(info, response);
                future.complete(response);
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    // TEMPORARY
    /*public static void main(String[] args) throws IOException {
        JQiskit instance = JQiskit.from("REDACTED");
        Backend backend = Backend.from("ibm_brisbane", instance);
        System.out.println(backend.status().status());

        QuantumGate X = QuantumGate.X;
        QuantumGate Y = QuantumGate.Y;
        QuantumGate Z = QuantumGate.Z;
        QuantumGate H = QuantumGate.H;
        QuantumGate I = QuantumGate.I;
        ControlledX CX = new ControlledX(1);
        Qasm.VERSIONS.get(3.0f).instance();

        System.out.println(System.currentTimeMillis());
        QuantumCircuit circuit = new QuantumCircuit.Compiler(2, 2, 3.0f)
                .gate(H)
                .gate(CX)
                .compile();
        System.out.println(System.currentTimeMillis());

        System.out.println(circuit.instruction());
    }*/
}
