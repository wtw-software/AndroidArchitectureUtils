package no.wtw.android.architectureutils.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewAdapterBase<D, V extends View & ViewWrapper.Binder<D>> extends RecyclerView.Adapter<ViewWrapper<D, V>> {

    protected List<D> originalItems = new ArrayList<>();
    protected List<D> filteredItems = new ArrayList<>();

    protected Insets listItemInsets = Insets.NONE;

    public void setListItemInsets(Insets insets) {
        this.listItemInsets = insets;
        notifyDataSetChanged();
    }

    private OnItemClickListener<D, V> listener;

    public void setOnItemClickListener(OnItemClickListener<D, V> listener) {
        this.listener = listener;
    }

    @Override
    public ViewWrapper<D, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final ViewWrapper<D, V> viewHolder, final int position) {
        final V view = viewHolder.getView();
        final D data = filteredItems.get(position);
        view.bind(data, listItemInsets);
        view.setOnClickListener(v -> onItemClick(viewHolder.getAdapterPosition(), view, data));
    }

    public void onItemClick(int position, V view, D data) {
        if (listener != null)
            listener.onItemClick(position, view, data);
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

    public void replaceAll(Collection<D> collection) {
        originalItems.clear();
        originalItems.addAll(collection);
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
    }

    // Override to filter
    protected List<D> getFilteredItems(String constraint) {
        return originalItems;
    }

}
