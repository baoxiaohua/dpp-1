package com.hh.sd.core.service;

import com.hh.sd.core.domain.DataProcessorParameter;
import com.hh.sd.core.repository.DataProcessorParameterRepository;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.service.mapper.DataProcessorParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DataProcessorParameter.
 */
@Service
@Transactional
public class DataProcessorParameterService {

    private final Logger log = LoggerFactory.getLogger(DataProcessorParameterService.class);

    private final DataProcessorParameterRepository dataProcessorParameterRepository;

    private final DataProcessorParameterMapper dataProcessorParameterMapper;

    public DataProcessorParameterService(DataProcessorParameterRepository dataProcessorParameterRepository, DataProcessorParameterMapper dataProcessorParameterMapper) {
        this.dataProcessorParameterRepository = dataProcessorParameterRepository;
        this.dataProcessorParameterMapper = dataProcessorParameterMapper;
    }

    /**
     * Save a dataProcessorParameter.
     *
     * @param dataProcessorParameterDTO the entity to save
     * @return the persisted entity
     */
    public DataProcessorParameterDTO save(DataProcessorParameterDTO dataProcessorParameterDTO) {
        log.debug("Request to save DataProcessorParameter : {}", dataProcessorParameterDTO);

        DataProcessorParameter dataProcessorParameter = dataProcessorParameterMapper.toEntity(dataProcessorParameterDTO);
        dataProcessorParameter = dataProcessorParameterRepository.save(dataProcessorParameter);
        return dataProcessorParameterMapper.toDto(dataProcessorParameter);
    }

    /**
     * Get all the dataProcessorParameters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataProcessorParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataProcessorParameters");
        return dataProcessorParameterRepository.findAll(pageable)
            .map(dataProcessorParameterMapper::toDto);
    }


    /**
     * Get one dataProcessorParameter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DataProcessorParameterDTO> findOne(Long id) {
        log.debug("Request to get DataProcessorParameter : {}", id);
        return dataProcessorParameterRepository.findById(id)
            .map(dataProcessorParameterMapper::toDto);
    }

    /**
     * Delete the dataProcessorParameter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataProcessorParameter : {}", id);
        dataProcessorParameterRepository.deleteById(id);
    }
}
