package ge.tbc.testautomation.mappers;

import ge.tbc.testautomation.models.CdmData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CdmMapper {

    @Select("""
            SELECT
                ID AS id,
                ADDRESS AS address,
                WORKING_HOURS AS workingHours,
                CURRENCIES AS currencies
            FROM CDM_TEST_DATA
            ORDER BY ID
            """)
    List<CdmData> getAllCdmData();
}