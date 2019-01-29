package com.hh.sd.core.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.DataProcessorParameterQueryService;
import com.hh.sd.core.service.DataProcessorParameterService;
import com.hh.sd.core.service.custom.DataProcessorParameterCustomService;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing DataProcessorParameter.
 */
@RestController
@RequestMapping("/api")
public class DataProcessorParameterCustomResource {

    private final Logger log = LoggerFactory.getLogger(DataProcessorParameterCustomResource.class);

    private static final String ENTITY_NAME = "coreDataProcessorParameter";

    private final DataProcessorParameterService dataProcessorParameterService;
    private final DataProcessorParameterCustomService dataProcessorParameterCustomService;
    private final DataProcessorParameterQueryService dataProcessorParameterQueryService;

    public DataProcessorParameterCustomResource(DataProcessorParameterService dataProcessorParameterService, DataProcessorParameterCustomService dataProcessorParameterCustomService, DataProcessorParameterQueryService dataProcessorParameterQueryService) {
        this.dataProcessorParameterService = dataProcessorParameterService;
        this.dataProcessorParameterCustomService = dataProcessorParameterCustomService;
        this.dataProcessorParameterQueryService = dataProcessorParameterQueryService;
    }

    @GetMapping("/data-processor-parameters/findByDataProcessorId")
    @Timed
    public ResponseEntity<DataProcessorParameterDTO> findByDataProcessorId(@RequestParam Long dataProcessorId) {
        log.debug("REST request to get DataProcessorParameter : {}", dataProcessorId);
        Optional<DataProcessorParameterDTO> dataProcessorParameterDTO = dataProcessorParameterCustomService.findByDataProcessorId(dataProcessorId);

        return ResponseEntity.ok().body(dataProcessorParameterDTO.orElse(null));
    }

    @PostMapping("/data-processor-parameters/save")
    @Timed
    public ResponseEntity<DataProcessorParameterDTO> saveDataProcessorParameter(@Valid @RequestBody DataProcessorParameterDTO dataProcessorParameterDTO) throws URISyntaxException {
        log.debug("REST request to save DataProcessorParameter : {}", dataProcessorParameterDTO);

        DataProcessorParameterDTO result = dataProcessorParameterService.save(dataProcessorParameterDTO);
        return ResponseEntity.created(new URI("/api/data-processor-parameters/save/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
