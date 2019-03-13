package com.vborodin.onlineshop.productservice.util;

import com.vborodin.onlineshop.productservice.exception.ApiException;
import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileStorageService {
    private final Path fileStorageLocation;
    private final String fileUploadDir;

    public FileStorageService(@Value("${file.upload-dir:upload}") String fileUploadDir) {
        this.fileUploadDir = fileUploadDir;
        this.fileStorageLocation = Paths.get("static/" + fileUploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new ApiException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public String storeFile(MultipartFile file, String subFolder) {
        String fileName = DigestUtils.md5DigestAsHex(StringUtils.cleanPath(file.getName()).getBytes())
                .concat(".")
                .concat(FilenameUtils.getExtension(file.getOriginalFilename()));

        try {
            Path targetLocation;
            if (subFolder == null) {
                targetLocation = this.fileStorageLocation.resolve(fileName);
            } else {
                targetLocation = Paths.get(this.fileStorageLocation.toString(), subFolder, fileName);
            }

            if (!Files.exists(targetLocation.getParent()))
                Files.createDirectories(targetLocation.getParent());

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ApiException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public String getFileUploadDir() {
        return this.fileUploadDir;
    }
}
