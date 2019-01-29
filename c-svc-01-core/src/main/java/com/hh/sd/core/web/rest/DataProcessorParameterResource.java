package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.DataProcessorParameterService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.service.dto.DataProcessorParameterCriteria;
import com.hh.sd.core.service.DataProcessorParameterQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataProcessorParameter.
 */
@RestController
@RequestMapping("/api")
public class DataProcessorParameterResource {

    private final Logger log = LoggerFactory.getLogger(DataProcessorParameterResource.class);

    private static final String ENTITY_NAME = "coreDataProcessorParameter";

    private final DataProcessorParameterService dataProcessorParameterService;

    private final DataProcessorParameterQueryService dataProcessorParameterQueryService;

    public DataProcessorParameterResource(DataProcessorParameterService dataProcessorParameterService, DataProcessorParameterQueryService dataProcessorParameterQueryService) {
        this.dataProcessorParameterService = dataProcessorParameterService;
        this.dataProcessorParameterQueryService = dataProcessorParameterQueryService;
    }

    /**
     * POST  /data-processor-parameters : Create a new dataProcessorParameter.
     *
     * @param dataProcessorParameterDTO the dataProcessorParameterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataProcessorParameterDTO, or with status 400 (Bad Request) if the dataProcessorParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-processor-parameters")
    @Timed
    public ResponseEntity<DataProcessorParameterDTO> createDataProcessorParameter(@Valid @RequestBody DataProcessorParameterDTO dataProcessorParameterDTO) throws URISyntaxException {
        log.debug("REST request to save DataProcessorParameter : {}", dataProcessorParameterDTO);
        if (dataProcessorParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataProcessorParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataProcessorParameterDTO result = dataProcessorParameterService.save(dataProcessorParameterDTO);
        return ResponseEntity.created(new URI("/api/data-processor-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-processor-parameters : Updates an existing dataProcessorParameter.
     *
     * @param dataProcessorParameterDTO the dataProcessorParameterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataProcessorParameterDTO,
     * or with status 400 (Bad Request) if the dataProcessorParameterDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataProcessorParameterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-processor-parameters")
    @Timed
    public ResponseEntity<DataProcessorParameterDTO> updateDataProcessorParameter(@Valid @RequestBody DataProcessorParameterDTO dataProcessorParameterDTO) throws URISyntaxException {
        log.debug("REST request to update DataProcessorParameter : {}", dataProcessorParameterDTO);
        if (dataProcessorParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataProcessorParameterDTO result = dataProcessorParameterService.save(dataProcessorParameterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataProcessorParameterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-processor-parameters : get all the dataProcessorParameters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataProcessorParameters in body
     */
    @GetMapping("/data-processor-parameters")
    @Timed
    public ResponseEntity<List<DataProcessorParameterDTO>> getAllDataProcessorParameters(DataProcessorParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DataProcessorParameters by criteria: {}", criteria);
        Page<DataProcessorParameterDTO> page = dataProcessorParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-processor-parameters");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /data-processor-parameters/count : count all the dataProcessorParameters.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-processor-parameters/count")
    @Timed
    public ResponseEntity<Long> countDataProcessorParameters(DataProcessorParameterCriteria criteria) {
        log.debug("REST request to count DataProcessorParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataProcessorParameterQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-processor-parameters/:id : get the "id" dataProcessorParameter.
     *
     * @param id the id of the dataProcessorParameterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataProcessorParameterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-processor-parameters/{id}")
    @Timed
    public ResponseEntity<DataProcessorParameterDTO> getDataProcessorParameter(@PathVariable Long id) {
        log.debug("REST request to get DataProcessorParameter : {}", id);
        Optional<DataProcessorParameterDTO> dataProcessorParameterDTO = dataProcessorParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataProcessorParameterDTO);
    }

    /**
     * DELETE  /data-processor-parameters/:id : delete the "id" dataProcessorParameter.
     *
     * @param id the id of the dataProcessorParameterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-processor-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataProcessorParameter(@PathVariable Long id) {
        log.debug("REST request to delete DataProcessorParameter : {}", id);
        dataProcessorParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
