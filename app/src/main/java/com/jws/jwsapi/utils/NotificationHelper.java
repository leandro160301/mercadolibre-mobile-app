package com.jws.jwsapi.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.jws.jwsapi.R;

public class NotificationHelper {
    private static PopupWindow popupWindow;

    public static void showNotification(Context context,
                                        View anchorView,
                                        String message,
                                        FieldPopupType fieldPopupType,
                                        Integer icon,
                                        FieldPopupTime fieldPopupTime,
                                        Runnable buttonClick,
                                        Boolean closeEnabled
    ) {

        if (popupWindow != null) popupWindow.dismiss();
        if (anchorView == null) return;

        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.item_customnotification, null);

        TextView textView = customView.findViewById(R.id.text);
        LinearLayout lnNav = customView.findViewById(R.id.ln_nav);
        ImageView imageView = customView.findViewById(R.id.imageView);
        ImageView imNext = customView.findViewById(R.id.im_next);
        Button closeButton = customView.findViewById(R.id.bt_close);
        ConstraintLayout clButton = customView.findViewById(R.id.cl_button_text);

        if (icon != null) imageView.setImageResource(icon);
        if (icon == null || buttonClick != null)
            imageView.setImageResource(R.drawable.notification);
        if (buttonClick != null) imageView.setImageResource(R.drawable.info);
        if (closeEnabled == null) closeEnabled = false;

        if (buttonClick == null) {
            clButton.setBackgroundResource(R.color.transparente);
            imNext.setVisibility(View.GONE);
        }
        switch (fieldPopupType) {
            case OK -> lnNav.setBackgroundResource(R.drawable.stylenotification_ok);
            case ERROR -> lnNav.setBackgroundResource(R.drawable.stylenotification_error);
        }

        Animation bubbleInAnimation = AnimationUtils.loadAnimation(context, R.anim.bubble_in);
        Animation bubbleOutAnimation = AnimationUtils.loadAnimation(context, R.anim.bubble_out);


        textView.setText(message);

        popupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(anchorView, Gravity.FILL, 0, 0);

        lnNav.startAnimation(bubbleInAnimation);

        long milis = 3000;
        if (fieldPopupTime == FieldPopupTime.HIGH) milis = 6000;
        closeButton.setVisibility(fieldPopupTime == FieldPopupTime.NEVER || closeEnabled ? View.VISIBLE : View.GONE);
        closeButton.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupWindow = null;
        });

        textView.setOnClickListener(v -> {
            if (buttonClick == null) return;

            buttonClick.run();
            popupWindow.dismiss();
            popupWindow = null;
        });

        if (fieldPopupTime != FieldPopupTime.NEVER) {
            customView.postDelayed(() -> {
                if (popupWindow != null) {
                    lnNav.startAnimation(bubbleOutAnimation);

                    bubbleOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (fieldPopupTime != FieldPopupTime.NEVER) {
                                popupWindow.dismiss();
                                popupWindow = null;
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
            }, milis);
        }
    }

    public enum FieldPopupType {
        NOTIFICATION,
        ERROR,
        OK
    }


    public enum FieldPopupTime {
        LOW,
        HIGH,
        NEVER
    }

}
