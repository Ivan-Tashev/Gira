package com.example.gira.repo;

import com.example.gira.model.entity.ClassificationEntity;
import com.example.gira.model.entity.TaskEntity;
import com.example.gira.model.entity.enums.ClassificationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepo extends JpaRepository<ClassificationEntity, Long> {

    ClassificationEntity findByClassificationName(ClassificationEnum classificationName);
}
