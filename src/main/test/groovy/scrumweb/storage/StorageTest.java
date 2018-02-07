package groovy.scrumweb.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import scrumweb.storage.FileStorage;
import scrumweb.storage.Storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private FileStorage storage;

    @Before
    public void setUp() {
        storage = new Storage(() -> folder.getRoot().toPath());
    }

    @Test
    public void testSaveIconAndCheckSize() throws IOException {
        MockMultipartFile multipartFile =
            new MockMultipartFile("file", "ring.jpg",
                "multipart/formdata", new ClassPathResource("ring.jpg").getInputStream());

        Path icon = storage.storeProjectIcon(multipartFile);

        File file = new File(String.valueOf(icon));
        BufferedImage bi = ImageIO.read(file);

        Assert.assertTrue(icon.endsWith("ring.jpg"));
        Assert.assertTrue(bi.getHeight() == 128 && bi.getWidth() == 128);
        Assert.assertTrue(Files.exists(icon));
    }
}
