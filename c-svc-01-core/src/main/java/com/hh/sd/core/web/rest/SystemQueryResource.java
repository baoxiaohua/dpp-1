package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.SystemQueryService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.service.dto.SystemQueryCriteria;
import com.hh.sd.core.service.SystemQueryQueryService;
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
 * REST controller for managing SystemQuery.
 */
@RestController
@RequestMapping("/api")
public class SystemQueryResource {

    private final Logger log = LoggerFactory.getLogger(SystemQueryResource.class);

    private static final String ENTITY_NAME = "coreSystemQuery";

    private final SystemQueryService systemQueryService;

    private final SystemQueryQueryService systemQueryQueryService;

    public SystemQueryResource(SystemQueryService systemQueryService, SystemQueryQueryService systemQueryQueryService) {
        this.systemQueryService = systemQueryService;
        this.systemQueryQueryService = systemQueryQueryService;
    }

    /**
     * POST  /system-queries : Create a new systemQuery.
     *
     * @param systemQueryDTO the systemQueryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemQueryDTO, or with status 400 (Bad Request) if the systemQuery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-queries")
    @Timed
    public ResponseEntity<SystemQueryDTO> createSystemQuery(@Valid @RequestBody SystemQueryDTO systemQueryDTO) throws URISyntaxException {
        log.debug("REST request to save SystemQuery : {}", systemQueryDTO);
        if (systemQueryDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemQueryDTO result = systemQueryService.save(systemQueryDTO);
        return ResponseEntity.created(new URI("/api/system-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-queries : Updates an existing systemQuery.
     *
     * @param systemQueryDTO the systemQueryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemQueryDTO,
     * or with status 400 (Bad Request) if the systemQueryDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemQueryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-queries")
    @Timed
    public ResponseEntity<SystemQueryDTO> updateSystemQuery(@Valid @RequestBody SystemQueryDTO systemQueryDTO) throws URISyntaxException {
        log.debug("REST request to update SystemQuery : {}", systemQueryDTO);
        if (systemQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemQueryDTO result = systemQueryService.save(systemQueryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemQueryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-queries : get all the systemQueries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of systemQueries in body
     */
    @GetMapping("/system-queries")
    @Timed
    public ResponseEntity<List<SystemQueryDTO>> getAllSystemQueries(SystemQueryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemQueries by criteria: {}", criteria);
        Page<SystemQueryDTO> page = systemQueryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-queries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /system-queries/count : count all the systemQueries.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/system-queries/count")
    @Timed
    public ResponseEntity<Long> countSystemQueries(SystemQueryCriteria criteria) {
        log.debug("REST request to count SystemQueries by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemQueryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /system-queries/:id : get the "id" systemQuery.
     *
     * @param id the id of the systemQueryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemQueryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-queries/{id}")
    @Timed
    public ResponseEntity<SystemQueryDTO> getSystemQuery(@PathVariable Long id) {
        log.debug("REST request to get SystemQuery : {}", id);
        Optional<SystemQueryDTO> systemQueryDTO = systemQueryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemQueryDTO);
    }

    /**
     * DELETE  /system-queries/:id : delete the "id" systemQuery.
     *
     * @param id the id of the systemQueryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-queries/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystemQuery(@PathVariable Long id) {
        log.debug("REST request to delete SystemQuery : {}", id);
        systemQueryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
