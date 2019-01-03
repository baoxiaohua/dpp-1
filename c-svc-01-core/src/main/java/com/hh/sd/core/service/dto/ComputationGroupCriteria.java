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
 * Criteria class for the ComputationGroup entity. This class is used in ComputationGroupResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /computation-groups?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComputationGroupCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter identifier;

    private StringFilter name;

    private StringFilter remark;

    private InstantFilter createTs;

    private InstantFilter updateTs;

    private BooleanFilter deleted;

    public ComputationGroupCriteria() {
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
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
        final ComputationGroupCriteria that = (ComputationGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(identifier, that.identifier) &&
            Objects.equals(name, that.name) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(createTs, that.createTs) &&
            Objects.equals(updateTs, that.updateTs) &&
            Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        identifier,
        name,
        remark,
        createTs,
        updateTs,
        deleted
        );
    }

    @Override
    public String toString() {
        return "ComputationGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (createTs != null ? "createTs=" + createTs + ", " : "") +
                (updateTs != null ? "updateTs=" + updateTs + ", " : "") +
                (deleted != null ? "deleted=" + deleted + ", " : "") +
            "}";
    }

}
