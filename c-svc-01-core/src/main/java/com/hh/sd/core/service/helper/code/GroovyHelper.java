package com.hh.sd.core.service.helper.code;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.service.custom.model.DataSubProcessorResultModel;
import com.hh.sd.core.utility.SpringContextUtility;
import groovy.lang.GroovyClassLoader;
import lombok.var;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class GroovyHelper {

    public DataSubProcessorResultModel execute(DataSubProcessor dataSubProcessor,
                                               Map<String, List<Map<String, Object>>> interimObjectMap,
                                               Map<String, Object> paramMap,
                                               boolean debug)
        throws Exception {
        Class clazz = new GroovyClassLoader().parseClass(dataSubProcessor.getCode());
        Method execute = null;

        for(var m: clazz.getMethods()) {
            if(m.getName().equals("execute")) {
                execute = m;
                break;
            }
        }

        if(execute==null) throw new Exception("Must declare execute method");

        Object instance = clazz.newInstance();
        SpringContextUtility.autowireBean(instance);

        var result = (DataSubProcessorResultModel)execute.invoke(instance, interimObjectMap, paramMap, debug);
        if(!debug) result.setDebug("");

        return result;
    }

}
