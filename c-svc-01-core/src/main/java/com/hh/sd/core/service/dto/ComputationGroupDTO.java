package com.hh.sd.core.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ComputationGroup entity.
 */
public class ComputationGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String identifier;

    @NotNull
    @Size(max = 50)
    private String name;

    private String remark;

    private Instant createTs;

    private Instant updateTs;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
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

        ComputationGroupDTO computationGroupDTO = (ComputationGroupDTO) o;
        if (computationGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), computationGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComputationGroupDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
