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

import com.hh.sd.core.domain.SystemQuery;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.SystemQueryRepository;
import com.hh.sd.core.service.dto.SystemQueryCriteria;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.service.mapper.SystemQueryMapper;

/**
 * Service for executing complex queries for SystemQuery entities in the database.
 * The main input is a {@link SystemQueryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemQueryDTO} or a {@link Page} of {@link SystemQueryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemQueryQueryService extends QueryService<SystemQuery> {

    private final Logger log = LoggerFactory.getLogger(SystemQueryQueryService.class);

    private final SystemQueryRepository systemQueryRepository;

    private final SystemQueryMapper systemQueryMapper;

    public SystemQueryQueryService(SystemQueryRepository systemQueryRepository, SystemQueryMapper systemQueryMapper) {
        this.systemQueryRepository = systemQueryRepository;
        this.systemQueryMapper = systemQueryMapper;
    }

    /**
     * Return a {@link List} of {@link SystemQueryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemQueryDTO> findByCriteria(SystemQueryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemQuery> specification = createSpecification(criteria);
        return systemQueryMapper.toDto(systemQueryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemQueryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemQueryDTO> findByCriteria(SystemQueryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemQuery> specification = createSpecification(criteria);
        return systemQueryRepository.findAll(specification, page)
            .map(systemQueryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemQueryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemQuery> specification = createSpecification(criteria);
        return systemQueryRepository.count(specification);
    }

    /**
     * Function to convert SystemQueryCriteria to a {@link Specification}
     */
    private Specification<SystemQuery> createSpecification(SystemQueryCriteria criteria) {
        Specification<SystemQuery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SystemQuery_.id));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), SystemQuery_.identifier));
            }
            if (criteria.getDefinition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefinition(), SystemQuery_.definition));
            }
            if (criteria.getRoles() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoles(), SystemQuery_.roles));
            }
            if (criteria.getCreateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTs(), SystemQuery_.createTs));
            }
            if (criteria.getUpdateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTs(), SystemQuery_.updateTs));
            }
        }
        return specification;
    }
}
