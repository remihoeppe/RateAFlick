package com.example.demo.config.seed;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.relational.SchemaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Drops all tables and optionally recreates the schema from JPA entities.
 * Used when app.seeder.drop-and-reseed is true.
 */
@Component
public class SchemaReset {

    private static final Logger logger = LoggerFactory.getLogger(SchemaReset.class);

    /**
     * Drops all tables in the current database (MySQL). Disables foreign key checks
     * so tables can be dropped in any order.
     */
    public void dropAllTables(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        try {
            List<String> tableNames = jdbcTemplate.queryForList(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE() AND table_type = 'BASE TABLE'",
                    String.class);
            for (String tableName : tableNames) {
                jdbcTemplate.execute("DROP TABLE IF EXISTS `" + tableName + "`");
                logger.debug("Dropped table: {}", tableName);
            }
            logger.info("Dropped {} tables", tableNames.size());
        } finally {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    /**
     * Recreates the schema from JPA entities using Hibernate's SchemaManager (Hibernate 6.2+).
     */
    public void recreateSchema(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        SchemaManager schemaManager = sessionFactory.getSchemaManager();
        schemaManager.exportMappedObjects(true);
        logger.info("Schema recreated from entity mappings.");
    }
}
