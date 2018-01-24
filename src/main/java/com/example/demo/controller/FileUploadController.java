package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FileUploadController {

    @RequestMapping(value = "fileupload")
    @ResponseBody
    public boolean fileupload(@RequestParam(value = "filename")MultipartFile file){
        if(file.isEmpty()){
            return false;
        }
        String name=file.getOriginalFilename();
        int Size= (int) file.getSize();
        System.out.println(name + "-->" + Size);

        String path="D:/NewFile";
        File f=new File(path+"/"+name);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdir();
        }
        try {
            file.transferTo(f); //保存文件
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 实现多文件上传
     * */
    @RequestMapping(value="multifileUpload",method= RequestMethod.POST)
    public @ResponseBody boolean multifileUpload(@RequestParam(value = "filename")List<MultipartFile> files){

        if(files.isEmpty()){
            System.out.println("文件为空");
            return false;
        }

        String path = "D:/NewFile" ;

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return false;
            }else{
                File dest = new File(path + "/" + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    @RequestMapping("download")
    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename="报名照片.jpg";
        String filePath = "D:/NewFile" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String("报名照片.jpg".getBytes("gb2312"),"ISO_8859_1"));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "file")
    public String file(){
        return "file";
    }

    @RequestMapping(value = "filemore")
    public String filemore(){
        return "multifile";
    }


    @RequestMapping(value = "a")
    public String a() throws Exception {
        throw new Exception("发生错误");
    }
}
