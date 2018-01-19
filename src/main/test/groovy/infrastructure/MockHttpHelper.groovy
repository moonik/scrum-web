package infrastructure

import org.skyscreamer.jsonassert.JSONParser
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import scrumweb.common.ApplicationConstants

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class MockHttpHelper {
    private final MediaType jsonMediaType = MediaType.APPLICATION_JSON_UTF8
    private final String apiPrefix = ApplicationConstants.API_URL

    private final static MockHttpHelper instance = new MockHttpHelper()

    private MockHttpHelper() {
    }

    static MockHttpHelper httpHelper() {
        instance
    }

    MockMultipartHttpServletRequestBuilder requestFileUpload(String relativePath) {
        fileUpload(apiPrefix + relativePath)
    }

    MockHttpServletRequestBuilder requestGet(String relativePath, Map<String, Object> params) {
        final query = params
                .collect { key, value -> key + "=" + value }
                .join("&")

        requestGet(relativePath + "?" + query)
    }

    MockHttpServletRequestBuilder requestGet(String relativePath) {
        attachMediaType(get(apiPrefix + relativePath))
    }

    RequestBuilder requestDeleteWithoutContent(String relativePath) {
        attachMediaType(delete(apiPrefix + relativePath))
    }

    RequestBuilder requestDelete(String relativePath, String content) {
        attachMediaType(delete(apiPrefix + relativePath).content(parseSmartJson(content)))
    }

    RequestBuilder requestPost(String relativePath, String jsonContent) {
        requestPostWithContent(relativePath, jsonContent)
    }

    RequestBuilder requestPostWithContent(String relativePath, String content) {
        //JSONParser is smart one and allows to send json without proper quotes in the names of keys
        createPost(relativePath).content(parseSmartJson(content))
    }

    RequestBuilder requestPutWithContent(String relativePath, String content) {
        createPut(relativePath).content(parseSmartJson(content))
    }

    MockHttpServletRequestBuilder requestPutWithContent(String relativePath, byte[] content) {
        createPut(relativePath).content(content)
    }

    RequestBuilder requestPostWithoutContent(String relativePath) {
        createPost(relativePath)
    }

    private static String parseSmartJson(String content) {
        JSONParser.parseJSON(content).toString()
    }

    private MockHttpServletRequestBuilder createPost(String relativePath) {
        attachMediaType(post(apiPrefix + relativePath))
    }

    private MockHttpServletRequestBuilder createPut(String relativePath) {
        attachMediaType(put(apiPrefix + relativePath))
    }

    private MockHttpServletRequestBuilder attachMediaType(MockHttpServletRequestBuilder requestBuilder) {
        requestBuilder.contentType(jsonMediaType)
    }

    static Map<String, String> getUrlParameter(String url) {
        final parsableUrl = new URL(url)
        getQueryMap(parsableUrl.getQuery())
    }

    static ResultMatcher responseIsEqualTo(final String expected) {
        new ResultMatcher() {
            void match(MvcResult result) throws Exception {
                assert result.getResponse().getContentAsString() == expected
            }
        }
    }

    static ResultMatcher responseIsEqualTo(final byte[] expected) {
        new ResultMatcher() {
            void match(MvcResult result) throws Exception {
                assert result.getResponse().getContentAsByteArray() == expected
            }
        }
    }

    private static Map<String, String> getQueryMap(String query) {
        query.split("&").collectEntries { keyValue ->
            final name = keyValue.split("=")[0]
            final value = keyValue.split("=")[1]
            [(name): value]
        }
    }
}
