package com.hh.sd.core.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DataProcessorParameter entity.
 */
public class DataProcessorParameterDTO implements Serializable {

    private Long id;

    @NotNull
    private Long dataProcessorId;

    @Lob
    private String json;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataProcessorId() {
        return dataProcessorId;
    }

    public void setDataProcessorId(Long dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataProcessorParameterDTO dataProcessorParameterDTO = (DataProcessorParameterDTO) o;
        if (dataProcessorParameterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataProcessorParameterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataProcessorParameterDTO{" +
            "id=" + getId() +
            ", dataProcessorId=" + getDataProcessorId() +
            ", json='" + getJson() + "'" +
            "}";
    }
}
