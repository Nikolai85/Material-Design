package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

public class FlipActivity extends AppCompatActivity {

    RelativeLayout relativeLayout1,relativeLayout2;
    ViewAnimator viewAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);

        viewAnimator = (ViewAnimator) findViewById(R.id.viewFlipper);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3D flip
                AnimationFlipFactory.flipTransition(viewAnimator,
                        AnimationFlipFactory.FlipDirection.RIGHT_LEFT);
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3D flip
                AnimationFlipFactory.flipTransition(viewAnimator,
                        AnimationFlipFactory.FlipDirection.LEFT_RIGHT);
            }
        });
    }
}
