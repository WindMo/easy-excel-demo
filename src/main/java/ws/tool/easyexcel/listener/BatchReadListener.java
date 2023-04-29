package ws.tool.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 批量读取
 *
 * @author WindShadow
 * @version 2023-04-30.
 */

@Slf4j
public class BatchReadListener<T> implements ReadListener<T> {

    private List<T> cache;
    private final int limit;
    private final Consumer<List<T>> processor;

    public BatchReadListener(int limit, Consumer<List<T>> processor) {
        this.limit = limit;
        this.processor = processor;
        reset();
    }

    private void reset() {

        this.cache = new LinkedList<>();
    }


    /**
     * 逐行读取
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {

        cache.add(data);
        if (cache.size() >= limit) {

            log.info("批处理");
            processor.accept(cache);
            reset();
        }
    }

    /**
     * 读取完毕
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        log.info("读取完毕");
        processor.accept(cache);
    }

    /**
     * 异常处理
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        ReadListener.super.onException(exception, context);

        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }
}
