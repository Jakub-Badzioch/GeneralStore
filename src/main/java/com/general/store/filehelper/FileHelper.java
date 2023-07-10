package com.general.store.filehelper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileHelper {

   public void saveInputStreamAsFile(InputStream inputStream, Path completeFilePath) throws IOException {
       Files.copy(inputStream, completeFilePath);
    }

    public void createDirectoryIfNotExists(Path path) throws IOException {
       if (Files.notExists(path)) Files.createDirectory(path);
   }
}
