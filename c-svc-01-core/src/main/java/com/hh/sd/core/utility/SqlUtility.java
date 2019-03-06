package com.hh.sd.core.utility;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.var;
import org.apache.commons.lang.ArrayUtils;
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

    public static String processOptionalParam(String sql, Map<String, Object> paramMap) {
        String resSql = sql;
        Matcher matcher = Pattern.compile("\\[.*?:([a-z|0-9|-|_]*).*?]",Pattern.CASE_INSENSITIVE).matcher(resSql);
        while(matcher.find()) {
            String segment = matcher.group(0);
            String param = matcher.group(1);

            if(!paramMap.containsKey(param)) resSql = resSql.replace(segment, " ");
        }

        resSql = resSql.replaceAll("\\[|]","");

        return resSql;
    }

    public static String getCountSql(String sql) {
        sql = sql.replaceAll("\\$sort\\{.*?}", " ");
        sql = sql.replaceAll("\\$page\\{.*?}", " ");
        String countSql = "select count(*) cnt from ( " + sql + " ) tbl_cnt";
        return countSql;
    }

    public static String getSortingClause(List<String> sortArr) {
        if(sortArr.size() == 0) return "";
        var sortStr = StringUtils.join(sortArr, ",");

        return "order by " + sortStr.replaceAll(":", " ") + " ";
    }

    public static String getSortingPagingSql(String sql, Map<String, Object> paramMap, String pagingTemplate) {
        Matcher matcher = Pattern.compile("\\$sort\\{(.*?)}").matcher(sql);
        if(matcher.find()) {
            var sortArr = (List<String>)paramMap.getOrDefault(matcher.group(1).trim(), new ArrayList<String>());
            sql = sql.replace(matcher.group(0), SqlUtility.getSortingClause(sortArr));
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

    public static List<String> getQueryParamList(String sql) {
        var paramList = new ArrayList<String>();

        Matcher matcher = Pattern.compile(":([a-z|0-9|-|_]*)",Pattern.CASE_INSENSITIVE).matcher(sql);
        while(matcher.find()) {
            String param = matcher.group(1);
            paramList.add(param);
        }

        return paramList;
    }

    private static String getPagingClause(int pageNum, int pageSize, String pagingTemplate) {
        if(pageSize == -1 || pageNum == -1) return "";

        int offset = pageSize * (pageNum - 1);
        return pagingTemplate.replace("$offset", String.valueOf(offset)).replace("$pageSize", String.valueOf(pageSize));
    }

}
