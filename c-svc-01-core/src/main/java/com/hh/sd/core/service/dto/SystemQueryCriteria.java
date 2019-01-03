package com.hh.sd.core.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the SystemQuery entity. This class is used in SystemQueryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /system-queries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemQueryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter identifier;

    private StringFilter definition;

    private StringFilter roles;

    private InstantFilter createTs;

    private InstantFilter updateTs;

    public SystemQueryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdentifier() {
        return identifier;
    }

    public void setIdentifier(StringFilter identifier) {
        this.identifier = identifier;
    }

    public StringFilter getDefinition() {
        return definition;
    }

    public void setDefinition(StringFilter definition) {
        this.definition = definition;
    }

    public StringFilter getRoles() {
        return roles;
    }

    public void setRoles(StringFilter roles) {
        this.roles = roles;
    }

    public InstantFilter getCreateTs() {
        return createTs;
    }

    public void setCreateTs(InstantFilter createTs) {
        this.createTs = createTs;
    }

    public InstantFilter getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(InstantFilter updateTs) {
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
        final SystemQueryCriteria that = (SystemQueryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(identifier, that.identifier) &&
            Objects.equals(definition, that.definition) &&
            Objects.equals(roles, that.roles) &&
            Objects.equals(createTs, that.createTs) &&
            Objects.equals(updateTs, that.updateTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        identifier,
        definition,
        roles,
        createTs,
        updateTs
        );
    }

    @Override
    public String toString() {
        return "SystemQueryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (definition != null ? "definition=" + definition + ", " : "") +
                (roles != null ? "roles=" + roles + ", " : "") +
                (createTs != null ? "createTs=" + createTs + ", " : "") +
                (updateTs != null ? "updateTs=" + updateTs + ", " : "") +
            "}";
    }

}
