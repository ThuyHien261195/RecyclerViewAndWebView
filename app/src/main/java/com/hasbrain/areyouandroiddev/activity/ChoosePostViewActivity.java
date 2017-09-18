package com.hasbrain.areyouandroiddev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hasbrain.areyouandroiddev.ConstantCollection;
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
        Intent intent = new Intent(this, PostListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_list_view)
    public void onClickBtnViewByListView() {
        Intent intent = new Intent(this, PostListActivity.class);
        intent.putExtra(ConstantCollection.EXTRA_NAME_GROUP_VIEW_TYPE,
                ConstantCollection.LIST_VIEW);
        startActivity(intent);
    }

    @OnClick(R.id.button_grid_view)
    public void onClickBtnViewByGridView() {
        Intent intent = new Intent(this, PostListActivity.class);
        intent.putExtra(ConstantCollection.EXTRA_NAME_GROUP_VIEW_TYPE,
                ConstantCollection.GRID_VIEW);
        startActivity(intent);
    }

    @OnClick(R.id.button_expandable_list)
    public void onClickBtnExpandableListView() {
        Intent intent = new Intent(this, PostInSectionActivity.class);
        intent.putExtra(ConstantCollection.EXTRA_NAME_GROUP_VIEW_TYPE,
                ConstantCollection.EXPANDABLE_LIST_VIEW);
        startActivity(intent);
    }

    @OnClick(R.id.button_expanable_recycler)
    public void onClickBtnExpandableRecyclerView() {
        Intent intent = new Intent(this, PostInSectionActivity.class);
        intent.putExtra(ConstantCollection.EXTRA_NAME_GROUP_VIEW_TYPE,
                ConstantCollection.EXPANDABLE_RECYCLER_VIEW);
        startActivity(intent);
    }
}
