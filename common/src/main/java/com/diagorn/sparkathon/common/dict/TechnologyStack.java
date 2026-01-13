package com.diagorn.sparkathon.common.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Technology stack dictionary enum
 *
 * @author diagorn
 */
@RequiredArgsConstructor
@Getter
public enum TechnologyStack {
    JAVA("Java", "Java programming language", TechnologyCategory.BACKEND),
    SPRING_BOOT("Spring Boot", "Spring Boot framework", TechnologyCategory.BACKEND),
    KOTLIN("Kotlin", "Kotlin programming language", TechnologyCategory.BACKEND),
    NODE_JS("Node.js", "Node.js runtime", TechnologyCategory.BACKEND),
    PYTHON("Python", "Python programming language", TechnologyCategory.BACKEND),
    VUE_JS("Vue.js", "Vue.js framework", TechnologyCategory.FRONTEND),
    REACT("React", "React framework", TechnologyCategory.FRONTEND),
    ANGULAR("Angular", "Angular framework", TechnologyCategory.FRONTEND),
    POSTGRESQL("PostgreSQL", "PostgreSQL database", TechnologyCategory.DATABASE),
    MONGODB("MongoDB", "NoSQL document database", TechnologyCategory.DATABASE),
    REDIS("Redis", "In-memory data store", TechnologyCategory.DATABASE),
    MYSQL("MySQL", "MySQL database", TechnologyCategory.DATABASE),
    DOCKER("Docker", "Containerization platform", TechnologyCategory.DEVOPS),
    KUBERNETES("Kubernetes", "Container orchestration", TechnologyCategory.DEVOPS),
    KAFKA("Kafka", "Message broker", TechnologyCategory.BACKEND),
    AWS("AWS", "Amazon Web Services", TechnologyCategory.CLOUD),
    TENSORFLOW("TensorFlow", "Machine learning framework", TechnologyCategory.AI_ML),
    PYTORCH("PyTorch", "Machine learning library", TechnologyCategory.AI_ML);

    /**
     * Technology name
     */
    private final String name;
    /**
     * Technology description
     */
    private final String description;
    /**
     * Technology category
     */
    private final TechnologyCategory category;

    public static Optional<TechnologyStack> ofName(String name) {
        return Arrays.stream(values())
                .filter(tech -> tech.name.equalsIgnoreCase(name))
                .findFirst();
    }

    public static List<TechnologyStack> getByCategory(TechnologyCategory category) {
        return Arrays.stream(values())
                .filter(tech -> tech.category == category)
                .collect(Collectors.toList());
    }
}