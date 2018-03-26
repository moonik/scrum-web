package scrumweb.storage.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scrumweb.exception.EmptyFileException;
import scrumweb.storage.StorageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final Location location;

    @Override
    public Path storeProjectIcon(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new EmptyFileException(file.getOriginalFilename());
        }

        Path destinationPath = StorageUtils.checkName(file, location());

        BufferedImage image = ImageIO.read(file.getInputStream());
        BufferedImage bi = StorageUtils.getScaledInstance(image, 128, 128,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);

        ImageIO.write(bi, "jpg", new File(String.valueOf(destinationPath)));

        return destinationPath;
    }

    @Override
    public Resource loadAsResource(String filename) throws NoSuchFileException, MalformedURLException {
        Resource resource;
        resource = new UrlResource(load(filename).toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new NoSuchFileException("Could not read file: " + filename);
        }
    }

    @Override
    public Path load(String filename) {
        return location().resolve(filename);
    }

    @Override
    public void delete(String file) throws IOException {
        Files.deleteIfExists(location().resolve(file));
    }

    private Path location() {
        return location.toPath();
    }
}
