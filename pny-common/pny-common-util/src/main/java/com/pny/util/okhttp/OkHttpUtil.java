package com.pny.util.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @ClassName: OkHttpUtil 工具类
 * @author pmyun
 * @version
 * @since JDK 1.8
 */
public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    /**
     * 
     * get:get请求
     *
     * @param url
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    public static String get(String url ,String json) throws IOException {
        Map map= JSONObject.parseObject(json, Map.class);
        StringBuffer reqStr =new StringBuffer();
        int num =0 ;
        for (Object o : map.entrySet()) {
            
            Map.Entry entry = (Map.Entry) o;
            if(null!=entry.getValue()&&StringUtils.isNotEmpty(entry.getValue().toString())) {
                num = num+1;
                if(num>1) reqStr.append("&");
                reqStr.append((String) entry.getKey()+"="+entry.getValue());
            }
        }
        if(StringUtils.isNotEmpty(reqStr.toString())) {
            url =url+"?"+reqStr;
        }
        Builder builder= new Request.Builder().url(url);
        
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    public static String get(String url ,Map map) throws IOException {
        StringBuffer reqStr =new StringBuffer();
        int num =0 ;
        for (Object o : map.entrySet()) {
            
            Map.Entry entry = (Map.Entry) o;
            if(null!=entry.getValue()&&StringUtils.isNotEmpty(entry.getValue().toString())) {
                num = num+1;
                if(num>1) reqStr.append("&");
                reqStr.append((String) entry.getKey()+"="+entry.getValue());
            }
        }
        if(StringUtils.isNotEmpty(reqStr.toString())) {
            url =url+"?"+reqStr;
        }
        Builder builder= new Request.Builder().url(url);
        
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void postAsync(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        Builder rbuilder= new Request.Builder().url(url);
        Request request = rbuilder.post(builder.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 
     * post:post请求Map
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String post(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        Builder rbuilder= new Request.Builder().url(url);
        Request request =rbuilder.post(builder.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 
     * post:post请求JSON
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String post(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builder= new Request.Builder().url(url);
        
        Request request = builder.post(requestBody).build();
        Response execute = client.newCall(request).execute();
        return execute.body().string();
    }

    /**
     * 
     * post:文件上传
     *
     * @param url
     * @param file
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String post(String url, File file) throws IOException {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), fileBody)
                .build();
        Builder builder= new Request.Builder().url(url);
        
        Request request = builder.post(requestBody).build();
        return client.newCall(request).execute().body().string();
    }
    
    /**
     * 
     * put:put请求Map
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String put(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        Builder rbuilder= new Request.Builder().url(url);
        Request request = rbuilder.put(builder.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    /**
     * 
     * put:put请求JSON
     * @param url
     * @param json
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String put(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builder= new Request.Builder().url(url);
        
        Request request = builder.put(requestBody).build();
        Response execute = client.newCall(request).execute();
        return execute.body().string();
    }
    
    /**
     * 
     * put:delete请求Map
     * @param url
     * @param map
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String delete(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        Builder rbuilder= new Request.Builder().url(url);
        Request request = rbuilder.delete(builder.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    /**
     * 
     * delete:delete请求JSON
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String delete(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builder= new Request.Builder().url(url);
        Request request = builder.delete(requestBody).build();
        Response execute = client.newCall(request).execute();
        return execute.body().string();
    }
    
    
    /**
     * 
     * patch:patch请求Map
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String patch(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        Builder rbuilder= new Request.Builder().url(url);
        Request request = rbuilder.patch(builder.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    /**
     * 
     * patch:patch请求JSON
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     * @since JDK 1.8
     */
    public static String patch(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Builder builder= new Request.Builder().url(url);
        Request request = builder.patch(requestBody).build();
        Response execute = client.newCall(request).execute();
        return execute.body().string();
    }
    
   
}