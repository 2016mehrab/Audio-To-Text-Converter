package com.samurai74.audiototextconverter.service.impl;

import com.samurai74.audiototextconverter.service.StreamTranscriptionService;
import com.samurai74.audiototextconverter.service.TranscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@Service
@Qualifier("openaiTranscriber")
@RequiredArgsConstructor
public class OpenApiAudioTranscription implements TranscriptionService {
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    @Override
    public String getTranscription(MultipartFile file) throws IOException {
        FileSystemResource fileResource =TranscriptionService.getTranscriptionFile(file);
        AudioTranscriptionPrompt atp = new AudioTranscriptionPrompt(fileResource);
        AudioTranscriptionResponse atr= openAiAudioTranscriptionModel.call(atp);
        return atr.getResult().getOutput();
    }

}
