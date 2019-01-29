package com.hh.sd.core.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.hh.sd.core.domain.enumeration.DataProcessorType;

/**
 * A DTO for the DataSubProcessor entity.
 */
public class DataSubProcessorDTO implements Serializable {

    private Long id;

    @NotNull
    private Long dataProcessorId;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private Integer sequence;

    @NotNull
    private DataProcessorType dataProcessorType;

    @Lob
    private String code;

    @NotNull
    private Boolean outputAsTable;

    @NotNull
    private Boolean outputAsObject;

    @NotNull
    private Boolean outputAsResult;

    private Instant createTs;

    @Size(max = 50)
    private String createBy;

    private Instant updateTs;

    @Size(max = 50)
    private String updateBy;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public DataProcessorType getDataProcessorType() {
        return dataProcessorType;
    }

    public void setDataProcessorType(DataProcessorType dataProcessorType) {
        this.dataProcessorType = dataProcessorType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isOutputAsTable() {
        return outputAsTable;
    }

    public void setOutputAsTable(Boolean outputAsTable) {
        this.outputAsTable = outputAsTable;
    }

    public Boolean isOutputAsObject() {
        return outputAsObject;
    }

    public void setOutputAsObject(Boolean outputAsObject) {
        this.outputAsObject = outputAsObject;
    }

    public Boolean isOutputAsResult() {
        return outputAsResult;
    }

    public void setOutputAsResult(Boolean outputAsResult) {
        this.outputAsResult = outputAsResult;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
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

        DataSubProcessorDTO dataSubProcessorDTO = (DataSubProcessorDTO) o;
        if (dataSubProcessorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataSubProcessorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataSubProcessorDTO{" +
            "id=" + getId() +
            ", dataProcessorId=" + getDataProcessorId() +
            ", name='" + getName() + "'" +
            ", sequence=" + getSequence() +
            ", dataProcessorType='" + getDataProcessorType() + "'" +
            ", code='" + getCode() + "'" +
            ", outputAsTable='" + isOutputAsTable() + "'" +
            ", outputAsObject='" + isOutputAsObject() + "'" +
            ", outputAsResult='" + isOutputAsResult() + "'" +
            ", createTs='" + getCreateTs() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            ", updateBy='" + getUpdateBy() + "'" +
            "}";
    }
}
