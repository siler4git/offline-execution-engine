package com.vipabc.vliveshow.apitest.bean.asset.request.RrequestEntity.MultiPartEntity;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.MultipartContent;

import java.io.File;
import java.io.Serializable;
import java.net.URL;


@SuppressWarnings({"DefaultAnnotationParam", "unused"})
class BinaryBody implements Serializable {
    private String name;
    private String realFileName;
    private String mimeType;
    private String fileName;

    MultipartContent.Part process() {
        URL url = this.getClass().getClassLoader().getResource(realFileName);
        File file = url == null ? null : new File(url.getFile());
        if (file == null || !file.exists())
            return null;

        MultipartContent.Part part = new MultipartContent.Part();
        part.setEncoding(null);
        part.setHeaders(new HttpHeaders().set("Content-Disposition", String.format("form-data; name=\"%s\"; filename=\"%s\"", name, fileName)));
        part.setContent(new FileContent(mimeType, file));
        return part;
    }


    /**
     * Getter
     */
    public String getName() {
        return name;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Setter
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
