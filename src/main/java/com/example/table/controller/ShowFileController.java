package com.example.table.controller;

import com.example.table.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class ShowFileController {
    //文件上传
    @RequestMapping("upload")
    @ResponseBody
    public String upload (@RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = "d:/upload/";
        // 文件重命名，防止重复
        fileName = filePath + fileName;
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
    //pdf/docx文件预览
    @RequestMapping(value = "/showpdf/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> showpdf(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        File file = null;
        FileInputStream fileInputStream = null;
        file = new File("D:/upload/"+fileName);
        String pathname="";
        String type=fileName.substring(fileName.lastIndexOf(".")+1);
        try {
            fileInputStream = new FileInputStream(file);
                pathname = FileUtil.file2pdf(fileInputStream, "D:/upload/openOffice", type);
                return FileUtil.preview(pathname);
        }catch (IOException e){
           e.printStackTrace();
        }
        return null;
    }
    //xlsx文件预览
    @RequestMapping(value = "/showhtml/{fileName}", method = RequestMethod.GET)
    public void showhtml(@PathVariable("fileName") String fileName, HttpServletResponse response){
        File file = null;
        FileInputStream fileInputStream = null;
        file = new File("D:/upload/"+fileName);
        String pathname="";
        String type=fileName.substring(fileName.lastIndexOf(".")+1);
        try {
            fileInputStream = new FileInputStream(file);
                pathname = FileUtil.file2Html(fileInputStream, "D:/upload/openOffice", type);
                File filet = new File(pathname);
                if (filet.exists()){
                    byte[] data = null;
                        FileInputStream input = new FileInputStream(filet);
                        data = new byte[input.available()];
                        input.read(data);
                        response.setHeader("Content-type", "text/html;charset=gb2312");
                        response.getOutputStream().write(data);
                        input.close();

                }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //文件下载
    @RequestMapping("download")
    @ResponseBody
    public void download(HttpServletResponse response) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        File file = new File("D:\\upload\\需求ljx-1基础数据4.docx");
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + "需求ljx-1基础数据4.docx");
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }
    //ocx控件下载
    @RequestMapping("downloadocx")
    @ResponseBody
    public void downloadocx(HttpServletResponse response) throws Exception {

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        // 文件地址，真实环境是存放在数据库中的
        File file = new File(path+"static/ocx控件.rar");
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        String fileName = java.net.URLEncoder.encode("ocx控件.rar", "UTF-8");
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + fileName);
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }
}
