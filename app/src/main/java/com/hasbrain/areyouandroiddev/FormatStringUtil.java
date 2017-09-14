package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;

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

    public static String getColoredSpanned(Context context, String text, int color) {
        color = ContextCompat.getColor(context, color);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        return "<font color='" + hexColor + "'>" + text + "</font>";
    }

    public static String formatAuthorTitle(Context context, String author, String subReddit) {
        author = getColoredSpanned(context, author, R.color.color_author_title);
        subReddit = getColoredSpanned(context, subReddit, R.color.color_author_title);
        String authorTitle = context.getResources().getString(
                R.string.title_author, author, subReddit);
        return authorTitle;
    }
}
