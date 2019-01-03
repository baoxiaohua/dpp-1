package com.hh.sd.core.repository;

import com.hh.sd.core.domain.DataProcessor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataProcessorRepository extends JpaRepository<DataProcessor, Long>, JpaSpecificationExecutor<DataProcessor> {

}
