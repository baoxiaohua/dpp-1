package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.DataProcessor;
import com.hh.sd.core.repository.DataProcessorRepository;
import com.hh.sd.core.service.DataProcessorService;
import com.hh.sd.core.service.dto.DataProcessorDTO;
import com.hh.sd.core.service.mapper.DataProcessorMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.DataProcessorCriteria;
import com.hh.sd.core.service.DataProcessorQueryService;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.hh.sd.core.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hh.sd.core.domain.enumeration.State;
/**
 * Test class for the DataProcessorResource REST controller.
 *
 * @see DataProcessorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class DataProcessorResourceIntTest {

    private static final String DEFAULT_NAME_SPACE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SPACE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final State DEFAULT_STATE = State.DRAFT;
    private static final State UPDATED_STATE = State.ENABLED;

    private static final Boolean DEFAULT_REST_API = false;
    private static final Boolean UPDATED_REST_API = true;

    private static final Instant DEFAULT_CREATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private DataProcessorRepository dataProcessorRepository;

    @Autowired
    private DataProcessorMapper dataProcessorMapper;

    @Autowired
    private DataProcessorService dataProcessorService;

    @Autowired
    private DataProcessorQueryService dataProcessorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataProcessorMockMvc;

    private DataProcessor dataProcessor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataProcessorResource dataProcessorResource = new DataProcessorResource(dataProcessorService, dataProcessorQueryService);
        this.restDataProcessorMockMvc = MockMvcBuilders.standaloneSetup(dataProcessorResource)
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
    public static DataProcessor createEntity(EntityManager em) {
        DataProcessor dataProcessor = new DataProcessor()
            .nameSpace(DEFAULT_NAME_SPACE)
            .identifier(DEFAULT_IDENTIFIER)
            .name(DEFAULT_NAME)
            .state(DEFAULT_STATE)
            .restApi(DEFAULT_REST_API)
            .createTs(DEFAULT_CREATE_TS)
            .createBy(DEFAULT_CREATE_BY)
            .updateTs(DEFAULT_UPDATE_TS)
            .updateBy(DEFAULT_UPDATE_BY)
            .deleted(DEFAULT_DELETED);
        return dataProcessor;
    }

    @Before
    public void initTest() {
        dataProcessor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataProcessor() throws Exception {
        int databaseSizeBeforeCreate = dataProcessorRepository.findAll().size();

        // Create the DataProcessor
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);
        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isCreated());

        // Validate the DataProcessor in the database
        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeCreate + 1);
        DataProcessor testDataProcessor = dataProcessorList.get(dataProcessorList.size() - 1);
        assertThat(testDataProcessor.getNameSpace()).isEqualTo(DEFAULT_NAME_SPACE);
        assertThat(testDataProcessor.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testDataProcessor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataProcessor.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testDataProcessor.isRestApi()).isEqualTo(DEFAULT_REST_API);
        assertThat(testDataProcessor.getCreateTs()).isEqualTo(DEFAULT_CREATE_TS);
        assertThat(testDataProcessor.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDataProcessor.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
        assertThat(testDataProcessor.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testDataProcessor.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createDataProcessorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataProcessorRepository.findAll().size();

        // Create the DataProcessor with an existing ID
        dataProcessor.setId(1L);
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataProcessor in the database
        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameSpaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataProcessorRepository.findAll().size();
        // set the field null
        dataProcessor.setNameSpace(null);

        // Create the DataProcessor, which fails.
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataProcessorRepository.findAll().size();
        // set the field null
        dataProcessor.setIdentifier(null);

        // Create the DataProcessor, which fails.
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataProcessorRepository.findAll().size();
        // set the field null
        dataProcessor.setName(null);

        // Create the DataProcessor, which fails.
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRestApiIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataProcessorRepository.findAll().size();
        // set the field null
        dataProcessor.setRestApi(null);

        // Create the DataProcessor, which fails.
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        restDataProcessorMockMvc.perform(post("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataProcessors() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList
        restDataProcessorMockMvc.perform(get("/api/data-processors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameSpace").value(hasItem(DEFAULT_NAME_SPACE.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].restApi").value(hasItem(DEFAULT_REST_API.booleanValue())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDataProcessor() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get the dataProcessor
        restDataProcessorMockMvc.perform(get("/api/data-processors/{id}", dataProcessor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataProcessor.getId().intValue()))
            .andExpect(jsonPath("$.nameSpace").value(DEFAULT_NAME_SPACE.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.restApi").value(DEFAULT_REST_API.booleanValue()))
            .andExpect(jsonPath("$.createTs").value(DEFAULT_CREATE_TS.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where nameSpace equals to DEFAULT_NAME_SPACE
        defaultDataProcessorShouldBeFound("nameSpace.equals=" + DEFAULT_NAME_SPACE);

        // Get all the dataProcessorList where nameSpace equals to UPDATED_NAME_SPACE
        defaultDataProcessorShouldNotBeFound("nameSpace.equals=" + UPDATED_NAME_SPACE);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameSpaceIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where nameSpace in DEFAULT_NAME_SPACE or UPDATED_NAME_SPACE
        defaultDataProcessorShouldBeFound("nameSpace.in=" + DEFAULT_NAME_SPACE + "," + UPDATED_NAME_SPACE);

        // Get all the dataProcessorList where nameSpace equals to UPDATED_NAME_SPACE
        defaultDataProcessorShouldNotBeFound("nameSpace.in=" + UPDATED_NAME_SPACE);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameSpaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where nameSpace is not null
        defaultDataProcessorShouldBeFound("nameSpace.specified=true");

        // Get all the dataProcessorList where nameSpace is null
        defaultDataProcessorShouldNotBeFound("nameSpace.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where identifier equals to DEFAULT_IDENTIFIER
        defaultDataProcessorShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the dataProcessorList where identifier equals to UPDATED_IDENTIFIER
        defaultDataProcessorShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultDataProcessorShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the dataProcessorList where identifier equals to UPDATED_IDENTIFIER
        defaultDataProcessorShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where identifier is not null
        defaultDataProcessorShouldBeFound("identifier.specified=true");

        // Get all the dataProcessorList where identifier is null
        defaultDataProcessorShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where name equals to DEFAULT_NAME
        defaultDataProcessorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataProcessorList where name equals to UPDATED_NAME
        defaultDataProcessorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataProcessorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataProcessorList where name equals to UPDATED_NAME
        defaultDataProcessorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where name is not null
        defaultDataProcessorShouldBeFound("name.specified=true");

        // Get all the dataProcessorList where name is null
        defaultDataProcessorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where state equals to DEFAULT_STATE
        defaultDataProcessorShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the dataProcessorList where state equals to UPDATED_STATE
        defaultDataProcessorShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where state in DEFAULT_STATE or UPDATED_STATE
        defaultDataProcessorShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the dataProcessorList where state equals to UPDATED_STATE
        defaultDataProcessorShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where state is not null
        defaultDataProcessorShouldBeFound("state.specified=true");

        // Get all the dataProcessorList where state is null
        defaultDataProcessorShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByRestApiIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where restApi equals to DEFAULT_REST_API
        defaultDataProcessorShouldBeFound("restApi.equals=" + DEFAULT_REST_API);

        // Get all the dataProcessorList where restApi equals to UPDATED_REST_API
        defaultDataProcessorShouldNotBeFound("restApi.equals=" + UPDATED_REST_API);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByRestApiIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where restApi in DEFAULT_REST_API or UPDATED_REST_API
        defaultDataProcessorShouldBeFound("restApi.in=" + DEFAULT_REST_API + "," + UPDATED_REST_API);

        // Get all the dataProcessorList where restApi equals to UPDATED_REST_API
        defaultDataProcessorShouldNotBeFound("restApi.in=" + UPDATED_REST_API);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByRestApiIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where restApi is not null
        defaultDataProcessorShouldBeFound("restApi.specified=true");

        // Get all the dataProcessorList where restApi is null
        defaultDataProcessorShouldNotBeFound("restApi.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createTs equals to DEFAULT_CREATE_TS
        defaultDataProcessorShouldBeFound("createTs.equals=" + DEFAULT_CREATE_TS);

        // Get all the dataProcessorList where createTs equals to UPDATED_CREATE_TS
        defaultDataProcessorShouldNotBeFound("createTs.equals=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateTsIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createTs in DEFAULT_CREATE_TS or UPDATED_CREATE_TS
        defaultDataProcessorShouldBeFound("createTs.in=" + DEFAULT_CREATE_TS + "," + UPDATED_CREATE_TS);

        // Get all the dataProcessorList where createTs equals to UPDATED_CREATE_TS
        defaultDataProcessorShouldNotBeFound("createTs.in=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createTs is not null
        defaultDataProcessorShouldBeFound("createTs.specified=true");

        // Get all the dataProcessorList where createTs is null
        defaultDataProcessorShouldNotBeFound("createTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createBy equals to DEFAULT_CREATE_BY
        defaultDataProcessorShouldBeFound("createBy.equals=" + DEFAULT_CREATE_BY);

        // Get all the dataProcessorList where createBy equals to UPDATED_CREATE_BY
        defaultDataProcessorShouldNotBeFound("createBy.equals=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateByIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createBy in DEFAULT_CREATE_BY or UPDATED_CREATE_BY
        defaultDataProcessorShouldBeFound("createBy.in=" + DEFAULT_CREATE_BY + "," + UPDATED_CREATE_BY);

        // Get all the dataProcessorList where createBy equals to UPDATED_CREATE_BY
        defaultDataProcessorShouldNotBeFound("createBy.in=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByCreateByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where createBy is not null
        defaultDataProcessorShouldBeFound("createBy.specified=true");

        // Get all the dataProcessorList where createBy is null
        defaultDataProcessorShouldNotBeFound("createBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateTs equals to DEFAULT_UPDATE_TS
        defaultDataProcessorShouldBeFound("updateTs.equals=" + DEFAULT_UPDATE_TS);

        // Get all the dataProcessorList where updateTs equals to UPDATED_UPDATE_TS
        defaultDataProcessorShouldNotBeFound("updateTs.equals=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateTsIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateTs in DEFAULT_UPDATE_TS or UPDATED_UPDATE_TS
        defaultDataProcessorShouldBeFound("updateTs.in=" + DEFAULT_UPDATE_TS + "," + UPDATED_UPDATE_TS);

        // Get all the dataProcessorList where updateTs equals to UPDATED_UPDATE_TS
        defaultDataProcessorShouldNotBeFound("updateTs.in=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateTs is not null
        defaultDataProcessorShouldBeFound("updateTs.specified=true");

        // Get all the dataProcessorList where updateTs is null
        defaultDataProcessorShouldNotBeFound("updateTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateBy equals to DEFAULT_UPDATE_BY
        defaultDataProcessorShouldBeFound("updateBy.equals=" + DEFAULT_UPDATE_BY);

        // Get all the dataProcessorList where updateBy equals to UPDATED_UPDATE_BY
        defaultDataProcessorShouldNotBeFound("updateBy.equals=" + UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateByIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateBy in DEFAULT_UPDATE_BY or UPDATED_UPDATE_BY
        defaultDataProcessorShouldBeFound("updateBy.in=" + DEFAULT_UPDATE_BY + "," + UPDATED_UPDATE_BY);

        // Get all the dataProcessorList where updateBy equals to UPDATED_UPDATE_BY
        defaultDataProcessorShouldNotBeFound("updateBy.in=" + UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByUpdateByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where updateBy is not null
        defaultDataProcessorShouldBeFound("updateBy.specified=true");

        // Get all the dataProcessorList where updateBy is null
        defaultDataProcessorShouldNotBeFound("updateBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where deleted equals to DEFAULT_DELETED
        defaultDataProcessorShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the dataProcessorList where deleted equals to UPDATED_DELETED
        defaultDataProcessorShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultDataProcessorShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the dataProcessorList where deleted equals to UPDATED_DELETED
        defaultDataProcessorShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllDataProcessorsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        // Get all the dataProcessorList where deleted is not null
        defaultDataProcessorShouldBeFound("deleted.specified=true");

        // Get all the dataProcessorList where deleted is null
        defaultDataProcessorShouldNotBeFound("deleted.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataProcessorShouldBeFound(String filter) throws Exception {
        restDataProcessorMockMvc.perform(get("/api/data-processors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameSpace").value(hasItem(DEFAULT_NAME_SPACE.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].restApi").value(hasItem(DEFAULT_REST_API.booleanValue())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restDataProcessorMockMvc.perform(get("/api/data-processors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataProcessorShouldNotBeFound(String filter) throws Exception {
        restDataProcessorMockMvc.perform(get("/api/data-processors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataProcessorMockMvc.perform(get("/api/data-processors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataProcessor() throws Exception {
        // Get the dataProcessor
        restDataProcessorMockMvc.perform(get("/api/data-processors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataProcessor() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        int databaseSizeBeforeUpdate = dataProcessorRepository.findAll().size();

        // Update the dataProcessor
        DataProcessor updatedDataProcessor = dataProcessorRepository.findById(dataProcessor.getId()).get();
        // Disconnect from session so that the updates on updatedDataProcessor are not directly saved in db
        em.detach(updatedDataProcessor);
        updatedDataProcessor
            .nameSpace(UPDATED_NAME_SPACE)
            .identifier(UPDATED_IDENTIFIER)
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .restApi(UPDATED_REST_API)
            .createTs(UPDATED_CREATE_TS)
            .createBy(UPDATED_CREATE_BY)
            .updateTs(UPDATED_UPDATE_TS)
            .updateBy(UPDATED_UPDATE_BY)
            .deleted(UPDATED_DELETED);
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(updatedDataProcessor);

        restDataProcessorMockMvc.perform(put("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isOk());

        // Validate the DataProcessor in the database
        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeUpdate);
        DataProcessor testDataProcessor = dataProcessorList.get(dataProcessorList.size() - 1);
        assertThat(testDataProcessor.getNameSpace()).isEqualTo(UPDATED_NAME_SPACE);
        assertThat(testDataProcessor.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testDataProcessor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataProcessor.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDataProcessor.isRestApi()).isEqualTo(UPDATED_REST_API);
        assertThat(testDataProcessor.getCreateTs()).isEqualTo(UPDATED_CREATE_TS);
        assertThat(testDataProcessor.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDataProcessor.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
        assertThat(testDataProcessor.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testDataProcessor.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDataProcessor() throws Exception {
        int databaseSizeBeforeUpdate = dataProcessorRepository.findAll().size();

        // Create the DataProcessor
        DataProcessorDTO dataProcessorDTO = dataProcessorMapper.toDto(dataProcessor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataProcessorMockMvc.perform(put("/api/data-processors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataProcessor in the database
        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataProcessor() throws Exception {
        // Initialize the database
        dataProcessorRepository.saveAndFlush(dataProcessor);

        int databaseSizeBeforeDelete = dataProcessorRepository.findAll().size();

        // Get the dataProcessor
        restDataProcessorMockMvc.perform(delete("/api/data-processors/{id}", dataProcessor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataProcessor> dataProcessorList = dataProcessorRepository.findAll();
        assertThat(dataProcessorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataProcessor.class);
        DataProcessor dataProcessor1 = new DataProcessor();
        dataProcessor1.setId(1L);
        DataProcessor dataProcessor2 = new DataProcessor();
        dataProcessor2.setId(dataProcessor1.getId());
        assertThat(dataProcessor1).isEqualTo(dataProcessor2);
        dataProcessor2.setId(2L);
        assertThat(dataProcessor1).isNotEqualTo(dataProcessor2);
        dataProcessor1.setId(null);
        assertThat(dataProcessor1).isNotEqualTo(dataProcessor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataProcessorDTO.class);
        DataProcessorDTO dataProcessorDTO1 = new DataProcessorDTO();
        dataProcessorDTO1.setId(1L);
        DataProcessorDTO dataProcessorDTO2 = new DataProcessorDTO();
        assertThat(dataProcessorDTO1).isNotEqualTo(dataProcessorDTO2);
        dataProcessorDTO2.setId(dataProcessorDTO1.getId());
        assertThat(dataProcessorDTO1).isEqualTo(dataProcessorDTO2);
        dataProcessorDTO2.setId(2L);
        assertThat(dataProcessorDTO1).isNotEqualTo(dataProcessorDTO2);
        dataProcessorDTO1.setId(null);
        assertThat(dataProcessorDTO1).isNotEqualTo(dataProcessorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataProcessorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataProcessorMapper.fromId(null)).isNull();
    }
}
