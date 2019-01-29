package com.hh.sd.core.service.helper.sql;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.utility.SqlUtility;
import lombok.var;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class KylinHelper {

    public DataSubProcessorResultModel execute(DataSubProcessor dataSubProcessor, Map<String, Object> paramMap, boolean debug) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        var result = query(dataSubProcessor.getCode());

        var dataSubProcessorResultModel = new DataSubProcessorResultModel();
        dataSubProcessorResultModel.setResult(result);

        return dataSubProcessorResultModel;
    }

    private Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Driver driver = (Driver)Class.forName("org.apache.kylin.jdbc.Driver").newInstance();

        Properties info = new Properties();
        info.put("user", "ADMIN");
        info.put("password", "KYLIN");

        Connection conn = driver.connect("jdbc:kylin://10.96.129.55:7070/dpp", info);

        return conn;
    }

    private List<Map<String, Object>> query(String sql) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        var result = new ArrayList<Map<String, Object>>();

        Connection conn = getConnection();
        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        return SqlUtility.extractData(resultSet);
    }
}
