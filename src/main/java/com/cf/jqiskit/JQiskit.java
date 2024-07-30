package com.cf.jqiskit;

import com.cf.jqiskit.exceptions.IBMException;
import com.cf.jqiskit.gson_adapters.JsonAdapter;
import com.cf.jqiskit.ibm.IBMHttpRequest;
import com.cf.jqiskit.ibm.IBMRequestInfo;
import com.cf.jqiskit.ibm.endpoint.IBMEndpoint;
import com.cf.jqiskit.ibm.objects.Instance;
import com.cf.jqiskit.ibm.responses.IBMObjectResponse;
import com.cf.jqiskit.ibm.responses.IBMResponse;
import com.cf.jqiskit.io.response.Response;
import com.cf.jqiskit.util.general.Reflection;
import com.cf.jqiskit.util.general.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class JQiskit {
    private static final String ADAPTER_PACKAGE = "com.cf.jqiskit.gson_adapters.types";
    public static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls();
        Reflection.consumeClasses(ADAPTER_PACKAGE, JQiskit.class.getClassLoader(), clazz -> {
            try {
                JsonAdapter adapter = (JsonAdapter) clazz.getDeclaredConstructor().newInstance();
                builder.registerTypeAdapter(adapter.type(), adapter);
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

    public static JQiskit from(String apiToken) throws IOException {
        IBMResponse response = new IBMResponse();

        new IBMHttpRequest(new IBMRequestInfo(IBMEndpoint.USER), apiToken).populate(response);

        if (response.getStatus() != 200) {
            throw new IBMException(response.getErrors());
        }

        JQiskit instance = new JQiskit(apiToken);
        instance.updateInstances();

        return instance;
    }

    private final String apiToken;
    private Map<String, Instance> instances;

    private JQiskit(String apiToken) {
        this.apiToken = apiToken;
        this.instances = null;
    }

    public void updateInstances() throws IOException {
        IBMRequestInfo info = new IBMRequestInfo(IBMEndpoint.INSTANCES);
        IBMObjectResponse<Map<String, Instance>> response = new IBMObjectResponse<>(new TypeToken<Map<String, Instance>>() {}.type());

        request(info, response);
        response.throwErrors();

        this.instances = response.getResult();
    }

    public void request(IBMRequestInfo info, Response response) throws IOException {
        new IBMHttpRequest(info, apiToken).populate(response);
    }

    public Instance instance(String name) {
        return instances.get(name);
    }
}
