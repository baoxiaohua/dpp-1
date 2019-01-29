package com.hh.sd.core.repository.implement;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class DataProcessorRepositoryImpl {
    private final Logger log = LoggerFactory.getLogger(DataProcessorRepositoryImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    public Page<Map<String, Object>> query(String sql, Pageable pageable, Map<String, Object> parameters) {
        StopWatch stopWatch = new StopWatch("DataProcessorRepositoryImpl-query");

//        stopWatch.start("Counting");
//        String countSql=getCountQuery(queryModel);
//        Query countQuery=entityManager.createNativeQuery(countSql);
//        setParameters(countQuery, queryModel, criteria);
//        BigInteger totalCount=(BigInteger)countQuery.getSingleResult();
//        stopWatch.stop();

        stopWatch.start("Query data");
//        Query query = entityManager.createNativeQuery(queryModel.getQuery() + getSortClause(pageable));
//        setParameters(query, queryModel, criteria);
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

//        if(queryModel.getPageable()) {
//            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
//            query.setMaxResults(pageable.getPageSize());
//        }

        List<Map<String, Object>> resultList=query.getResultList();
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return new PageImpl<>(resultList, pageable, 100);
    }
}
