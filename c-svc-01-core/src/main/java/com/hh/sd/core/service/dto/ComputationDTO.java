package com.hh.sd.core.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.hh.sd.core.domain.enumeration.ComputationType;
import com.hh.sd.core.domain.enumeration.ComputationStatus;

/**
 * A DTO for the Computation entity.
 */
public class ComputationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String identifier;

    @NotNull
    @Size(max = 50)
    private String name;

    private ComputationType type;

    private ComputationStatus status;

    private String remark;

    private Instant createTs;

    private Instant updateTs;

    private Long computationGroupId;

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

    public ComputationType getType() {
        return type;
    }

    public void setType(ComputationType type) {
        this.type = type;
    }

    public ComputationStatus getStatus() {
        return status;
    }

    public void setStatus(ComputationStatus status) {
        this.status = status;
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

    public Long getComputationGroupId() {
        return computationGroupId;
    }

    public void setComputationGroupId(Long computationGroupId) {
        this.computationGroupId = computationGroupId;
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

        ComputationDTO computationDTO = (ComputationDTO) o;
        if (computationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), computationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComputationDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            ", computationGroupId=" + getComputationGroupId() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
