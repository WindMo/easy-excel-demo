package ws.tool.easyexcel.converter.ex;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WindShadow
 * @version 2023-04-29.
 * @deprecated 难以实现
 */

@Deprecated
public class CommonListConverter implements Converter<List<?>> {

    private final static String SEPARATE = ",";

    @Override
    public Class<?> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public List<?> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        String value = cellData.getStringValue();
        return Stream.of(value.split(SEPARATE)).collect(Collectors.toList());
    }

    @Override
    public WriteCellData<?> convertToExcelData(List<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return new WriteCellData<>(value.stream().map(Object::toString).collect(Collectors.joining(SEPARATE)));
    }
}
