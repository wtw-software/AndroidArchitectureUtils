package no.wtw.android.architectureutils.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

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
        void bind(D data);
    }

}