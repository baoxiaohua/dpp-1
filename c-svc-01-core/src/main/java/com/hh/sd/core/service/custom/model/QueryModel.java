package com.hh.sd.core.service.custom.model;

import lombok.Data;

import java.util.List;

@Data
public class QueryModel {
    private Boolean pageable;
    private String countColumn;
    private String template;
    private List<QuerySegmentModel> segments;

    private String query;
    private List<String> parameters;
}
