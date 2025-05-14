package com.samurai74.audiototextconverter.service.impl;

import com.samurai74.audiototextconverter.config.AudioProcessorConfig;
import com.samurai74.audiototextconverter.constant.Constants;
import com.samurai74.audiototextconverter.service.TranscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vosk.Model;
import org.vosk.Recognizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class VoskTranscriptionService implements TranscriptionService {
    private final Model model;
    private final AudioProcessorConfig audioProcessorConfig;


    @Override
    public String getTranscription(MultipartFile file) throws IOException {
        FileSystemResource fsr= TranscriptionService.getTranscriptionFile(file);
        StringBuilder sb = new StringBuilder();
        var sampledFile= audioProcessorConfig.sampleTo16K(fsr.getFile());
        try (InputStream audioStream = new FileInputStream(sampledFile);
             ) {

            try(Recognizer recognizer = new Recognizer(model, Constants.SAMPLE_RATE)){
                int bufferSize = 2048;
                byte[]buffer = new byte [bufferSize];
                int bytesRead;
                while((bytesRead=audioStream.read(buffer))>0){
                    if(recognizer.acceptWaveForm(buffer,bytesRead)){
                        sb.append(recognizer.getResult());
                        log.info(sb.toString());
                    }
                    else {
                        log.info("*********");
                        log.info("Showing Parital Result");
                        log.info(sb.toString());
                        log.info("*********");
                    }
                }
                sb.append(recognizer.getFinalResult());
            }
            return sb.toString();
        }
    }
}
