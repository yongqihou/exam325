package com.example.exam325;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/HelloWorld")
    public String HelloWorld() {
        return "HelloWorld";
    }

    @PostMapping("/toUpload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        // 如果图片为空，显示错误信息
        if ((file.getOriginalFilename().isEmpty())) {
            return "upload";
        } else {
            // 获取图片的名字
            String fileName = file.getOriginalFilename();
            // 图片存放的文件夹地址
            String filePath = "D:\\nginx-1.24.0\\html\\img\\";
            // 文件夹地址 + 名字 = 图片的路径
            String fileAddress = filePath + fileName;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath+fileName);
                fileOutputStream.write(file.getBytes());
                // 把图片路径写入数据库
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "index";
        }
    }

    @PostMapping("/toUploads")
    public String uploadAvatars(@RequestParam("files") MultipartFile[] files) {
        // 如果图片为空，显示错误信息
        for (MultipartFile file : files) {
            if ((file.getOriginalFilename().isEmpty())) {
                return "upload";
            } else {
                // 获取图片的名字
                String fileName = file.getOriginalFilename();
                // 图片存放的文件夹地址
                String filePath = "D:\\nginx-1.24.0\\html\\img\\";
                // 文件夹地址 + 名字 = 图片的路径
                String fileAddress = filePath + fileName;
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(filePath+fileName);
                    fileOutputStream.write(file.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
             }
        }
        return "index";

    }

    @GetMapping("/find")
    public String find(HttpServletResponse response) {
        return "find";
    }

    @GetMapping("/download")
    public String downloads(HttpServletResponse response) throws Exception {
        System.out.println("fdskmf");
        //要下载的图片地址
//        String  path = request.getServletContext().getRealPath("D:\\nginx-1.24.0\\html\\img\\");
        String path = "D:\\nginx-1.24.0\\html\\img\\";
        String fileName = "car-2.png";

        File file = new File(path, fileName);
        //2、 读取文件--输入流
        InputStream input = new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();

        byte[] buff = new byte[1024];
        int index = 0;
        //4、执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        return null;
    }
}
