package com.hh.sd.core.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.hh.sd.core.domain.enumeration.State;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the DataProcessor entity. This class is used in DataProcessorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-processors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataProcessorCriteria implements Serializable {
    /**
     * Class for filtering State
     */
    public static class StateFilter extends Filter<State> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameSpace;

    private StringFilter identifier;

    private StringFilter name;

    private StateFilter state;

    private BooleanFilter restApi;

    private InstantFilter createTs;

    private StringFilter createBy;

    private InstantFilter updateTs;

    private StringFilter updateBy;

    private BooleanFilter deleted;

    public DataProcessorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(StringFilter nameSpace) {
        this.nameSpace = nameSpace;
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

    public StateFilter getState() {
        return state;
    }

    public void setState(StateFilter state) {
        this.state = state;
    }

    public BooleanFilter getRestApi() {
        return restApi;
    }

    public void setRestApi(BooleanFilter restApi) {
        this.restApi = restApi;
    }

    public InstantFilter getCreateTs() {
        return createTs;
    }

    public void setCreateTs(InstantFilter createTs) {
        this.createTs = createTs;
    }

    public StringFilter getCreateBy() {
        return createBy;
    }

    public void setCreateBy(StringFilter createBy) {
        this.createBy = createBy;
    }

    public InstantFilter getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(InstantFilter updateTs) {
        this.updateTs = updateTs;
    }

    public StringFilter getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(StringFilter updateBy) {
        this.updateBy = updateBy;
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
        final DataProcessorCriteria that = (DataProcessorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameSpace, that.nameSpace) &&
            Objects.equals(identifier, that.identifier) &&
            Objects.equals(name, that.name) &&
            Objects.equals(state, that.state) &&
            Objects.equals(restApi, that.restApi) &&
            Objects.equals(createTs, that.createTs) &&
            Objects.equals(createBy, that.createBy) &&
            Objects.equals(updateTs, that.updateTs) &&
            Objects.equals(updateBy, that.updateBy) &&
            Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameSpace,
        identifier,
        name,
        state,
        restApi,
        createTs,
        createBy,
        updateTs,
        updateBy,
        deleted
        );
    }

    @Override
    public String toString() {
        return "DataProcessorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameSpace != null ? "nameSpace=" + nameSpace + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (restApi != null ? "restApi=" + restApi + ", " : "") +
                (createTs != null ? "createTs=" + createTs + ", " : "") +
                (createBy != null ? "createBy=" + createBy + ", " : "") +
                (updateTs != null ? "updateTs=" + updateTs + ", " : "") +
                (updateBy != null ? "updateBy=" + updateBy + ", " : "") +
                (deleted != null ? "deleted=" + deleted + ", " : "") +
            "}";
    }

}
