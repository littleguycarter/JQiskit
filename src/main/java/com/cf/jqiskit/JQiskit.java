package com.cf.jqiskit;

import com.cf.jqiskit.assembly.Qasm;
import com.cf.jqiskit.circuitry.QuantumGate;
import com.cf.jqiskit.circuitry.circuits.QuantumCircuit;
import com.cf.jqiskit.circuitry.gates.ControlledX;
import com.cf.jqiskit.exceptions.IBMException;
import com.cf.jqiskit.ibm.IBMEndpoint;
import com.cf.jqiskit.ibm.IBMHttpRequest;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.responses.IBMResponse;
import com.cf.jqiskit.io.response.Response;
import com.cf.jqiskit.gson_adapters.JsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

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

    public static JQiskit from(String apiToken, int poolSize) {
        IBMResponse response = new IBMResponse();

        try {
            new IBMHttpRequest(new IBMRequestInfo(IBMEndpoint.USER), apiToken).populate(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.getStatus() == 200) {
            System.out.println(response.getJsonResponse());
            return new JQiskit(apiToken, poolSize);
        }

        throw new IBMException(response.getErrors());
    }

    private final String apiToken;
    private final ScheduledExecutorService requestExecutor;

    private JQiskit(String apiToken, int poolSize) {
        this.apiToken = apiToken;
        this.requestExecutor = Executors.newScheduledThreadPool(poolSize);
    }

    public <T extends Response> CompletableFuture<T> request(IBMRequestInfo info, T response) {
        CompletableFuture<T> future = new CompletableFuture<>();

        requestExecutor.submit(() -> {
            try {
                new IBMHttpRequest(info, apiToken).populate(response);
                future.complete(response);
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    public <T extends Response> CompletableFuture<T> requestDelayed(IBMRequestInfo info, T response, long delay, TimeUnit units) {
        CompletableFuture<T> future = new CompletableFuture<>();

        requestExecutor.schedule(() -> {
            try {
                new IBMHttpRequest(info, apiToken).populate(response);
                future.complete(response);
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
        }, delay, units);

        return future;
    }

    // TEMPORARY
    public static void main(String[] args) {
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
    }
}
