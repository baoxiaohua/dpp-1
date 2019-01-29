package com.hh.sd.core.repository;

import com.hh.sd.core.domain.DataProcessorParameter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataProcessorParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataProcessorParameterRepository extends JpaRepository<DataProcessorParameter, Long>, JpaSpecificationExecutor<DataProcessorParameter> {

}
