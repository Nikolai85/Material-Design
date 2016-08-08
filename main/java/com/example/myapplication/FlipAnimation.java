package com.example.myapplication;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private Camera mCamera;
    private final ScaleUpDownEnum scaleType;

    public static final float SCALE_DEFAULT = 0.75f; private float scale;

    public FlipAnimation(float fromDegrees, float toDegrees, float centerX,
                         float centerY, float scale, ScaleUpDownEnum scaleType) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        this.scale = (scale<=0||scale>=1)?SCALE_DEFAULT:scale;
        this.scaleType = scaleType==null? ScaleUpDownEnum.SCALE_CYCLE:scaleType;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight){
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix(); camera.save();camera.rotateY(degrees);
        camera.getMatrix(matrix); camera.restore(); matrix.preTranslate
                (-centerX, -centerY); matrix.postTranslate(centerX, centerY);
        matrix.preScale(scaleType.getScale(scale, interpolatedTime),
                scaleType.getScale(scale, interpolatedTime), centerX, centerY);
    }

    public enum ScaleUpDownEnum {

        SCALE_UP, SCALE_DOWN, SCALE_CYCLE;

        public float getScale(float max, float iter) {
            switch(this) {
                case SCALE_UP:
                    return max + (1-max)*iter;
                case SCALE_DOWN:
                    return 1 - (1-max)*iter;
                case SCALE_CYCLE: {
                    final boolean halfWay = (iter > 0.5);
                    if (halfWay) {
                        return max + (1-max)*(iter-0.5f)*2;
                    } else {
                        return 1 - (1-max)*(iter*2);
                    }
                }
                default:
                    return 1;
            }
        }
    }
}
