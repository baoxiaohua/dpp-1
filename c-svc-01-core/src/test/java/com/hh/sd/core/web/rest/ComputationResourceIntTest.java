package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.Computation;
import com.hh.sd.core.repository.ComputationRepository;
import com.hh.sd.core.service.ComputationService;
import com.hh.sd.core.service.dto.ComputationDTO;
import com.hh.sd.core.service.mapper.ComputationMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.ComputationCriteria;
import com.hh.sd.core.service.ComputationQueryService;

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

import com.hh.sd.core.domain.enumeration.ComputationType;
import com.hh.sd.core.domain.enumeration.ComputationStatus;
/**
 * Test class for the ComputationResource REST controller.
 *
 * @see ComputationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class ComputationResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ComputationType DEFAULT_TYPE = ComputationType.KYLIN_SQL;
    private static final ComputationType UPDATED_TYPE = ComputationType.KYLIN_SQL;

    private static final ComputationStatus DEFAULT_STATUS = ComputationStatus.DRAFT;
    private static final ComputationStatus UPDATED_STATUS = ComputationStatus.ACTIVE;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_COMPUTATION_GROUP_ID = 1L;
    private static final Long UPDATED_COMPUTATION_GROUP_ID = 2L;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ComputationRepository computationRepository;

    @Autowired
    private ComputationMapper computationMapper;

    @Autowired
    private ComputationService computationService;

    @Autowired
    private ComputationQueryService computationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComputationMockMvc;

    private Computation computation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComputationResource computationResource = new ComputationResource(computationService, computationQueryService);
        this.restComputationMockMvc = MockMvcBuilders.standaloneSetup(computationResource)
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
    public static Computation createEntity(EntityManager em) {
        Computation computation = new Computation()
            .identifier(DEFAULT_IDENTIFIER)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .remark(DEFAULT_REMARK)
            .createTs(DEFAULT_CREATE_TS)
            .updateTs(DEFAULT_UPDATE_TS)
            .computationGroupId(DEFAULT_COMPUTATION_GROUP_ID)
            .deleted(DEFAULT_DELETED);
        return computation;
    }

    @Before
    public void initTest() {
        computation = createEntity(em);
    }

    @Test
    @Transactional
    public void createComputation() throws Exception {
        int databaseSizeBeforeCreate = computationRepository.findAll().size();

        // Create the Computation
        ComputationDTO computationDTO = computationMapper.toDto(computation);
        restComputationMockMvc.perform(post("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isCreated());

        // Validate the Computation in the database
        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeCreate + 1);
        Computation testComputation = computationList.get(computationList.size() - 1);
        assertThat(testComputation.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testComputation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testComputation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testComputation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testComputation.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testComputation.getCreateTs()).isEqualTo(DEFAULT_CREATE_TS);
        assertThat(testComputation.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
        assertThat(testComputation.getComputationGroupId()).isEqualTo(DEFAULT_COMPUTATION_GROUP_ID);
        assertThat(testComputation.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createComputationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = computationRepository.findAll().size();

        // Create the Computation with an existing ID
        computation.setId(1L);
        ComputationDTO computationDTO = computationMapper.toDto(computation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComputationMockMvc.perform(post("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Computation in the database
        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = computationRepository.findAll().size();
        // set the field null
        computation.setIdentifier(null);

        // Create the Computation, which fails.
        ComputationDTO computationDTO = computationMapper.toDto(computation);

        restComputationMockMvc.perform(post("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isBadRequest());

        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = computationRepository.findAll().size();
        // set the field null
        computation.setName(null);

        // Create the Computation, which fails.
        ComputationDTO computationDTO = computationMapper.toDto(computation);

        restComputationMockMvc.perform(post("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isBadRequest());

        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComputations() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList
        restComputationMockMvc.perform(get("/api/computations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computation.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].computationGroupId").value(hasItem(DEFAULT_COMPUTATION_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComputation() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get the computation
        restComputationMockMvc.perform(get("/api/computations/{id}", computation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(computation.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createTs").value(DEFAULT_CREATE_TS.toString()))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS.toString()))
            .andExpect(jsonPath("$.computationGroupId").value(DEFAULT_COMPUTATION_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllComputationsByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where identifier equals to DEFAULT_IDENTIFIER
        defaultComputationShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the computationList where identifier equals to UPDATED_IDENTIFIER
        defaultComputationShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllComputationsByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultComputationShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the computationList where identifier equals to UPDATED_IDENTIFIER
        defaultComputationShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllComputationsByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where identifier is not null
        defaultComputationShouldBeFound("identifier.specified=true");

        // Get all the computationList where identifier is null
        defaultComputationShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where name equals to DEFAULT_NAME
        defaultComputationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the computationList where name equals to UPDATED_NAME
        defaultComputationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComputationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultComputationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the computationList where name equals to UPDATED_NAME
        defaultComputationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComputationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where name is not null
        defaultComputationShouldBeFound("name.specified=true");

        // Get all the computationList where name is null
        defaultComputationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where type equals to DEFAULT_TYPE
        defaultComputationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the computationList where type equals to UPDATED_TYPE
        defaultComputationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllComputationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultComputationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the computationList where type equals to UPDATED_TYPE
        defaultComputationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllComputationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where type is not null
        defaultComputationShouldBeFound("type.specified=true");

        // Get all the computationList where type is null
        defaultComputationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where status equals to DEFAULT_STATUS
        defaultComputationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the computationList where status equals to UPDATED_STATUS
        defaultComputationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllComputationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultComputationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the computationList where status equals to UPDATED_STATUS
        defaultComputationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllComputationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where status is not null
        defaultComputationShouldBeFound("status.specified=true");

        // Get all the computationList where status is null
        defaultComputationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where remark equals to DEFAULT_REMARK
        defaultComputationShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the computationList where remark equals to UPDATED_REMARK
        defaultComputationShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllComputationsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultComputationShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the computationList where remark equals to UPDATED_REMARK
        defaultComputationShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllComputationsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where remark is not null
        defaultComputationShouldBeFound("remark.specified=true");

        // Get all the computationList where remark is null
        defaultComputationShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByCreateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where createTs equals to DEFAULT_CREATE_TS
        defaultComputationShouldBeFound("createTs.equals=" + DEFAULT_CREATE_TS);

        // Get all the computationList where createTs equals to UPDATED_CREATE_TS
        defaultComputationShouldNotBeFound("createTs.equals=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationsByCreateTsIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where createTs in DEFAULT_CREATE_TS or UPDATED_CREATE_TS
        defaultComputationShouldBeFound("createTs.in=" + DEFAULT_CREATE_TS + "," + UPDATED_CREATE_TS);

        // Get all the computationList where createTs equals to UPDATED_CREATE_TS
        defaultComputationShouldNotBeFound("createTs.in=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationsByCreateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where createTs is not null
        defaultComputationShouldBeFound("createTs.specified=true");

        // Get all the computationList where createTs is null
        defaultComputationShouldNotBeFound("createTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByUpdateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where updateTs equals to DEFAULT_UPDATE_TS
        defaultComputationShouldBeFound("updateTs.equals=" + DEFAULT_UPDATE_TS);

        // Get all the computationList where updateTs equals to UPDATED_UPDATE_TS
        defaultComputationShouldNotBeFound("updateTs.equals=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationsByUpdateTsIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where updateTs in DEFAULT_UPDATE_TS or UPDATED_UPDATE_TS
        defaultComputationShouldBeFound("updateTs.in=" + DEFAULT_UPDATE_TS + "," + UPDATED_UPDATE_TS);

        // Get all the computationList where updateTs equals to UPDATED_UPDATE_TS
        defaultComputationShouldNotBeFound("updateTs.in=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationsByUpdateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where updateTs is not null
        defaultComputationShouldBeFound("updateTs.specified=true");

        // Get all the computationList where updateTs is null
        defaultComputationShouldNotBeFound("updateTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByComputationGroupIdIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where computationGroupId equals to DEFAULT_COMPUTATION_GROUP_ID
        defaultComputationShouldBeFound("computationGroupId.equals=" + DEFAULT_COMPUTATION_GROUP_ID);

        // Get all the computationList where computationGroupId equals to UPDATED_COMPUTATION_GROUP_ID
        defaultComputationShouldNotBeFound("computationGroupId.equals=" + UPDATED_COMPUTATION_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllComputationsByComputationGroupIdIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where computationGroupId in DEFAULT_COMPUTATION_GROUP_ID or UPDATED_COMPUTATION_GROUP_ID
        defaultComputationShouldBeFound("computationGroupId.in=" + DEFAULT_COMPUTATION_GROUP_ID + "," + UPDATED_COMPUTATION_GROUP_ID);

        // Get all the computationList where computationGroupId equals to UPDATED_COMPUTATION_GROUP_ID
        defaultComputationShouldNotBeFound("computationGroupId.in=" + UPDATED_COMPUTATION_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllComputationsByComputationGroupIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where computationGroupId is not null
        defaultComputationShouldBeFound("computationGroupId.specified=true");

        // Get all the computationList where computationGroupId is null
        defaultComputationShouldNotBeFound("computationGroupId.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationsByComputationGroupIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where computationGroupId greater than or equals to DEFAULT_COMPUTATION_GROUP_ID
        defaultComputationShouldBeFound("computationGroupId.greaterOrEqualThan=" + DEFAULT_COMPUTATION_GROUP_ID);

        // Get all the computationList where computationGroupId greater than or equals to UPDATED_COMPUTATION_GROUP_ID
        defaultComputationShouldNotBeFound("computationGroupId.greaterOrEqualThan=" + UPDATED_COMPUTATION_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllComputationsByComputationGroupIdIsLessThanSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where computationGroupId less than or equals to DEFAULT_COMPUTATION_GROUP_ID
        defaultComputationShouldNotBeFound("computationGroupId.lessThan=" + DEFAULT_COMPUTATION_GROUP_ID);

        // Get all the computationList where computationGroupId less than or equals to UPDATED_COMPUTATION_GROUP_ID
        defaultComputationShouldBeFound("computationGroupId.lessThan=" + UPDATED_COMPUTATION_GROUP_ID);
    }


    @Test
    @Transactional
    public void getAllComputationsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where deleted equals to DEFAULT_DELETED
        defaultComputationShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the computationList where deleted equals to UPDATED_DELETED
        defaultComputationShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllComputationsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultComputationShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the computationList where deleted equals to UPDATED_DELETED
        defaultComputationShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllComputationsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        // Get all the computationList where deleted is not null
        defaultComputationShouldBeFound("deleted.specified=true");

        // Get all the computationList where deleted is null
        defaultComputationShouldNotBeFound("deleted.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultComputationShouldBeFound(String filter) throws Exception {
        restComputationMockMvc.perform(get("/api/computations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computation.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].computationGroupId").value(hasItem(DEFAULT_COMPUTATION_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restComputationMockMvc.perform(get("/api/computations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultComputationShouldNotBeFound(String filter) throws Exception {
        restComputationMockMvc.perform(get("/api/computations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComputationMockMvc.perform(get("/api/computations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComputation() throws Exception {
        // Get the computation
        restComputationMockMvc.perform(get("/api/computations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComputation() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        int databaseSizeBeforeUpdate = computationRepository.findAll().size();

        // Update the computation
        Computation updatedComputation = computationRepository.findById(computation.getId()).get();
        // Disconnect from session so that the updates on updatedComputation are not directly saved in db
        em.detach(updatedComputation);
        updatedComputation
            .identifier(UPDATED_IDENTIFIER)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .remark(UPDATED_REMARK)
            .createTs(UPDATED_CREATE_TS)
            .updateTs(UPDATED_UPDATE_TS)
            .computationGroupId(UPDATED_COMPUTATION_GROUP_ID)
            .deleted(UPDATED_DELETED);
        ComputationDTO computationDTO = computationMapper.toDto(updatedComputation);

        restComputationMockMvc.perform(put("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isOk());

        // Validate the Computation in the database
        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeUpdate);
        Computation testComputation = computationList.get(computationList.size() - 1);
        assertThat(testComputation.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testComputation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testComputation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testComputation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testComputation.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testComputation.getCreateTs()).isEqualTo(UPDATED_CREATE_TS);
        assertThat(testComputation.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
        assertThat(testComputation.getComputationGroupId()).isEqualTo(UPDATED_COMPUTATION_GROUP_ID);
        assertThat(testComputation.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingComputation() throws Exception {
        int databaseSizeBeforeUpdate = computationRepository.findAll().size();

        // Create the Computation
        ComputationDTO computationDTO = computationMapper.toDto(computation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComputationMockMvc.perform(put("/api/computations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Computation in the database
        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComputation() throws Exception {
        // Initialize the database
        computationRepository.saveAndFlush(computation);

        int databaseSizeBeforeDelete = computationRepository.findAll().size();

        // Get the computation
        restComputationMockMvc.perform(delete("/api/computations/{id}", computation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Computation> computationList = computationRepository.findAll();
        assertThat(computationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Computation.class);
        Computation computation1 = new Computation();
        computation1.setId(1L);
        Computation computation2 = new Computation();
        computation2.setId(computation1.getId());
        assertThat(computation1).isEqualTo(computation2);
        computation2.setId(2L);
        assertThat(computation1).isNotEqualTo(computation2);
        computation1.setId(null);
        assertThat(computation1).isNotEqualTo(computation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComputationDTO.class);
        ComputationDTO computationDTO1 = new ComputationDTO();
        computationDTO1.setId(1L);
        ComputationDTO computationDTO2 = new ComputationDTO();
        assertThat(computationDTO1).isNotEqualTo(computationDTO2);
        computationDTO2.setId(computationDTO1.getId());
        assertThat(computationDTO1).isEqualTo(computationDTO2);
        computationDTO2.setId(2L);
        assertThat(computationDTO1).isNotEqualTo(computationDTO2);
        computationDTO1.setId(null);
        assertThat(computationDTO1).isNotEqualTo(computationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(computationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(computationMapper.fromId(null)).isNull();
    }
}
