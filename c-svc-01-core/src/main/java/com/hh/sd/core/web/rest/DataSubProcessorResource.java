package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.DataSubProcessorService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import com.hh.sd.core.service.dto.DataSubProcessorCriteria;
import com.hh.sd.core.service.DataSubProcessorQueryService;
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
 * REST controller for managing DataSubProcessor.
 */
@RestController
@RequestMapping("/api")
public class DataSubProcessorResource {

    private final Logger log = LoggerFactory.getLogger(DataSubProcessorResource.class);

    private static final String ENTITY_NAME = "coreDataSubProcessor";

    private final DataSubProcessorService dataSubProcessorService;

    private final DataSubProcessorQueryService dataSubProcessorQueryService;

    public DataSubProcessorResource(DataSubProcessorService dataSubProcessorService, DataSubProcessorQueryService dataSubProcessorQueryService) {
        this.dataSubProcessorService = dataSubProcessorService;
        this.dataSubProcessorQueryService = dataSubProcessorQueryService;
    }

    /**
     * POST  /data-sub-processors : Create a new dataSubProcessor.
     *
     * @param dataSubProcessorDTO the dataSubProcessorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataSubProcessorDTO, or with status 400 (Bad Request) if the dataSubProcessor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-sub-processors")
    @Timed
    public ResponseEntity<DataSubProcessorDTO> createDataSubProcessor(@Valid @RequestBody DataSubProcessorDTO dataSubProcessorDTO) throws URISyntaxException {
        log.debug("REST request to save DataSubProcessor : {}", dataSubProcessorDTO);
        if (dataSubProcessorDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataSubProcessor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataSubProcessorDTO result = dataSubProcessorService.save(dataSubProcessorDTO);
        return ResponseEntity.created(new URI("/api/data-sub-processors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-sub-processors : Updates an existing dataSubProcessor.
     *
     * @param dataSubProcessorDTO the dataSubProcessorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataSubProcessorDTO,
     * or with status 400 (Bad Request) if the dataSubProcessorDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataSubProcessorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-sub-processors")
    @Timed
    public ResponseEntity<DataSubProcessorDTO> updateDataSubProcessor(@Valid @RequestBody DataSubProcessorDTO dataSubProcessorDTO) throws URISyntaxException {
        log.debug("REST request to update DataSubProcessor : {}", dataSubProcessorDTO);
        if (dataSubProcessorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataSubProcessorDTO result = dataSubProcessorService.save(dataSubProcessorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataSubProcessorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-sub-processors : get all the dataSubProcessors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataSubProcessors in body
     */
    @GetMapping("/data-sub-processors")
    @Timed
    public ResponseEntity<List<DataSubProcessorDTO>> getAllDataSubProcessors(DataSubProcessorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DataSubProcessors by criteria: {}", criteria);
        Page<DataSubProcessorDTO> page = dataSubProcessorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-sub-processors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /data-sub-processors/count : count all the dataSubProcessors.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-sub-processors/count")
    @Timed
    public ResponseEntity<Long> countDataSubProcessors(DataSubProcessorCriteria criteria) {
        log.debug("REST request to count DataSubProcessors by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataSubProcessorQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-sub-processors/:id : get the "id" dataSubProcessor.
     *
     * @param id the id of the dataSubProcessorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataSubProcessorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-sub-processors/{id}")
    @Timed
    public ResponseEntity<DataSubProcessorDTO> getDataSubProcessor(@PathVariable Long id) {
        log.debug("REST request to get DataSubProcessor : {}", id);
        Optional<DataSubProcessorDTO> dataSubProcessorDTO = dataSubProcessorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataSubProcessorDTO);
    }

    /**
     * DELETE  /data-sub-processors/:id : delete the "id" dataSubProcessor.
     *
     * @param id the id of the dataSubProcessorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-sub-processors/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataSubProcessor(@PathVariable Long id) {
        log.debug("REST request to delete DataSubProcessor : {}", id);
        dataSubProcessorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
