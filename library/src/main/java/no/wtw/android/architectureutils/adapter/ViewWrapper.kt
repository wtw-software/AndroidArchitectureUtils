package no.wtw.android.architectureutils.adapter

import android.view.View
import androidx.core.graphics.Insets
import androidx.recyclerview.widget.RecyclerView

class ViewWrapper<D, V>(val view: V) : RecyclerView.ViewHolder(view)
        where V : View, V : ViewWrapper.Binder<D> {

    interface Binder<D> {
        fun bind(data: D, insets: Insets? = null)
    }

}