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

import com.hh.sd.core.domain.Computation;
import com.hh.sd.core.domain.*; // for static metamodels
import com.hh.sd.core.repository.ComputationRepository;
import com.hh.sd.core.service.dto.ComputationCriteria;
import com.hh.sd.core.service.dto.ComputationDTO;
import com.hh.sd.core.service.mapper.ComputationMapper;

/**
 * Service for executing complex queries for Computation entities in the database.
 * The main input is a {@link ComputationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComputationDTO} or a {@link Page} of {@link ComputationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComputationQueryService extends QueryService<Computation> {

    private final Logger log = LoggerFactory.getLogger(ComputationQueryService.class);

    private final ComputationRepository computationRepository;

    private final ComputationMapper computationMapper;

    public ComputationQueryService(ComputationRepository computationRepository, ComputationMapper computationMapper) {
        this.computationRepository = computationRepository;
        this.computationMapper = computationMapper;
    }

    /**
     * Return a {@link List} of {@link ComputationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComputationDTO> findByCriteria(ComputationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Computation> specification = createSpecification(criteria);
        return computationMapper.toDto(computationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComputationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComputationDTO> findByCriteria(ComputationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Computation> specification = createSpecification(criteria);
        return computationRepository.findAll(specification, page)
            .map(computationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComputationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Computation> specification = createSpecification(criteria);
        return computationRepository.count(specification);
    }

    /**
     * Function to convert ComputationCriteria to a {@link Specification}
     */
    private Specification<Computation> createSpecification(ComputationCriteria criteria) {
        Specification<Computation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Computation_.id));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), Computation_.identifier));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Computation_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Computation_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Computation_.status));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Computation_.remark));
            }
            if (criteria.getCreateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTs(), Computation_.createTs));
            }
            if (criteria.getUpdateTs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTs(), Computation_.updateTs));
            }
            if (criteria.getComputationGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getComputationGroupId(), Computation_.computationGroupId));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Computation_.deleted));
            }
        }
        return specification;
    }
}
