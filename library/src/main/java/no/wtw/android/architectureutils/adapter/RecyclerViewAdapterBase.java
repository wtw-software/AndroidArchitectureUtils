package no.wtw.android.architectureutils.adapter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import no.wtw.android.architectureutils.R;

public abstract class RecyclerViewAdapterBase<D, V extends View & ViewWrapper.Binder<D>> extends RecyclerView.Adapter<ViewWrapper<D, V>> {

    protected List<D> originalItems = new ArrayList<>();
    protected List<D> filteredItems = new ArrayList<>();

    private OnItemClickListener<D, V> listener;
    private OnItemClickListener<D, V> extraClickListener;
    @DrawableRes
    private int extraButtonClickResource;

    public void setOnItemClickListener(OnItemClickListener<D, V> listener) {
        this.listener = listener;
    }

    public void setonItemExtraClickListener(OnItemClickListener<D, V> listener, @DrawableRes int iconResourceId) {
        this.extraClickListener = listener;
        this.extraButtonClickResource = iconResourceId;
        notifyDataSetChanged();
    }

    @Override
    public final ViewWrapper<D, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final ViewWrapper<D, V> viewHolder, final int position) {
        final V view = viewHolder.getView();
        final D data = filteredItems.get(position);
        view.bind(data);
        view.setOnClickListener(v -> onItemClick(viewHolder.getAdapterPosition(), view, data));
        ImageButton extraClickButton = view.findViewById(R.id.extra_button);
        extraClickButton.setOnClickListener(v -> onItemExtraClick(viewHolder.getAdapterPosition(), view, data));
        extraClickButton.setVisibility(extraClickListener == null ? View.GONE : View.VISIBLE);
        extraClickButton.setImageResource(extraButtonClickResource);
    }

    public void onItemClick(int position, V view, D data) {
        if (listener != null)
            listener.onItemClick(position, view, data);
    }

    public void onItemExtraClick(int position, V view, D data) {
        if (extraClickListener != null)
            extraClickListener.onItemClick(position, view, data);
    }

    @Override
    public int getItemCount() {
        if (filteredItems == null)
            return 0;
        return filteredItems.size();
    }

    public void add(D item) {
        originalItems.add(item);
        filter(null);
        notifyDataSetChanged();
    }

    public void addAll(Collection<D> collection) {
        originalItems.addAll(collection);
        filter(null);
        notifyDataSetChanged();
    }

    public void clear() {
        originalItems.clear();
        filter(null);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener<D, V> {
        void onItemClick(int position, V view, D data);
    }

    public List<D> getItems() {
        return filteredItems;
    }

    public void filter(String constraint) {
        filteredItems.clear();
        filteredItems.addAll(getFilteredItems(constraint));
        notifyDataSetChanged();
    }

    // Override to filter
    protected List<D> getFilteredItems(String constraint) {
        return originalItems;
    }

}
