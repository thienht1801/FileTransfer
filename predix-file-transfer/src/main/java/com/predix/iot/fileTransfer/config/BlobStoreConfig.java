package com.predix.iot.fileTransfer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "blobstore")
public class BlobStoreConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String url;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String objectStoreAccessKey) {
        this.accessKey = objectStoreAccessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String objectStoreSecretKey) {
        this.secretKey = objectStoreSecretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
