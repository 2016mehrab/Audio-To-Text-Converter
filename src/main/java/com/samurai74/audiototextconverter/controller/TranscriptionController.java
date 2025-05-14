package com.samurai74.audiototextconverter.controller;

import com.samurai74.audiototextconverter.service.StreamTranscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;


@RestController
@RequestMapping(path = "/api/v1/transcribe")
@RequiredArgsConstructor
@Tag(name="Vosk-Transcriber")
public class TranscriptionController {
    private final StreamTranscriptionService streamTranscriptionService;

    @Operation(
    summary = "Post Endpoint for Vosk transcriber"
    )

    @PostMapping(path = "/vosk")
    public ResponseBodyEmitter transcribeAudioAndStream(
            @RequestParam("file") MultipartFile file
    ){
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        new Thread(()->{
            try{
                streamTranscriptionService.streamTranscription(file,emitter);
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }
}
