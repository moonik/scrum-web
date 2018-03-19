package groovy.scrumweb.storage

import org.junit.ClassRule
import org.junit.rules.TemporaryFolder
import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile
import scrumweb.common.StorageProperties
import scrumweb.storage.service.LocationImpl
import scrumweb.storage.service.StorageServiceImpl
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.NoSuchFileException

class StorageServiceTest extends Specification {

    @ClassRule @Shared
    TemporaryFolder folder
    @Shared
    def properties = new StorageProperties()
    @Shared
    def storage
    @Shared
    def location
    @Shared
    def multipartFile
    @Shared
    def filename = "ring.jpg"

    def setupSpec() {
        properties = new StorageProperties()
        properties.setLocation(folder.getRoot().toString())
        location = new LocationImpl(properties)
        storage = new StorageServiceImpl(location)
        multipartFile =
                new MockMultipartFile("file", filename,
                        "multipart/formdata", new ClassPathResource(filename).getInputStream())
    }

    def "should throw exception when file does not exists"() {
        when:
        storage.loadAsResource(filename)

        then:
        NoSuchFileException e = thrown()
        e.message == "Could not read file: " + filename
    }

    def "should save icon"() {
        when:
        def file = storage.storeProjectIcon(multipartFile)

        then:
//        1 * StorageUtils.checkName(multipartFile, location.toPath())
        Files.exists(file)
        file.endsWith(filename)

    }

    def "should load icon"() {
        when:
        def resource = storage.loadAsResource(filename)

        then:
        resource.getFilename() == filename
    }

    def "should delete icon" () {
        when:
        def file = storage.delete(filename)
        !Files.exists(file)

        then:
        NullPointerException e = thrown()
    }
}
