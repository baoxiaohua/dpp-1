package com.hh.sd.core.repository.custom;

import com.hh.sd.core.domain.SystemQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the SystemQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemQueryCustomRepository extends JpaRepository<SystemQuery, Long>, JpaSpecificationExecutor<SystemQuery> {
//    @Query("select sq from SystemQuery sq where sq.identifier=:identifier")
//    Optional<SystemQuery> getByIdentifier(@Param("identifier") String identifier);

    Optional<SystemQuery> findByIdentifier(String identifier);
}
