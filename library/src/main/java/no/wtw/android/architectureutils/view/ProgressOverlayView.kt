package no.wtw.android.architectureutils.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import no.wtw.android.architectureutils.R
import no.wtw.android.architectureutils.databinding.ProgressOverlayBinding
import no.wtw.android.architectureutils.util.AnimationUtility

class ProgressOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val viewBinding by viewBinding<ProgressOverlayBinding>()

    init {
        viewBinding.apply {
            // contentDescription = getContext().getString(R.string.please_wait)
            if (isInEditMode) {
                alpha = 0.5f
            } else {
                visibility = GONE
            }
        }
    }

    fun hide() {
        AnimationUtility.fadeOut(this)
    }

    fun show() {
        AnimationUtility.fadeIn(this)
    }

}