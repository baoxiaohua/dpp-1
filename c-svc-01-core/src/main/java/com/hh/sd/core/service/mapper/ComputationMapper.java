package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.ComputationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Computation and its DTO ComputationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComputationMapper extends EntityMapper<ComputationDTO, Computation> {



    default Computation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Computation computation = new Computation();
        computation.setId(id);
        return computation;
    }
}
