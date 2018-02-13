package scrumweb.storage.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scrumweb.storage.service.StorageServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static scrumweb.common.ApplicationConstants.API_URL;

@Controller
@RequestMapping(API_URL + "storage")
class StorageController {

    private final StorageServiceImpl storageServiceImpl;

    StorageController(StorageServiceImpl storageServiceImpl) {
        this.storageServiceImpl = storageServiceImpl;
    }

    @PostMapping("/icon")
    @ResponseStatus(HttpStatus.CREATED)
    public Path uploadIcon(@RequestParam("file") MultipartFile file) throws IOException {
        return storageServiceImpl.storeProjectIcon(file);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> load(@PathVariable String filename) {

        try {
            Resource file = storageServiceImpl.loadAsResource(filename);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
        }
        catch (NoSuchFileException e) {
            return ResponseEntity.notFound().build();
        }

        catch (MalformedURLException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
