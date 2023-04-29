package ws.tool.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import ws.tool.easyexcel.listener.BatchReadListener;
import ws.tool.easyexcel.pojo.BasicView;
import ws.tool.easyexcel.pojo.Car;
import ws.tool.easyexcel.pojo.Gender;
import ws.tool.easyexcel.pojo.Team;

import java.io.*;
import java.util.*;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public class EasyExcelTests {

    private static List<BasicView> views;
    private static String file = "D:\\program\\JAVA\\项目\\easy-excel-demo\\easy-excel-demo\\src\\test\\excel-out\\ex.xlsx";
    private static String img = "classpath:img/idea.png";

    private static InputStream in;
    private static OutputStream out;

    private static boolean useFile = false;

    static void synStream() {

        if (!useFile) {

            in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        }
    }

    @BeforeAll
    static void beforeAll() {

        views = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            BasicView view = new BasicView();
            views.add(view);

            int id = i + 100;
            view.setId(id);
            view.setName("name-" + id);
            view.setOk(i % 2 == 0);
            view.setGender(i % 2 == 0 ? Gender.FEMALE : Gender.MALE);
            view.setEmails(Arrays.asList(id + "-e@ws.com", id + "-x@ws.com"));
            view.setDate(new Date(System.currentTimeMillis()));

            view.setImage(readFileResource(img));
            view.setTeam(new Team(id + "-teamId", id + "-teamName"));

            List<Car> cars = new ArrayList<>();
            cars.add(new Car(id + "-carA", "red"));
            cars.add(new Car(id + "-carB", "red"));
            view.setCars(cars);

            view.setIgnore(1L);
        }

        if (useFile) {

            try {
                in = new FileInputStream(file);
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {

            out = new ByteArrayOutputStream();
        }
    }

    @Test
    void readAndWriteTest() {

        String sheetName = "EasyExcelSheet";
        // 使用 BasicView 属性作为head
        EasyExcel.write(out, BasicView.class)
                .sheet(sheetName)
                .doWrite(views);

        synStream();

        // 同步读，一次性读到内存
        List<BasicView> readList = EasyExcel.read(in)
                .head(BasicView.class)
                .sheet(sheetName)
                .doReadSync();
        Assertions.assertEquals(views, readList);

        synStream();

        // 监听器读，ReadListener 注意线程安全问题
        List<BasicView> batchReadList = new LinkedList<>();
        EasyExcel.read(in, BasicView.class, new BatchReadListener<BasicView>(2, batchReadList::addAll))
                .sheet(sheetName)
                .doRead();
        Assertions.assertEquals(views, batchReadList);
    }

    private static byte[] readFileResource(String res) {

        try {
            return readAll(new FileInputStream(ResourceUtils.getFile(res)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readAll(InputStream inputStream) {

        try {
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
