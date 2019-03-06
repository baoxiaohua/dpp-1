package com.hh.sd.core.service.helper.sql;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.utility.SqlUtility;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqliteHelper {

    private Connection connection = null;

    public SqliteHelper() throws SQLException {

    }

    public DataSubProcessorResultModel execute(DataSubProcessor dataSubProcessor, Map<String, Object> paramMap, boolean debug) throws SQLException {
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
            dataSubProcessorResultModel.setTotal(NumberUtils.toLong(countResult.get(0).get("cnt").toString()));
        }

        sql = SqlUtility.getSortingPagingSql(sql, paramMap, " limit $offset,$pageSize ");

        if (debug) dataSubProcessorResultModel.setDebug("Sql: " + sql +"; ");

        var result = this.query(sql, paramMap, paramList);

        dataSubProcessorResultModel.setResult(result);

        return dataSubProcessorResultModel;
    }

    public void insert(String tableName, List<Map<String, Object>> data) throws Exception {
        if(data == null || data.size() == 0) throw new Exception(tableName + " is empty, cannot output as table");

        // region - create table
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(this.getCreateSql(tableName, data.get(0)));
        // endregion

        // region - batch insert
        List<String> colNames = new ArrayList<>();
        String valuePlaceholder = "";
        for(var entry: data.get(0).entrySet()){
            colNames.add(entry.getKey());
            valuePlaceholder += "?,";
        }
        valuePlaceholder = StringUtils.stripEnd(valuePlaceholder,",");

        PreparedStatement preparedStatement = getConnection().prepareStatement(
            "insert into " + tableName + "(" + StringUtils.join(colNames, ",") + ") values ("+valuePlaceholder+")");
        connection.setAutoCommit(false);

        for(var row: data) {
            for(int i=0;i<colNames.size();i++) {
                var name = colNames.get(i);
                var value = row.get(name);

                preparedStatement.setObject(i+1, value);
            }
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        // endregion
    }

    private List<Map<String, Object>> query(String sql, Map<String, Object> paramMap, List<String> paramList) throws SQLException {
        var result = new ArrayList<Map<String, Object>>();

        sql = sql.replaceAll(":([a-z|0-9|-|_]*)","?");

        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        for(var i=0; i<paramList.size(); i++) {
            preparedStatement.setObject(i+1, paramMap.get(paramList.get(i)));
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        return SqlUtility.extractData(resultSet);
    }

    private Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        }
        return connection;
    }

    private String getCreateSql(String tableName, Map<String, Object> record) {
        String sql = "create table " + tableName + "(";

        for(var entry: record.entrySet()) {
            sql += entry.getKey() + " " + getColumnType(entry.getValue()) + ",";
        }

        sql = StringUtils.stripEnd(sql, ",") + ")";

        return sql;
    }

    private String getColumnType(Object value) {
        if(value instanceof BigInteger) return "integer";
        if(value instanceof Integer) return "integer";
        if(value instanceof Long) return "integer";

        if(value instanceof Double) return "real";
        if(value instanceof Float) return "real";

        if(value instanceof String) return "text";

        return "text";
    }
}
