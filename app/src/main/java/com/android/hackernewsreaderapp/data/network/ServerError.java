package com.android.hackernewsreaderapp.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaid on 03/03/2016.
 */
public class ServerError {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;

    private String serverResponse;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }
}
