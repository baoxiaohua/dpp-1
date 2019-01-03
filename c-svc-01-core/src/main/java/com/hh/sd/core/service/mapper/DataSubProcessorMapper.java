package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataSubProcessor and its DTO DataSubProcessorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataSubProcessorMapper extends EntityMapper<DataSubProcessorDTO, DataSubProcessor> {



    default DataSubProcessor fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataSubProcessor dataSubProcessor = new DataSubProcessor();
        dataSubProcessor.setId(id);
        return dataSubProcessor;
    }
}
