package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.DataProcessorService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.DataProcessorDTO;
import com.hh.sd.core.service.dto.DataProcessorCriteria;
import com.hh.sd.core.service.DataProcessorQueryService;
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
 * REST controller for managing DataProcessor.
 */
@RestController
@RequestMapping("/api")
public class DataProcessorResource {

    private final Logger log = LoggerFactory.getLogger(DataProcessorResource.class);

    private static final String ENTITY_NAME = "coreDataProcessor";

    private final DataProcessorService dataProcessorService;

    private final DataProcessorQueryService dataProcessorQueryService;

    public DataProcessorResource(DataProcessorService dataProcessorService, DataProcessorQueryService dataProcessorQueryService) {
        this.dataProcessorService = dataProcessorService;
        this.dataProcessorQueryService = dataProcessorQueryService;
    }

    /**
     * POST  /data-processors : Create a new dataProcessor.
     *
     * @param dataProcessorDTO the dataProcessorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataProcessorDTO, or with status 400 (Bad Request) if the dataProcessor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-processors")
    @Timed
    public ResponseEntity<DataProcessorDTO> createDataProcessor(@Valid @RequestBody DataProcessorDTO dataProcessorDTO) throws URISyntaxException {
        log.debug("REST request to save DataProcessor : {}", dataProcessorDTO);
        if (dataProcessorDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataProcessor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataProcessorDTO result = dataProcessorService.save(dataProcessorDTO);
        return ResponseEntity.created(new URI("/api/data-processors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-processors : Updates an existing dataProcessor.
     *
     * @param dataProcessorDTO the dataProcessorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataProcessorDTO,
     * or with status 400 (Bad Request) if the dataProcessorDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataProcessorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-processors")
    @Timed
    public ResponseEntity<DataProcessorDTO> updateDataProcessor(@Valid @RequestBody DataProcessorDTO dataProcessorDTO) throws URISyntaxException {
        log.debug("REST request to update DataProcessor : {}", dataProcessorDTO);
        if (dataProcessorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataProcessorDTO result = dataProcessorService.save(dataProcessorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataProcessorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-processors : get all the dataProcessors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataProcessors in body
     */
    @GetMapping("/data-processors")
    @Timed
    public ResponseEntity<List<DataProcessorDTO>> getAllDataProcessors(DataProcessorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DataProcessors by criteria: {}", criteria);
        Page<DataProcessorDTO> page = dataProcessorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-processors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /data-processors/count : count all the dataProcessors.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-processors/count")
    @Timed
    public ResponseEntity<Long> countDataProcessors(DataProcessorCriteria criteria) {
        log.debug("REST request to count DataProcessors by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataProcessorQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-processors/:id : get the "id" dataProcessor.
     *
     * @param id the id of the dataProcessorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataProcessorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-processors/{id}")
    @Timed
    public ResponseEntity<DataProcessorDTO> getDataProcessor(@PathVariable Long id) {
        log.debug("REST request to get DataProcessor : {}", id);
        Optional<DataProcessorDTO> dataProcessorDTO = dataProcessorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataProcessorDTO);
    }

    /**
     * DELETE  /data-processors/:id : delete the "id" dataProcessor.
     *
     * @param id the id of the dataProcessorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-processors/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataProcessor(@PathVariable Long id) {
        log.debug("REST request to delete DataProcessor : {}", id);
        dataProcessorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
