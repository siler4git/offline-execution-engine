package com.vipabc.vliveshow.apitest.bean.asset.request;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.JsonBody;
import com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.MultiPartEntity.MultiPartEntity;
import com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.RequestParam;
import leo.engineData.testData.Bean;

import java.io.IOException;
import java.io.Serializable;

@SuppressWarnings({"DefaultAnnotationParam", "unused", "MismatchedQueryAndUpdateOfCollection"})
public class Request extends Bean<Object, HttpResponse> implements Serializable {
            private static final HttpTransport httpTransport = new NetHttpTransport();
//    private static final Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("192.168.23.199", 8080));
//    private static final HttpTransport httpTransport = new NetHttpTransport.Builder().setProxy(proxy).build();
    private static final HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

    private String method;
    private String url;
    private RequestParam param;
    private MultiPartEntity multiPartEntity;
    private JsonBody jsonBody;


    @Override
    public HttpResponse execute(Object o) {
        try {
            String url = param == null ? this.url : param.process(this.url);

            logger.info(String.format("[%d] %s", Thread.currentThread().getId(), url));

            HttpContent content = null;

            if (multiPartEntity != null)
                content = multiPartEntity.process();

            if (jsonBody != null)
                content = jsonBody.process(null);

            HttpRequest httpRequest = requestFactory.buildRequest(method, new GenericUrl(url), content);

            return httpRequest.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString() {
        return url;
    }


    /**
     * Getter
     */
    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public RequestParam getParam() {
        return param;
    }

    public MultiPartEntity getMultiPartEntity() {
        return multiPartEntity;
    }

    public JsonBody getJsonBody() {
        return jsonBody;
    }

    /**
     * Setter
     */
    public void setMethod(String method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParam(RequestParam param) {
        this.param = param;
    }

    public void setMultiPartEntity(MultiPartEntity multiPartEntity) {
        this.multiPartEntity = multiPartEntity;
    }

    public void setJsonBody(JsonBody jsonBody) {
        this.jsonBody = jsonBody;
    }


}
