package scrumweb.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {
    public static final String API_URL = "/api/scrum-web/";
    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 6048000L;
    public static final String SIGNING_KEY = "biedra";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String DATE_FORMAT = "yy-MM-dd HH:mm";
}
