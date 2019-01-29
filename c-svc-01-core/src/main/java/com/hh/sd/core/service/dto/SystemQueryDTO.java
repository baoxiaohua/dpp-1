package com.hh.sd.core.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SystemQuery entity.
 */
public class SystemQueryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String identifier;

    @NotNull
    @Size(max = 4000)
    private String definition;

    @Size(max = 50)
    private String roles;

    private Instant createTs;

    private Instant updateTs;

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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemQueryDTO systemQueryDTO = (SystemQueryDTO) o;
        if (systemQueryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemQueryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemQueryDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", definition='" + getDefinition() + "'" +
            ", roles='" + getRoles() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            "}";
    }
}
