package com.samurai74.audiototextconverter.controller;

import com.samurai74.audiototextconverter.services.TranscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/transcribe")
@RequiredArgsConstructor
public class TranscriptionController {
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final TranscriptionService transcriptionService;

    @PostMapping
    public ResponseEntity<String> transcribeAudioToText(
            @RequestParam("file")MultipartFile file
            ) throws IOException {
        return ResponseEntity.ok(transcriptionService.getTranscription(file));
    }
}
