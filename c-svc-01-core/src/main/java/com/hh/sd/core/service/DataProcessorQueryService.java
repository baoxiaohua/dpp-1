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

import com.hh.sd.core.domain.DataProcessor;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.DataProcessorRepository;
import com.hh.sd.core.service.dto.DataProcessorCriteria;
import com.hh.sd.core.service.dto.DataProcessorDTO;
import com.hh.sd.core.service.mapper.DataProcessorMapper;

/**
 * Service for executing complex queries for DataProcessor entities in the database.
 * The main input is a {@link DataProcessorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataProcessorDTO} or a {@link Page} of {@link DataProcessorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataProcessorQueryService extends QueryService<DataProcessor> {

    private final Logger log = LoggerFactory.getLogger(DataProcessorQueryService.class);

    private final DataProcessorRepository dataProcessorRepository;

    private final DataProcessorMapper dataProcessorMapper;

    public DataProcessorQueryService(DataProcessorRepository dataProcessorRepository, DataProcessorMapper dataProcessorMapper) {
        this.dataProcessorRepository = dataProcessorRepository;
        this.dataProcessorMapper = dataProcessorMapper;
    }

    /**
     * Return a {@link List} of {@link DataProcessorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataProcessorDTO> findByCriteria(DataProcessorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataProcessor> specification = createSpecification(criteria);
        return dataProcessorMapper.toDto(dataProcessorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataProcessorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataProcessorDTO> findByCriteria(DataProcessorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataProcessor> specification = createSpecification(criteria);
        return dataProcessorRepository.findAll(specification, page)
            .map(dataProcessorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataProcessorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataProcessor> specification = createSpecification(criteria);
        return dataProcessorRepository.count(specification);
    }

    /**
     * Function to convert DataProcessorCriteria to a {@link Specification}
     */
    private Specification<DataProcessor> createSpecification(DataProcessorCriteria criteria) {
        Specification<DataProcessor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataProcessor_.id));
            }
            if (criteria.getNameSpace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameSpace(), DataProcessor_.nameSpace));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), DataProcessor_.identifier));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataProcessor_.name));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), DataProcessor_.state));
            }
            if (criteria.getRestApi() != null) {
                specification = specification.and(buildSpecification(criteria.getRestApi(), DataProcessor_.restApi));
            }
            if (criteria.getCreateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTs(), DataProcessor_.createTs));
            }
            if (criteria.getCreateBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreateBy(), DataProcessor_.createBy));
            }
            if (criteria.getUpdateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTs(), DataProcessor_.updateTs));
            }
            if (criteria.getUpdateBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdateBy(), DataProcessor_.updateBy));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), DataProcessor_.deleted));
            }
        }
        return specification;
    }
}
