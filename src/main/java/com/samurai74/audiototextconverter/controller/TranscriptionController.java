package com.samurai74.audiototextconverter.controller;

import com.samurai74.audiototextconverter.service.StreamTranscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;


@RestController
@RequestMapping(path = "/api/v1/transcribe")
@RequiredArgsConstructor
@Tag(name="Vosk-Transcriber")
@CrossOrigin(origins = "https://audio-to-text-converter.netlify.app ,http://localhost:5173, http://localhost:4173") // Allow only frontend
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
