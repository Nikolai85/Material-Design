package com.example.myapplication;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.widget.ViewAnimator;

public class AnimationFlipFactory {

    public enum FlipDirection {
        LEFT_RIGHT,
        RIGHT_LEFT;

        public float getStartDegreeForFirstView() {
            return 0;
        }

        public float getStartDegreeForSecondView() {
            switch(this) {
                case LEFT_RIGHT:
                    return -90;
                case RIGHT_LEFT:
                    return 90;
                default:
                    return 0;
            }
        }

        public float getEndDegreeForFirstView() {
            switch(this) {
                case LEFT_RIGHT:
                    return 90;
                case RIGHT_LEFT:
                    return -90;
                default:
                    return 0;
            }
        }

        public float getEndDegreeForSecondView() {
            return 0;
        }

        public FlipDirection theOtherDirection() {
            switch(this) {
                case LEFT_RIGHT:
                    return RIGHT_LEFT;
                case RIGHT_LEFT:
                    return LEFT_RIGHT;
                default:
                    return null;
            }
        }
    }

    public static Animation[] flipAnimation(final View fromView, FlipDirection dir,
                                            long duration, Interpolator interpolator) {
        Animation[] result = new Animation[2];
        float centerX;
        float centerY;

        centerX = fromView.getWidth() / 2.0f;
        centerY = fromView.getHeight() / 2.0f;

        Animation outFlip= new FlipAnimation(dir.getStartDegreeForFirstView(),
                dir.getEndDegreeForFirstView(), centerX, centerY, FlipAnimation
                .SCALE_DEFAULT, FlipAnimation.ScaleUpDownEnum.SCALE_DOWN);
        outFlip.setDuration(duration);
        outFlip.setFillAfter(true);
        outFlip.setInterpolator(interpolator==null?new AccelerateInterpolator()
                :interpolator);

        AnimationSet outAnimation = new AnimationSet(true);
        outAnimation.addAnimation(outFlip);
        result[0] = outAnimation;

        Animation inFlip = new FlipAnimation(dir.getStartDegreeForSecondView(),
                dir.getEndDegreeForSecondView(), centerX, centerY, FlipAnimation
                .SCALE_DEFAULT, FlipAnimation.ScaleUpDownEnum.SCALE_UP);
        inFlip.setDuration(duration);
        inFlip.setFillAfter(true);
        inFlip.setInterpolator(interpolator==null?new AccelerateInterpolator()
                :interpolator);
        inFlip.setStartOffset(duration);

        AnimationSet inAnimation = new AnimationSet(true);
        inAnimation.addAnimation(inFlip);
        result[1] = inAnimation;

        return result;
    }

    public static void flipTransition(final ViewAnimator viewAnimator, FlipDirection dir) {

        final View fromView = viewAnimator.getCurrentView();
        final int currentIndex = viewAnimator.getDisplayedChild();
        final int nextIndex = (currentIndex + 1)%viewAnimator.getChildCount();

        Animation[] animc = AnimationFlipFactory.flipAnimation(fromView,
                (nextIndex < currentIndex?dir.theOtherDirection():dir), 500, null);

        viewAnimator.setOutAnimation(animc[0]);
        viewAnimator.setInAnimation(animc[1]);

        viewAnimator.showNext();
    }
}
