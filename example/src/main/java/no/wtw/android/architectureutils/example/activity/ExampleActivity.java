package no.wtw.android.architectureutils.example.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import no.wtw.android.architectureutils.adapter.ListItemAdapter;
import no.wtw.android.architectureutils.example.R;
import no.wtw.android.architectureutils.model.Listable;

@SuppressLint("Registered")
@EActivity(R.layout.example_activity)
public class ExampleActivity extends AppCompatActivity {

    @ViewById(R.id.list)
    protected RecyclerView list;

    @Bean
    protected ListItemAdapter adapter;

    @AfterViews
    protected void init() {
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((position, view, data) -> Toast.makeText(ExampleActivity.this, "onItemClick", Toast.LENGTH_SHORT).show());
        // adapter.setonItemExtraClickListener((position, view, data) -> Toast.makeText(ExampleActivity.this, "onItemExtraClick", Toast.LENGTH_SHORT).show(), R.drawable.ic_more_vert_black_24dp);

        for (int i = 0; i < 10; i++)
            adapter.add(new ExampleItem());
    }

    private class ExampleItem implements Listable {

        @Override
        public String getTitle(Context context) {
            return "foo";
        }

        @Override
        public String getSubTitle(Context context) {
            return "bar";
        }

        @Override
        public int getIconResourceId(Context context) {
            return R.drawable.ic_person_black_24dp;
        }

    }
}

