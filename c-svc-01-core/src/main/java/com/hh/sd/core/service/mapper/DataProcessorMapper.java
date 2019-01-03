package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.DataProcessorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataProcessor and its DTO DataProcessorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataProcessorMapper extends EntityMapper<DataProcessorDTO, DataProcessor> {



    default DataProcessor fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.setId(id);
        return dataProcessor;
    }
}
