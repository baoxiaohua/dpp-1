package com.hh.sd.core.service.custom.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataSubProcessorResultModel {
    private long total;
    private String debug;
    private List<Map<String, Object>> result;
}
