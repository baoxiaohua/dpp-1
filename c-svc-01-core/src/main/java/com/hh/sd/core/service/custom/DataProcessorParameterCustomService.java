package com.hh.sd.core.service.custom;

import com.hh.sd.core.repository.DataProcessorParameterRepository;
import com.hh.sd.core.repository.custom.DataProcessorParameterCustomRepository;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.service.mapper.DataProcessorParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DataProcessorParameter.
 */
@Service
@Transactional
public class DataProcessorParameterCustomService {

    private final Logger log = LoggerFactory.getLogger(DataProcessorParameterCustomService.class);

    private final DataProcessorParameterRepository dataProcessorParameterRepository;
    private final DataProcessorParameterCustomRepository dataProcessorParameterCustomRepository;
    private final DataProcessorParameterMapper dataProcessorParameterMapper;

    public DataProcessorParameterCustomService(DataProcessorParameterRepository dataProcessorParameterRepository, DataProcessorParameterCustomRepository dataProcessorParameterCustomRepository, DataProcessorParameterMapper dataProcessorParameterMapper) {
        this.dataProcessorParameterRepository = dataProcessorParameterRepository;
        this.dataProcessorParameterCustomRepository = dataProcessorParameterCustomRepository;
        this.dataProcessorParameterMapper = dataProcessorParameterMapper;
    }


    @Transactional(readOnly = true)
    public Optional<DataProcessorParameterDTO> findByDataProcessorId(Long dataProcessorId) {
        log.debug("Request to get DataProcessorParameter by dataProcessorId: {}", dataProcessorId);
        return dataProcessorParameterCustomRepository.findByDataProcessorId(dataProcessorId)
            .map(dataProcessorParameterMapper::toDto);
    }
}
