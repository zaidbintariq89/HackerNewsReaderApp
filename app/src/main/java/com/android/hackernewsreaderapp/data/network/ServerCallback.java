package com.android.hackernewsreaderapp.data.network;

import android.text.TextUtils;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by zaid on 03/03/2016.
 */
public abstract class ServerCallback<T> implements Callback<T> {

    public abstract void onFailure(ServerError restError);

    public abstract void onSuccess(Response<T> response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response);
        } else {
            ServerError serverError = parseError(response);
            if (serverError == null) {
                serverError = new ServerError();
                serverError.setMessage("Something went wrong");
                serverError.setStatus(500);
            } else if (serverError.getStatus() == 500) {
                if (!TextUtils.isEmpty(serverError.getServerResponse())) {
                    serverError.setMessage(serverError.getServerResponse());
                } else {
                    serverError.setMessage("Something went wrong");
                }
            }
            onFailure(serverError);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ServerError error = new ServerError();
        error.setMessage(t.getMessage());
        error.setStatus(500);
        onFailure(error);
    }

    private ServerError parseError(Response<?> response) {
        ServerError error = new ServerError();
        ExceptionParser exceptionParser = new ExceptionParser(response);
        String bodyString = exceptionParser.getBodyAsString();

        if (!TextUtils.isEmpty(bodyString)) {
            Gson gson = new Gson();
            try {
                error = gson.fromJson(bodyString, ServerError.class);
            } catch (Exception e) {
                error.setMessage(e.getMessage());
                error.setStatus(500);
            } finally {
                error.setServerResponse(bodyString);
            }
        }

        return error;
    }
}