package ws.tool.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author WindShadow
 * @version 2023-04-30.
 */

public class RowReadListener<T> implements ReadListener<T> {

    private final Consumer<T> consumer;

    public RowReadListener(Consumer<T> consumer) {
        this.consumer = Objects.requireNonNull(consumer);
    }

    /**
     * 逐行读取
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {

        consumer.accept(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
