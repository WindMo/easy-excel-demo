package ws.tool.easyexcel.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import com.alibaba.excel.converters.string.StringImageConverter;
import lombok.*;
import ws.tool.easyexcel.converter.ListStringStringConverter;
import ws.tool.easyexcel.converter.TeamStringConverter;
import ws.tool.easyexcel.converter.ex.EnumStringConverter;

import java.util.Date;
import java.util.List;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */


@HeadRowHeight(25)      /* 表头行高 */
@ColumnWidth(10)        /* 列宽     */
@ContentRowHeight(20)   /* 表行高   */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(exclude = {"date", "image", "cars", "ignore"})
public class BasicView {

    /**
     * 以属性名为表头
     */
    private Integer id;

    /**
     * 指定列位置
     */
    @ExcelProperty(index = 0)
    private String name;

    /**
     * 指定表头
     */
    @ExcelProperty("结果")
    private Boolean ok;

    /**
     * 指定日期格式（时间读写有问题，秒会不一致）
     */
    @DateTimeFormat("yyyy年MM月dd日 HH:mm:ss")
    private Date date;

    /**
     * 图片（写可以读不行），支持：
     * <ul>
     *     <li>byte[]（自动挡 默认使用{@link ByteArrayImageConverter}）</li>
     *     <li>InputStream（自动挡）</li>
     *     <li>File（自动挡）</li>
     *     <li>WriteCellData（自定义设置图片进去）</li>
     *     <li>String（文件路径）（手动挡：声明使用{@link StringImageConverter}）</li>
     * </ul>
     */
    private byte[] image;

    /**
     * 指定单类型转换器
     */
    @ExcelProperty(converter = TeamStringConverter.class)
    private Team team;

    /**
     * 指定自定义枚举转换器，不使用单类型转换器
     */
    @ExcelProperty(converter = EnumStringConverter.class)
    private Gender gender;

    /**
     * 指定自定义枚举转换器，不使用单类型转换器
     */
    @ExcelProperty(converter = EnumStringConverter.class)
    private Language language;

    /**
     * 指定集合转换器，每个类型的集合都要写一遍，目前官方的转换器没有针对集合的优化
     */
    @ExcelProperty(converter = ListStringStringConverter.class)
    private List<String> emails;

//    /**
//     * 指定通用集合转换器，固定集合的处理逻辑：逗号分隔
//     */
//    @ExcelProperty(converter = ListStringStringConverter.class)
//    private List<String> phoneNumbers;


    //    @ExcelProperty(converter = CommonListConverter.class)
    @ExcelIgnore
    private List<Car> cars;


    /**
     * 为空的字段，依旧写入但无值
     */
    private final String home = null;

    /**
     * 忽略的字段
     */
    @ExcelIgnore
    private Object ignore;
}
