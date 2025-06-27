package no.wtw.android.architectureutils.adapter;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.RecyclerView;

public class ViewWrapper<D, V extends View & ViewWrapper.Binder<D>> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }

    public interface Binder<D> {
        void bind(D data, Insets insets);
    }

}