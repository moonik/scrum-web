package scrumweb.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public interface FileStorage {

    Path storeProjectIcon(MultipartFile file) throws IOException;

    Path load(String filename);

    Resource loadAsResource(String filename) throws NoSuchFileException, MalformedURLException;

    void delete(String file) throws IOException;
}
