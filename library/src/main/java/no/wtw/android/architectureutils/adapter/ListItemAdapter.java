package no.wtw.android.architectureutils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import androidx.annotation.DrawableRes;
import no.wtw.android.architectureutils.R;
import no.wtw.android.architectureutils.model.Listable;
import no.wtw.android.architectureutils.view.ListItemView;
import no.wtw.android.architectureutils.view.ListItemView_;

@EBean
public class ListItemAdapter extends RecyclerViewAdapterBase<Listable, ListItemView> {

    @RootContext
    protected Context context;

    private OnItemClickListener<Listable, ListItemView> extraClickListener;
    @DrawableRes
    private int extraButtonClickResource;

    @Override
    protected ListItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ListItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<Listable, ListItemView> viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        ImageButton extraClickButton = viewHolder.getView().findViewById(R.id.extra_button);
        extraClickButton.setOnClickListener(v -> onItemExtraClick(viewHolder.getAdapterPosition(), viewHolder.getView(), filteredItems.get(position)));
        extraClickButton.setVisibility(extraClickListener == null ? View.GONE : View.VISIBLE);
        extraClickButton.setImageResource(extraButtonClickResource);
    }

    public void setonItemExtraClickListener(OnItemClickListener<Listable, ListItemView> listener, @DrawableRes int iconResourceId) {
        this.extraClickListener = listener;
        this.extraButtonClickResource = iconResourceId;
        notifyDataSetChanged();
    }

    public void onItemExtraClick(int position, ListItemView view, Listable data) {
        if (extraClickListener != null)
            extraClickListener.onItemClick(position, view, data);
    }

}
