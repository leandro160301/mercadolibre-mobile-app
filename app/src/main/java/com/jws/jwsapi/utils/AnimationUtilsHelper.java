package com.jws.jwsapi.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class AnimationUtilsHelper {

    public static void fadeAnimation(View view, float fromAlpha, float toAlpha, long duration, AnimationEndListener listener) {
        AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setDuration(duration);
        if (listener != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        view.startAnimation(animation);
    }

    public static void rotateAnimation(View view, float fromDegrees, float toDegrees, long duration, AnimationEndListener listener) {
        RotateAnimation animation = new RotateAnimation(
                fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(duration);
        if (listener != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        view.startAnimation(animation);
    }

    public static void scaleAnimation(View view, float fromX, float toX, float fromY, float toY, long duration, AnimationEndListener listener) {
        ScaleAnimation animation = new ScaleAnimation(
                fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(duration);
        if (listener != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        view.startAnimation(animation);
    }

    public static void translateAnimation(View view, float fromX, float toX, float fromY, float toY, long duration, AnimationEndListener listener) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setDuration(duration);
        if (listener != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        view.startAnimation(animation);
    }

    public static void scaleAndRotateAnimation(View view, float scaleFrom, float scaleTo, float rotateFrom, float rotateTo, long duration, AnimationEndListener listener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                scaleFrom, scaleTo, scaleFrom, scaleTo,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        RotateAnimation rotateAnimation = new RotateAnimation(
                rotateFrom, rotateTo,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        scaleAnimation.setDuration(duration);
        rotateAnimation.setDuration(duration);


        if (listener != null) {
            Animation.AnimationListener combinedListener = new Animation.AnimationListener() {
                private boolean scaleEnded = false;
                private boolean rotateEnded = false;

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (animation == scaleAnimation) scaleEnded = true;
                    if (animation == rotateAnimation) rotateEnded = true;
                    if (scaleEnded && rotateEnded) listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            };
            scaleAnimation.setAnimationListener(combinedListener);
            rotateAnimation.setAnimationListener(combinedListener);
        }

        view.startAnimation(scaleAnimation);
        view.startAnimation(rotateAnimation);
    }

    public static void combinedAnimations(View view, long duration, AnimationEndListener listener, Animation... animations) {
        AnimationSet animationSet = new AnimationSet(true);

        for (Animation animation : animations) {
            animation.setDuration(duration);
            animationSet.addAnimation(animation);
        }

        if (listener != null) {
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        view.startAnimation(animationSet);
    }

    @NonNull
    public static View setupDialogView(AlertDialog dialog) {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        View dialogView = dialog.getWindow().getDecorView();
        dialogView.setPivotX(716 / 2f);
        dialogView.setPivotY(69 / 2f);

        return dialogView;
    }

    public static void animateBubbleDialogOpen(View dialogView) {
        dialogView.setScaleX(0);
        dialogView.setScaleY(0);

        dialogView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(300)
                .start();
    }

    public static void animateBubbleDialogClose(AlertDialog dialog) {
        if (dialog == null || dialog.getWindow() == null) return;

        final View dialogView = dialog.getWindow().getDecorView();

        dialogView.setPivotX(dialogView.getWidth() / 2f);
        dialogView.setPivotY(dialogView.getHeight() / 2f);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dialogView, "scaleX", 1f, 1.2f, 0.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dialogView, "scaleY", 1f, 1.2f, 0.8f);

        scaleX.setDuration(200);
        scaleY.setDuration(200);

        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dialog.cancel();
            }
        });

        scaleX.start();
        scaleY.start();
    }

    public static void animateBubbleCloseFragment(final View view, final DisplayMetrics displayMetrics, Runnable action) {
        if (view == null || displayMetrics == null) return;

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 0.8f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 0.8f, 0f);

        ObjectAnimator translateX = ObjectAnimator.ofFloat(view, "translationX", 0f, screenWidth - view.getWidth());
        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", 0f, screenHeight - view.getHeight());

        scaleX.setDuration(300);
        scaleY.setDuration(300);
        translateX.setDuration(300);
        translateY.setDuration(300);

        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                action.run();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, translateX, translateY);
        animatorSet.start();
    }

    private Animation createShrinkAnimation(View fragmentView, View buttonHome) {

        int[] buttonLocation = new int[2];
        buttonHome.getLocationOnScreen(buttonLocation);
        int buttonCenterX = buttonLocation[0] + buttonHome.getWidth() / 2;
        int buttonCenterY = buttonLocation[1] + buttonHome.getHeight() / 2;

        int[] fragmentLocation = new int[2];
        fragmentView.getLocationOnScreen(fragmentLocation);
        int fragmentCenterX = fragmentLocation[0] + fragmentView.getWidth() / 2;
        int fragmentCenterY = fragmentLocation[1] + fragmentView.getHeight() / 2;

        float deltaX = buttonCenterX - fragmentCenterX;
        float deltaY = buttonCenterY - fragmentCenterY;

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 0.0f,
                1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(100);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                0, deltaX,
                0, deltaY
        );
        System.out.println("delta:" + deltaX + deltaY);
        translateAnimation.setDuration(100);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    /*    @Override
    public void openFragmentPrincipal() {
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);

        if (currentFragment != null && currentFragment.getView() != null) {
            View currentView = currentFragment.getView();
            Button buttonHome = ((AppCompatActivity) context).findViewById(R.id.bt_home);

            Animation shrinkAnimation = createShrinkAnimation(currentView, buttonHome);
            currentView.startAnimation(shrinkAnimation);

            currentView.postDelayed(() -> {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.fragment_fade_in,
                        0
                );

                ContainerCoreFragment containerFragment = ContainerCoreFragment.newInstance(fragment.getClass());
                transaction.replace(R.id.container_fragment, containerFragment).commit();
            }, 90);
        } else {
            ContainerCoreFragment containerFragment = ContainerCoreFragment.newInstance(fragment.getClass());
            fragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, containerFragment)
                    .commit();
        }
    }*/


}