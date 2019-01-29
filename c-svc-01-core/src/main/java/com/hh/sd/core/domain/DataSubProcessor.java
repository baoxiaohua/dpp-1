package com.hh.sd.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.hh.sd.core.domain.enumeration.DataProcessorType;

/**
 * A DataSubProcessor.
 */
@Entity
@Table(name = "data_sub_processor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataSubProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_processor_id", nullable = false)
    private Long dataProcessorId;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_processor_type", nullable = false)
    private DataProcessorType dataProcessorType;

    @Lob
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "output_as_table", nullable = false)
    private Boolean outputAsTable;

    @NotNull
    @Column(name = "output_as_object", nullable = false)
    private Boolean outputAsObject;

    @NotNull
    @Column(name = "output_as_result", nullable = false)
    private Boolean outputAsResult;

    @Column(name = "create_ts")
    private Instant createTs;

    @Size(max = 50)
    @Column(name = "create_by", length = 50)
    private String createBy;

    @Column(name = "update_ts")
    private Instant updateTs;

    @Size(max = 50)
    @Column(name = "update_by", length = 50)
    private String updateBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataProcessorId() {
        return dataProcessorId;
    }

    public DataSubProcessor dataProcessorId(Long dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
        return this;
    }

    public void setDataProcessorId(Long dataProcessorId) {
        this.dataProcessorId = dataProcessorId;
    }

    public String getName() {
        return name;
    }

    public DataSubProcessor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public DataSubProcessor sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public DataProcessorType getDataProcessorType() {
        return dataProcessorType;
    }

    public DataSubProcessor dataProcessorType(DataProcessorType dataProcessorType) {
        this.dataProcessorType = dataProcessorType;
        return this;
    }

    public void setDataProcessorType(DataProcessorType dataProcessorType) {
        this.dataProcessorType = dataProcessorType;
    }

    public String getCode() {
        return code;
    }

    public DataSubProcessor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isOutputAsTable() {
        return outputAsTable;
    }

    public DataSubProcessor outputAsTable(Boolean outputAsTable) {
        this.outputAsTable = outputAsTable;
        return this;
    }

    public void setOutputAsTable(Boolean outputAsTable) {
        this.outputAsTable = outputAsTable;
    }

    public Boolean isOutputAsObject() {
        return outputAsObject;
    }

    public DataSubProcessor outputAsObject(Boolean outputAsObject) {
        this.outputAsObject = outputAsObject;
        return this;
    }

    public void setOutputAsObject(Boolean outputAsObject) {
        this.outputAsObject = outputAsObject;
    }

    public Boolean isOutputAsResult() {
        return outputAsResult;
    }

    public DataSubProcessor outputAsResult(Boolean outputAsResult) {
        this.outputAsResult = outputAsResult;
        return this;
    }

    public void setOutputAsResult(Boolean outputAsResult) {
        this.outputAsResult = outputAsResult;
    }

    public Instant getCreateTs() {
        return createTs;
    }

    public DataSubProcessor createTs(Instant createTs) {
        this.createTs = createTs;
        return this;
    }

    public void setCreateTs(Instant createTs) {
        this.createTs = createTs;
    }

    public String getCreateBy() {
        return createBy;
    }

    public DataSubProcessor createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getUpdateTs() {
        return updateTs;
    }

    public DataSubProcessor updateTs(Instant updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(Instant updateTs) {
        this.updateTs = updateTs;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public DataSubProcessor updateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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
        DataSubProcessor dataSubProcessor = (DataSubProcessor) o;
        if (dataSubProcessor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataSubProcessor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataSubProcessor{" +
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
