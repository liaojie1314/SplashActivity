package com.example.splashactivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class AnimatedActivity extends AppCompatActivity {
    //Initialize variable
    ImageView ivTop, ivHeart, ivBeat, ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated);

        //Assign variable
        ivTop = findViewById(R.id.iv_top);
        ivHeart = findViewById(R.id.iv_heart);
        ivBottom = findViewById(R.id.iv_bottom);
        textView = findViewById(R.id.text_view);

        //set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize top animation
        Animation animation1 = AnimationUtils.loadAnimation(this
                , R.anim.top_wave);
        //start top animation
        ivTop.setAnimation(animation1);

        //Initialize object animation
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivHeart,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );
        //Set duration
        objectAnimator.setDuration(500);
        //Set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //Set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //Start animator
        objectAnimator.start();

        //Set animate text
        animateText("Heart Beat");
        //Load GIF
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBottom);
        //Initialize bottom animation
        Animation animation2 = AnimationUtils.loadAnimation(this
                , R.anim.bottom_wave);
        //start bottom animation
        ivBottom.setAnimation(animation2);

        //Initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Redirect to main activity
                startActivity(new Intent(AnimatedActivity.this
                        , MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                //Finish activity
                finish();
            }
        }, 4000);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //When runnable is run
            //Set text
            textView.setText(charSequence.subSequence(0, index++));
            //Check condition
            if (index <= charSequence.length()) {
                //When index is equal to text length
                //Run handler
                mHandler.postDelayed(mRunnable, delay);
            }
        }
    };

    //Create animated text method
    public void animateText(CharSequence cs) {
        //Set text
        charSequence = cs;
        //Clear index
        index = 0;
        //Clear text
        textView.setText("");
        //Remove call back
        mHandler.removeCallbacks(mRunnable);
        //Run handler
        mHandler.postDelayed(mRunnable, delay);
    }
}