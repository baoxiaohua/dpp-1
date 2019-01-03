package com.hh.sd.core.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.DataSubProcessorRepository;
import com.hh.sd.core.service.dto.DataSubProcessorCriteria;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import com.hh.sd.core.service.mapper.DataSubProcessorMapper;

/**
 * Service for executing complex queries for DataSubProcessor entities in the database.
 * The main input is a {@link DataSubProcessorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataSubProcessorDTO} or a {@link Page} of {@link DataSubProcessorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataSubProcessorQueryService extends QueryService<DataSubProcessor> {

    private final Logger log = LoggerFactory.getLogger(DataSubProcessorQueryService.class);

    private final DataSubProcessorRepository dataSubProcessorRepository;

    private final DataSubProcessorMapper dataSubProcessorMapper;

    public DataSubProcessorQueryService(DataSubProcessorRepository dataSubProcessorRepository, DataSubProcessorMapper dataSubProcessorMapper) {
        this.dataSubProcessorRepository = dataSubProcessorRepository;
        this.dataSubProcessorMapper = dataSubProcessorMapper;
    }

    /**
     * Return a {@link List} of {@link DataSubProcessorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataSubProcessorDTO> findByCriteria(DataSubProcessorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataSubProcessor> specification = createSpecification(criteria);
        return dataSubProcessorMapper.toDto(dataSubProcessorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataSubProcessorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataSubProcessorDTO> findByCriteria(DataSubProcessorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataSubProcessor> specification = createSpecification(criteria);
        return dataSubProcessorRepository.findAll(specification, page)
            .map(dataSubProcessorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataSubProcessorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataSubProcessor> specification = createSpecification(criteria);
        return dataSubProcessorRepository.count(specification);
    }

    /**
     * Function to convert DataSubProcessorCriteria to a {@link Specification}
     */
    private Specification<DataSubProcessor> createSpecification(DataSubProcessorCriteria criteria) {
        Specification<DataSubProcessor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataSubProcessor_.id));
            }
            if (criteria.getDataProcessorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataProcessorId(), DataSubProcessor_.dataProcessorId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataSubProcessor_.name));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), DataSubProcessor_.sequence));
            }
            if (criteria.getDataProcessorType() != null) {
                specification = specification.and(buildSpecification(criteria.getDataProcessorType(), DataSubProcessor_.dataProcessorType));
            }
            if (criteria.getOutputAsTable() != null) {
                specification = specification.and(buildSpecification(criteria.getOutputAsTable(), DataSubProcessor_.outputAsTable));
            }
            if (criteria.getOutputAsObject() != null) {
                specification = specification.and(buildSpecification(criteria.getOutputAsObject(), DataSubProcessor_.outputAsObject));
            }
            if (criteria.getOutputAsResult() != null) {
                specification = specification.and(buildSpecification(criteria.getOutputAsResult(), DataSubProcessor_.outputAsResult));
            }
            if (criteria.getCreateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTs(), DataSubProcessor_.createTs));
            }
            if (criteria.getCreateBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreateBy(), DataSubProcessor_.createBy));
            }
            if (criteria.getUpdateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTs(), DataSubProcessor_.updateTs));
            }
            if (criteria.getUpdateBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdateBy(), DataSubProcessor_.updateBy));
            }
        }
        return specification;
    }
}
