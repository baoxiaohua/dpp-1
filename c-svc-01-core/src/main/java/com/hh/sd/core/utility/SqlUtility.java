package com.hh.sd.core.utility;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtility {

    public static String preProcessSql(String sql) {
        String resSql;
        // Strip end semicolon
        resSql = StringUtils.stripEnd(sql, ";");
        resSql = resSql.replaceAll("\n", " ");
        return resSql;
    }

    public static String injectParameterValue(String sql, Map<String, Object> paramMap) {
        final String[] resSqlArr = {sql};

        paramMap.entrySet().forEach(entry -> {
            resSqlArr[0] = resSqlArr[0].replaceAll("\\$\\{" + entry.getKey() + "}", entry.getValue().toString());
        });

        String resSql = resSqlArr[0];
        Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(resSql);
        while(matcher.find()) {
            String fullClause = matcher.group(0);
            String clause = matcher.group(1);
            if (clause.matches(".*\\$\\{.*?}.*")) resSql = resSql.replace(fullClause, " ");
            else resSql = resSql.replace(fullClause, clause);
        }

        return resSql;
    }

    public static String getCountSql(String sql) {
        sql = sql.replaceAll("\\$sort\\{.*?}", " ");
        sql = sql.replaceAll("\\$page\\{.*?}", " ");
        String countSql = "select count(*) cnt from ( " + sql + " ) tbl_cnt";
        return countSql;
    }

    /**
     * @param sortStr e.g. id:desc,name:asc
     * @return
     */
    public static String getSortingClause(String sortStr) {
        if(sortStr.trim().equals("")) return "";

        return "order by " + sortStr.replaceAll(":", " ") + " ";
    }

    public static String getSortingPagingSql(String sql, Map<String, Object> paramMap, String pagingTemplate) {
        Matcher matcher = Pattern.compile("\\$sort\\{(.*?)}").matcher(sql);
        if(matcher.find()) {
            var sortStr = paramMap.getOrDefault(matcher.group(1).trim(), "").toString();
            sql = sql.replace(matcher.group(0), SqlUtility.getSortingClause(sortStr));
        }

        matcher = Pattern.compile("\\$page\\{(.*?)}").matcher(sql);
        if(matcher.find()) {
            var params = matcher.group(1).split(",");
            var pageNum = NumberUtils.toInt(paramMap.getOrDefault(params[0].trim(),-1).toString());
            var pageSize = NumberUtils.toInt(paramMap.getOrDefault(params[1].trim(),-1).toString());

            sql = sql.replace(matcher.group(0), getPagingClause(pageNum, pageSize, pagingTemplate));
        }

        return sql;
    }

    public static List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException {
        var result = new ArrayList<Map<String, Object>>();
        var metaData = resultSet.getMetaData();

        while(resultSet.next()) {
            var item = new HashMap<String, Object>();
            for(int i = 1; i<=metaData.getColumnCount(); i++) {
                item.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            result.add(item);
        }

        return result;
    }

    private static String getPagingClause(int pageNum, int pageSize, String pagingTemplate) {
        if(pageSize == -1 || pageNum == -1) return "";

        int offset = pageSize * (pageNum - 1);
        return pagingTemplate.replace("$offset", String.valueOf(offset)).replace("$pageSize", String.valueOf(pageSize));
    }


}
