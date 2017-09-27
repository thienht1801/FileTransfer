package com.predix.iot.fileTransfer.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.predix.iot.fileTransfer.service.BlobstoreService;

@Configuration
@Profile("dev")
@EnableConfigurationProperties({BlobStoreConfig.class})
public class LocalConfig {
    Log log = LogFactory.getLog(LocalConfig.class);

    @Autowired
    private BlobStoreConfig objectStoreProperties;

    @Bean
    public BlobstoreService objectStoreService() {
        log.info("objectStoreService(): " + objectStoreProperties.getAccessKey()
                + objectStoreProperties.getSecretKey() + ", " + objectStoreProperties.getBucket());

        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(objectStoreProperties.getAccessKey(), objectStoreProperties.getSecretKey()));
        s3Client.setEndpoint(objectStoreProperties.getUrl());

        try {
            // Remove the Credentials from the Object Store URL
            URL url = new URL(objectStoreProperties.getUrl());
            String urlWithoutCredentials = url.getProtocol() + "://" + url.getHost();

            // Return BlobstoreService
            return new BlobstoreService(s3Client, objectStoreProperties.getBucket(), urlWithoutCredentials);
        } catch (MalformedURLException e) {
            log.error("create(): Couldnt parse the URL provided by VCAP_SERVICES. Exception = " + e.getMessage());
            throw new RuntimeException("Blobstore URL is Invalid", e);
        }
    }
}
