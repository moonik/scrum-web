package scrumweb.storage;

import com.google.common.net.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;

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

    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth,
                                                  int targetHeight, Object hint,
                                                  boolean higherQuality) {
        int type =
            (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        int w;
        int h;
        if (higherQuality) {
            w = img.getWidth();
            h = img.getHeight();
        } else {
            w = targetWidth;
            h = targetHeight;
        }
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }
            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    public static ResponseEntity<?> response(String link) {
        return ResponseEntity.ok()
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, DELETE, GET, OPTIONS")
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            .header(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1728000")
            .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "*")
            .header(HttpHeaders.X_FRAME_OPTIONS, "*")
            .body(StorageLink.from(link));
    }

    public static boolean checkFile(String filename, Path location) {
        return Files.exists(location.resolve(filename));
    }
}
