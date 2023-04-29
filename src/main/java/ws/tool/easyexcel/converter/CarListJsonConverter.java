package ws.tool.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import ws.tool.easyexcel.pojo.Car;
import ws.tool.easyexcel.util.JacksonUtils;

import java.util.List;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public class CarListJsonConverter implements Converter<List<Car>> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public List<Car> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        String value = cellData.getStringValue();
        return JacksonUtils.parseJson(value, new TypeReference<List<Car>>() {
        });
    }

    @Override
    public WriteCellData<?> convertToExcelData(List<Car> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(CellDataTypeEnum.STRING, JacksonUtils.toJson(value));
    }
}
