package com.app.collectandrecycle.utils;

import android.view.View;
import android.widget.TextView;

import com.app.collectandrecycle.data.models.Request;

import androidx.databinding.BindingAdapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

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

    @BindingAdapter("time")
    public static void bindTime(TextView textView, Long milliseconds) {
        if (milliseconds != null) {
            textView.setText(Utils.convertMillisecondsToDate(milliseconds, Constants.TIME_FORMAT));
        }
    }

    @BindingAdapter("dateTime")
    public static void bindDateTime(TextView textView, Long milliseconds) {
        if (milliseconds != null) {
            textView.setText(Utils.convertMillisecondsToDate(milliseconds, Constants.DATE_TIME_FORMAT));
        }
    }

    @BindingAdapter("confirmVisibility")
    public static void bindConfirmVisibility(TextView textView, String status) {
        if (status.equals(Request.RequestStatus.New.name())) {
            textView.setVisibility(View.VISIBLE);
        } else if (status.equals(Request.RequestStatus.Confirmed.name())
                || status.equals(Request.RequestStatus.Delivered.name())) {
            textView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("deliverVisibility")
    public static void bindDeliverVisibility(TextView textView, String status) {
        if (status.equals(Request.RequestStatus.New.name())) {
            textView.setVisibility(View.VISIBLE);
        } else if (status.equals(Request.RequestStatus.Delivered.name())) {
            textView.setVisibility(View.GONE);
        } else {
            //status is confirmed
            textView.setVisibility(View.VISIBLE);
            textView.setWidth(MATCH_PARENT);
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
