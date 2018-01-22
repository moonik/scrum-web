package scrumweb.common;

public interface ApplicationConstants {

    String API_URL = "/api/scrum-web/";
    long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    String SIGNING_KEY = "biedra";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
