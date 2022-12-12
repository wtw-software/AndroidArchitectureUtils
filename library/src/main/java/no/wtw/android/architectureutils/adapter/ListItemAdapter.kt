package no.wtw.android.architectureutils.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import no.wtw.android.architectureutils.R
import no.wtw.android.architectureutils.model.Listable
import no.wtw.android.architectureutils.view.ListItemView

open class ListItemAdapter(
    private val context: Context,
) : RecyclerViewAdapterBase<Listable, ListItemView>() {

    private var extraClickListener: OnItemClickListener<Listable, ListItemView>? = null

    @DrawableRes
    private var extraButtonClickResource = 0

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) =
        ListItemView(context)

    override fun onBindViewHolder(viewHolder: ViewWrapper<Listable, ListItemView>, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        val extraClickButton = viewHolder.view.findViewById<ImageButton>(R.id.extra_button)
        extraClickButton.setOnClickListener { onItemExtraClick(viewHolder.bindingAdapterPosition, viewHolder.view, filteredItems[position]) }
        extraClickButton.visibility = if (extraClickListener == null) View.GONE else View.VISIBLE
        extraClickButton.setImageResource(extraButtonClickResource)
    }

    fun setonItemExtraClickListener(listener: OnItemClickListener<Listable, ListItemView>?, @DrawableRes iconResourceId: Int) {
        extraClickListener = listener
        extraButtonClickResource = iconResourceId
        notifyDataSetChanged()
    }

    fun onItemExtraClick(position: Int, view: ListItemView, data: Listable) {
        extraClickListener?.onItemClick(position, view, data)
    }

}