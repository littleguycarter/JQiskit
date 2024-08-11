package com.cf.jqiskit;

import com.cf.jqiskit.gson_adapters.JsonDeserializer;
import com.cf.jqiskit.ibm.IBMHttpRequest;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.backend.BackendStatusEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.jobs.JobEndpoint;
import com.cf.jqiskit.ibm.endpoint.types.session.CloseSessionEndpoint;
import com.cf.jqiskit.ibm.objects.BackendStatus;
import com.cf.jqiskit.ibm.objects.Instance;
import com.cf.jqiskit.ibm.objects.JobInfo;
import com.cf.jqiskit.ibm.objects.Session;
import com.cf.jqiskit.ibm.responses.IBMObjectResponse;
import com.cf.jqiskit.ibm.responses.IBMResponse;
import com.cf.jqiskit.io.response.Response;
import com.cf.jqiskit.io.response.types.StatusResponse;
import com.cf.jqiskit.util.general.Reflection;
import com.cf.jqiskit.util.general.TypeToken;
import com.cf.jqiskit.util.io.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class JQiskitService {
    private static final String ADAPTER_PACKAGE = "com.cf.jqiskit.gson_adapters.types";
    public static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls();
        Reflection.consumeClasses(ADAPTER_PACKAGE, JQiskitService.class.getClassLoader(), clazz -> {
            try {
                JsonDeserializer<?> adapter = (JsonDeserializer<?>) clazz.getDeclaredConstructor().newInstance();
                builder.registerTypeAdapter(adapter.token().type(), adapter);
            } catch (InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e
            ) {
                throw new RuntimeException(e);
            }
        });

        GSON = builder.create();
    }

    private final String apiToken;

    public JQiskitService(String apiToken) {
        this.apiToken = apiToken;
    }

    public Map<String, Instance> fetchInstances() throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(IBMEndpoint.INSTANCES);
        IBMObjectResponse<Map<String, Instance>> response = new IBMObjectResponse<>(new TypeToken<>() {});

        request(info, response);
        response.throwErrors();

        return response.getResult();
    }

    public Session openSession(Instance instance, String backend) throws IOException {
        JsonObject payload = new JsonObject();
        payload.addProperty("backend", backend);
        payload.addProperty("instance", instance.name());

        IBMRequestInfo info = new IBMRequestInfo(IBMEndpoint.CREATE_SESSION, payload.toString());
        IBMResponse response = new IBMResponse();

        new IBMHttpRequest(info, apiToken).populate(response);
        response.throwErrors();

        return new Session(this, backend, instance, response.getJsonResponse().get("id").getAsString());
    }

    public void closeSession(String sessionId) throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(new CloseSessionEndpoint(sessionId));
        StatusResponse response = new StatusResponse();

        new IBMHttpRequest(info, apiToken).populate(response);

        if (!HttpUtil.isSuccessful(response.getStatus())) {
            throw new IOException("Failed to close IBM Session: Error Code " + response.getStatus());
        }
    }

    public BackendStatus fetchBackendStatus(String backend) throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(new BackendStatusEndpoint(backend));
        IBMObjectResponse<BackendStatus> response = new IBMObjectResponse<>(BackendStatus.class);

        request(info, response);
        response.throwErrors();

        return response.getResult();
    }

    public String[] fetchBackendIds() throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(IBMEndpoint.LIST_BACKENDS);
        IBMResponse response = new IBMResponse();

        request(info, response);
        response.throwErrors();

        return GSON.fromJson(response.getJsonResponse().get("devices").getAsJsonArray(), String[].class);
    }

    public JobInfo.Status getJobStatus(String jobId) throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(new JobEndpoint(jobId));
        IBMResponse response = new IBMResponse();

        request(info, response);
        response.throwErrors();

        return JobInfo.Status.valueOf(response.getJsonResponse().get("status").getAsString().toUpperCase());
    }

    public void request(IBMRequestInfo info, Response response) throws IOException {
        new IBMHttpRequest(info, apiToken).populate(response);
    }
}
