package com.hh.sd.core.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.hh.sd.core.domain.enumeration.DataProcessorType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the DataSubProcessor entity. This class is used in DataSubProcessorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-sub-processors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataSubProcessorCriteria implements Serializable {
    /**
     * Class for filtering DataProcessorType
     */
    public static class DataProcessorTypeFilter extends Filter<DataProcessorType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter dataProcessorId;

    private StringFilter name;

    private IntegerFilter sequence;

    private DataProcessorTypeFilter dataProcessorType;

    private BooleanFilter outputAsTable;

    private BooleanFilter outputAsObject;

    private BooleanFilter outputAsResult;

    private InstantFilter createTs;

    private StringFilter createBy;

    private InstantFilter updateTs;

    private StringFilter updateBy;

    public DataSubProcessorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDataProcessorId() {
        return dataProcessorId;
    }

    public void setDataProcessorId(LongFilter dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
    }

    public DataProcessorTypeFilter getDataProcessorType() {
        return dataProcessorType;
    }

    public void setDataProcessorType(DataProcessorTypeFilter dataProcessorType) {
        this.dataProcessorType = dataProcessorType;
    }

    public BooleanFilter getOutputAsTable() {
        return outputAsTable;
    }

    public void setOutputAsTable(BooleanFilter outputAsTable) {
        this.outputAsTable = outputAsTable;
    }

    public BooleanFilter getOutputAsObject() {
        return outputAsObject;
    }

    public void setOutputAsObject(BooleanFilter outputAsObject) {
        this.outputAsObject = outputAsObject;
    }

    public BooleanFilter getOutputAsResult() {
        return outputAsResult;
    }

    public void setOutputAsResult(BooleanFilter outputAsResult) {
        this.outputAsResult = outputAsResult;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataSubProcessorCriteria that = (DataSubProcessorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataProcessorId, that.dataProcessorId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(dataProcessorType, that.dataProcessorType) &&
            Objects.equals(outputAsTable, that.outputAsTable) &&
            Objects.equals(outputAsObject, that.outputAsObject) &&
            Objects.equals(outputAsResult, that.outputAsResult) &&
            Objects.equals(createTs, that.createTs) &&
            Objects.equals(createBy, that.createBy) &&
            Objects.equals(updateTs, that.updateTs) &&
            Objects.equals(updateBy, that.updateBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataProcessorId,
        name,
        sequence,
        dataProcessorType,
        outputAsTable,
        outputAsObject,
        outputAsResult,
        createTs,
        createBy,
        updateTs,
        updateBy
        );
    }

    @Override
    public String toString() {
        return "DataSubProcessorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataProcessorId != null ? "dataProcessorId=" + dataProcessorId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (dataProcessorType != null ? "dataProcessorType=" + dataProcessorType + ", " : "") +
                (outputAsTable != null ? "outputAsTable=" + outputAsTable + ", " : "") +
                (outputAsObject != null ? "outputAsObject=" + outputAsObject + ", " : "") +
                (outputAsResult != null ? "outputAsResult=" + outputAsResult + ", " : "") +
                (createTs != null ? "createTs=" + createTs + ", " : "") +
                (createBy != null ? "createBy=" + createBy + ", " : "") +
                (updateTs != null ? "updateTs=" + updateTs + ", " : "") +
                (updateBy != null ? "updateBy=" + updateBy + ", " : "") +
            "}";
    }

}
