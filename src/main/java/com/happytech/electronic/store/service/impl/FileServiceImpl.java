package com.happytech.electronic.store.service.impl;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.exception.BadApiRequestException;
import com.happytech.electronic.store.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        //abc png
        String originalFilename = file.getOriginalFilename();
        log.info("Filname : {}", originalFilename);
        String filename = UUID.randomUUID().toString();


        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileNameWithExtension = filename + extention;
        String fullPathWithFileName = path  + fileNameWithExtension;

         log.info("full image path : {} ",fullPathWithFileName);

         if (extention.equalsIgnoreCase(AppConstants.PNG) || extention.equalsIgnoreCase(AppConstants.JPG) || extention.equalsIgnoreCase(AppConstants.JPEG)) {

            //file save
            log.info("file extention is {} ",extention);
            File folder = new File(path);

            if (!folder.exists()) {

                //create the folder
                folder.mkdirs();
            }

            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        } else {

            throw new BadApiRequestException("File with this " + extention + " not allowed..!!");
        }

    }

    @Override
    public InputStream getResource(String path , String name) throws FileNotFoundException {

        String fullPath = path+File.separator+name;
                InputStream inputStream = new FileInputStream(fullPath);
                return  inputStream;

    }
}
