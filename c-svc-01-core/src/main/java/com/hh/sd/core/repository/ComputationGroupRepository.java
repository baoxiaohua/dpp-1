package com.hh.sd.core.repository;

import com.hh.sd.core.domain.ComputationGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComputationGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComputationGroupRepository extends JpaRepository<ComputationGroup, Long>, JpaSpecificationExecutor<ComputationGroup> {

}
