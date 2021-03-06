package no.wtw.android.architectureutils.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import no.wtw.android.architectureutils.adapter.ViewWrapper;
import no.wtw.android.architectureutils.model.Listable;

@EViewGroup(resName = "list_item_view")
public class ListItemView extends RelativeLayout implements ViewWrapper.Binder<Listable> {

    @ViewById(resName = "icon")
    protected ImageView iconView;
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
        iconView.setImageResource(data.getIconResourceId(getContext()));
        iconView.setVisibility(data.getIconResourceId(getContext()) == 0 ? GONE : VISIBLE);
        titleView.setText(data.getTitle(getContext()));
        subTitleView.setText(data.getSubTitle(getContext()));
        subTitleView.setVisibility(TextUtils.isEmpty(data.getSubTitle(getContext())) ? GONE : VISIBLE);
    }

}
