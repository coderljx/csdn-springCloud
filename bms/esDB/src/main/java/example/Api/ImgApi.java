package example.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/img")
public class ImgApi {

    @GetMapping("/viewImg/{url}")
    public void viewImg(
            @PathVariable("url") String url,
            HttpServletResponse httpServletResponse
    ) {
        System.out.println(url);
        httpServletResponse.setHeader("Content-Type", "image/png; charset=utf-8");
        ServletOutputStream out = null;
        try {
            out = httpServletResponse.getOutputStream();
            FileInputStream input = new FileInputStream( "/home/brother/img/" + url);
            byte[] buf = new byte[2048];
            while (input.read(buf) >= 0) {
                out.write(buf);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
