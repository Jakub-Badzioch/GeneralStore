package com.general.store.validator.impl;

import com.general.store.validator.IsItJpgIn180x80px;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.File;

public class IsItJpgIn180x80pxImpl implements ConstraintValidator<IsItJpgIn180x80px, MultipartFile> {
    @SneakyThrows
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        String extension = FilenameUtils.getExtension(value.getOriginalFilename());
        BufferedImage bufferedImage = ImageIO.read((File) value);
        return "jpg".equals(extension) && bufferedImage.getWidth()<=180 && bufferedImage.getHeight()<=80;
    }
}
