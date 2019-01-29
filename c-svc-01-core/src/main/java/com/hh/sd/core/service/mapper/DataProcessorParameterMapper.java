package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataProcessorParameter and its DTO DataProcessorParameterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataProcessorParameterMapper extends EntityMapper<DataProcessorParameterDTO, DataProcessorParameter> {



    default DataProcessorParameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataProcessorParameter dataProcessorParameter = new DataProcessorParameter();
        dataProcessorParameter.setId(id);
        return dataProcessorParameter;
    }
}
