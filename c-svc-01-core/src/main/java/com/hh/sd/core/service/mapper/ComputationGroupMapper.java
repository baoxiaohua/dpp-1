package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.ComputationGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ComputationGroup and its DTO ComputationGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComputationGroupMapper extends EntityMapper<ComputationGroupDTO, ComputationGroup> {



    default ComputationGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ComputationGroup computationGroup = new ComputationGroup();
        computationGroup.setId(id);
        return computationGroup;
    }
}
