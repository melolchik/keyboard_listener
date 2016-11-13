
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Olga Melekhina on 04.07.2016.
 */
public class KeyboardObserver {

    protected View mRootView;
    protected boolean isKeyboardDown = true;
    protected KeyboardListener mKeyboardListener;

    public interface KeyboardListener{
        void onSoftKeyboardShow();
        void onSoftKeyboardHide();
    }
    public KeyboardObserver(View rootView){
        mRootView = rootView;
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(mRootView.getRootView())) {
                    if (isKeyboardDown) {
                        //log("keyboard UP");
                        if(mKeyboardListener != null) {
                            mKeyboardListener.onSoftKeyboardShow();
                        }
                        isKeyboardDown = false;
                    }
                } else {
                    if (!isKeyboardDown) {
                        //log("keyboard Down");
                        if(mKeyboardListener != null) {
                            mKeyboardListener.onSoftKeyboardHide();
                        }
                        isKeyboardDown = true;
                    }
                }
            }
        });
    }

    public void setKeyboardListener(KeyboardListener listener) {
        mKeyboardListener = listener;
    }

    private boolean keyboardShown(View rootView) {

        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        //log("keyboardShown : rootView.getBottom() = " + rootView.getBottom());
        //log("keyboardShown : r.bottom = " + r.bottom);
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
