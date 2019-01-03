package com.hh.sd.core.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.hh.sd.core.service.SystemQueryService;
import com.hh.sd.core.service.custom.SystemQueryCustomService;
import com.hh.sd.core.service.dto.SystemQueryCriteria;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.web.rest.errors.BadRequestAlertException;
import com.hh.sd.core.web.rest.util.HeaderUtil;
import com.hh.sd.core.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing SystemQuery.
 */
@RestController
@RequestMapping("/api")
public class SystemQueryCustomResource {

    private final Logger log = LoggerFactory.getLogger(SystemQueryCustomResource.class);

    private static final String ENTITY_NAME = "coreSystemQuery";

    private final SystemQueryCustomService systemQueryCustomService;

    public SystemQueryCustomResource(SystemQueryCustomService systemQueryCustomService) {
        this.systemQueryCustomService = systemQueryCustomService;
    }

    @PostMapping("/system-queries/query")
    @Timed
    public ResponseEntity<List<Map<String, Object>>> query(String identifier, Pageable pageable, @RequestBody Map<String, Object> criteria) throws URISyntaxException {
        log.debug("REST request to do custom query : {}", criteria, pageable);

        try {
            Page<Map<String, Object>> page = systemQueryCustomService.query(identifier, pageable, criteria);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/custom-query");
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
        catch (Exception ex){
            throw new BadRequestAlertException(ex.getMessage(),"","");
        }
    }

    @GetMapping("/system-queries/upsert")
    @Timed
    public ResponseEntity<SystemQueryDTO> upsert(Long id, String identifier, String definition, String roles) {
        log.debug("REST request to upsert SystemQuery");

        SystemQueryDTO systemQueryDTO=systemQueryCustomService.upsert(id, identifier, definition, roles);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemQueryDTO.getId().toString()))
            .body(systemQueryDTO);
    }
}
