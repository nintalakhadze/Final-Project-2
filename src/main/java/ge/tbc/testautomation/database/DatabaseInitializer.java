package ge.tbc.testautomation.database;

import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initializeDatabase() {

        try (SqlSession session =
                     DatabaseConfig
                             .getSqlSessionFactory()
                             .openSession();

             Connection connection =
                     session.getConnection();

             Statement statement =
                     connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS CDM_TEST_DATA (
                        ID INT PRIMARY KEY,
                        ADDRESS VARCHAR(255) NOT NULL,
                        WORKING_HOURS VARCHAR(255) NOT NULL,
                        CURRENCIES VARCHAR(50) NOT NULL
                    )
                    """);

            statement.executeUpdate(
                    "DELETE FROM CDM_TEST_DATA"
            );

            statement.executeUpdate("""
                    INSERT INTO CDM_TEST_DATA
                    (ID, ADDRESS, WORKING_HOURS, CURRENCIES)
                    VALUES
                    (1, 'ბათუმი, ტ. აბუსერიძის ქ. #73',
                     'ორშაბათი-პარასკევი: 10:00-18:00; შაბათი: 10:00-14:00',
                     '₾,$,€'),

                    (2, 'ქობულეთი, დ. აღმაშენებელის ქ. #118 ა',
                     'ორშაბათი-პარასკევი: 10:00-17:30; შაბათი: 10:00-14:00',
                     '₾,$,€'),

                    (3, 'ქუთაისი, ბუხაიძის ქ. #21',
                     'სამუშაო საათები - 24/7',
                     '₾,$,€'),

                    (4, 'თბილისი, ქავთარაძის ქ. #1',
                     'ორშაბათი-პარასკევი: 10:00-21:30; შაბათი: 10:00-21:30',
                     '₾,$,€'),

                    (5, 'თბილისი, თავისუფლების მოედანი #7',
                     'სამუშაო საათები - 24/7',
                     '₾,$,€')
                    """);

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to initialize CDM test database",
                    e
            );
        }
    }
}