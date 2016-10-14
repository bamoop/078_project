package com_t.macvision.mv_078.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.util.ImageFromFileCache;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-23
 * Time: 16:52
 * QQ  : 294894105 ZH
 */

public class WelcomeActivity extends Activity {
    @Bind(R.id.image_welcome)
    ImageView imageWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.welcome_bg).into(imageWelcome);
        final Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 1000 * 1);
        return;
    }
//        Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
////设置动画时间            alphaAnimation.setDuration(3000);
//        imageWelcome.startAnimation(alphaAnimation);
//        alphaAnimation.setDuration(1500);
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
}
