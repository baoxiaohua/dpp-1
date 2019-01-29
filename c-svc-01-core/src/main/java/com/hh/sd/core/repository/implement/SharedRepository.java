package com.hh.sd.core.repository.implement;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public class SharedRepository {
    private final Logger log = LoggerFactory.getLogger(SharedRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    public List<Map<String, Object>> executeNativeSql(String sql) {
        StopWatch stopWatch = new StopWatch("SystemQueryRepositoryImpl-query");

        stopWatch.start("Query data");
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> resultList=query.getResultList();
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return resultList;
    }
}
