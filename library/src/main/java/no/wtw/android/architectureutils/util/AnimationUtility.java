package no.wtw.android.architectureutils.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import static android.view.View.VISIBLE;

public class AnimationUtility {

    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        if (view.getVisibility() == toVisibility)
            return;
        boolean show = toVisibility == VISIBLE;
        if (show)
            view.setAlpha(0);
        view.setVisibility(VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

    public static void fadeIn(View view) {
        animateView(view, View.VISIBLE, 1, 200);
    }

    public static void fadeOut(View view) {
        AnimationUtility.animateView(view, View.GONE, 0, 200);
    }

}
