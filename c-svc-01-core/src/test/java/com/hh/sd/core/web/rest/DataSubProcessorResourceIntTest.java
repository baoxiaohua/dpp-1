package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.DataSubProcessor;
import com.hh.sd.core.repository.DataSubProcessorRepository;
import com.hh.sd.core.service.DataSubProcessorService;
import com.hh.sd.core.service.dto.DataSubProcessorDTO;
import com.hh.sd.core.service.mapper.DataSubProcessorMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.DataSubProcessorCriteria;
import com.hh.sd.core.service.DataSubProcessorQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.hh.sd.core.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hh.sd.core.domain.enumeration.DataProcessorType;
/**
 * Test class for the DataSubProcessorResource REST controller.
 *
 * @see DataSubProcessorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class DataSubProcessorResourceIntTest {

    private static final Long DEFAULT_DATA_PROCESSOR_ID = 1L;
    private static final Long UPDATED_DATA_PROCESSOR_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final DataProcessorType DEFAULT_DATA_PROCESSOR_TYPE = DataProcessorType.SQL_INTERIM;
    private static final DataProcessorType UPDATED_DATA_PROCESSOR_TYPE = DataProcessorType.SQL_DB;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OUTPUT_AS_TABLE = false;
    private static final Boolean UPDATED_OUTPUT_AS_TABLE = true;

    private static final Boolean DEFAULT_OUTPUT_AS_OBJECT = false;
    private static final Boolean UPDATED_OUTPUT_AS_OBJECT = true;

    private static final Boolean DEFAULT_OUTPUT_AS_RESULT = false;
    private static final Boolean UPDATED_OUTPUT_AS_RESULT = true;

    private static final Instant DEFAULT_CREATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBBBBBBB";

    @Autowired
    private DataSubProcessorRepository dataSubProcessorRepository;

    @Autowired
    private DataSubProcessorMapper dataSubProcessorMapper;

    @Autowired
    private DataSubProcessorService dataSubProcessorService;

    @Autowired
    private DataSubProcessorQueryService dataSubProcessorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataSubProcessorMockMvc;

    private DataSubProcessor dataSubProcessor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataSubProcessorResource dataSubProcessorResource = new DataSubProcessorResource(dataSubProcessorService, dataSubProcessorQueryService);
        this.restDataSubProcessorMockMvc = MockMvcBuilders.standaloneSetup(dataSubProcessorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSubProcessor createEntity(EntityManager em) {
        DataSubProcessor dataSubProcessor = new DataSubProcessor()
            .dataProcessorId(DEFAULT_DATA_PROCESSOR_ID)
            .name(DEFAULT_NAME)
            .sequence(DEFAULT_SEQUENCE)
            .dataProcessorType(DEFAULT_DATA_PROCESSOR_TYPE)
            .code(DEFAULT_CODE)
            .parameter(DEFAULT_PARAMETER)
            .outputAsTable(DEFAULT_OUTPUT_AS_TABLE)
            .outputAsObject(DEFAULT_OUTPUT_AS_OBJECT)
            .outputAsResult(DEFAULT_OUTPUT_AS_RESULT)
            .createTs(DEFAULT_CREATE_TS)
            .createBy(DEFAULT_CREATE_BY)
            .updateTs(DEFAULT_UPDATE_TS)
            .updateBy(DEFAULT_UPDATE_BY);
        return dataSubProcessor;
    }

    @Before
    public void initTest() {
        dataSubProcessor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataSubProcessor() throws Exception {
        int databaseSizeBeforeCreate = dataSubProcessorRepository.findAll().size();

        // Create the DataSubProcessor
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);
        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isCreated());

        // Validate the DataSubProcessor in the database
        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeCreate + 1);
        DataSubProcessor testDataSubProcessor = dataSubProcessorList.get(dataSubProcessorList.size() - 1);
        assertThat(testDataSubProcessor.getDataProcessorId()).isEqualTo(DEFAULT_DATA_PROCESSOR_ID);
        assertThat(testDataSubProcessor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSubProcessor.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testDataSubProcessor.getDataProcessorType()).isEqualTo(DEFAULT_DATA_PROCESSOR_TYPE);
        assertThat(testDataSubProcessor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDataSubProcessor.getParameter()).isEqualTo(DEFAULT_PARAMETER);
        assertThat(testDataSubProcessor.isOutputAsTable()).isEqualTo(DEFAULT_OUTPUT_AS_TABLE);
        assertThat(testDataSubProcessor.isOutputAsObject()).isEqualTo(DEFAULT_OUTPUT_AS_OBJECT);
        assertThat(testDataSubProcessor.isOutputAsResult()).isEqualTo(DEFAULT_OUTPUT_AS_RESULT);
        assertThat(testDataSubProcessor.getCreateTs()).isEqualTo(DEFAULT_CREATE_TS);
        assertThat(testDataSubProcessor.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDataSubProcessor.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
        assertThat(testDataSubProcessor.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createDataSubProcessorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataSubProcessorRepository.findAll().size();

        // Create the DataSubProcessor with an existing ID
        dataSubProcessor.setId(1L);
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSubProcessor in the database
        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataProcessorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setDataProcessorId(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setName(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setSequence(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataProcessorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setDataProcessorType(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutputAsTableIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setOutputAsTable(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutputAsObjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setOutputAsObject(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutputAsResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSubProcessorRepository.findAll().size();
        // set the field null
        dataSubProcessor.setOutputAsResult(null);

        // Create the DataSubProcessor, which fails.
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        restDataSubProcessorMockMvc.perform(post("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessors() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSubProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProcessorId").value(hasItem(DEFAULT_DATA_PROCESSOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].dataProcessorType").value(hasItem(DEFAULT_DATA_PROCESSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())))
            .andExpect(jsonPath("$.[*].outputAsTable").value(hasItem(DEFAULT_OUTPUT_AS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].outputAsObject").value(hasItem(DEFAULT_OUTPUT_AS_OBJECT.booleanValue())))
            .andExpect(jsonPath("$.[*].outputAsResult").value(hasItem(DEFAULT_OUTPUT_AS_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getDataSubProcessor() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get the dataSubProcessor
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors/{id}", dataSubProcessor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataSubProcessor.getId().intValue()))
            .andExpect(jsonPath("$.dataProcessorId").value(DEFAULT_DATA_PROCESSOR_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.dataProcessorType").value(DEFAULT_DATA_PROCESSOR_TYPE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER.toString()))
            .andExpect(jsonPath("$.outputAsTable").value(DEFAULT_OUTPUT_AS_TABLE.booleanValue()))
            .andExpect(jsonPath("$.outputAsObject").value(DEFAULT_OUTPUT_AS_OBJECT.booleanValue()))
            .andExpect(jsonPath("$.outputAsResult").value(DEFAULT_OUTPUT_AS_RESULT.booleanValue()))
            .andExpect(jsonPath("$.createTs").value(DEFAULT_CREATE_TS.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorId equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldBeFound("dataProcessorId.equals=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataSubProcessorList where dataProcessorId equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldNotBeFound("dataProcessorId.equals=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorIdIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorId in DEFAULT_DATA_PROCESSOR_ID or UPDATED_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldBeFound("dataProcessorId.in=" + DEFAULT_DATA_PROCESSOR_ID + "," + UPDATED_DATA_PROCESSOR_ID);

        // Get all the dataSubProcessorList where dataProcessorId equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldNotBeFound("dataProcessorId.in=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorId is not null
        defaultDataSubProcessorShouldBeFound("dataProcessorId.specified=true");

        // Get all the dataSubProcessorList where dataProcessorId is null
        defaultDataSubProcessorShouldNotBeFound("dataProcessorId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorId greater than or equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldBeFound("dataProcessorId.greaterOrEqualThan=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataSubProcessorList where dataProcessorId greater than or equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldNotBeFound("dataProcessorId.greaterOrEqualThan=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorId less than or equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldNotBeFound("dataProcessorId.lessThan=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataSubProcessorList where dataProcessorId less than or equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataSubProcessorShouldBeFound("dataProcessorId.lessThan=" + UPDATED_DATA_PROCESSOR_ID);
    }


    @Test
    @Transactional
    public void getAllDataSubProcessorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where name equals to DEFAULT_NAME
        defaultDataSubProcessorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataSubProcessorList where name equals to UPDATED_NAME
        defaultDataSubProcessorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataSubProcessorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataSubProcessorList where name equals to UPDATED_NAME
        defaultDataSubProcessorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where name is not null
        defaultDataSubProcessorShouldBeFound("name.specified=true");

        // Get all the dataSubProcessorList where name is null
        defaultDataSubProcessorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where sequence equals to DEFAULT_SEQUENCE
        defaultDataSubProcessorShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the dataSubProcessorList where sequence equals to UPDATED_SEQUENCE
        defaultDataSubProcessorShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultDataSubProcessorShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the dataSubProcessorList where sequence equals to UPDATED_SEQUENCE
        defaultDataSubProcessorShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where sequence is not null
        defaultDataSubProcessorShouldBeFound("sequence.specified=true");

        // Get all the dataSubProcessorList where sequence is null
        defaultDataSubProcessorShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where sequence greater than or equals to DEFAULT_SEQUENCE
        defaultDataSubProcessorShouldBeFound("sequence.greaterOrEqualThan=" + DEFAULT_SEQUENCE);

        // Get all the dataSubProcessorList where sequence greater than or equals to UPDATED_SEQUENCE
        defaultDataSubProcessorShouldNotBeFound("sequence.greaterOrEqualThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where sequence less than or equals to DEFAULT_SEQUENCE
        defaultDataSubProcessorShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the dataSubProcessorList where sequence less than or equals to UPDATED_SEQUENCE
        defaultDataSubProcessorShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorType equals to DEFAULT_DATA_PROCESSOR_TYPE
        defaultDataSubProcessorShouldBeFound("dataProcessorType.equals=" + DEFAULT_DATA_PROCESSOR_TYPE);

        // Get all the dataSubProcessorList where dataProcessorType equals to UPDATED_DATA_PROCESSOR_TYPE
        defaultDataSubProcessorShouldNotBeFound("dataProcessorType.equals=" + UPDATED_DATA_PROCESSOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorType in DEFAULT_DATA_PROCESSOR_TYPE or UPDATED_DATA_PROCESSOR_TYPE
        defaultDataSubProcessorShouldBeFound("dataProcessorType.in=" + DEFAULT_DATA_PROCESSOR_TYPE + "," + UPDATED_DATA_PROCESSOR_TYPE);

        // Get all the dataSubProcessorList where dataProcessorType equals to UPDATED_DATA_PROCESSOR_TYPE
        defaultDataSubProcessorShouldNotBeFound("dataProcessorType.in=" + UPDATED_DATA_PROCESSOR_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByDataProcessorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where dataProcessorType is not null
        defaultDataSubProcessorShouldBeFound("dataProcessorType.specified=true");

        // Get all the dataSubProcessorList where dataProcessorType is null
        defaultDataSubProcessorShouldNotBeFound("dataProcessorType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsTableIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsTable equals to DEFAULT_OUTPUT_AS_TABLE
        defaultDataSubProcessorShouldBeFound("outputAsTable.equals=" + DEFAULT_OUTPUT_AS_TABLE);

        // Get all the dataSubProcessorList where outputAsTable equals to UPDATED_OUTPUT_AS_TABLE
        defaultDataSubProcessorShouldNotBeFound("outputAsTable.equals=" + UPDATED_OUTPUT_AS_TABLE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsTableIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsTable in DEFAULT_OUTPUT_AS_TABLE or UPDATED_OUTPUT_AS_TABLE
        defaultDataSubProcessorShouldBeFound("outputAsTable.in=" + DEFAULT_OUTPUT_AS_TABLE + "," + UPDATED_OUTPUT_AS_TABLE);

        // Get all the dataSubProcessorList where outputAsTable equals to UPDATED_OUTPUT_AS_TABLE
        defaultDataSubProcessorShouldNotBeFound("outputAsTable.in=" + UPDATED_OUTPUT_AS_TABLE);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsTable is not null
        defaultDataSubProcessorShouldBeFound("outputAsTable.specified=true");

        // Get all the dataSubProcessorList where outputAsTable is null
        defaultDataSubProcessorShouldNotBeFound("outputAsTable.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsObjectIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsObject equals to DEFAULT_OUTPUT_AS_OBJECT
        defaultDataSubProcessorShouldBeFound("outputAsObject.equals=" + DEFAULT_OUTPUT_AS_OBJECT);

        // Get all the dataSubProcessorList where outputAsObject equals to UPDATED_OUTPUT_AS_OBJECT
        defaultDataSubProcessorShouldNotBeFound("outputAsObject.equals=" + UPDATED_OUTPUT_AS_OBJECT);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsObjectIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsObject in DEFAULT_OUTPUT_AS_OBJECT or UPDATED_OUTPUT_AS_OBJECT
        defaultDataSubProcessorShouldBeFound("outputAsObject.in=" + DEFAULT_OUTPUT_AS_OBJECT + "," + UPDATED_OUTPUT_AS_OBJECT);

        // Get all the dataSubProcessorList where outputAsObject equals to UPDATED_OUTPUT_AS_OBJECT
        defaultDataSubProcessorShouldNotBeFound("outputAsObject.in=" + UPDATED_OUTPUT_AS_OBJECT);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsObjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsObject is not null
        defaultDataSubProcessorShouldBeFound("outputAsObject.specified=true");

        // Get all the dataSubProcessorList where outputAsObject is null
        defaultDataSubProcessorShouldNotBeFound("outputAsObject.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsResultIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsResult equals to DEFAULT_OUTPUT_AS_RESULT
        defaultDataSubProcessorShouldBeFound("outputAsResult.equals=" + DEFAULT_OUTPUT_AS_RESULT);

        // Get all the dataSubProcessorList where outputAsResult equals to UPDATED_OUTPUT_AS_RESULT
        defaultDataSubProcessorShouldNotBeFound("outputAsResult.equals=" + UPDATED_OUTPUT_AS_RESULT);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsResultIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsResult in DEFAULT_OUTPUT_AS_RESULT or UPDATED_OUTPUT_AS_RESULT
        defaultDataSubProcessorShouldBeFound("outputAsResult.in=" + DEFAULT_OUTPUT_AS_RESULT + "," + UPDATED_OUTPUT_AS_RESULT);

        // Get all the dataSubProcessorList where outputAsResult equals to UPDATED_OUTPUT_AS_RESULT
        defaultDataSubProcessorShouldNotBeFound("outputAsResult.in=" + UPDATED_OUTPUT_AS_RESULT);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByOutputAsResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where outputAsResult is not null
        defaultDataSubProcessorShouldBeFound("outputAsResult.specified=true");

        // Get all the dataSubProcessorList where outputAsResult is null
        defaultDataSubProcessorShouldNotBeFound("outputAsResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createTs equals to DEFAULT_CREATE_TS
        defaultDataSubProcessorShouldBeFound("createTs.equals=" + DEFAULT_CREATE_TS);

        // Get all the dataSubProcessorList where createTs equals to UPDATED_CREATE_TS
        defaultDataSubProcessorShouldNotBeFound("createTs.equals=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateTsIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createTs in DEFAULT_CREATE_TS or UPDATED_CREATE_TS
        defaultDataSubProcessorShouldBeFound("createTs.in=" + DEFAULT_CREATE_TS + "," + UPDATED_CREATE_TS);

        // Get all the dataSubProcessorList where createTs equals to UPDATED_CREATE_TS
        defaultDataSubProcessorShouldNotBeFound("createTs.in=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createTs is not null
        defaultDataSubProcessorShouldBeFound("createTs.specified=true");

        // Get all the dataSubProcessorList where createTs is null
        defaultDataSubProcessorShouldNotBeFound("createTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createBy equals to DEFAULT_CREATE_BY
        defaultDataSubProcessorShouldBeFound("createBy.equals=" + DEFAULT_CREATE_BY);

        // Get all the dataSubProcessorList where createBy equals to UPDATED_CREATE_BY
        defaultDataSubProcessorShouldNotBeFound("createBy.equals=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateByIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createBy in DEFAULT_CREATE_BY or UPDATED_CREATE_BY
        defaultDataSubProcessorShouldBeFound("createBy.in=" + DEFAULT_CREATE_BY + "," + UPDATED_CREATE_BY);

        // Get all the dataSubProcessorList where createBy equals to UPDATED_CREATE_BY
        defaultDataSubProcessorShouldNotBeFound("createBy.in=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByCreateByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where createBy is not null
        defaultDataSubProcessorShouldBeFound("createBy.specified=true");

        // Get all the dataSubProcessorList where createBy is null
        defaultDataSubProcessorShouldNotBeFound("createBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateTs equals to DEFAULT_UPDATE_TS
        defaultDataSubProcessorShouldBeFound("updateTs.equals=" + DEFAULT_UPDATE_TS);

        // Get all the dataSubProcessorList where updateTs equals to UPDATED_UPDATE_TS
        defaultDataSubProcessorShouldNotBeFound("updateTs.equals=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateTsIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateTs in DEFAULT_UPDATE_TS or UPDATED_UPDATE_TS
        defaultDataSubProcessorShouldBeFound("updateTs.in=" + DEFAULT_UPDATE_TS + "," + UPDATED_UPDATE_TS);

        // Get all the dataSubProcessorList where updateTs equals to UPDATED_UPDATE_TS
        defaultDataSubProcessorShouldNotBeFound("updateTs.in=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateTs is not null
        defaultDataSubProcessorShouldBeFound("updateTs.specified=true");

        // Get all the dataSubProcessorList where updateTs is null
        defaultDataSubProcessorShouldNotBeFound("updateTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateBy equals to DEFAULT_UPDATE_BY
        defaultDataSubProcessorShouldBeFound("updateBy.equals=" + DEFAULT_UPDATE_BY);

        // Get all the dataSubProcessorList where updateBy equals to UPDATED_UPDATE_BY
        defaultDataSubProcessorShouldNotBeFound("updateBy.equals=" + UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateByIsInShouldWork() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateBy in DEFAULT_UPDATE_BY or UPDATED_UPDATE_BY
        defaultDataSubProcessorShouldBeFound("updateBy.in=" + DEFAULT_UPDATE_BY + "," + UPDATED_UPDATE_BY);

        // Get all the dataSubProcessorList where updateBy equals to UPDATED_UPDATE_BY
        defaultDataSubProcessorShouldNotBeFound("updateBy.in=" + UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataSubProcessorsByUpdateByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        // Get all the dataSubProcessorList where updateBy is not null
        defaultDataSubProcessorShouldBeFound("updateBy.specified=true");

        // Get all the dataSubProcessorList where updateBy is null
        defaultDataSubProcessorShouldNotBeFound("updateBy.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataSubProcessorShouldBeFound(String filter) throws Exception {
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSubProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProcessorId").value(hasItem(DEFAULT_DATA_PROCESSOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].dataProcessorType").value(hasItem(DEFAULT_DATA_PROCESSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())))
            .andExpect(jsonPath("$.[*].outputAsTable").value(hasItem(DEFAULT_OUTPUT_AS_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].outputAsObject").value(hasItem(DEFAULT_OUTPUT_AS_OBJECT.booleanValue())))
            .andExpect(jsonPath("$.[*].outputAsResult").value(hasItem(DEFAULT_OUTPUT_AS_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())));

        // Check, that the count call also returns 1
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataSubProcessorShouldNotBeFound(String filter) throws Exception {
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataSubProcessor() throws Exception {
        // Get the dataSubProcessor
        restDataSubProcessorMockMvc.perform(get("/api/data-sub-processors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataSubProcessor() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        int databaseSizeBeforeUpdate = dataSubProcessorRepository.findAll().size();

        // Update the dataSubProcessor
        DataSubProcessor updatedDataSubProcessor = dataSubProcessorRepository.findById(dataSubProcessor.getId()).get();
        // Disconnect from session so that the updates on updatedDataSubProcessor are not directly saved in db
        em.detach(updatedDataSubProcessor);
        updatedDataSubProcessor
            .dataProcessorId(UPDATED_DATA_PROCESSOR_ID)
            .name(UPDATED_NAME)
            .sequence(UPDATED_SEQUENCE)
            .dataProcessorType(UPDATED_DATA_PROCESSOR_TYPE)
            .code(UPDATED_CODE)
            .parameter(UPDATED_PARAMETER)
            .outputAsTable(UPDATED_OUTPUT_AS_TABLE)
            .outputAsObject(UPDATED_OUTPUT_AS_OBJECT)
            .outputAsResult(UPDATED_OUTPUT_AS_RESULT)
            .createTs(UPDATED_CREATE_TS)
            .createBy(UPDATED_CREATE_BY)
            .updateTs(UPDATED_UPDATE_TS)
            .updateBy(UPDATED_UPDATE_BY);
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(updatedDataSubProcessor);

        restDataSubProcessorMockMvc.perform(put("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isOk());

        // Validate the DataSubProcessor in the database
        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeUpdate);
        DataSubProcessor testDataSubProcessor = dataSubProcessorList.get(dataSubProcessorList.size() - 1);
        assertThat(testDataSubProcessor.getDataProcessorId()).isEqualTo(UPDATED_DATA_PROCESSOR_ID);
        assertThat(testDataSubProcessor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSubProcessor.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDataSubProcessor.getDataProcessorType()).isEqualTo(UPDATED_DATA_PROCESSOR_TYPE);
        assertThat(testDataSubProcessor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDataSubProcessor.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testDataSubProcessor.isOutputAsTable()).isEqualTo(UPDATED_OUTPUT_AS_TABLE);
        assertThat(testDataSubProcessor.isOutputAsObject()).isEqualTo(UPDATED_OUTPUT_AS_OBJECT);
        assertThat(testDataSubProcessor.isOutputAsResult()).isEqualTo(UPDATED_OUTPUT_AS_RESULT);
        assertThat(testDataSubProcessor.getCreateTs()).isEqualTo(UPDATED_CREATE_TS);
        assertThat(testDataSubProcessor.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDataSubProcessor.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
        assertThat(testDataSubProcessor.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingDataSubProcessor() throws Exception {
        int databaseSizeBeforeUpdate = dataSubProcessorRepository.findAll().size();

        // Create the DataSubProcessor
        DataSubProcessorDTO dataSubProcessorDTO = dataSubProcessorMapper.toDto(dataSubProcessor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSubProcessorMockMvc.perform(put("/api/data-sub-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataSubProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSubProcessor in the database
        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataSubProcessor() throws Exception {
        // Initialize the database
        dataSubProcessorRepository.saveAndFlush(dataSubProcessor);

        int databaseSizeBeforeDelete = dataSubProcessorRepository.findAll().size();

        // Get the dataSubProcessor
        restDataSubProcessorMockMvc.perform(delete("/api/data-sub-processors/{id}", dataSubProcessor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataSubProcessor> dataSubProcessorList = dataSubProcessorRepository.findAll();
        assertThat(dataSubProcessorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSubProcessor.class);
        DataSubProcessor dataSubProcessor1 = new DataSubProcessor();
        dataSubProcessor1.setId(1L);
        DataSubProcessor dataSubProcessor2 = new DataSubProcessor();
        dataSubProcessor2.setId(dataSubProcessor1.getId());
        assertThat(dataSubProcessor1).isEqualTo(dataSubProcessor2);
        dataSubProcessor2.setId(2L);
        assertThat(dataSubProcessor1).isNotEqualTo(dataSubProcessor2);
        dataSubProcessor1.setId(null);
        assertThat(dataSubProcessor1).isNotEqualTo(dataSubProcessor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataSubProcessorDTO.class);
        DataSubProcessorDTO dataSubProcessorDTO1 = new DataSubProcessorDTO();
        dataSubProcessorDTO1.setId(1L);
        DataSubProcessorDTO dataSubProcessorDTO2 = new DataSubProcessorDTO();
        assertThat(dataSubProcessorDTO1).isNotEqualTo(dataSubProcessorDTO2);
        dataSubProcessorDTO2.setId(dataSubProcessorDTO1.getId());
        assertThat(dataSubProcessorDTO1).isEqualTo(dataSubProcessorDTO2);
        dataSubProcessorDTO2.setId(2L);
        assertThat(dataSubProcessorDTO1).isNotEqualTo(dataSubProcessorDTO2);
        dataSubProcessorDTO1.setId(null);
        assertThat(dataSubProcessorDTO1).isNotEqualTo(dataSubProcessorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataSubProcessorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataSubProcessorMapper.fromId(null)).isNull();
    }
}
