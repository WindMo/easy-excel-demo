package ws.tool.easyexcel.converter.ex;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举与其名称之间的转换器
 *
 * @author WindShadow
 * @version 2023-04-29.
 */

public class EnumStringConverter implements Converter<Enum<?>> {

    private static final Map<Class<?>, Method> METHOD_CACHE = new ConcurrentHashMap<>();

    private static Method findValueOfMethod(Class<Enum<?>> enumClass) throws NoSuchMethodException {

        Method m;
        if (METHOD_CACHE.containsKey(enumClass)) {

            m = METHOD_CACHE.get(enumClass);
        } else {

            m = enumClass.getMethod("valueOf", String.class);
            METHOD_CACHE.put(enumClass, m);
        }
        return m;
    }

    /**
     * 支持的java类
     *
     * @return
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Enum.class;
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
    public Enum<?> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {

        Class<?> enumClass = contentProperty.getField().getType();
        if (enumClass.isEnum()) {

            Method method = findValueOfMethod((Class<Enum<?>>) enumClass);
            String value = cellData.getStringValue();
            return (Enum<?>) method.invoke(enumClass, value);
        }
        return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);

    }

    @Override
    public WriteCellData<?> convertToExcelData(Enum<?> value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {

        return new WriteCellData<>(CellDataTypeEnum.STRING, value.name());
    }
}
