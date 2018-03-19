package scrumweb.storage.service;

import org.springframework.stereotype.Component;
import scrumweb.common.StorageProperties;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocationImpl implements Location {

    private final String location;

    public LocationImpl(StorageProperties location) {
        this.location = location.getLocation();
    }

    @Override
    public Path toPath() {
        return Paths.get(location);
    }
}
