package com.samurai74.audiototextconverter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samurai74.audiototextconverter.config.AudioProcessorConfig;
import com.samurai74.audiototextconverter.constant.Constants;
import com.samurai74.audiototextconverter.mapper.VoskResult;
import com.samurai74.audiototextconverter.service.StreamTranscriptionService;
import com.samurai74.audiototextconverter.service.TranscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.vosk.Model;
import org.vosk.Recognizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class VoskTranscriptionService implements TranscriptionService , StreamTranscriptionService {
    private final Model model;
    private final AudioProcessorConfig audioProcessorConfig;
    private final ObjectMapper objectMapper;


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
                        String jsonText =recognizer.getResult();
                        VoskResult  voskResult = objectMapper.readValue(jsonText, VoskResult.class);
                        sb.append(voskResult.getText());
                        log.info(sb.toString());
                    }
                    else {
                        log.info("*********");
                        log.info("Showing Parital Result");
                        log.info(sb.toString());
                        log.info("*********");
                    }
                }
                String jsonText =recognizer.getFinalResult();
                VoskResult  voskResult = objectMapper.readValue(jsonText, VoskResult.class);
                sb.append(voskResult.getText());
            }
            return sb.toString();
        }
    }

    @Override
    public void streamTranscription(MultipartFile file, ResponseBodyEmitter emitter) throws IOException {
        FileSystemResource fsr = TranscriptionService.getTranscriptionFile(file);
        var  sampledFile = audioProcessorConfig.sampleTo16K(fsr.getFile());
        try(InputStream audioStream = new FileInputStream(sampledFile);
            Recognizer rz= new Recognizer(model, Constants.SAMPLE_RATE)
        ){
            VoskResult voskResult ;
            int bufferSize = 2048;
            byte[]buffer = new byte[bufferSize];
            int bytesRead;
            while((bytesRead = audioStream.read(buffer))>0){
                if(rz.acceptWaveForm(buffer,bytesRead)){
                    var jsonText = rz.getResult();
                        voskResult= objectMapper.readValue(jsonText, VoskResult.class);
                        if(voskResult.getText()!=null){

                            emitter.send(voskResult.getText()+"\n");
                        }
                }
                else{
                    var jsonText =rz.getPartialResult();
                        voskResult= objectMapper.readValue(jsonText, VoskResult.class);
                        if(voskResult.getText()!=null){

                            emitter.send(voskResult.getText()+"\n");
                        }
                }
            }

        }

    }
}
