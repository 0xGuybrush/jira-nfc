package ie.davidmoloney.jira;

import org.apache.commons.codec.binary.Base64;

class Base64Encoder {
    public String encode(String username, String password) {
        String userPassCombo = username + ":" + password;
        return new String(Base64.encodeBase64(userPassCombo.getBytes()));
    }
}
