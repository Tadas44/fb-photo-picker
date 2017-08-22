package lt.valaitis.lib.facebook.components;

import android.content.Intent;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
public class ActivityResult {

    private final int resultCode;
    private final int requestCode;
    private final Intent data;

    public ActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Intent getData() {
        return data;
    }
}
