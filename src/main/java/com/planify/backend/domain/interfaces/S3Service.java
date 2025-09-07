package com.planify.backend.domain.interfaces;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class S3Service {

    private final S3AsyncClient s3AsyncClient;
    private final String bucketName;

    public S3Service(S3AsyncClient s3AsyncClient) {
        this.s3AsyncClient = s3AsyncClient;
        this.bucketName = System.getenv("AWS_S3_BUCKET");
    }

    public Mono<String> uploadFile(FilePart filePart) {
        String extension = getExtension(filePart.filename());
        String uuidFileName = UUID.randomUUID().toString() + "." + extension;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uuidFileName)
                .contentType(filePart.headers().getContentType() != null
                        ? filePart.headers().getContentType().toString()
                        : "application/octet-stream")
                .build();

        // Convertir el contenido del FilePart en un byte[]
        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer); // liberar memoria
                    baos.writeBytes(bytes);
                    return baos.toByteArray();
                })
                .flatMap(bytes ->
                        Mono.fromFuture(
                                s3AsyncClient.putObject(request, AsyncRequestBody.fromBytes(bytes))
                        ).thenReturn("https://" + bucketName + ".s3.amazonaws.com/" + uuidFileName)
                );
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex != -1) ? fileName.substring(dotIndex + 1) : "";
    }
}
