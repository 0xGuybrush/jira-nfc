package ie.davidmoloney.jira;

import org.apache.http.client.methods.HttpGet;

class NoFollowGet extends HttpGet {

    private static final String HTTP_PROTOCOL_HANDLE_REDIRECTS = "http.protocol.handle-redirects";

    public NoFollowGet(String uri) {
        super(uri);
        getParams().setParameter(HTTP_PROTOCOL_HANDLE_REDIRECTS, false);
    }
}
