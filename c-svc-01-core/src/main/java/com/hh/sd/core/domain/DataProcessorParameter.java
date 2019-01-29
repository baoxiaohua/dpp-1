package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DataProcessorParameter.
 */
@Entity
@Table(name = "data_processor_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataProcessorParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_processor_id", nullable = false)
    private Long dataProcessorId;

    @Lob
    @Column(name = "json")
    private String json;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataProcessorId() {
        return dataProcessorId;
    }

    public DataProcessorParameter dataProcessorId(Long dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
        return this;
    }

    public void setDataProcessorId(Long dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
    }

    public String getJson() {
        return json;
    }

    public DataProcessorParameter json(String json) {
        this.json = json;
        return this;
    }

    public void setJson(String json) {
        this.json = json;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataProcessorParameter dataProcessorParameter = (DataProcessorParameter) o;
        if (dataProcessorParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataProcessorParameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataProcessorParameter{" +
            "id=" + getId() +
            ", dataProcessorId=" + getDataProcessorId() +
            ", json='" + getJson() + "'" +
            "}";
    }
}
