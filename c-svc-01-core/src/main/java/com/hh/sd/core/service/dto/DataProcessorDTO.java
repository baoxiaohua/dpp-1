package com.hh.sd.core.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.hh.sd.core.domain.enumeration.State;

/**
 * A DTO for the DataProcessor entity.
 */
public class DataProcessorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String nameSpace;

    @NotNull
    @Size(max = 50)
    private String identifier;

    @NotNull
    @Size(max = 50)
    private String name;

    private State state;

    @NotNull
    private Boolean restApi;

    private Instant createTs;

    @Size(max = 50)
    private String createBy;

    private Instant updateTs;

    @Size(max = 50)
    private String updateBy;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean isRestApi() {
        return restApi;
    }

    public void setRestApi(Boolean restApi) {
        this.restApi = restApi;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataProcessorDTO dataProcessorDTO = (DataProcessorDTO) o;
        if (dataProcessorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataProcessorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataProcessorDTO{" +
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
