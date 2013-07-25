package ie.davidmoloney.jira;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

public class AuthorizedNoFollowGet extends NoFollowGet {

    public AuthorizedNoFollowGet(ConnectionDetails connectionDetails, String relativeUri) {
        super(connectionDetails.getBaseUri() + relativeUri);
        setHeader(createAuthorizationHeader(connectionDetails.getUser(), connectionDetails.getPassword()));
    }

    public Header createAuthorizationHeader( String username, String password ) {
        StringBuilder authorizationValue = new StringBuilder();
        authorizationValue.append("Basic ");
        String userPassCombo = username + ":" + password;
        String encodedUserPassCombo = new String(Base64.encodeBase64(userPassCombo.getBytes()));
        authorizationValue.append(encodedUserPassCombo);
        return new BasicHeader(HttpHeaders.AUTHORIZATION, authorizationValue.toString());
    }

}
