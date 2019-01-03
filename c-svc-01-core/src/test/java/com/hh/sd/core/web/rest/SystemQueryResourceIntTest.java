package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.SystemQuery;
import com.hh.sd.core.repository.SystemQueryRepository;
import com.hh.sd.core.service.SystemQueryService;
import com.hh.sd.core.service.dto.SystemQueryDTO;
import com.hh.sd.core.service.mapper.SystemQueryMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.SystemQueryCriteria;
import com.hh.sd.core.service.SystemQueryQueryService;

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

/**
 * Test class for the SystemQueryResource REST controller.
 *
 * @see SystemQueryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class SystemQueryResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_DEFINITION = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITION = "BBBBBBBBBB";

    private static final String DEFAULT_ROLES = "AAAAAAAAAA";
    private static final String UPDATED_ROLES = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SystemQueryRepository systemQueryRepository;

    @Autowired
    private SystemQueryMapper systemQueryMapper;

    @Autowired
    private SystemQueryService systemQueryService;

    @Autowired
    private SystemQueryQueryService systemQueryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemQueryMockMvc;

    private SystemQuery systemQuery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemQueryResource systemQueryResource = new SystemQueryResource(systemQueryService, systemQueryQueryService);
        this.restSystemQueryMockMvc = MockMvcBuilders.standaloneSetup(systemQueryResource)
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
    public static SystemQuery createEntity(EntityManager em) {
        SystemQuery systemQuery = new SystemQuery()
            .identifier(DEFAULT_IDENTIFIER)
            .definition(DEFAULT_DEFINITION)
            .roles(DEFAULT_ROLES)
            .createTs(DEFAULT_CREATE_TS)
            .updateTs(DEFAULT_UPDATE_TS);
        return systemQuery;
    }

    @Before
    public void initTest() {
        systemQuery = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemQuery() throws Exception {
        int databaseSizeBeforeCreate = systemQueryRepository.findAll().size();

        // Create the SystemQuery
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(systemQuery);
        restSystemQueryMockMvc.perform(post("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemQuery in the database
        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeCreate + 1);
        SystemQuery testSystemQuery = systemQueryList.get(systemQueryList.size() - 1);
        assertThat(testSystemQuery.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testSystemQuery.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
        assertThat(testSystemQuery.getRoles()).isEqualTo(DEFAULT_ROLES);
        assertThat(testSystemQuery.getCreateTs()).isEqualTo(DEFAULT_CREATE_TS);
        assertThat(testSystemQuery.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
    }

    @Test
    @Transactional
    public void createSystemQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemQueryRepository.findAll().size();

        // Create the SystemQuery with an existing ID
        systemQuery.setId(1L);
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(systemQuery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemQueryMockMvc.perform(post("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemQuery in the database
        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemQueryRepository.findAll().size();
        // set the field null
        systemQuery.setIdentifier(null);

        // Create the SystemQuery, which fails.
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(systemQuery);

        restSystemQueryMockMvc.perform(post("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefinitionIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemQueryRepository.findAll().size();
        // set the field null
        systemQuery.setDefinition(null);

        // Create the SystemQuery, which fails.
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(systemQuery);

        restSystemQueryMockMvc.perform(post("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemQueries() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList
        restSystemQueryMockMvc.perform(get("/api/system-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].roles").value(hasItem(DEFAULT_ROLES.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemQuery() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get the systemQuery
        restSystemQueryMockMvc.perform(get("/api/system-queries/{id}", systemQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemQuery.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION.toString()))
            .andExpect(jsonPath("$.roles").value(DEFAULT_ROLES.toString()))
            .andExpect(jsonPath("$.createTs").value(DEFAULT_CREATE_TS.toString()))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS.toString()));
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where identifier equals to DEFAULT_IDENTIFIER
        defaultSystemQueryShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the systemQueryList where identifier equals to UPDATED_IDENTIFIER
        defaultSystemQueryShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultSystemQueryShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the systemQueryList where identifier equals to UPDATED_IDENTIFIER
        defaultSystemQueryShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where identifier is not null
        defaultSystemQueryShouldBeFound("identifier.specified=true");

        // Get all the systemQueryList where identifier is null
        defaultSystemQueryShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByDefinitionIsEqualToSomething() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where definition equals to DEFAULT_DEFINITION
        defaultSystemQueryShouldBeFound("definition.equals=" + DEFAULT_DEFINITION);

        // Get all the systemQueryList where definition equals to UPDATED_DEFINITION
        defaultSystemQueryShouldNotBeFound("definition.equals=" + UPDATED_DEFINITION);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByDefinitionIsInShouldWork() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where definition in DEFAULT_DEFINITION or UPDATED_DEFINITION
        defaultSystemQueryShouldBeFound("definition.in=" + DEFAULT_DEFINITION + "," + UPDATED_DEFINITION);

        // Get all the systemQueryList where definition equals to UPDATED_DEFINITION
        defaultSystemQueryShouldNotBeFound("definition.in=" + UPDATED_DEFINITION);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByDefinitionIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where definition is not null
        defaultSystemQueryShouldBeFound("definition.specified=true");

        // Get all the systemQueryList where definition is null
        defaultSystemQueryShouldNotBeFound("definition.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByRolesIsEqualToSomething() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where roles equals to DEFAULT_ROLES
        defaultSystemQueryShouldBeFound("roles.equals=" + DEFAULT_ROLES);

        // Get all the systemQueryList where roles equals to UPDATED_ROLES
        defaultSystemQueryShouldNotBeFound("roles.equals=" + UPDATED_ROLES);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByRolesIsInShouldWork() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where roles in DEFAULT_ROLES or UPDATED_ROLES
        defaultSystemQueryShouldBeFound("roles.in=" + DEFAULT_ROLES + "," + UPDATED_ROLES);

        // Get all the systemQueryList where roles equals to UPDATED_ROLES
        defaultSystemQueryShouldNotBeFound("roles.in=" + UPDATED_ROLES);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByRolesIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where roles is not null
        defaultSystemQueryShouldBeFound("roles.specified=true");

        // Get all the systemQueryList where roles is null
        defaultSystemQueryShouldNotBeFound("roles.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByCreateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where createTs equals to DEFAULT_CREATE_TS
        defaultSystemQueryShouldBeFound("createTs.equals=" + DEFAULT_CREATE_TS);

        // Get all the systemQueryList where createTs equals to UPDATED_CREATE_TS
        defaultSystemQueryShouldNotBeFound("createTs.equals=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByCreateTsIsInShouldWork() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where createTs in DEFAULT_CREATE_TS or UPDATED_CREATE_TS
        defaultSystemQueryShouldBeFound("createTs.in=" + DEFAULT_CREATE_TS + "," + UPDATED_CREATE_TS);

        // Get all the systemQueryList where createTs equals to UPDATED_CREATE_TS
        defaultSystemQueryShouldNotBeFound("createTs.in=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByCreateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where createTs is not null
        defaultSystemQueryShouldBeFound("createTs.specified=true");

        // Get all the systemQueryList where createTs is null
        defaultSystemQueryShouldNotBeFound("createTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByUpdateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where updateTs equals to DEFAULT_UPDATE_TS
        defaultSystemQueryShouldBeFound("updateTs.equals=" + DEFAULT_UPDATE_TS);

        // Get all the systemQueryList where updateTs equals to UPDATED_UPDATE_TS
        defaultSystemQueryShouldNotBeFound("updateTs.equals=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByUpdateTsIsInShouldWork() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where updateTs in DEFAULT_UPDATE_TS or UPDATED_UPDATE_TS
        defaultSystemQueryShouldBeFound("updateTs.in=" + DEFAULT_UPDATE_TS + "," + UPDATED_UPDATE_TS);

        // Get all the systemQueryList where updateTs equals to UPDATED_UPDATE_TS
        defaultSystemQueryShouldNotBeFound("updateTs.in=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllSystemQueriesByUpdateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        // Get all the systemQueryList where updateTs is not null
        defaultSystemQueryShouldBeFound("updateTs.specified=true");

        // Get all the systemQueryList where updateTs is null
        defaultSystemQueryShouldNotBeFound("updateTs.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSystemQueryShouldBeFound(String filter) throws Exception {
        restSystemQueryMockMvc.perform(get("/api/system-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].roles").value(hasItem(DEFAULT_ROLES.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())));

        // Check, that the count call also returns 1
        restSystemQueryMockMvc.perform(get("/api/system-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSystemQueryShouldNotBeFound(String filter) throws Exception {
        restSystemQueryMockMvc.perform(get("/api/system-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemQueryMockMvc.perform(get("/api/system-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSystemQuery() throws Exception {
        // Get the systemQuery
        restSystemQueryMockMvc.perform(get("/api/system-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemQuery() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        int databaseSizeBeforeUpdate = systemQueryRepository.findAll().size();

        // Update the systemQuery
        SystemQuery updatedSystemQuery = systemQueryRepository.findById(systemQuery.getId()).get();
        // Disconnect from session so that the updates on updatedSystemQuery are not directly saved in db
        em.detach(updatedSystemQuery);
        updatedSystemQuery
            .identifier(UPDATED_IDENTIFIER)
            .definition(UPDATED_DEFINITION)
            .roles(UPDATED_ROLES)
            .createTs(UPDATED_CREATE_TS)
            .updateTs(UPDATED_UPDATE_TS);
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(updatedSystemQuery);

        restSystemQueryMockMvc.perform(put("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isOk());

        // Validate the SystemQuery in the database
        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeUpdate);
        SystemQuery testSystemQuery = systemQueryList.get(systemQueryList.size() - 1);
        assertThat(testSystemQuery.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testSystemQuery.getDefinition()).isEqualTo(UPDATED_DEFINITION);
        assertThat(testSystemQuery.getRoles()).isEqualTo(UPDATED_ROLES);
        assertThat(testSystemQuery.getCreateTs()).isEqualTo(UPDATED_CREATE_TS);
        assertThat(testSystemQuery.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemQuery() throws Exception {
        int databaseSizeBeforeUpdate = systemQueryRepository.findAll().size();

        // Create the SystemQuery
        SystemQueryDTO systemQueryDTO = systemQueryMapper.toDto(systemQuery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemQueryMockMvc.perform(put("/api/system-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemQuery in the database
        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemQuery() throws Exception {
        // Initialize the database
        systemQueryRepository.saveAndFlush(systemQuery);

        int databaseSizeBeforeDelete = systemQueryRepository.findAll().size();

        // Get the systemQuery
        restSystemQueryMockMvc.perform(delete("/api/system-queries/{id}", systemQuery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SystemQuery> systemQueryList = systemQueryRepository.findAll();
        assertThat(systemQueryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemQuery.class);
        SystemQuery systemQuery1 = new SystemQuery();
        systemQuery1.setId(1L);
        SystemQuery systemQuery2 = new SystemQuery();
        systemQuery2.setId(systemQuery1.getId());
        assertThat(systemQuery1).isEqualTo(systemQuery2);
        systemQuery2.setId(2L);
        assertThat(systemQuery1).isNotEqualTo(systemQuery2);
        systemQuery1.setId(null);
        assertThat(systemQuery1).isNotEqualTo(systemQuery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemQueryDTO.class);
        SystemQueryDTO systemQueryDTO1 = new SystemQueryDTO();
        systemQueryDTO1.setId(1L);
        SystemQueryDTO systemQueryDTO2 = new SystemQueryDTO();
        assertThat(systemQueryDTO1).isNotEqualTo(systemQueryDTO2);
        systemQueryDTO2.setId(systemQueryDTO1.getId());
        assertThat(systemQueryDTO1).isEqualTo(systemQueryDTO2);
        systemQueryDTO2.setId(2L);
        assertThat(systemQueryDTO1).isNotEqualTo(systemQueryDTO2);
        systemQueryDTO1.setId(null);
        assertThat(systemQueryDTO1).isNotEqualTo(systemQueryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemQueryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemQueryMapper.fromId(null)).isNull();
    }
}
