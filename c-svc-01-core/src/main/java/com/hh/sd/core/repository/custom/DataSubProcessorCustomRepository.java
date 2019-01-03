package com.hh.sd.core.repository.custom;

import com.hh.sd.core.domain.DataSubProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DataSubProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataSubProcessorCustomRepository extends JpaRepository<DataSubProcessor, Long>, JpaSpecificationExecutor<DataSubProcessor> {

    List<DataSubProcessor> findByDataProcessorId(Long dataProcessorId);
}
