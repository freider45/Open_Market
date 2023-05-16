package co.unicauca.openmarket.server.infra;

import co.unicauca.strategyserver.helpers.JsonError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private List<JsonError> errors;

    public ErrorResponse(String code, String error, String message) {
        errors = new ArrayList<>();
        JsonError jsonError = new JsonError();
        jsonError.setCode(code);
        jsonError.setError(error);
        jsonError.setMessage(message);
        errors.add(jsonError);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(errors);
    }
}
