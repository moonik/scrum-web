package infrastructure

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher

@Slf4j
class JsonResultMatcher {
    static JsonResultMatcher jsonResponse() {
        return new JsonResultMatcher()
    }

    private JsonResultMatcher() {

    }

    ResultMatcher isEqual(final Object expectedJson) {
        final expectedJsonString = JsonOutput.toJson(expectedJson)
        isEqual(expectedJsonString)
    }

    ResultMatcher isEqual(final String expectedJson) {
        return new ResultMatcher() {
            @Override
            void match(MvcResult result) throws Exception {
                final String actualResponse = result.getResponse().getContentAsString()
                try {
                    JSONAssert.assertEquals(expectedJson, actualResponse, true)
                } catch (AssertionError error) {
                    log.error("actual response {}", actualResponse)
                    log.error("expected response {}", expectedJson)
                    throw error
                }
            }
        }
    }

    ResultMatcher notEmpty() {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                assert !result.getResponse().getContentAsString().isEmpty()
            }
        }
    }

    ResultMatcher hasValidContentType() {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                assert result.getResponse().getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
            }
        }
    }
}
