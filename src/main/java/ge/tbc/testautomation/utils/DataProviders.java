package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.database.DatabaseConfig;
import ge.tbc.testautomation.database.DatabaseInitializer;
import ge.tbc.testautomation.mappers.CdmMapper;
import ge.tbc.testautomation.models.CdmData;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {

    @DataProvider(name = "localizationData", parallel = true)
    public static Object[][] localizationData() {
        return new Object[][]{
                {
                        "ka",
                        "ჩემთვის",
                        "სესხები",
                        "სესხის მოთხოვნა",
                        "სესხის კალკულატორი",
                        "სესხის თანხა",
                        "სესხის ვადა"
                },
                {
                        "en",
                        "Personal",
                        "Loans",
                        "Get a Loan",
                        "Loan Calculator",
                        "Loan Amount",
                        "Loan Term"
                }
        };
    }

    @DataProvider(name = "cdmData")
    public static Object[][] cdmData() {

        System.out.println("CDM DATA PROVIDER STARTED");

        DatabaseInitializer.initializeDatabase();

        try (SqlSession session =
                     DatabaseConfig
                             .getSqlSessionFactory()
                             .openSession()) {

            CdmMapper mapper =
                    session.getMapper(CdmMapper.class);

            List<CdmData> cdmList =
                    mapper.getAllCdmData();

            System.out.println(
                    "CDM RECORDS FROM DB: " + cdmList.size()
            );

            return cdmList.stream()
                    .map(cdm -> new Object[]{cdm})
                    .toArray(Object[][]::new);
        }
    }
}