package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.ComputationGroup;
import com.hh.sd.core.repository.ComputationGroupRepository;
import com.hh.sd.core.service.ComputationGroupService;
import com.hh.sd.core.service.dto.ComputationGroupDTO;
import com.hh.sd.core.service.mapper.ComputationGroupMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.ComputationGroupCriteria;
import com.hh.sd.core.service.ComputationGroupQueryService;

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
 * Test class for the ComputationGroupResource REST controller.
 *
 * @see ComputationGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class ComputationGroupResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ComputationGroupRepository computationGroupRepository;

    @Autowired
    private ComputationGroupMapper computationGroupMapper;

    @Autowired
    private ComputationGroupService computationGroupService;

    @Autowired
    private ComputationGroupQueryService computationGroupQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComputationGroupMockMvc;

    private ComputationGroup computationGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComputationGroupResource computationGroupResource = new ComputationGroupResource(computationGroupService, computationGroupQueryService);
        this.restComputationGroupMockMvc = MockMvcBuilders.standaloneSetup(computationGroupResource)
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
    public static ComputationGroup createEntity(EntityManager em) {
        ComputationGroup computationGroup = new ComputationGroup()
            .identifier(DEFAULT_IDENTIFIER)
            .name(DEFAULT_NAME)
            .remark(DEFAULT_REMARK)
            .createTs(DEFAULT_CREATE_TS)
            .updateTs(DEFAULT_UPDATE_TS)
            .deleted(DEFAULT_DELETED);
        return computationGroup;
    }

    @Before
    public void initTest() {
        computationGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createComputationGroup() throws Exception {
        int databaseSizeBeforeCreate = computationGroupRepository.findAll().size();

        // Create the ComputationGroup
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(computationGroup);
        restComputationGroupMockMvc.perform(post("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ComputationGroup in the database
        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ComputationGroup testComputationGroup = computationGroupList.get(computationGroupList.size() - 1);
        assertThat(testComputationGroup.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testComputationGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testComputationGroup.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testComputationGroup.getCreateTs()).isEqualTo(DEFAULT_CREATE_TS);
        assertThat(testComputationGroup.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
        assertThat(testComputationGroup.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createComputationGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = computationGroupRepository.findAll().size();

        // Create the ComputationGroup with an existing ID
        computationGroup.setId(1L);
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(computationGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComputationGroupMockMvc.perform(post("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ComputationGroup in the database
        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = computationGroupRepository.findAll().size();
        // set the field null
        computationGroup.setIdentifier(null);

        // Create the ComputationGroup, which fails.
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(computationGroup);

        restComputationGroupMockMvc.perform(post("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = computationGroupRepository.findAll().size();
        // set the field null
        computationGroup.setName(null);

        // Create the ComputationGroup, which fails.
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(computationGroup);

        restComputationGroupMockMvc.perform(post("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComputationGroups() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList
        restComputationGroupMockMvc.perform(get("/api/computation-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computationGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComputationGroup() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get the computationGroup
        restComputationGroupMockMvc.perform(get("/api/computation-groups/{id}", computationGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(computationGroup.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createTs").value(DEFAULT_CREATE_TS.toString()))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where identifier equals to DEFAULT_IDENTIFIER
        defaultComputationGroupShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the computationGroupList where identifier equals to UPDATED_IDENTIFIER
        defaultComputationGroupShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultComputationGroupShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the computationGroupList where identifier equals to UPDATED_IDENTIFIER
        defaultComputationGroupShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where identifier is not null
        defaultComputationGroupShouldBeFound("identifier.specified=true");

        // Get all the computationGroupList where identifier is null
        defaultComputationGroupShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where name equals to DEFAULT_NAME
        defaultComputationGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the computationGroupList where name equals to UPDATED_NAME
        defaultComputationGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultComputationGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the computationGroupList where name equals to UPDATED_NAME
        defaultComputationGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where name is not null
        defaultComputationGroupShouldBeFound("name.specified=true");

        // Get all the computationGroupList where name is null
        defaultComputationGroupShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where remark equals to DEFAULT_REMARK
        defaultComputationGroupShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the computationGroupList where remark equals to UPDATED_REMARK
        defaultComputationGroupShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultComputationGroupShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the computationGroupList where remark equals to UPDATED_REMARK
        defaultComputationGroupShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where remark is not null
        defaultComputationGroupShouldBeFound("remark.specified=true");

        // Get all the computationGroupList where remark is null
        defaultComputationGroupShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByCreateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where createTs equals to DEFAULT_CREATE_TS
        defaultComputationGroupShouldBeFound("createTs.equals=" + DEFAULT_CREATE_TS);

        // Get all the computationGroupList where createTs equals to UPDATED_CREATE_TS
        defaultComputationGroupShouldNotBeFound("createTs.equals=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByCreateTsIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where createTs in DEFAULT_CREATE_TS or UPDATED_CREATE_TS
        defaultComputationGroupShouldBeFound("createTs.in=" + DEFAULT_CREATE_TS + "," + UPDATED_CREATE_TS);

        // Get all the computationGroupList where createTs equals to UPDATED_CREATE_TS
        defaultComputationGroupShouldNotBeFound("createTs.in=" + UPDATED_CREATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByCreateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where createTs is not null
        defaultComputationGroupShouldBeFound("createTs.specified=true");

        // Get all the computationGroupList where createTs is null
        defaultComputationGroupShouldNotBeFound("createTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByUpdateTsIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where updateTs equals to DEFAULT_UPDATE_TS
        defaultComputationGroupShouldBeFound("updateTs.equals=" + DEFAULT_UPDATE_TS);

        // Get all the computationGroupList where updateTs equals to UPDATED_UPDATE_TS
        defaultComputationGroupShouldNotBeFound("updateTs.equals=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByUpdateTsIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where updateTs in DEFAULT_UPDATE_TS or UPDATED_UPDATE_TS
        defaultComputationGroupShouldBeFound("updateTs.in=" + DEFAULT_UPDATE_TS + "," + UPDATED_UPDATE_TS);

        // Get all the computationGroupList where updateTs equals to UPDATED_UPDATE_TS
        defaultComputationGroupShouldNotBeFound("updateTs.in=" + UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByUpdateTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where updateTs is not null
        defaultComputationGroupShouldBeFound("updateTs.specified=true");

        // Get all the computationGroupList where updateTs is null
        defaultComputationGroupShouldNotBeFound("updateTs.specified=false");
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where deleted equals to DEFAULT_DELETED
        defaultComputationGroupShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the computationGroupList where deleted equals to UPDATED_DELETED
        defaultComputationGroupShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultComputationGroupShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the computationGroupList where deleted equals to UPDATED_DELETED
        defaultComputationGroupShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllComputationGroupsByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        // Get all the computationGroupList where deleted is not null
        defaultComputationGroupShouldBeFound("deleted.specified=true");

        // Get all the computationGroupList where deleted is null
        defaultComputationGroupShouldNotBeFound("deleted.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultComputationGroupShouldBeFound(String filter) throws Exception {
        restComputationGroupMockMvc.perform(get("/api/computation-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computationGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTs").value(hasItem(DEFAULT_CREATE_TS.toString())))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restComputationGroupMockMvc.perform(get("/api/computation-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultComputationGroupShouldNotBeFound(String filter) throws Exception {
        restComputationGroupMockMvc.perform(get("/api/computation-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComputationGroupMockMvc.perform(get("/api/computation-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComputationGroup() throws Exception {
        // Get the computationGroup
        restComputationGroupMockMvc.perform(get("/api/computation-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComputationGroup() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        int databaseSizeBeforeUpdate = computationGroupRepository.findAll().size();

        // Update the computationGroup
        ComputationGroup updatedComputationGroup = computationGroupRepository.findById(computationGroup.getId()).get();
        // Disconnect from session so that the updates on updatedComputationGroup are not directly saved in db
        em.detach(updatedComputationGroup);
        updatedComputationGroup
            .identifier(UPDATED_IDENTIFIER)
            .name(UPDATED_NAME)
            .remark(UPDATED_REMARK)
            .createTs(UPDATED_CREATE_TS)
            .updateTs(UPDATED_UPDATE_TS)
            .deleted(UPDATED_DELETED);
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(updatedComputationGroup);

        restComputationGroupMockMvc.perform(put("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ComputationGroup in the database
        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeUpdate);
        ComputationGroup testComputationGroup = computationGroupList.get(computationGroupList.size() - 1);
        assertThat(testComputationGroup.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testComputationGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testComputationGroup.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testComputationGroup.getCreateTs()).isEqualTo(UPDATED_CREATE_TS);
        assertThat(testComputationGroup.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
        assertThat(testComputationGroup.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingComputationGroup() throws Exception {
        int databaseSizeBeforeUpdate = computationGroupRepository.findAll().size();

        // Create the ComputationGroup
        ComputationGroupDTO computationGroupDTO = computationGroupMapper.toDto(computationGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComputationGroupMockMvc.perform(put("/api/computation-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computationGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ComputationGroup in the database
        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComputationGroup() throws Exception {
        // Initialize the database
        computationGroupRepository.saveAndFlush(computationGroup);

        int databaseSizeBeforeDelete = computationGroupRepository.findAll().size();

        // Get the computationGroup
        restComputationGroupMockMvc.perform(delete("/api/computation-groups/{id}", computationGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ComputationGroup> computationGroupList = computationGroupRepository.findAll();
        assertThat(computationGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComputationGroup.class);
        ComputationGroup computationGroup1 = new ComputationGroup();
        computationGroup1.setId(1L);
        ComputationGroup computationGroup2 = new ComputationGroup();
        computationGroup2.setId(computationGroup1.getId());
        assertThat(computationGroup1).isEqualTo(computationGroup2);
        computationGroup2.setId(2L);
        assertThat(computationGroup1).isNotEqualTo(computationGroup2);
        computationGroup1.setId(null);
        assertThat(computationGroup1).isNotEqualTo(computationGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComputationGroupDTO.class);
        ComputationGroupDTO computationGroupDTO1 = new ComputationGroupDTO();
        computationGroupDTO1.setId(1L);
        ComputationGroupDTO computationGroupDTO2 = new ComputationGroupDTO();
        assertThat(computationGroupDTO1).isNotEqualTo(computationGroupDTO2);
        computationGroupDTO2.setId(computationGroupDTO1.getId());
        assertThat(computationGroupDTO1).isEqualTo(computationGroupDTO2);
        computationGroupDTO2.setId(2L);
        assertThat(computationGroupDTO1).isNotEqualTo(computationGroupDTO2);
        computationGroupDTO1.setId(null);
        assertThat(computationGroupDTO1).isNotEqualTo(computationGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(computationGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(computationGroupMapper.fromId(null)).isNull();
    }
}
