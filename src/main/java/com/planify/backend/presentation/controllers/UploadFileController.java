package com.planify.backend.presentation.controllers;

import com.planify.backend.domain.interfaces.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/files")
public class UploadFileController {
    private final S3Service s3Service;

    public UploadFileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") FilePart file) {
        return s3Service.uploadFile(file)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest()
                                .body("Error al subir archivo: " + e.getMessage())
                ));
    }
}
