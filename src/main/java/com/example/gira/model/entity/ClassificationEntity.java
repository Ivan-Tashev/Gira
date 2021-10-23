package com.example.gira.model.entity;

import com.example.gira.model.entity.enums.ClassificationEnum;

import javax.persistence.*;

@Entity
@Table(name = "classifications")
public class ClassificationEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ClassificationEnum classificationName;
    @Column
    private String description;

    public ClassificationEnum getClassificationName() {
        return classificationName;
    }

    public ClassificationEntity setClassificationName(ClassificationEnum classificationName) {
        this.classificationName = classificationName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ClassificationEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
