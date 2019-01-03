package com.hh.sd.core.service.mapper;

import com.hh.sd.core.domain.*;
import com.hh.sd.core.service.dto.SystemQueryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemQuery and its DTO SystemQueryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemQueryMapper extends EntityMapper<SystemQueryDTO, SystemQuery> {



    default SystemQuery fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemQuery systemQuery = new SystemQuery();
        systemQuery.setId(id);
        return systemQuery;
    }
}
