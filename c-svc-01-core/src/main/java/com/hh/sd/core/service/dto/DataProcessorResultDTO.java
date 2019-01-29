package com.hh.sd.core.service.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Map;

@Data
public class DataProcessorResultDTO {
    private boolean success;
    private String error;
    private Map<String, Object> results;
}
