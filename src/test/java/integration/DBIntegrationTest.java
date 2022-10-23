package integration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class DBIntegrationTest {

    private static final String PASSWORD = "1234";

    protected String getDbPassword() {
        return PASSWORD;
    }

    protected String getDbUrl() {
        return "jdbc:mysql://localhost:3306/";
    }

    protected String getDb() {
        return "DemoApplicationTest";
    }

    protected String getConnectionString() {
        return getDbUrl() + getDb();
    }

    protected void runMigration(double level) {
        String url = getDbUrl();
        String db = getDb();
        Flyway flyway = new Flyway(
            new FluentConfiguration()
                .schemas(db)
                .defaultSchema(db)
                .createSchemas(true)
                .target(Double.toString(level))
                .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();
    }

}
