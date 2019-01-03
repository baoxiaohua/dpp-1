package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SystemQuery.
 */
@Entity
@Table(name = "system_query")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemQuery implements Serializable {

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
    @Size(max = 2000)
    @Column(name = "definition", length = 2000, nullable = false)
    private String definition;

    @Size(max = 50)
    @Column(name = "jhi_roles", length = 50)
    private String roles;

    @Column(name = "create_ts")
    private Instant createTs;

    @Column(name = "update_ts")
    private Instant updateTs;

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

    public SystemQuery identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDefinition() {
        return definition;
    }

    public SystemQuery definition(String definition) {
        this.definition = definition;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getRoles() {
        return roles;
    }

    public SystemQuery roles(String roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public SystemQuery createTs(Instant createTs) {
        this.createTs = createTs;
        return this;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public SystemQuery updateTs(Instant updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
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
        SystemQuery systemQuery = (SystemQuery) o;
        if (systemQuery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemQuery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemQuery{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", definition='" + getDefinition() + "'" +
            ", roles='" + getRoles() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            "}";
    }
}
