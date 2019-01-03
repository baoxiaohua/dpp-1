package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ComputationGroup.
 */
@Entity
@Table(name = "computation_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ComputationGroup implements Serializable {

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

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_ts")
    private Instant createTs;

    @Column(name = "update_ts")
    private Instant updateTs;

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

    public ComputationGroup identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public ComputationGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public ComputationGroup remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public ComputationGroup createTs(Instant createTs) {
        this.createTs = createTs;
        return this;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public ComputationGroup updateTs(Instant updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public ComputationGroup deleted(Boolean deleted) {
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
        ComputationGroup computationGroup = (ComputationGroup) o;
        if (computationGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), computationGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComputationGroup{" +
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
