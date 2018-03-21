package scrumweb.storage.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import scrumweb.storage.StorageUtils;
import scrumweb.storage.service.StorageServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.NoSuchFileException;

import static scrumweb.common.ApplicationConstants.API_URL;

@Controller
@RequestMapping(API_URL + "storage")
public class StorageController {

    private final StorageServiceImpl storageServiceImpl;

    StorageController(StorageServiceImpl storageServiceImpl) {
        this.storageServiceImpl = storageServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {

        URI link = StorageUtils.createUri(storageServiceImpl.storeProjectIcon(file).getFileName());
        return StorageUtils.response(link);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> load(@PathVariable String filename) {

        try {
            Resource file = storageServiceImpl.loadAsResource(filename);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

        } catch (NoSuchFileException e) {
            return ResponseEntity.notFound().build();

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) throws IOException {
        try {
            storageServiceImpl.delete(filename);
            return ResponseEntity.ok().build();
        } catch (NoSuchFileException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
