package com.hh.sd.core.service;

import com.hh.sd.core.domain.DataProcessor;
import com.hh.sd.core.repository.DataProcessorRepository;
import com.hh.sd.core.service.dto.DataProcessorDTO;
import com.hh.sd.core.service.mapper.DataProcessorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DataProcessor.
 */
@Service
@Transactional
public class DataProcessorService {

    private final Logger log = LoggerFactory.getLogger(DataProcessorService.class);

    private final DataProcessorRepository dataProcessorRepository;

    private final DataProcessorMapper dataProcessorMapper;

    public DataProcessorService(DataProcessorRepository dataProcessorRepository, DataProcessorMapper dataProcessorMapper) {
        this.dataProcessorRepository = dataProcessorRepository;
        this.dataProcessorMapper = dataProcessorMapper;
    }

    /**
     * Save a dataProcessor.
     *
     * @param dataProcessorDTO the entity to save
     * @return the persisted entity
     */
    public DataProcessorDTO save(DataProcessorDTO dataProcessorDTO) {
        log.debug("Request to save DataProcessor : {}", dataProcessorDTO);

        DataProcessor dataProcessor = dataProcessorMapper.toEntity(dataProcessorDTO);
        dataProcessor = dataProcessorRepository.save(dataProcessor);
        return dataProcessorMapper.toDto(dataProcessor);
    }

    /**
     * Get all the dataProcessors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataProcessorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataProcessors");
        return dataProcessorRepository.findAll(pageable)
            .map(dataProcessorMapper::toDto);
    }


    /**
     * Get one dataProcessor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DataProcessorDTO> findOne(Long id) {
        log.debug("Request to get DataProcessor : {}", id);
        return dataProcessorRepository.findById(id)
            .map(dataProcessorMapper::toDto);
    }

    /**
     * Delete the dataProcessor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataProcessor : {}", id);
        dataProcessorRepository.deleteById(id);
    }
}
