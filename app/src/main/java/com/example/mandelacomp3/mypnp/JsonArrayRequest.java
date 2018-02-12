package com.example.mandelacomp3.mypnp;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import javax.json.JsonObject;

public class JsonArrayRequest extends JsonRequest<JsonObject> {

    public JsonArrayRequest(int method, String url, String requestBody, Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
        return Response.success( null, HttpHeaderParser.parseCacheHeaders(response) );

    }
}
