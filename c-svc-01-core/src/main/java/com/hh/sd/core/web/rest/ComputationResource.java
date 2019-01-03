package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.ComputationService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.ComputationDTO;
import com.hh.sd.core.service.dto.ComputationCriteria;
import com.hh.sd.core.service.ComputationQueryService;
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
 * REST controller for managing Computation.
 */
@RestController
@RequestMapping("/api")
public class ComputationResource {

    private final Logger log = LoggerFactory.getLogger(ComputationResource.class);

    private static final String ENTITY_NAME = "coreComputation";

    private final ComputationService computationService;

    private final ComputationQueryService computationQueryService;

    public ComputationResource(ComputationService computationService, ComputationQueryService computationQueryService) {
        this.computationService = computationService;
        this.computationQueryService = computationQueryService;
    }

    /**
     * POST  /computations : Create a new computation.
     *
     * @param computationDTO the computationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new computationDTO, or with status 400 (Bad Request) if the computation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/computations")
    @Timed
    public ResponseEntity<ComputationDTO> createComputation(@Valid @RequestBody ComputationDTO computationDTO) throws URISyntaxException {
        log.debug("REST request to save Computation : {}", computationDTO);
        if (computationDTO.getId() != null) {
            throw new BadRequestAlertException("A new computation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComputationDTO result = computationService.save(computationDTO);
        return ResponseEntity.created(new URI("/api/computations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /computations : Updates an existing computation.
     *
     * @param computationDTO the computationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated computationDTO,
     * or with status 400 (Bad Request) if the computationDTO is not valid,
     * or with status 500 (Internal Server Error) if the computationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/computations")
    @Timed
    public ResponseEntity<ComputationDTO> updateComputation(@Valid @RequestBody ComputationDTO computationDTO) throws URISyntaxException {
        log.debug("REST request to update Computation : {}", computationDTO);
        if (computationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComputationDTO result = computationService.save(computationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, computationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /computations : get all the computations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of computations in body
     */
    @GetMapping("/computations")
    @Timed
    public ResponseEntity<List<ComputationDTO>> getAllComputations(ComputationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Computations by criteria: {}", criteria);
        Page<ComputationDTO> page = computationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/computations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /computations/count : count all the computations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/computations/count")
    @Timed
    public ResponseEntity<Long> countComputations(ComputationCriteria criteria) {
        log.debug("REST request to count Computations by criteria: {}", criteria);
        return ResponseEntity.ok().body(computationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /computations/:id : get the "id" computation.
     *
     * @param id the id of the computationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the computationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/computations/{id}")
    @Timed
    public ResponseEntity<ComputationDTO> getComputation(@PathVariable Long id) {
        log.debug("REST request to get Computation : {}", id);
        Optional<ComputationDTO> computationDTO = computationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(computationDTO);
    }

    /**
     * DELETE  /computations/:id : delete the "id" computation.
     *
     * @param id the id of the computationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/computations/{id}")
    @Timed
    public ResponseEntity<Void> deleteComputation(@PathVariable Long id) {
        log.debug("REST request to delete Computation : {}", id);
        computationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
