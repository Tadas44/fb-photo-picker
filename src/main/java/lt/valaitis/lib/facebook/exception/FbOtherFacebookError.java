package lt.valaitis.lib.facebook.exception;

import com.facebook.FacebookRequestError;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FbOtherFacebookError extends RuntimeException {
    public FbOtherFacebookError(FacebookRequestError error) {
    }
}
