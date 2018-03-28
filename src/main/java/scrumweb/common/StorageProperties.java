package scrumweb.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @Setter
public class StorageProperties {
    @Value("${location.upload-dir:upload-dir}")
    private String location;
}
