package no.wtw.android.architectureutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import no.wtw.android.architectureutils.adapter.ViewWrapper;
import no.wtw.android.architectureutils.model.Listable;

@EViewGroup(resName = "list_item_view")
public class ListItemView extends RelativeLayout implements ViewWrapper.Binder<Listable> {

    @ViewById(resName = "title")
    protected TextView titleView;
    @ViewById(resName = "subtitle")
    protected TextView subTitleView;

    public ListItemView(Context context) {
        super(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(Listable data) {
        titleView.setText(data.getTitle());
        subTitleView.setText(data.getSubTitle());
    }

}
