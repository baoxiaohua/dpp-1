package com.hh.sd.core.repository;

import com.hh.sd.core.domain.Computation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Computation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComputationRepository extends JpaRepository<Computation, Long>, JpaSpecificationExecutor<Computation> {

}
