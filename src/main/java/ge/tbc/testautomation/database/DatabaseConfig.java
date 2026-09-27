package ge.tbc.testautomation.database;

import ge.tbc.testautomation.mappers.CdmMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class DatabaseConfig {

    private static final String DB_URL =
            "jdbc:h2:mem:branchDB;DB_CLOSE_DELAY=-1";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static SqlSessionFactory sqlSessionFactory;

    private DatabaseConfig() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {

        if (sqlSessionFactory == null) {

            PooledDataSource dataSource = new PooledDataSource();

            dataSource.setDriver("org.h2.Driver");
            dataSource.setUrl(DB_URL);
            dataSource.setUsername(DB_USER);
            dataSource.setPassword(DB_PASSWORD);

            Environment environment = new Environment(
                    "test",
                    new JdbcTransactionFactory(),
                    dataSource
            );

            Configuration configuration =
                    new Configuration(environment);

            configuration.addMapper(CdmMapper.class);

            sqlSessionFactory =
                    new SqlSessionFactoryBuilder()
                            .build(configuration);
        }

        return sqlSessionFactory;
    }
}