package no.wtw.android.architectureutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.androidannotations.annotations.EViewGroup;

import no.wtw.android.architectureutils.R;
import no.wtw.android.architectureutils.util.AnimationUtility;

@EViewGroup(resName = "progress_overlay")
public class ProgressOverlayView extends FrameLayout {

    public ProgressOverlayView(Context context) {
        this(context, null, 0);
    }

    public ProgressOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContentDescription(getContext().getString(R.string.please_wait));
        if (isInEditMode()) {
            setAlpha(0.5f);
        } else {
            setVisibility(GONE);
            setClickable(true);
        }
    }

    public void hide() {
        AnimationUtility.fadeOut(this);
    }

    public void show() {
        AnimationUtility.fadeIn(this);
    }

}
