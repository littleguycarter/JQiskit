package com.cf.jqiskit.ibm.responses;


import com.cf.jqiskit.JQiskit;
import com.cf.jqiskit.ibm.IBMError;
import com.cf.jqiskit.io.HttpUtil;
import com.cf.jqiskit.io.response.types.JsonResponse;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Set;

public class IBMResponse extends JsonResponse {
    private Set<IBMError> errors;

    @Override
    public void use(HttpURLConnection connection) throws IOException {
        super.use(connection);

        ImmutableSet.Builder<IBMError> builder = ImmutableSet.builder();

        if (HttpUtil.isSuccessful(getStatus())) {
            this.errors = builder.build();
            return;
        }

        JsonArray errorArray = getJsonResponse().getAsJsonArray("errors");

        for (int i = 0; i < errorArray.size(); i++) {
            builder.add(JQiskit.GSON.fromJson(errorArray.get(i), IBMError.class));
        }

        this.errors = builder.build();
    }

    public Set<IBMError> getErrors() {
        return errors;
    }

    public boolean isErrored() {
        return !errors.isEmpty();
    }
}
