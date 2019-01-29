package com.hh.sd.core.service.custom;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.repository.DataSubProcessorRepository;
import com.hh.sd.core.repository.custom.DataSubProcessorCustomRepository;
import com.hh.sd.core.repository.implement.DataProcessorRepositoryImpl;
import com.hh.sd.core.repository.implement.SharedRepository;
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
    private final DataProcessorRepositoryImpl dataProcessorRepositoryImpl;
    private final SharedRepository sharedRepository;

    public DataSubProcessorCustomService(DataSubProcessorRepository dataSubProcessorRepository, DataSubProcessorMapper dataSubProcessorMapper, DataSubProcessorCustomRepository dataSubProcessorCustomRepository, DataProcessorRepositoryImpl dataProcessorRepositoryImpl, SharedRepository sharedRepository) {
        this.dataSubProcessorRepository = dataSubProcessorRepository;
        this.dataSubProcessorMapper = dataSubProcessorMapper;
        this.dataSubProcessorCustomRepository = dataSubProcessorCustomRepository;
        this.dataProcessorRepositoryImpl = dataProcessorRepositoryImpl;
        this.sharedRepository = sharedRepository;
    }

    public List<DataSubProcessorDTO> reorder(Long dataProcessorId, List<Long> idList) {
        var dataSubProcessorList = dataSubProcessorCustomRepository.findByDataProcessorIdOrderBySequenceAsc(dataProcessorId);

        dataSubProcessorList.forEach(item -> item.setSequence(idList.indexOf(item.getId()) + 1));

        dataSubProcessorList = dataSubProcessorRepository.saveAll(dataSubProcessorList);
        dataSubProcessorList.sort(Comparator.comparing(DataSubProcessor::getSequence));

        return dataSubProcessorMapper.toDto(dataSubProcessorList);
    }

//    public DataProcessorResultDTO execute(Long id, Map<String, Object> critiera) {
//        var resultDto = new DataProcessorResultDTO();
//
//        var debug = (Boolean) critiera.getOrDefault("debug", false);
//
//        var dataSubProcessor = dataSubProcessorRepository.findById(id).get();
//        var dataSubProcessorResult = new HashMap<String, Object>();
//
//        var sorts = (List<String>) critiera.getOrDefault("sort", null);
//        var pageNum = (int) critiera.getOrDefault("pageNum", -1);
//        var pageSize = (int) critiera.getOrDefault("pageSize", -1);
//
//
//
//        resultDto.setSuccess(true);
//
//        var pageDto = new PageDTO();
//        pageDto.setTotal(total);
//        pageDto.setData(result);
//        dataSubProcessorResult.put(dataSubProcessor.getName(), pageDto);
//
//        resultDto.setResults(dataSubProcessorResult);
//        return resultDto;
//    }

//    public DataProcessorResultDTO execute(DataSubProcessor dataSubProcessor, Pageable pageable) {
//        try {
//            var statement = (Select)CCJSqlParserUtil.parse(dataSubProcessor.getCode());
//
//            var queryResult = dataProcessorRepositoryImpl.query(dataSubProcessor.getCode(), pageable, null);
//
//            var dataProcessResult = new HashMap<String, Page<Map<String, Object>>>();
//            dataProcessResult.put(dataSubProcessor.getName(), queryResult);
//
//            var result = new DataProcessorResultDTO() {};
//            result.setSuccess(true);
//            result.setError(null);
//            result.setResults(dataProcessResult);
//            return result;
//        }
//        catch (Exception ex) {
//            var result = new DataProcessorResultDTO() {};
//            result.setSuccess(false);
//            result.setError(ex.getMessage());
//            result.setResults(null);
//            return result;
//        }
//    }
}
