package com.blebail.blog.sample1.fixtures;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class SqlMemoryDatabase implements BeforeAllCallback {

    private static final String APPLICATION_PROPERTIES_PATH = "/application.properties";

    private static final String DB_SCHEMA_SQL_PATH = "/db_schema.sql";

    private DataSource dataSource;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        loadDataSource();
        createSchema();
    }

    private void loadDataSource() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES_PATH));

        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(properties.getProperty("datasource.url"));
        jdbcDataSource.setUser(properties.getProperty("datasource.username"));
        jdbcDataSource.setPassword(properties.getProperty("datasource.password"));

        dataSource = jdbcDataSource;
    }

    private void createSchema() throws IOException, URISyntaxException {
        Path schemaPath = Paths.get(getClass().getResource(DB_SCHEMA_SQL_PATH).toURI());
        String createSchemaSql = new String(Files.readAllBytes(schemaPath));

        new DbSetup(
                new DataSourceDestination(dataSource),
                Operations.sql(createSchemaSql)
        ).launch();
    }

    public DataSource dataSource() {
        return dataSource;
    }
}