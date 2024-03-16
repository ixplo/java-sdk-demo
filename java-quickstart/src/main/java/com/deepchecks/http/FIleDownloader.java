package com.deepchecks.http;

import com.deepchecks.config.PropsLoader;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
@Slf4j
public class FIleDownloader {
    OkHttpClient httpClient;
    PropsLoader propsLoader;

    public FIleDownloader() {
        propsLoader = new PropsLoader();
        OkHttpUtil.init(propsLoader.readBoolean("ignoreSsl"));
        this.httpClient = OkHttpUtil.getClient();
    }

    public void download(String url, String fileName) {
        try {
            log.info("Downloading file from {}", url);
            downloadFile(url, fileName);
            log.info("File download: SUCCESS, filename={}", fileName);
        } catch (IOException e) {
            log.info("File download: FAILURE", e);
            throw new RuntimeException(e);
        }
    }
    private void downloadFile(String url, String fileName) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        ResponseBody body = response.body();

        FileUtils.copyToFile(body.byteStream(), new File(fileName));
        response.close();
    }
}
