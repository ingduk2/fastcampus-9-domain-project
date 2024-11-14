package org.fastcampus.acceptance;

import com.google.common.base.CaseFormat;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.EntityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataBaseCleanUp implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(this::isEntity)
                .map(e -> {
                    //@Table 어노테이션이 있으면 name 속성, 없으면 Entity Name CamelCase -> snake_case 로 변환
                    String tableName = hasTableAnnotation(e)
                            ? e.getJavaType().getAnnotation(Table.class).name()
                            : CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName());

                    // @Table 어노테이션의 name 속성이 비어있다면, CamelCase -> snake_case 로 변환
                    return tableName.isBlank()
                            ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName())
                            : tableName;
                })
                .collect(Collectors.toList());
    }

    private boolean isEntity(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    private boolean hasTableAnnotation(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (final String tableName : tableNames) {
            // EntityType에서 직접 Entity 클래스 정보를 얻어오기
            Class<?> entityClass = findEntityClassByTableName(tableName);
            boolean isEmbeddedId = hasEmbeddedId(entityClass);

            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            if (!isEmbeddedId) {
                String idColumnName = getIdFieldName(entityClass);
                entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + idColumnName + " RESTART WITH 1").executeUpdate();
            }
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private boolean hasEmbeddedId(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .anyMatch(field -> field.isAnnotationPresent(EmbeddedId.class));
    }

    private Class<?> findEntityClassByTableName(String tableName) {
        return entityManager.getMetamodel().getEntities().stream()
                .filter(e -> {
                    String currentTableName = Optional.ofNullable(e.getJavaType().getAnnotation(Table.class))
                            .map(Table::name)
                            .filter(name -> !name.isBlank())
                            .orElse(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()));
                    return tableName.equals(currentTableName);
                })
                .findFirst()
                .map(EntityType::getJavaType)
                .orElseThrow(() -> new IllegalStateException("EntityType not found for table: " + tableName));
    }

    private String getIdFieldName(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(field -> {
                    // @Column 어노테이션의 name 속성을 우선적으로 가져옵니다.
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation != null && !columnAnnotation.name().isBlank()) {
                        return columnAnnotation.name(); // @Column의 name을 반환
                    } else {
                        return field.getName(); // 기본 필드 이름을 반환
                    }
                })
                .map(fieldName -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)) // CamelCase를 snake_case로 변환
                .findFirst() // 첫 번째 ID 필드만 반환합니다.
                .orElseThrow(() -> new IllegalStateException("ID field not found for entity: " + entityClass.getSimpleName()));
    }
}
