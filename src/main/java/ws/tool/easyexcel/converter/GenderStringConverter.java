package ws.tool.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import ws.tool.easyexcel.pojo.Gender;


/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public class GenderStringConverter implements Converter<Gender> {

    /**
     * 支持的java类
     *
     * @return
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Gender.class;
    }

    /**
     * 单元格中设置的类型
     *
     * @return
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Gender convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {

        String value = cellData.getStringValue();
        return Gender.valueOf(value);
    }


    @Override
    public WriteCellData<?> convertToExcelData(Gender value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {

        return new WriteCellData<>(value.toString());
    }
}
