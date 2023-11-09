package no.wtw.android.architectureutils.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.RelativeLayout
import no.wtw.android.architectureutils.adapter.ViewWrapper
import no.wtw.android.architectureutils.databinding.ListItemViewBinding
import no.wtw.android.architectureutils.model.Listable

open class ListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr),
    ViewWrapper.Binder<Listable> {

    private val viewBinding by viewBinding<ListItemViewBinding>()

    override fun bind(data: Listable) {
        viewBinding.apply {
            icon.setImageResource(data.getIconResourceId(context))
            icon.visibility = if (data.getIconResourceId(context) == 0) GONE else VISIBLE
            title.text = data.getTitle(context)
            subtitle.text = data.getSubTitle(context)
            subtitle.visibility = if (TextUtils.isEmpty(data.getSubTitle(context))) GONE else VISIBLE
        }
    }

}