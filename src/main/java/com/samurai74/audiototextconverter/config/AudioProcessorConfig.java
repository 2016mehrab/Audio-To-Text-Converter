package com.samurai74.audiototextconverter.config;


import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class AudioProcessorConfig {
    @Value("${ffmpeg.path}")
    private String ffmpegPath;
    @Value("${ffprobe.path}")
    private String ffprobePath;

    public File sampleTo16K(File file) throws IOException {
        File outputFile = File.createTempFile("resampled",".wav");
        FFmpeg fFmpeg = new FFmpeg(ffmpegPath) ;
        FFprobe fFprobe = new FFprobe(ffprobePath) ;
        FFmpegBuilder fb= new FFmpegBuilder()
                .addInput(file.getAbsolutePath())
                .addOutput(outputFile.getAbsolutePath())
                .setAudioSampleRate(FFmpeg.AUDIO_SAMPLE_16000)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
        executor.createJob(fb).run();
       return outputFile;
    }
}
