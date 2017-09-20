package com.hasbrain.areyouandroiddev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hasbrain.areyouandroiddev.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoosePostViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_post_view);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_recycler_view)
    public void onClickBtnViewByRecyclerView() {
        Intent intent = new Intent(this, PostRecyclerViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_list_view)
    public void onClickBtnViewByListView() {
        Intent intent = new Intent(this, PostListViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_grid_view)
    public void onClickBtnViewByGridView() {
        Intent intent = new Intent(this, PostGridViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_expandable_list)
    public void onClickBtnExpandableListView() {
        Intent intent = new Intent(this, PostInSectionListViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_expanable_recycler)
    public void onClickBtnExpandableRecyclerView() {
        Intent intent = new Intent(this, PostInSectionRecyclerViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_new_expanable_recycler)
    public void onClickBtnNewExpandableRecyclerView() {
        Intent intent = new Intent(this, PostExpandRecylerViewActivity.class);
        startActivity(intent);
    }
}
