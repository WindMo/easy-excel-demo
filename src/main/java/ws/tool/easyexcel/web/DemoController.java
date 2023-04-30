package ws.tool.easyexcel.web;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ws.tool.easyexcel.pojo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author WindShadow
 * @version 2023-04-30.
 */

@RestController
public class DemoController {

    @GetMapping("/excel")
    public void excelDownload(HttpServletResponse response) throws IOException {

        String img = "classpath:img/idea.png";

        List<BasicView> views = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            BasicView view = new BasicView();
            views.add(view);

            int id = i + 100;
            view.setId(id);
            view.setName("name-" + id);
            view.setOk(i % 2 == 0);
            view.setGender(i % 2 == 0 ? Gender.FEMALE : Gender.MALE);
            view.setLanguage(i % 2 == 0 ? Language.CHINESE : Language.ENGLISH);
            view.setEmails(Arrays.asList(id + "-e@ws.com", id + "-x@ws.com"));
            view.setDate(new Date(System.currentTimeMillis()));

            view.setImage(FileUtils.readFileToByteArray(ResourceUtils.getFile(img)));
            view.setTeam(new Team(id + "-teamId", id + "-teamName"));

            List<Car> cars = new ArrayList<>();
            cars.add(new Car(id + "-carA", "red"));
            cars.add(new Car(id + "-carB", "red"));
            view.setCars(cars);

            view.setIgnore(new Object());
        }

        String sheetName = "EasyExcelSheet";

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("content-disposition" , "attachment; filename=EasyExcel.xlsx" );

        EasyExcel.write(response.getOutputStream(), BasicView.class)
                .sheet(sheetName)
                .doWrite(views);
    }
}
