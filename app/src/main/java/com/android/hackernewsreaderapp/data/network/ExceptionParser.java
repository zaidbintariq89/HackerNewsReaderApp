package com.android.hackernewsreaderapp.data.network;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Response;

/**
 * Created by zaid on 08/03/2016.
 */
public class ExceptionParser {
    private final ResponseBody body;
    private final String bodyString;

    public ExceptionParser(Response response) {
        this.body = cloneResponseBody(response.errorBody());
        this.bodyString = getBodyAsString(body);
    }

    public String getBodyAsString() {
        return bodyString;
    }

    @Nullable
    private static String getBodyAsString(@Nullable ResponseBody responseBody) {
        if (responseBody == null) {
            return null;
        }
        try {
            return responseBody.string();
        } catch (IOException e) {
            Log.w("", "Failed to read error ResponseBody as String");
            return null;
        }
    }

    @Nullable
    private static ResponseBody cloneResponseBody(@Nullable final ResponseBody body) {
        if (body == null) {
            return null;
        }
        final Buffer buffer = new Buffer();
        try {
            BufferedSource source = body.source();
            buffer.writeAll(source);
            source.close();
        } catch (IOException e) {
            Log.w("Testing", "Failed to clone ResponseBody");
            return null;
        }
        return new ResponseBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public BufferedSource source() {
                return buffer.clone();
            }
        };
    }
}
