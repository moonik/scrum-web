package scrumweb.storage;

import lombok.Data;

@Data(staticConstructor = "from")
public class StorageLink {

    private final String link;
}
