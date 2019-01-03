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

import com.hh.sd.core.domain.ComputationGroup;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.ComputationGroupRepository;
import com.hh.sd.core.service.dto.ComputationGroupCriteria;
import com.hh.sd.core.service.dto.ComputationGroupDTO;
import com.hh.sd.core.service.mapper.ComputationGroupMapper;

/**
 * Service for executing complex queries for ComputationGroup entities in the database.
 * The main input is a {@link ComputationGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComputationGroupDTO} or a {@link Page} of {@link ComputationGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComputationGroupQueryService extends QueryService<ComputationGroup> {

    private final Logger log = LoggerFactory.getLogger(ComputationGroupQueryService.class);

    private final ComputationGroupRepository computationGroupRepository;

    private final ComputationGroupMapper computationGroupMapper;

    public ComputationGroupQueryService(ComputationGroupRepository computationGroupRepository, ComputationGroupMapper computationGroupMapper) {
        this.computationGroupRepository = computationGroupRepository;
        this.computationGroupMapper = computationGroupMapper;
    }

    /**
     * Return a {@link List} of {@link ComputationGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComputationGroupDTO> findByCriteria(ComputationGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComputationGroup> specification = createSpecification(criteria);
        return computationGroupMapper.toDto(computationGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComputationGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComputationGroupDTO> findByCriteria(ComputationGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComputationGroup> specification = createSpecification(criteria);
        return computationGroupRepository.findAll(specification, page)
            .map(computationGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComputationGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComputationGroup> specification = createSpecification(criteria);
        return computationGroupRepository.count(specification);
    }

    /**
     * Function to convert ComputationGroupCriteria to a {@link Specification}
     */
    private Specification<ComputationGroup> createSpecification(ComputationGroupCriteria criteria) {
        Specification<ComputationGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ComputationGroup_.id));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), ComputationGroup_.identifier));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ComputationGroup_.name));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), ComputationGroup_.remark));
            }
            if (criteria.getCreateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTs(), ComputationGroup_.createTs));
            }
            if (criteria.getUpdateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTs(), ComputationGroup_.updateTs));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), ComputationGroup_.deleted));
            }
        }
        return specification;
    }
}
