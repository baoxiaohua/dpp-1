package com.hh.sd.core.service.custom;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.repository.DataSubProcessorRepository;
import com.hh.sd.core.repository.custom.DataSubProcessorCustomRepository;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import com.hh.sd.core.service.mapper.DataSubProcessorMapper;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * Service Implementation for managing DataSubProcessor.
 */
@Service
@Transactional
public class DataSubProcessorCustomService {

    private final Logger log = LoggerFactory.getLogger(DataSubProcessorCustomService.class);

    private final DataSubProcessorRepository dataSubProcessorRepository;
    private final DataSubProcessorMapper dataSubProcessorMapper;
    private final DataSubProcessorCustomRepository dataSubProcessorCustomRepository;

    public DataSubProcessorCustomService(DataSubProcessorRepository dataSubProcessorRepository, DataSubProcessorMapper dataSubProcessorMapper, DataSubProcessorCustomRepository dataSubProcessorCustomRepository) {
        this.dataSubProcessorRepository = dataSubProcessorRepository;
        this.dataSubProcessorMapper = dataSubProcessorMapper;
        this.dataSubProcessorCustomRepository = dataSubProcessorCustomRepository;
    }

    public List<DataSubProcessorDTO> reorder(Long dataProcessorId, List<Long> idList) {
        var dataSubProcessorList = dataSubProcessorCustomRepository.findByDataProcessorId(dataProcessorId);

        dataSubProcessorList.forEach(item -> item.setSequence(idList.indexOf(item.getId()) + 1));

        dataSubProcessorList = dataSubProcessorRepository.saveAll(dataSubProcessorList);
        dataSubProcessorList.sort(Comparator.comparing(DataSubProcessor::getSequence));

        return dataSubProcessorMapper.toDto(dataSubProcessorList);
    }
}
