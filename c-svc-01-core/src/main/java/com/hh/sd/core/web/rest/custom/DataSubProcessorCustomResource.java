package com.hh.sd.core.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.service.DataSubProcessorQueryService;
import com.hh.sd.core.service.DataSubProcessorService;
import com.hh.sd.core.service.custom.DataSubProcessorCustomService;
import com.hh.sd.core.service.dto.DataProcessorResultDTO;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import lombok.var;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<DataSubProcessorDTO>> recorder(Long dataProcessorId, @Valid @RequestBody List<Long> idList) throws URISyntaxException {
        var dataSubProcessorDTOList = dataSubProcessorCustomService.reorder(dataProcessorId, idList);
        return ResponseEntity.ok().body(dataSubProcessorDTOList);
    }

//    @PostMapping("/data-sub-processors/execute/{id}")
//    @Timed
//    public ResponseEntity<DataProcessorResultDTO> execute(@PathVariable Long id, @Valid @RequestBody Map<String, Object> criteria) throws URISyntaxException {
//        DataProcessorResultDTO result;
//        try {
//            result = dataSubProcessorCustomService.execute(id, criteria);
//        }
//        catch (Exception ex) {
//            result = new DataProcessorResultDTO();
//            result.setSuccess(false);
//
//            Throwable embeddedEx = ex.getCause();
//            if(embeddedEx instanceof SQLGrammarException) result.setError(((SQLGrammarException)embeddedEx).getSQLException().toString());
//            else result.setError(ex.getMessage());
//        }
//        return ResponseEntity.ok().body(result);
//
//    }

//    @PostMapping("/data-sub-processors/execute")
//    @Timed
//    public ResponseEntity<DataProcessorResultDTO> execute(Pageable pageable, @Valid @RequestBody DataSubProcessor dataSubProcessor) throws URISyntaxException {
//        var result = dataSubProcessorCustomService.execute(dataSubProcessor, pageable);
//        return ResponseEntity.ok().body(result);
//    }
}
