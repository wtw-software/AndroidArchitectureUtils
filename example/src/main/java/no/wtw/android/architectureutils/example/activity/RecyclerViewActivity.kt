package no.wtw.android.architectureutils.example.activity

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.wtw.android.architectureutils.adapter.ListItemAdapter
import no.wtw.android.architectureutils.example.R
import no.wtw.android.architectureutils.model.Listable
import no.wtw.android.architectureutils.view.ListItemView
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.ViewById

@SuppressLint("Registered")
@EActivity(R.layout.recycler_view_activity)
open class RecyclerViewActivity : AppCompatActivity() {

    @ViewById(R.id.list)
    lateinit var list: RecyclerView

    @Bean
    lateinit var adapter: ListItemAdapter

    @AfterViews
    fun init() {
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        list.adapter = adapter
        adapter.setOnItemClickListener { _: Int, _: ListItemView?, _: Listable? -> Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show() }
        adapter.setonItemExtraClickListener({ _: Int, _: ListItemView?, _: Listable? -> Toast.makeText(this, "onItemExtraClick", Toast.LENGTH_SHORT).show() }, R.drawable.ic_more_vert_black_24dp)
        for (i in 0..9) adapter.add(ExampleItem())
    }

    private inner class ExampleItem : Listable {
        override fun getTitle(context: Context): String {
            return "foo"
        }

        override fun getSubTitle(context: Context): String {
            return "bar"
        }

        override fun getIconResourceId(context: Context): Int {
            return R.drawable.ic_person_black_24dp
        }
    }

}