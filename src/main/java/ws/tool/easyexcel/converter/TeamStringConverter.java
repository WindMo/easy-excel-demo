package ws.tool.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import ws.tool.easyexcel.pojo.Team;
import ws.tool.easyexcel.util.JacksonUtils;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public class TeamStringConverter implements Converter<Team> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Team.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Team convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        String value = cellData.getStringValue();
        return JacksonUtils.parseJson(value, Team.class);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Team value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(CellDataTypeEnum.STRING, JacksonUtils.toJson(value));
    }
}
