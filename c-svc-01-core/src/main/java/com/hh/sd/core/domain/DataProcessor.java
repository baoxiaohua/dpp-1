package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.hh.sd.core.domain.enumeration.State;

/**
 * A DataProcessor.
 */
@Entity
@Table(name = "data_processor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name_space", length = 50, nullable = false)
    private String nameSpace;

    @NotNull
    @Size(max = 50)
    @Column(name = "identifier", length = 50, nullable = false, unique = true)
    private String identifier;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @NotNull
    @Column(name = "rest_api", nullable = false)
    private Boolean restApi;

    @Column(name = "create_ts")
    private Instant createTs;

    @Size(max = 50)
    @Column(name = "create_by", length = 50)
    private String createBy;

    @Column(name = "update_ts")
    private Instant updateTs;

    @Size(max = 50)
    @Column(name = "update_by", length = 50)
    private String updateBy;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public DataProcessor nameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public DataProcessor identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public DataProcessor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public DataProcessor state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean isRestApi() {
        return restApi;
    }

    public DataProcessor restApi(Boolean restApi) {
        this.restApi = restApi;
        return this;
    }

    public void setRestApi(Boolean restApi) {
        this.restApi = restApi;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public DataProcessor createTs(Instant createTs) {
        this.createTs = createTs;
        return this;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public String getCreateBy() {
        return createBy;
    }

    public DataProcessor createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public DataProcessor updateTs(Instant updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public DataProcessor updateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public DataProcessor deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        DataProcessor dataProcessor = (DataProcessor) o;
        if (dataProcessor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataProcessor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataProcessor{" +
            "id=" + getId() +
            ", nameSpace='" + getNameSpace() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", name='" + getName() + "'" +
            ", state='" + getState() + "'" +
            ", restApi='" + isRestApi() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            ", updateBy='" + getUpdateBy() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
