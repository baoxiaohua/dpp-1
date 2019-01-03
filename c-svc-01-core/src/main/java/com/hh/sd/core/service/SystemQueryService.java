package com.hh.sd.core.service;

import com.hh.sd.core.domain.SystemQuery;
import com.hh.sd.core.repository.SystemQueryRepository;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.service.mapper.SystemQueryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SystemQuery.
 */
@Service
@Transactional
public class SystemQueryService {

    private final Logger log = LoggerFactory.getLogger(SystemQueryService.class);

    private final SystemQueryRepository systemQueryRepository;

    private final SystemQueryMapper systemQueryMapper;

    public SystemQueryService(SystemQueryRepository systemQueryRepository, SystemQueryMapper systemQueryMapper) {
        this.systemQueryRepository = systemQueryRepository;
        this.systemQueryMapper = systemQueryMapper;
    }

    /**
     * Save a systemQuery.
     *
     * @param systemQueryDTO the entity to save
     * @return the persisted entity
     */
    public SystemQueryDTO save(SystemQueryDTO systemQueryDTO) {
        log.debug("Request to save SystemQuery : {}", systemQueryDTO);

        SystemQuery systemQuery = systemQueryMapper.toEntity(systemQueryDTO);
        systemQuery = systemQueryRepository.save(systemQuery);
        return systemQueryMapper.toDto(systemQuery);
    }

    /**
     * Get all the systemQueries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemQueryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemQueries");
        return systemQueryRepository.findAll(pageable)
            .map(systemQueryMapper::toDto);
    }


    /**
     * Get one systemQuery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SystemQueryDTO> findOne(Long id) {
        log.debug("Request to get SystemQuery : {}", id);
        return systemQueryRepository.findById(id)
            .map(systemQueryMapper::toDto);
    }

    /**
     * Delete the systemQuery by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemQuery : {}", id);
        systemQueryRepository.deleteById(id);
    }
}
