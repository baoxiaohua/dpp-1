package com.hh.sd.core.service;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.repository.DataSubProcessorRepository;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import com.hh.sd.core.service.mapper.DataSubProcessorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DataSubProcessor.
 */
@Service
@Transactional
public class DataSubProcessorService {

    private final Logger log = LoggerFactory.getLogger(DataSubProcessorService.class);

    private final DataSubProcessorRepository dataSubProcessorRepository;

    private final DataSubProcessorMapper dataSubProcessorMapper;

    public DataSubProcessorService(DataSubProcessorRepository dataSubProcessorRepository, DataSubProcessorMapper dataSubProcessorMapper) {
        this.dataSubProcessorRepository = dataSubProcessorRepository;
        this.dataSubProcessorMapper = dataSubProcessorMapper;
    }

    /**
     * Save a dataSubProcessor.
     *
     * @param dataSubProcessorDTO the entity to save
     * @return the persisted entity
     */
    public DataSubProcessorDTO save(DataSubProcessorDTO dataSubProcessorDTO) {
        log.debug("Request to save DataSubProcessor : {}", dataSubProcessorDTO);

        DataSubProcessor dataSubProcessor = dataSubProcessorMapper.toEntity(dataSubProcessorDTO);
        dataSubProcessor = dataSubProcessorRepository.save(dataSubProcessor);
        return dataSubProcessorMapper.toDto(dataSubProcessor);
    }

    /**
     * Get all the dataSubProcessors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataSubProcessorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataSubProcessors");
        return dataSubProcessorRepository.findAll(pageable)
            .map(dataSubProcessorMapper::toDto);
    }


    /**
     * Get one dataSubProcessor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DataSubProcessorDTO> findOne(Long id) {
        log.debug("Request to get DataSubProcessor : {}", id);
        return dataSubProcessorRepository.findById(id)
            .map(dataSubProcessorMapper::toDto);
    }

    /**
     * Delete the dataSubProcessor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataSubProcessor : {}", id);
        dataSubProcessorRepository.deleteById(id);
    }
}
