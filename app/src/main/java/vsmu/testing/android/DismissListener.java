package vsmu.testing.android;

import android.graphics.Bitmap;

import java.util.List;

//In this interface, you can define messages, which will be send to owner.
public interface DismissListener {
    //In this case we have two messages,
    //the first that is sent when the process is successful.
    void onSuccess(boolean dismiss);
}
