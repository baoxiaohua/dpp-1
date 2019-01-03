package com.hh.sd.core.repository;

import com.hh.sd.core.domain.DataSubProcessor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataSubProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataSubProcessorRepository extends JpaRepository<DataSubProcessor, Long>, JpaSpecificationExecutor<DataSubProcessor> {

}
