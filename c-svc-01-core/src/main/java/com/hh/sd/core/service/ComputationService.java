package com.hh.sd.core.service;

import com.hh.sd.core.domain.Computation;
import com.hh.sd.core.repository.ComputationRepository;
import com.hh.sd.core.service.dto.ComputationDTO;
import com.hh.sd.core.service.mapper.ComputationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Computation.
 */
@Service
@Transactional
public class ComputationService {

    private final Logger log = LoggerFactory.getLogger(ComputationService.class);

    private final ComputationRepository computationRepository;

    private final ComputationMapper computationMapper;

    public ComputationService(ComputationRepository computationRepository, ComputationMapper computationMapper) {
        this.computationRepository = computationRepository;
        this.computationMapper = computationMapper;
    }

    /**
     * Save a computation.
     *
     * @param computationDTO the entity to save
     * @return the persisted entity
     */
    public ComputationDTO save(ComputationDTO computationDTO) {
        log.debug("Request to save Computation : {}", computationDTO);

        Computation computation = computationMapper.toEntity(computationDTO);
        computation = computationRepository.save(computation);
        return computationMapper.toDto(computation);
    }

    /**
     * Get all the computations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ComputationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Computations");
        return computationRepository.findAll(pageable)
            .map(computationMapper::toDto);
    }


    /**
     * Get one computation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ComputationDTO> findOne(Long id) {
        log.debug("Request to get Computation : {}", id);
        return computationRepository.findById(id)
            .map(computationMapper::toDto);
    }

    /**
     * Delete the computation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Computation : {}", id);
        computationRepository.deleteById(id);
    }
}
