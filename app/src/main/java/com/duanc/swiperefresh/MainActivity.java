package com.duanc.swiperefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.duanc.refreshlayout.SwipyRefreshLayout;
import com.duanc.refreshlayout.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新  上拉加载
 */
public class MainActivity extends AppCompatActivity {

    SwipyRefreshLayout refreshLayout;
    RecyclerView recyclerView;

    boolean fristrefresh;
    private List<String> list = new ArrayList<>();
    RecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        refreshLayout = findViewById(R.id.swipy);

        for (int i = 0; i < 8; i++) {
            list.add(i + "G");
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyclerviewAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    fristrefresh = true;
                    refreshLayout.setRefreshing(false);
                    for (int i = 0; i < 3; i++) {
                        list.add(i * 3 + "G");

                    }
                    adapter.notifyDataSetChanged();


                    Toast.makeText(MainActivity.this, "222", Toast.LENGTH_SHORT).show();

                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    refreshLayout.setRefreshing(false);
                    fristrefresh = false;
                    for (int i = 0; i < 3; i++) {
                        list.add(i * 5 + "G");
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "33", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
