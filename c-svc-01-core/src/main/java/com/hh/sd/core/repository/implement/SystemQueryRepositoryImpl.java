package com.hh.sd.core.repository.implement;

import com.hh.sd.core.service.custom.model.QueryModel;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SystemQueryRepositoryImpl {
    private final Logger log = LoggerFactory.getLogger(SystemQueryRepositoryImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    public Page<Map<String, Object>> query(QueryModel queryModel, Pageable pageable, Map<String, Object> criteria) {
        StopWatch stopWatch = new StopWatch("SystemQueryRepositoryImpl-query");

        stopWatch.start("Counting");
        String countSql=getCountQuery(queryModel);
        Query countQuery=entityManager.createNativeQuery(countSql);
        setParameters(countQuery, queryModel, criteria);
        BigInteger totalCount=(BigInteger)countQuery.getSingleResult();
        stopWatch.stop();

        stopWatch.start("Query data");
        Query query = entityManager.createNativeQuery(queryModel.getQuery() + getSortClause(pageable));
        setParameters(query, queryModel, criteria);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        if(queryModel.getPageable()) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }

        List<Map<String, Object>> resultList=query.getResultList();
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return new PageImpl<>(resultList, pageable, totalCount.longValue());
    }

    private String getCountQuery(QueryModel queryModel) {
        return "select count("+queryModel.getCountColumn()+") from "
            + (queryModel.getQuery().split("(?i)FROM"))[1];
    }

    private String getSortClause(Pageable pageable) {
        String sql = "";
        Sort sort = pageable.getSort();
        if(sort == null) return sql;

        Iterator<Sort.Order> orders=sort.iterator();
        while(orders.hasNext()) {
            Sort.Order order = orders.next();

            sql += (sql.equals("")?"":",") + order.getProperty().replace("'","") + " " + (order.isAscending()?" asc":" desc");
        }

        if(!sql.equals("")) sql=" order by "+sql;

        return sql;
    }

    private void setParameters(Query query, QueryModel queryModel, Map<String, Object> criteria) {
        queryModel.getParameters().forEach(param-> query.setParameter(param, criteria.get(param)));
    }
}
