package com.hh.sd.core.repository.custom;

import com.hh.sd.core.domain.DataProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the DataProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataProcessorCustomRepository extends JpaRepository<DataProcessor, Long>, JpaSpecificationExecutor<DataProcessor> {

    Optional<DataProcessor> findByIdentifier(String identifier);
}
