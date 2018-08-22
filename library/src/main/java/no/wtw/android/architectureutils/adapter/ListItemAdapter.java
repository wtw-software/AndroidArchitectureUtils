package no.wtw.android.architectureutils.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import no.wtw.android.architectureutils.model.Listable;
import no.wtw.android.architectureutils.view.ListItemView;
import no.wtw.android.architectureutils.view.ListItemView_;

@EBean
public class ListItemAdapter extends RecyclerViewAdapterBase<Listable, ListItemView> {

    @RootContext
    protected Context context;

    @Override
    protected ListItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ListItemView_.build(context);
    }

}
