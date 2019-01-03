package com.hh.sd.core.service;

import com.hh.sd.core.domain.ComputationGroup;
import com.hh.sd.core.repository.ComputationGroupRepository;
import com.hh.sd.core.service.dto.ComputationGroupDTO;
import com.hh.sd.core.service.mapper.ComputationGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ComputationGroup.
 */
@Service
@Transactional
public class ComputationGroupService {

    private final Logger log = LoggerFactory.getLogger(ComputationGroupService.class);

    private final ComputationGroupRepository computationGroupRepository;

    private final ComputationGroupMapper computationGroupMapper;

    public ComputationGroupService(ComputationGroupRepository computationGroupRepository, ComputationGroupMapper computationGroupMapper) {
        this.computationGroupRepository = computationGroupRepository;
        this.computationGroupMapper = computationGroupMapper;
    }

    /**
     * Save a computationGroup.
     *
     * @param computationGroupDTO the entity to save
     * @return the persisted entity
     */
    public ComputationGroupDTO save(ComputationGroupDTO computationGroupDTO) {
        log.debug("Request to save ComputationGroup : {}", computationGroupDTO);

        ComputationGroup computationGroup = computationGroupMapper.toEntity(computationGroupDTO);
        computationGroup = computationGroupRepository.save(computationGroup);
        return computationGroupMapper.toDto(computationGroup);
    }

    /**
     * Get all the computationGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ComputationGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComputationGroups");
        return computationGroupRepository.findAll(pageable)
            .map(computationGroupMapper::toDto);
    }


    /**
     * Get one computationGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ComputationGroupDTO> findOne(Long id) {
        log.debug("Request to get ComputationGroup : {}", id);
        return computationGroupRepository.findById(id)
            .map(computationGroupMapper::toDto);
    }

    /**
     * Delete the computationGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ComputationGroup : {}", id);
        computationGroupRepository.deleteById(id);
    }
}
