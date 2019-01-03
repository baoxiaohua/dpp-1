package com.hh.sd.core.service.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.sd.core.domain.SystemQuery;
import com.hh.sd.core.repository.SystemQueryRepository;
import com.hh.sd.core.repository.implement.SystemQueryRepositoryImpl;
import com.hh.sd.core.service.custom.model.QueryModel;
import com.hh.sd.core.repository.custom.SystemQueryCustomRepository;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.service.mapper.SystemQueryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service Implementation for managing SystemQuery.
 */
@Service
@Transactional
public class SystemQueryCustomService {

    private final Logger log = LoggerFactory.getLogger(SystemQueryCustomService.class);

    private final SystemQueryRepository systemQueryRepository;
    private final SystemQueryCustomRepository systemQueryCustomRepository;
    private final SystemQueryRepositoryImpl systemQueryRepositoryImpl;
    private final SystemQueryMapper systemQueryMapper;

    public SystemQueryCustomService(SystemQueryRepository systemQueryRepository, SystemQueryCustomRepository systemQueryCustomRepository, SystemQueryRepositoryImpl systemQueryRepositoryImpl, SystemQueryMapper systemQueryMapper) {
        this.systemQueryRepository = systemQueryRepository;
        this.systemQueryCustomRepository = systemQueryCustomRepository;
        this.systemQueryRepositoryImpl = systemQueryRepositoryImpl;
        this.systemQueryMapper = systemQueryMapper;
    }

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> query(String identifier, Pageable pageable, Map<String, Object> criteria) throws Exception {

        StopWatch stopWatch = new StopWatch("WorkflowCustomService.getInitiationList");

        stopWatch.start("Find query by identifier and parse model");
        SystemQuery systemQuery=systemQueryCustomRepository.findByIdentifier(identifier).orElse(null);

        if(systemQuery==null) {
            throw new Exception("Query "+identifier+" does not exist");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        QueryModel queryModel = objectMapper.readValue(systemQuery.getDefinition(), QueryModel.class);
        stopWatch.stop();


        stopWatch.start("Generate query");
        queryModel.setParameters(new ArrayList<>());
        final String[] query = {queryModel.getTemplate()};

        queryModel.getSegments().forEach(seg->{
            String segQuery="";

            for(String cond : seg.getConditions()){
                Matcher matcher = Pattern.compile("(:)(\\w+)").matcher(cond);
                if(matcher.find()) {
                    String param = matcher.group(2);
                    Object val = criteria.getOrDefault(param, null);
                    if(val==null) break;

                    queryModel.getParameters().add(param);
                }

                segQuery += (segQuery.equals("")? "" : " " + seg.getType().toString() + " ") + cond;
            }

            query[0] = query[0].replace(":"+seg.getName(), segQuery.equals("")?seg.getEmptyCondition():segQuery);
        });

        queryModel.setQuery(query[0]);
        stopWatch.stop();

        stopWatch.start("Query data");
        Page<Map<String, Object>> result = systemQueryRepositoryImpl.query(queryModel,pageable,criteria);
        stopWatch.stop();

        log.debug(stopWatch.prettyPrint());

        return result;
    }

    public SystemQueryDTO upsert(Long id, String identifier, String definition, String roles) {
        SystemQuery systemQuery=new SystemQuery();
        systemQuery.setId(id);
        systemQuery.setIdentifier(identifier);

        systemQuery.setDefinition(definition.replace("\n","").replace("\r","").replace("\t",""));

        systemQuery.setRoles(roles);

        systemQuery=systemQueryRepository.save(systemQuery);

        return systemQueryMapper.toDto(systemQuery);
    }
}
