package com.hh.sd.core.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.domain.DataProcessor;
import com.hh.sd.core.service.DataProcessorQueryService;
import com.hh.sd.core.service.DataProcessorService;
import com.hh.sd.core.service.custom.DataProcessorCustomService;
import com.hh.sd.core.service.dto.DataProcessorResultDTO;
import lombok.var;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * REST controller for managing DataProcessor.
 */
@RestController
@RequestMapping("/api")
public class DataProcessorCustomResource {

    private final Logger log = LoggerFactory.getLogger(DataProcessorCustomResource.class);

    private static final String ENTITY_NAME = "coreDataProcessor";

    private final DataProcessorService dataProcessorService;
    private final DataProcessorCustomService dataProcessorCustomService;
    private final DataProcessorQueryService dataProcessorQueryService;

    public DataProcessorCustomResource(DataProcessorService dataProcessorService, DataProcessorCustomService dataProcessorCustomService, DataProcessorQueryService dataProcessorQueryService) {
        this.dataProcessorService = dataProcessorService;
        this.dataProcessorCustomService = dataProcessorCustomService;
        this.dataProcessorQueryService = dataProcessorQueryService;
    }


    @PostMapping("/data-processors/execute")
    @Timed
    public ResponseEntity<DataProcessorResultDTO> execute(@RequestParam String identifier, @Valid @RequestBody Map<String, Object> paramMap) throws URISyntaxException {
        return this.debug(identifier, -1L, paramMap);
    }

    @PostMapping("/data-processors/debug")
    @Timed
    public ResponseEntity<DataProcessorResultDTO> debug(@RequestParam String identifier, @RequestParam Long dataSubProcessorId, @Valid @RequestBody Map<String, Object> paramMap) throws URISyntaxException {
        DataProcessorResultDTO result;
        try {
            result = dataProcessorCustomService.execute(identifier, paramMap, dataSubProcessorId, true);
        }
        catch (Exception ex) {
            result = new DataProcessorResultDTO();
            result.setSuccess(false);

            Throwable embeddedEx = ex.getCause();
            if(embeddedEx instanceof SQLGrammarException) result.setError(((SQLGrammarException)embeddedEx).getSQLException().toString());
            else result.setError(ex.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/data-processors/enable")
    @Timed
    public ResponseEntity<DataProcessor> enable(@RequestParam long dataProcessorId, @RequestParam boolean enabled) throws URISyntaxException {
        var dataProcessor = dataProcessorCustomService.enable(dataProcessorId, enabled);

        return ResponseEntity.ok().body(dataProcessor);
    }
}
