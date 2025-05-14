package com.samurai74.audiototextconverter.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

public interface StreamTranscriptionService {
    void streamTranscription(MultipartFile file, ResponseBodyEmitter emitter) throws IOException;
}
