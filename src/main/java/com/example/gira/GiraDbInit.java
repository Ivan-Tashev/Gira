package com.example.gira;

import com.example.gira.model.entity.ClassificationEntity;
import com.example.gira.model.entity.enums.ClassificationEnum;
import com.example.gira.repo.ClassificationRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiraDbInit implements CommandLineRunner {
    private final ClassificationRepo classificationRepo;

    public GiraDbInit(ClassificationRepo classificationRepo) {
        this.classificationRepo = classificationRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (classificationRepo.count() == 0) {
            ClassificationEntity bug = new ClassificationEntity().setClassificationName(ClassificationEnum.BUG);
            ClassificationEntity feature = new ClassificationEntity().setClassificationName(ClassificationEnum.FEATURE);
            ClassificationEntity support = new ClassificationEntity().setClassificationName(ClassificationEnum.SUPPORT);
            ClassificationEntity other = new ClassificationEntity().setClassificationName(ClassificationEnum.OTHER);
            classificationRepo.saveAll(List.of(bug, feature, support, other));
        }
    }
}
