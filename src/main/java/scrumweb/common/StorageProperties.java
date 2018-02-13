package scrumweb.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Getter
@Setter
public class StorageProperties {

    @Autowired
    @Value("${location.upload-dir:upload-dir}")
    private String location;

}
