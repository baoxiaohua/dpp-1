package com.hh.sd.core.web.rest;

import com.hh.sd.core.CoreApp;

import com.hh.sd.core.domain.DataProcessorParameter;
import com.hh.sd.core.repository.DataProcessorParameterRepository;
import com.hh.sd.core.service.DataProcessorParameterService;
import com.hh.sd.core.service.dto.DataProcessorParameterDTO;
import com.hh.sd.core.service.mapper.DataProcessorParameterMapper;
import com.hh.sd.core.web.rest.errors.ExceptionTranslator;
import com.hh.sd.core.service.dto.DataProcessorParameterCriteria;
import com.hh.sd.core.service.DataProcessorParameterQueryService;

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
import java.util.List;


import static com.hh.sd.core.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DataProcessorParameterResource REST controller.
 *
 * @see DataProcessorParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class DataProcessorParameterResourceIntTest {

    private static final Long DEFAULT_DATA_PROCESSOR_ID = 1L;
    private static final Long UPDATED_DATA_PROCESSOR_ID = 2L;

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    @Autowired
    private DataProcessorParameterRepository dataProcessorParameterRepository;

    @Autowired
    private DataProcessorParameterMapper dataProcessorParameterMapper;

    @Autowired
    private DataProcessorParameterService dataProcessorParameterService;

    @Autowired
    private DataProcessorParameterQueryService dataProcessorParameterQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataProcessorParameterMockMvc;

    private DataProcessorParameter dataProcessorParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataProcessorParameterResource dataProcessorParameterResource = new DataProcessorParameterResource(dataProcessorParameterService, dataProcessorParameterQueryService);
        this.restDataProcessorParameterMockMvc = MockMvcBuilders.standaloneSetup(dataProcessorParameterResource)
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
    public static DataProcessorParameter createEntity(EntityManager em) {
        DataProcessorParameter dataProcessorParameter = new DataProcessorParameter()
            .dataProcessorId(DEFAULT_DATA_PROCESSOR_ID)
            .json(DEFAULT_JSON);
        return dataProcessorParameter;
    }

    @Before
    public void initTest() {
        dataProcessorParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataProcessorParameter() throws Exception {
        int databaseSizeBeforeCreate = dataProcessorParameterRepository.findAll().size();

        // Create the DataProcessorParameter
        DataProcessorParameterDTO dataProcessorParameterDTO = dataProcessorParameterMapper.toDto(dataProcessorParameter);
        restDataProcessorParameterMockMvc.perform(post("/api/data-processor-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorParameterDTO)))
            .andExpect(status().isCreated());

        // Validate the DataProcessorParameter in the database
        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeCreate + 1);
        DataProcessorParameter testDataProcessorParameter = dataProcessorParameterList.get(dataProcessorParameterList.size() - 1);
        assertThat(testDataProcessorParameter.getDataProcessorId()).isEqualTo(DEFAULT_DATA_PROCESSOR_ID);
        assertThat(testDataProcessorParameter.getJson()).isEqualTo(DEFAULT_JSON);
    }

    @Test
    @Transactional
    public void createDataProcessorParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataProcessorParameterRepository.findAll().size();

        // Create the DataProcessorParameter with an existing ID
        dataProcessorParameter.setId(1L);
        DataProcessorParameterDTO dataProcessorParameterDTO = dataProcessorParameterMapper.toDto(dataProcessorParameter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataProcessorParameterMockMvc.perform(post("/api/data-processor-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataProcessorParameter in the database
        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataProcessorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataProcessorParameterRepository.findAll().size();
        // set the field null
        dataProcessorParameter.setDataProcessorId(null);

        // Create the DataProcessorParameter, which fails.
        DataProcessorParameterDTO dataProcessorParameterDTO = dataProcessorParameterMapper.toDto(dataProcessorParameter);

        restDataProcessorParameterMockMvc.perform(post("/api/data-processor-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorParameterDTO)))
            .andExpect(status().isBadRequest());

        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataProcessorParameters() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataProcessorParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProcessorId").value(hasItem(DEFAULT_DATA_PROCESSOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));
    }
    
    @Test
    @Transactional
    public void getDataProcessorParameter() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get the dataProcessorParameter
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters/{id}", dataProcessorParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataProcessorParameter.getId().intValue()))
            .andExpect(jsonPath("$.dataProcessorId").value(DEFAULT_DATA_PROCESSOR_ID.intValue()))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON.toString()));
    }

    @Test
    @Transactional
    public void getAllDataProcessorParametersByDataProcessorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList where dataProcessorId equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldBeFound("dataProcessorId.equals=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataProcessorParameterList where dataProcessorId equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldNotBeFound("dataProcessorId.equals=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataProcessorParametersByDataProcessorIdIsInShouldWork() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList where dataProcessorId in DEFAULT_DATA_PROCESSOR_ID or UPDATED_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldBeFound("dataProcessorId.in=" + DEFAULT_DATA_PROCESSOR_ID + "," + UPDATED_DATA_PROCESSOR_ID);

        // Get all the dataProcessorParameterList where dataProcessorId equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldNotBeFound("dataProcessorId.in=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataProcessorParametersByDataProcessorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList where dataProcessorId is not null
        defaultDataProcessorParameterShouldBeFound("dataProcessorId.specified=true");

        // Get all the dataProcessorParameterList where dataProcessorId is null
        defaultDataProcessorParameterShouldNotBeFound("dataProcessorId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataProcessorParametersByDataProcessorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList where dataProcessorId greater than or equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldBeFound("dataProcessorId.greaterOrEqualThan=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataProcessorParameterList where dataProcessorId greater than or equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldNotBeFound("dataProcessorId.greaterOrEqualThan=" + UPDATED_DATA_PROCESSOR_ID);
    }

    @Test
    @Transactional
    public void getAllDataProcessorParametersByDataProcessorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        // Get all the dataProcessorParameterList where dataProcessorId less than or equals to DEFAULT_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldNotBeFound("dataProcessorId.lessThan=" + DEFAULT_DATA_PROCESSOR_ID);

        // Get all the dataProcessorParameterList where dataProcessorId less than or equals to UPDATED_DATA_PROCESSOR_ID
        defaultDataProcessorParameterShouldBeFound("dataProcessorId.lessThan=" + UPDATED_DATA_PROCESSOR_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDataProcessorParameterShouldBeFound(String filter) throws Exception {
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataProcessorParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProcessorId").value(hasItem(DEFAULT_DATA_PROCESSOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));

        // Check, that the count call also returns 1
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDataProcessorParameterShouldNotBeFound(String filter) throws Exception {
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataProcessorParameter() throws Exception {
        // Get the dataProcessorParameter
        restDataProcessorParameterMockMvc.perform(get("/api/data-processor-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataProcessorParameter() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        int databaseSizeBeforeUpdate = dataProcessorParameterRepository.findAll().size();

        // Update the dataProcessorParameter
        DataProcessorParameter updatedDataProcessorParameter = dataProcessorParameterRepository.findById(dataProcessorParameter.getId()).get();
        // Disconnect from session so that the updates on updatedDataProcessorParameter are not directly saved in db
        em.detach(updatedDataProcessorParameter);
        updatedDataProcessorParameter
            .dataProcessorId(UPDATED_DATA_PROCESSOR_ID)
            .json(UPDATED_JSON);
        DataProcessorParameterDTO dataProcessorParameterDTO = dataProcessorParameterMapper.toDto(updatedDataProcessorParameter);

        restDataProcessorParameterMockMvc.perform(put("/api/data-processor-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorParameterDTO)))
            .andExpect(status().isOk());

        // Validate the DataProcessorParameter in the database
        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeUpdate);
        DataProcessorParameter testDataProcessorParameter = dataProcessorParameterList.get(dataProcessorParameterList.size() - 1);
        assertThat(testDataProcessorParameter.getDataProcessorId()).isEqualTo(UPDATED_DATA_PROCESSOR_ID);
        assertThat(testDataProcessorParameter.getJson()).isEqualTo(UPDATED_JSON);
    }

    @Test
    @Transactional
    public void updateNonExistingDataProcessorParameter() throws Exception {
        int databaseSizeBeforeUpdate = dataProcessorParameterRepository.findAll().size();

        // Create the DataProcessorParameter
        DataProcessorParameterDTO dataProcessorParameterDTO = dataProcessorParameterMapper.toDto(dataProcessorParameter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataProcessorParameterMockMvc.perform(put("/api/data-processor-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataProcessorParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataProcessorParameter in the database
        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataProcessorParameter() throws Exception {
        // Initialize the database
        dataProcessorParameterRepository.saveAndFlush(dataProcessorParameter);

        int databaseSizeBeforeDelete = dataProcessorParameterRepository.findAll().size();

        // Get the dataProcessorParameter
        restDataProcessorParameterMockMvc.perform(delete("/api/data-processor-parameters/{id}", dataProcessorParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataProcessorParameter> dataProcessorParameterList = dataProcessorParameterRepository.findAll();
        assertThat(dataProcessorParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataProcessorParameter.class);
        DataProcessorParameter dataProcessorParameter1 = new DataProcessorParameter();
        dataProcessorParameter1.setId(1L);
        DataProcessorParameter dataProcessorParameter2 = new DataProcessorParameter();
        dataProcessorParameter2.setId(dataProcessorParameter1.getId());
        assertThat(dataProcessorParameter1).isEqualTo(dataProcessorParameter2);
        dataProcessorParameter2.setId(2L);
        assertThat(dataProcessorParameter1).isNotEqualTo(dataProcessorParameter2);
        dataProcessorParameter1.setId(null);
        assertThat(dataProcessorParameter1).isNotEqualTo(dataProcessorParameter2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataProcessorParameterDTO.class);
        DataProcessorParameterDTO dataProcessorParameterDTO1 = new DataProcessorParameterDTO();
        dataProcessorParameterDTO1.setId(1L);
        DataProcessorParameterDTO dataProcessorParameterDTO2 = new DataProcessorParameterDTO();
        assertThat(dataProcessorParameterDTO1).isNotEqualTo(dataProcessorParameterDTO2);
        dataProcessorParameterDTO2.setId(dataProcessorParameterDTO1.getId());
        assertThat(dataProcessorParameterDTO1).isEqualTo(dataProcessorParameterDTO2);
        dataProcessorParameterDTO2.setId(2L);
        assertThat(dataProcessorParameterDTO1).isNotEqualTo(dataProcessorParameterDTO2);
        dataProcessorParameterDTO1.setId(null);
        assertThat(dataProcessorParameterDTO1).isNotEqualTo(dataProcessorParameterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataProcessorParameterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataProcessorParameterMapper.fromId(null)).isNull();
    }
}
