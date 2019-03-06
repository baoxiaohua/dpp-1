package com.hh.sd.core.service.helper.sql;

import com.hh.sd.core.config.custom.KylinConfig;
import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.utility.SqlUtility;
import lombok.var;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class KylinHelper {

    private final KylinConfig kylinConfig;

    public KylinHelper(KylinConfig kylinConfig) {
        this.kylinConfig = kylinConfig;
    }

    public DataSubProcessorResultModel execute(DataSubProcessor dataSubProcessor, Map<String, Object> paramMap, boolean debug) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        var dataSubProcessorResultModel = new DataSubProcessorResultModel();
        dataSubProcessorResultModel.setTotal(-1);

        var sql = dataSubProcessor.getCode();
        sql = SqlUtility.preProcessSql(sql);
        sql = SqlUtility.processOptionalParam(sql, paramMap);
        var paramList = SqlUtility.getQueryParamList(sql);

        // if paging required, count total
        if (sql.matches(".*\\$page\\{.*?}.*")) {
            var countSql = SqlUtility.getCountSql(sql);
            if (debug) dataSubProcessorResultModel.setDebug("CountSql: " + countSql +"; ");
            var countResult = this.query(countSql, paramMap, paramList);
            dataSubProcessorResultModel.setTotal(NumberUtils.toLong(countResult.get(0).get("CNT").toString()));
        }

        sql = SqlUtility.getSortingPagingSql(sql, paramMap, " limit $offset,$pageSize ");

        if (debug) dataSubProcessorResultModel.setDebug("Sql: " + sql +"; ");

        var result = this.query(sql, paramMap, paramList);

        dataSubProcessorResultModel.setResult(result);

        return dataSubProcessorResultModel;
    }

    private Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Driver driver = (Driver)Class.forName("org.apache.kylin.jdbc.Driver").newInstance();

        Properties info = new Properties();
        info.put("user", kylinConfig.getUser());
        info.put("password", kylinConfig.getPassword());

        Connection conn = driver.connect(kylinConfig.getJdbcUrl(), info);

        return conn;
    }

    private List<Map<String, Object>> query(String sql, Map<String, Object> paramMap, List<String> paramList) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        var result = new ArrayList<Map<String, Object>>();

        sql = sql.replaceAll(":([a-z|0-9|-|_]*)","?");

        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        for(var i=0; i<paramList.size(); i++) {
            preparedStatement.setObject(i+1, paramMap.get(paramList.get(i)));
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        return SqlUtility.extractData(resultSet);
    }
}
