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

/**
 * Criteria class for the DataProcessorParameter entity. This class is used in DataProcessorParameterResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-processor-parameters?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataProcessorParameterCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter dataProcessorId;

    public DataProcessorParameterCriteria() {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataProcessorParameterCriteria that = (DataProcessorParameterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataProcessorId, that.dataProcessorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataProcessorId
        );
    }

    @Override
    public String toString() {
        return "DataProcessorParameterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataProcessorId != null ? "dataProcessorId=" + dataProcessorId + ", " : "") +
            "}";
    }

}
