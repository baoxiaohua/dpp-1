package com.hh.sd.core.repository.custom;

import com.hh.sd.core.domain.DataProcessorParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DataProcessorParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataProcessorParameterCustomRepository extends JpaRepository<DataProcessorParameter, Long>, JpaSpecificationExecutor<DataProcessorParameter> {

    Optional<DataProcessorParameter> findByDataProcessorId(Long dataProcessorId);
}
