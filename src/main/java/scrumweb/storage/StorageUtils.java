package scrumweb.storage;

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

    public static BufferedImage resize(BufferedImage bi) {
        Image image = bi.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
