package com.hh.sd.core.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.DataSubProcessorQueryService;
import com.hh.sd.core.service.DataSubProcessorService;
import com.hh.sd.core.service.custom.DataSubProcessorCustomService;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing DataSubProcessor.
 */
@RestController
@RequestMapping("/api")
public class DataSubProcessorCustomResource {

    private final Logger log = LoggerFactory.getLogger(DataSubProcessorCustomResource.class);

    private static final String ENTITY_NAME = "coreDataSubProcessor";

    private final DataSubProcessorService dataSubProcessorService;
    private final DataSubProcessorQueryService dataSubProcessorQueryService;
    private final DataSubProcessorCustomService dataSubProcessorCustomService;

    public DataSubProcessorCustomResource(DataSubProcessorService dataSubProcessorService, DataSubProcessorQueryService dataSubProcessorQueryService, DataSubProcessorCustomService dataSubProcessorCustomService) {
        this.dataSubProcessorService = dataSubProcessorService;
        this.dataSubProcessorQueryService = dataSubProcessorQueryService;
        this.dataSubProcessorCustomService = dataSubProcessorCustomService;
    }

    @PostMapping("/data-sub-processors/reorder")
    @Timed
    public ResponseEntity<List<DataSubProcessorDTO>> createDataSubProcessor(Long dataProcessorId, @Valid @RequestBody List<Long> idList) throws URISyntaxException {
        var dataSubProcessorDTOList = dataSubProcessorCustomService.reorder(dataProcessorId, idList);
        return ResponseEntity.ok().body(dataSubProcessorDTOList);
    }

}
