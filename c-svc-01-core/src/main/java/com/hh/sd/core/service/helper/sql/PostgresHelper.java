package com.hh.sd.core.service.helper.sql;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.repository.implement.SharedRepository;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.utility.SqlUtility;
import lombok.var;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostgresHelper {
    private final Logger log = LoggerFactory.getLogger(PostgresHelper.class);

    private final SharedRepository sharedRepository;

    public PostgresHelper(SharedRepository sharedRepository) {
        this.sharedRepository = sharedRepository;
    }

    public DataSubProcessorResultModel execute(DataSubProcessor dataSubProcessor, Map<String, Object> paramMap, boolean debug) {
        var dataSubProcessorResultModel = new DataSubProcessorResultModel();
        dataSubProcessorResultModel.setTotal(-1);

        var sql = dataSubProcessor.getCode();
        sql = SqlUtility.preProcessSql(sql);
        sql = SqlUtility.injectParameterValue(sql, paramMap);

        // if paging required, count total
        if (sql.matches(".*\\$page\\{.*?}.*")) {
            var countSql = SqlUtility.getCountSql(sql);
            if (debug) dataSubProcessorResultModel.setDebug("CountSql: " + countSql +"; ");
            var countResult = sharedRepository.executeNativeSql(countSql);
            dataSubProcessorResultModel.setTotal(NumberUtils.toLong(countResult.get(0).get("cnt").toString()));
        }

        sql = SqlUtility.getSortingPagingSql(sql, paramMap, " limit $pageSize offset $offset ");

        if (debug) dataSubProcessorResultModel.setDebug("Sql: " + sql +";" );

        var result = sharedRepository.executeNativeSql(sql);

        dataSubProcessorResultModel.setResult(result);

        return dataSubProcessorResultModel;
    }

}
