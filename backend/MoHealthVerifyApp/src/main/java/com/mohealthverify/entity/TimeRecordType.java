package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lkp_time_record_types")
public class TimeRecordType {
    @Id
    @Column(name = "time_record_type_id")
    private Long timeRecordTypeId;

    @Column(name = "time_record_type", nullable = false)
    private String timeRecordType;

    public Long getTimeRecordTypeId() { return timeRecordTypeId; }
    public void setTimeRecordTypeId(Long id) { this.timeRecordTypeId = id; }

    public String getTimeRecordType() { return timeRecordType; }
    public void setTimeRecordType(String type) { this.timeRecordType = type; }
}