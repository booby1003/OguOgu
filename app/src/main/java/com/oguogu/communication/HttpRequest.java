package com.oguogu.communication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.oguogu.GlobalApplication;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class HttpRequest {

    private volatile static HttpRequest instance;
    private static Context context;
    private static RequestQueue mVolleyQueue;
    private static final int TIMEOUT_MS = 5000;

    public static HttpRequest getInstance(Context ctx) {
        context = ctx;
        if(instance == null) {
            synchronized (HttpRequest.class) {
                instance = new HttpRequest();
            }
        }
        return instance;
    }

    private HttpRequest() {
        mVolleyQueue = Volley.newRequestQueue(context);
    }

    public interface ListenerHttpResponse {
        void success(String response);
        void fail(JSONObject response);
        void exception(VolleyError error);
        void networkerror();
    }

    /**
     * JsonObjectRequest
     * @param tag
     * @param method
     * @param url
     * @param jsonObject
     * @param listener
     */
    public void JsonObjectRequest(String tag, int method, String url, JSONObject jsonObject, final ListenerHttpResponse listener) {
        if(!checkNetWork()) {
            LogUtil.e("네트워크 연결 안됨");
            listener.networkerror();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("Response : " + response.toString());

                VoBase result = GlobalApplication.getGson().fromJson(response.toString(), VoBase.class);

                if(result.isSuccess()) {
                    listener.success(response.toString());
                    return;
                }

                listener.fail(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.exception(error);
            }
        });

        jsonObjectRequest.setTag(tag);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mVolleyQueue.add(jsonObjectRequest);
    }

    /**
     * StringRequest
     * @param tag
     * @param method
     * @param url
     * @param params
     * @param listener
     */
    public void StringRequest(String tag, int method, String url, final String params, final ListenerHttpResponse listener) {
        if(!checkNetWork()) {
            LogUtil.e("네트워크 연결 안됨");
            listener.networkerror();
            return;
        }
        LogUtil.d("url : " + url);

        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                LogUtil.d("Response : " + response);

                VoBase result = GlobalApplication.getGson().fromJson(response, VoBase.class);

                if(result.isSuccess()) {
                    listener.success(response);
                    return;
                }

                try {
                    listener.fail(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.exception(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map header = new HashMap();
//                header.put("Accept", "application/json");
//                header.put("Content-Type", "application/json");
//                return header;
                return super.getHeaders();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return params.getBytes();
            }
        };

        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mVolleyQueue.add(stringRequest);
    }

    /**
     *
     * @param url
     * @param params
     * @param dataParams
     * @param listener
     */
    public void multipartRequest(String url, final Map<String, String> params,
                                 final Map<String, DataPart> dataParams, final ListenerHttpResponse listener) {

        if(!checkNetWork()) {
            LogUtil.e("네트워크 연결 안됨");
            listener.networkerror();
            return;
        }
        LogUtil.d("url : " + url);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {

                LogUtil.d("Response : ");

                String resultResponse = new String(response.data);

                LogUtil.d("Response : " + resultResponse);

                VoBase result = GlobalApplication.getGson().fromJson(resultResponse, VoBase.class);

                if (result.isSuccess()) {
                    listener.success(resultResponse);
                    return;
                }

                try {
                    listener.fail(new JSONObject(resultResponse));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.exception(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                return dataParams;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);

//        multipartRequest.setTag(tag);
//        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        mVolleyQueue.add(multipartRequest);

    }

    private boolean checkNetWork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }


}
