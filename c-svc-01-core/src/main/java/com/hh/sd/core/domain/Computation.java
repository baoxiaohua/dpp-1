package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.hh.sd.core.domain.enumeration.ComputationType;

import com.hh.sd.core.domain.enumeration.ComputationStatus;

/**
 * A Computation.
 */
@Entity
@Table(name = "computation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Computation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "identifier", length = 50, nullable = false, unique = true)
    private String identifier;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ComputationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ComputationStatus status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_ts")
    private Instant createTs;

    @Column(name = "update_ts")
    private Instant updateTs;

    @Column(name = "computation_group_id")
    private Long computationGroupId;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Computation identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public Computation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComputationType getType() {
        return type;
    }

    public Computation type(ComputationType type) {
        this.type = type;
        return this;
    }

    public void setType(ComputationType type) {
        this.type = type;
    }

    public ComputationStatus getStatus() {
        return status;
    }

    public Computation status(ComputationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ComputationStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public Computation remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public Computation createTs(Instant createTs) {
        this.createTs = createTs;
        return this;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public Computation updateTs(Instant updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public Long getComputationGroupId() {
        return computationGroupId;
    }

    public Computation computationGroupId(Long computationGroupId) {
        this.computationGroupId = computationGroupId;
        return this;
    }

    public void setComputationGroupId(Long computationGroupId) {
        this.computationGroupId = computationGroupId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Computation deleted(Boolean deleted) {
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
        Computation computation = (Computation) o;
        if (computation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), computation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Computation{" +
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
