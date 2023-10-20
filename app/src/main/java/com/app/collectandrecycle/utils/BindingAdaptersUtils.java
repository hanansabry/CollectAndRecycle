package com.app.collectandrecycle.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingAdaptersUtils {

    @BindingAdapter({"bind:startDate", "bind:closeDate"})
    public static void bindOrderDates(TextView textView, long startDate, long closeDate) {
        textView.setText(String.format("From: %1s to: %2s",
                Utils.convertMillisecondsToDate(startDate, Constants.DATE_FORMAT),
                Utils.convertMillisecondsToDate(closeDate, Constants.DATE_FORMAT))
        );
    }

    @BindingAdapter("date")
    public static void bindDate(TextView textView, Long milliseconds) {
        if (milliseconds != null) {
            textView.setText(Utils.convertMillisecondsToDate(milliseconds, Constants.DATE_FORMAT));
        }
    }

    @BindingAdapter("delivery")
    public static void bindDelivery(TextView textView, boolean isDelivery) {
        if (isDelivery) {
            textView.setText("Delivery");
        } else {
            textView.setText("No Delivery");
        }
    }
}
