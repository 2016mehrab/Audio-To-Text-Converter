package com.samurai74.audiototextconverter.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface TranscriptionService {
    String getTranscription(MultipartFile file) throws IOException;
    static FileSystemResource getTranscriptionFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("audio", ".wav");
        file.transferTo(tempFile);
        return new FileSystemResource(tempFile);
    }

}
