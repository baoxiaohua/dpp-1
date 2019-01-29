package com.hh.sd.core.service.custom;

import com.hh.sd.core.domain.DataProcessor;
import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.domain.enumeration.DataProcessorType;
import com.hh.sd.core.repository.DataProcessorRepository;
import com.hh.sd.core.repository.custom.DataProcessorCustomRepository;
import com.hh.sd.core.repository.custom.DataSubProcessorCustomRepository;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.service.dto.DataProcessorResultDTO;
import com.hh.sd.core.service.helper.code.GroovyHelper;
import com.hh.sd.core.service.helper.sql.KylinHelper;
import com.hh.sd.core.service.helper.sql.PostgresHelper;
import com.hh.sd.core.service.helper.sql.SqliteHelper;
import com.hh.sd.core.service.mapper.DataProcessorMapper;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing DataProcessor.
 */
@Service
@Transactional
public class DataProcessorCustomService {

    private final Logger log = LoggerFactory.getLogger(DataProcessorCustomService.class);

    private final DataProcessorRepository dataProcessorRepository;
    private final DataProcessorMapper dataProcessorMapper;
    private final DataProcessorCustomRepository dataProcessorCustomRepository;
    private final DataSubProcessorCustomRepository dataSubProcessorCustomRepository;
    private final PostgresHelper postgresHelper;
    private final GroovyHelper groovyHelper;
    private final KylinHelper kylinHelper;

    public DataProcessorCustomService(DataProcessorRepository dataProcessorRepository, DataProcessorMapper dataProcessorMapper, DataProcessorCustomRepository dataProcessorCustomRepository, DataSubProcessorCustomRepository dataSubProcessorCustomRepository, PostgresHelper postgresHelper, GroovyHelper groovyHelper, KylinHelper kylinHelper) {
        this.dataProcessorRepository = dataProcessorRepository;
        this.dataProcessorMapper = dataProcessorMapper;
        this.dataProcessorCustomRepository = dataProcessorCustomRepository;
        this.dataSubProcessorCustomRepository = dataSubProcessorCustomRepository;
        this.postgresHelper = postgresHelper;
        this.groovyHelper = groovyHelper;
        this.kylinHelper = kylinHelper;
    }

    /**
     * @param identifier
     * @param paramMap
     * @param dataSubProcessorId
     * @return
     * @throws Exception
     */
    public DataProcessorResultDTO execute(String identifier, Map<String, Object> paramMap, Long dataSubProcessorId) throws Exception {
        StopWatch stopWatch = new StopWatch("Execute DataProcessor: " + dataSubProcessorId);

        // region - Load DataProcessor and DataSubProcessorList
        stopWatch.start("Load DataProcessor and DataSubProcessorList");
        DataProcessor dataProcessor = dataProcessorCustomRepository.findByIdentifier(identifier).orElse(null);
        if (dataProcessor == null) throw new Exception("DataProcessor not found by identifier: " + identifier);

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorCustomRepository.findByDataProcessorIdOrderBySequenceAsc(dataProcessor.getId());
        stopWatch.stop();
        // endregion

        var debug = (Boolean) paramMap.getOrDefault("debug", false);

        // region - Init final result DTO and SqliteHelper
        DataProcessorResultDTO dataProcessorResultDTO = new DataProcessorResultDTO();
        dataProcessorResultDTO.setSuccess(true);
        dataProcessorResultDTO.setResults(new HashMap<>());

        SqliteHelper sqliteHelper = new SqliteHelper();

        Map<String, List<Map<String, Object>>> interimObjects = new HashMap<>();
        // endregion

        // region - Execute DataSubProcessors
        for(int i=0; i<dataSubProcessorList.size(); i++) {
            var dataSubProcessor = dataSubProcessorList.get(i);

            stopWatch.start("Execute DataSubProcessor: " + dataSubProcessor.getName());

            DataSubProcessorResultModel dataSubProcessorResultModel = null;
            DataProcessorType dataProcessorType = dataSubProcessor.getDataProcessorType();
            if(dataProcessorType.equals(DataProcessorType.SQL_DB)) {
                dataSubProcessorResultModel = postgresHelper.execute(dataSubProcessor, paramMap, debug);
            }
            else if(dataProcessorType.equals(DataProcessorType.SQL_INTERIM)) {
                dataSubProcessorResultModel = sqliteHelper.execute(dataSubProcessor, paramMap, debug);
            }
            else if(dataProcessorType.equals(DataProcessorType.SQL_KYLIN)) {
                dataSubProcessorResultModel = kylinHelper.execute(dataSubProcessor, paramMap, debug);
            }
            else if(dataProcessorType.equals(DataProcessorType.CODE_GROOVY)) {
                dataSubProcessorResultModel = groovyHelper.execute(dataSubProcessor, interimObjects, paramMap, debug);
            }

            // Put result into result DTO if need to output as result or admin is testing the dataSubProcessor
            if ( (!dataSubProcessorId.equals(-1) && dataSubProcessor.isOutputAsResult()) || dataSubProcessor.getId().equals(dataSubProcessorId)) {
                dataProcessorResultDTO.getResults().put(dataSubProcessor.getName(), dataSubProcessorResultModel);
            }

            // If admin is testing a specific dataSubProcessor
            if(dataSubProcessor.getId().equals(dataSubProcessorId) || i == dataSubProcessorList.size() - 1) break;

            // Insert Sqlite if need to output as table
            if(dataSubProcessor.isOutputAsTable()) {
                sqliteHelper.insert(dataSubProcessor.getName(), dataSubProcessorResultModel.getResult());
            }

            // Put result into interim object map if need to output as object
            if(dataSubProcessor.isOutputAsObject()) {
                interimObjects.put(dataSubProcessor.getName(), dataSubProcessorResultModel.getResult());
            }

            stopWatch.stop();
        }
        // endregion

        log.debug(stopWatch.prettyPrint());
        return dataProcessorResultDTO;
    }

}
