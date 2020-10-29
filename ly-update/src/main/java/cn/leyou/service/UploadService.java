package cn.leyou.service;

import cn.itcast.exception.ExceptionEnum;
import cn.itcast.exception.LyException;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    BufferedImage read = null;

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    //支持文件的类型
    private  static final List<String> suffiex = Arrays.asList("image/png", "image/jpeg","image/jpg");
    public String uploadImage(MultipartFile file) {
        StorePath storePath = null;
        try {
            //1.图片信息校验
            String contentType = file.getContentType();
            System.out.println(contentType);
            if (!suffiex.contains(contentType)) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }

//          2.校验图片类容

            read = ImageIO.read(file.getInputStream());


            if (read == null) {
                System.out.println("read :" + read);
                throw new LyException(ExceptionEnum.FILE_UPLOAD_FAIL);
            }
            //3.保存到ngixn中

            //改造
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            //上传
             storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
//
//            File dir = new File("D:\\heima\\nginx\\html\\");
//        if(!dir.exists() ){
//            dir.mkdirs();
//        }
//
//            file.transferTo(new File(dir,file.getOriginalFilename()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
            //4.拼接字符串
//        String url ="http://image.leyou.com/"+file.getOriginalFilename();

            //发送地址给BrandService


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://image.leyou.com/" + storePath.getFullPath();
    }





}
