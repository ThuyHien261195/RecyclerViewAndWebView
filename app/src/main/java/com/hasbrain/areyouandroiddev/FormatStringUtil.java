package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.ParallelExecutorCompat;
import android.text.Html;
import android.text.Spanned;

import java.util.Calendar;
import java.util.List;

/**
 * Created by thuyhien on 9/14/17.
 */

public class FormatStringUtil {
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String author) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(author, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(author);
        }
        return result;
    }

    public static String getColoredSpanned(String text, int color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        return "<font color='" + hexColor + "'>" + text + "</font>";
    }

    public static String formatAuthorTitle(String authorTitle, String author, String subReddit, int color) {
        author = getColoredSpanned(author, color);
        subReddit = getColoredSpanned(subReddit, color);
        return String.format(authorTitle, author, subReddit);
    }

    public static String getPostTime(long createdUTC, List<String> titleList) {
        long currentTime = System.currentTimeMillis();
        long betweenTime = currentTime - createdUTC * 1000L;
        int postTime = (int) (betweenTime / ConstantCollection.MILLISECS_PER_YEAR);
        if (postTime != 0) {
            return String.format(titleList.get(2), postTime);
        }

        postTime = (int) (betweenTime / ConstantCollection.MILLISECS_PER_MONTH);
        if (postTime != 0) {
            return String.format(titleList.get(3), postTime);
        }

        postTime = (int) (betweenTime / ConstantCollection.MILLISECS_PER_DAY);
        if (postTime != 0) {
            return String.format(titleList.get(4), postTime);
        }

        postTime = (int) (betweenTime / ConstantCollection.MILLISECS_PER_HOUR);
        if (postTime != 0) {
            return String.format(titleList.get(5), postTime);
        }
        return "";
    }
}
