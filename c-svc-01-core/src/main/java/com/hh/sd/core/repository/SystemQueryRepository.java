package com.hh.sd.core.repository;

import com.hh.sd.core.domain.SystemQuery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemQueryRepository extends JpaRepository<SystemQuery, Long>, JpaSpecificationExecutor<SystemQuery> {

}
