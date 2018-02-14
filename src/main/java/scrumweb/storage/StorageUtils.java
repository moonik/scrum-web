package scrumweb.storage;

import com.google.common.net.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import scrumweb.storage.controller.StorageController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName;

public class StorageUtils {

    public static Path checkName(MultipartFile file, Path location) {
        Path destinationPath = location.resolve(file.getOriginalFilename());

        String pathAsString = destinationPath.toString();
        String ext = getFileExtension(pathAsString);
        String name = getNameWithoutExtension(pathAsString);

        int counter = 1;
        while (Files.exists(destinationPath)) {
            destinationPath = Paths.get(
                on(destinationPath.getFileSystem().getSeparator())
                    .join(
                        destinationPath.getParent().toString(),
                        name + "_" + (counter++) + (isNullOrEmpty(ext) ? "" : "." + ext)
                    )
            );
        }
        return destinationPath;
    }

    //todo images get wrong colors
    public static BufferedImage resize(BufferedImage bi) {
        Image image = bi.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static URI createUri(Path path) {
        return create(path.toString());
    }

    private static URI create(String string) {
        return fromMethodName(StorageController.class, "load", string).build().toUri();
    }

    public static ResponseEntity<?> response(URI link) {
        return ResponseEntity.created(link)
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, DELETE, GET, OPTIONS")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            .header(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1728000")
            .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "*")
            .header(HttpHeaders.X_FRAME_OPTIONS, "*")
            .body(StorageLink.from(link.toString()));
    }
}
