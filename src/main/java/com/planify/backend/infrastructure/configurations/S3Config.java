package com.planify.backend.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
public class S3Config {

    @Bean
    public S3AsyncClient s3AsyncClient() {
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = System.getenv("AWS_REGION");

        if (awsAccessKeyId == null || awsSecretKey == null || awsRegion == null) {
            throw new IllegalStateException("Variables de entorno AWS faltantes.");
        }

        return S3AsyncClient.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey))
                )
                .build();
    }
}
