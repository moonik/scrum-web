package scrumweb.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scrumweb.exception.EmptyFileException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class Storage implements FileStorage{

    private final Location location;

    public Storage(Location location) {
        this.location = location;
    }

    @Override
    public Path storeProjectIcon(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new EmptyFileException(file.getOriginalFilename());
        }

        Path destinationPath = StorageUtils.checkName(file, location());

        InputStream is = file.getInputStream();
        BufferedImage bi = StorageUtils.resize(ImageIO.read(is));

        ImageIO.write(bi, "png", new File(String.valueOf(destinationPath)));

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
        Files.deleteIfExists(Paths.get(file));
    }

    private Path location() {
        return location.toPath();
    }


}
