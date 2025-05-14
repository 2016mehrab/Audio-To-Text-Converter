package com.samurai74.audiototextconverter.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VoskResult {
    private  String text;
}
