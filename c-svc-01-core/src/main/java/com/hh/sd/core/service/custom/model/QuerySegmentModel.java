package com.hh.sd.core.service.custom.model;

import lombok.Data;

import java.util.List;

@Data
public class QuerySegmentModel {
    private String name;
    private QuerySegmentType type;
    private String emptyCondition;
    private List<String> conditions;
}
