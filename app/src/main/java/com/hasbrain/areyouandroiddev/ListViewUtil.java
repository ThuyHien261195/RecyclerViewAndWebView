package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ListViewUtil {

    public static List<Integer> setColorTitleList(Context context) {
        List<Integer> colorTitleList = new ArrayList<>();
        colorTitleList.add(ContextCompat.getColor(context, R.color.color_author_title));
        colorTitleList.add(ContextCompat.getColor(context, R.color.color_sticky_post));
        colorTitleList.add(ContextCompat.getColor(context, android.R.color.black));
        colorTitleList.add(ContextCompat.getColor(context, android.R.color.white));
        return colorTitleList;
    }

    public static List<String> setTitleList(Context context) {
        List<String> titleList = new ArrayList<>();
        titleList.add(context.getResources().getString(R.string.title_author));
        titleList.add(context.getResources().getString(R.string.title_comment));
        titleList.add(context.getResources().getString(R.string.title_created_years));
        titleList.add(context.getResources().getString(R.string.title_created_months));
        titleList.add(context.getResources().getString(R.string.title_created_days));
        titleList.add(context.getResources().getString(R.string.title_created_hours));
        return titleList;
    }
}
