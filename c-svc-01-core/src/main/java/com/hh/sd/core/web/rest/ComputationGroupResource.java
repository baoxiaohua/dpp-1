package com.hh.sd.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.ComputationGroupService;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import com.hh.sd.core.service.dto.ComputationGroupDTO;
import com.hh.sd.core.service.dto.ComputationGroupCriteria;
import com.hh.sd.core.service.ComputationGroupQueryService;
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
 * REST controller for managing ComputationGroup.
 */
@RestController
@RequestMapping("/api")
public class ComputationGroupResource {

    private final Logger log = LoggerFactory.getLogger(ComputationGroupResource.class);

    private static final String ENTITY_NAME = "coreComputationGroup";

    private final ComputationGroupService computationGroupService;

    private final ComputationGroupQueryService computationGroupQueryService;

    public ComputationGroupResource(ComputationGroupService computationGroupService, ComputationGroupQueryService computationGroupQueryService) {
        this.computationGroupService = computationGroupService;
        this.computationGroupQueryService = computationGroupQueryService;
    }

    /**
     * POST  /computation-groups : Create a new computationGroup.
     *
     * @param computationGroupDTO the computationGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new computationGroupDTO, or with status 400 (Bad Request) if the computationGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/computation-groups")
    @Timed
    public ResponseEntity<ComputationGroupDTO> createComputationGroup(@Valid @RequestBody ComputationGroupDTO computationGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ComputationGroup : {}", computationGroupDTO);
        if (computationGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new computationGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComputationGroupDTO result = computationGroupService.save(computationGroupDTO);
        return ResponseEntity.created(new URI("/api/computation-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /computation-groups : Updates an existing computationGroup.
     *
     * @param computationGroupDTO the computationGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated computationGroupDTO,
     * or with status 400 (Bad Request) if the computationGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the computationGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/computation-groups")
    @Timed
    public ResponseEntity<ComputationGroupDTO> updateComputationGroup(@Valid @RequestBody ComputationGroupDTO computationGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ComputationGroup : {}", computationGroupDTO);
        if (computationGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComputationGroupDTO result = computationGroupService.save(computationGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, computationGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /computation-groups : get all the computationGroups.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of computationGroups in body
     */
    @GetMapping("/computation-groups")
    @Timed
    public ResponseEntity<List<ComputationGroupDTO>> getAllComputationGroups(ComputationGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ComputationGroups by criteria: {}", criteria);
        Page<ComputationGroupDTO> page = computationGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/computation-groups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /computation-groups/count : count all the computationGroups.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/computation-groups/count")
    @Timed
    public ResponseEntity<Long> countComputationGroups(ComputationGroupCriteria criteria) {
        log.debug("REST request to count ComputationGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(computationGroupQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /computation-groups/:id : get the "id" computationGroup.
     *
     * @param id the id of the computationGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the computationGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/computation-groups/{id}")
    @Timed
    public ResponseEntity<ComputationGroupDTO> getComputationGroup(@PathVariable Long id) {
        log.debug("REST request to get ComputationGroup : {}", id);
        Optional<ComputationGroupDTO> computationGroupDTO = computationGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(computationGroupDTO);
    }

    /**
     * DELETE  /computation-groups/:id : delete the "id" computationGroup.
     *
     * @param id the id of the computationGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/computation-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteComputationGroup(@PathVariable Long id) {
        log.debug("REST request to delete ComputationGroup : {}", id);
        computationGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
