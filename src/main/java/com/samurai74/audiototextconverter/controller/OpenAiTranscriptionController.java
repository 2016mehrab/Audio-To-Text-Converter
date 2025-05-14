package com.samurai74.audiototextconverter.controller;
import com.samurai74.audiototextconverter.service.TranscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/transcribe/openai")
@RequiredArgsConstructor
@Tag(name="OpenAi-Transcriber")
public class OpenAiTranscriptionController  {

    private TranscriptionService transcriptionService;
    public OpenAiTranscriptionController(@Qualifier("openaiTranscriber") TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }


    @Operation(
            summary = "Post Endpoint for OpenAI transcriber"
    )
    @PostMapping
    public ResponseEntity<String> transcribeAudioToText(
            @RequestParam("file")MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(transcriptionService.getTranscription(file));
    }

}
