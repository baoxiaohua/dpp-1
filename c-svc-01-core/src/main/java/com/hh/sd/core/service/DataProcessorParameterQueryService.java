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

import com.hh.sd.core.domain.DataProcessorParameter;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.DataProcessorParameterRepository;
import com.hh.sd.core.service.dto.DataProcessorParameterCriteria;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.service.mapper.DataProcessorParameterMapper;

/**
 * Service for executing complex queries for DataProcessorParameter entities in the database.
 * The main input is a {@link DataProcessorParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataProcessorParameterDTO} or a {@link Page} of {@link DataProcessorParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataProcessorParameterQueryService extends QueryService<DataProcessorParameter> {

    private final Logger log = LoggerFactory.getLogger(DataProcessorParameterQueryService.class);

    private final DataProcessorParameterRepository dataProcessorParameterRepository;

    private final DataProcessorParameterMapper dataProcessorParameterMapper;

    public DataProcessorParameterQueryService(DataProcessorParameterRepository dataProcessorParameterRepository, DataProcessorParameterMapper dataProcessorParameterMapper) {
        this.dataProcessorParameterRepository = dataProcessorParameterRepository;
        this.dataProcessorParameterMapper = dataProcessorParameterMapper;
    }

    /**
     * Return a {@link List} of {@link DataProcessorParameterDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataProcessorParameterDTO> findByCriteria(DataProcessorParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataProcessorParameter> specification = createSpecification(criteria);
        return dataProcessorParameterMapper.toDto(dataProcessorParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataProcessorParameterDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataProcessorParameterDTO> findByCriteria(DataProcessorParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataProcessorParameter> specification = createSpecification(criteria);
        return dataProcessorParameterRepository.findAll(specification, page)
            .map(dataProcessorParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataProcessorParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataProcessorParameter> specification = createSpecification(criteria);
        return dataProcessorParameterRepository.count(specification);
    }

    /**
     * Function to convert DataProcessorParameterCriteria to a {@link Specification}
     */
    private Specification<DataProcessorParameter> createSpecification(DataProcessorParameterCriteria criteria) {
        Specification<DataProcessorParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataProcessorParameter_.id));
            }
            if (criteria.getDataProcessorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataProcessorId(), DataProcessorParameter_.dataProcessorId));
            }
        }
        return specification;
    }
}
