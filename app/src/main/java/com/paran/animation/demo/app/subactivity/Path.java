/**
 * KTH Developed by Java <br>
 *
 * @Copyright 2011 by Service Platform Development Team, KTH, Inc. All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of KTH, Inc. <br>
 * You shall not disclose such Confidential Information and shall use it only <br>
 * in accordance with the terms of the license agreement you entered into with KTH.
 */
package com.paran.animation.demo.app.subactivity;

import java.util.ArrayList;

import com.paran.animation.demo.app.R;
import com.paran.animation.demo.app.view.PathButton;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * com.paran.animation.demo.app.subactivity.Path.java - Creation date: 2011. 12. 21. <br>
 * Path 메뉴 화면 Demo
 *
 * @author KTH 단말어플리케이션개발팀 홍성훈(Email: breadval@kthcorp.com, Ext: 2923)
 * @version 1.0
 * @tags
 */
public class Path extends Activity implements OnClickListener {
    private static final String TAG = Path.class.getSimpleName();

    private Context context;

    /**
     * 메뉴가 열렸을때 이동 거리
     */
    private static final int length = 350;
    /**
     * (+)버튼 동작 시간
     */
    private static final int duration = 150;
    /**
     * 하위 메뉴 선택시 동작 시간
     */
    private static final int sub_select_duration = 150;
    /**
     * 하위 메뉴 동작시 각 버튼간의 시간 Gap
     */
    private static final int sub_offset = 30;

    private ImageButton plus_button;
//    private ImageView plus;

    /**
     * 하위 메뉴 버튼 리스트
     */
    private ArrayList<PathButton> buttons;

    /**
     * Menu 가 열려있는지 닫혀있는지 체크하기위한 flag
     */
    private boolean isMenuOpened = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path);

        context = this;

        plus_button = (ImageButton) findViewById(R.id.plus_button);
        plus_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isMenuOpened) {
                    isMenuOpened = true;
                } else {
                    isMenuOpened = false;
                }
                startMenuAnimation(isMenuOpened);
            }
        });

//        plus = (ImageView) findViewById(R.id.plus);

        buttons = new ArrayList<>();

        PathButton button = (PathButton) findViewById(R.id.camera);
        button.setOnClickListener(this);
        buttons.add(button);

        button = (PathButton) findViewById(R.id.with);
        button.setOnClickListener(this);
        buttons.add(button);

        button = (PathButton) findViewById(R.id.place);
        button.setOnClickListener(this);
        buttons.add(button);

        button = (PathButton) findViewById(R.id.music);
        button.setOnClickListener(this);
        buttons.add(button);

        button = (PathButton) findViewById(R.id.thought);
        button.setOnClickListener(this);
        buttons.add(button);

        button = (PathButton) findViewById(R.id.sleep);
        button.setOnClickListener(this);
        buttons.add(button);
    }

    /**
     * <PRE>
     * Comment : <br>
     * 하위 메뉴가 선택되었을 때, 애니메이션 처리
     * 선택된 메뉴는 커지면서 점점 투명해지다가 사라지며,
     * 나머지 메뉴는 작아지면서 점점 투명해지다가 사라진다.
     * (+)버튼은 닫힌 상태로 돌아간다.
     *
     * @param index 선택된 버튼의 index
     * @author kth
     * @version 1.0
     * @date 2011. 12. 22.
     * </PRE>
     */
    private void startSubButtonSelectedAnimation(int index) {
        Log.i(TAG, "startSubButtonSelectedAnimation()");
        for (int i = 0; i < buttons.size(); i++) {
            final PathButton view = buttons.get(i);
            if (index == i) {
                view.animate().scaleX(1.2f).scaleY(1.2f).alpha(0f).setDuration(sub_select_duration)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) { }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                setTralations(view, 0f, 0f, true);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) { }

                            @Override
                            public void onAnimationRepeat(Animator animation) { }
                        })
                        .start();

            } else {
                view.animate().alpha(0f).rotation(-360f).translationX(0f).translationY(0f).setDuration(duration)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) { }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                setTralations(view, 0f, 0f, false);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) { }

                            @Override
                            public void onAnimationRepeat(Animator animation) { }
                        })
                        .start();
            }
        }

        if (isMenuOpened) {
            //(+)버튼은 닫힌 상태로 돌아가야한다.
            isMenuOpened = false;

            Animation rotate = new RotateAnimation(
                    -45, 0
                    , Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);

            rotate.setInterpolator(AnimationUtils.loadInterpolator(this,
                    android.R.anim.anticipate_overshoot_interpolator));
            rotate.setFillAfter(true);
            rotate.setDuration(sub_select_duration);
            plus_button.startAnimation(rotate);

            //2012.1.17 일부단말에서 클릭 안되는 문제 해결을 위해 수정
            for (int i = 0; i < buttons.size(); i++) {
//                movePathButton(i, false);
            }
        }
    }

    /**
     * <PRE>
     * Comment : <br>
     * 하위 메뉴가 열리거나 닫힐때 애니메이션 처리
     * 하위 메뉴는 회전하면서 이동한다.
     * Dynamic한 효과를 위해 Interpolater를 사용한다.
     *
     * @param index 버튼 index
     * @param open
     * @author kth
     * @version 1.0
     * @date 2011. 12. 22.
     * </PRE>
     */
    private void startSubButtonAnimation(final int index, final boolean open) {
        Log.i(TAG, "startSubButtonAnimation()");
        final PathButton view = buttons.get(index);

        final int endX = (int) (length * Math.cos(Math.PI * 1 / 2 * (index) / (buttons.size() - 1)));
        final int endY = (int) (length * Math.sin(Math.PI * 1 / 2 * (index) / (buttons.size() - 1)));
        Log.d(TAG, "********************************************************");
        Log.d(TAG, "endX : " + endX + ", endY : " + endY);
        Log.d(TAG, "********************************************************");

        if (open) {
            view.animate().alpha(1f).rotation(360f).translationX(endX).translationY(-endY).setDuration(duration)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                    public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setTralations(view, endX, -endY, false);
//                            view.setTranslationX(endX);
//                            view.setTranslationY(-endY);
                        }

                        @Override
                    public void onAnimationCancel(Animator animation) { }

                        @Override
                    public void onAnimationRepeat(Animator animation) { }
                    })
                    .start();
        } else {
            view.animate().alpha(0f).rotation(-360f).translationX(0f).translationY(0f).setDuration(duration)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                    public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setTralations(view, 0f, 0f, false);
//                            view.setTranslationX(0f);
//                            view.setTranslationY(0f);
                        }

                        @Override
                    public void onAnimationCancel(Animator animation) { }

                        @Override
                    public void onAnimationRepeat(Animator animation) { }
                    })
                    .start();
        }
    }

    //2012.1.17 일부단말에서 클릭 안되는 문제 해결을 위해 수정
    private int orgLeftMargin = -1;
    private int orgBottomMargin = -1;

    @Deprecated
    private void movePathButton(int index, boolean open) {
        Log.i(TAG, "movePathButton()");
        PathButton view = buttons.get(index);

        int endX = (int) (length * Math.cos(Math.PI * 1 / 2 * (index) / (buttons.size() - 1)));
        int endY = (int) (length * Math.sin(Math.PI * 1 / 2 * (index) / (buttons.size() - 1)));
        Log.d(TAG, "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        Log.d(TAG, "endX : " + endX + ", endY : " + endY);
        Log.d(TAG, "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (orgLeftMargin == -1) {
            orgLeftMargin = params.leftMargin;
            orgBottomMargin = params.bottomMargin;
        }

        if (open) {
            params.leftMargin = orgLeftMargin + endX;
            params.bottomMargin = orgBottomMargin + endY;
        } else {
            params.leftMargin = orgLeftMargin;
            params.bottomMargin = orgBottomMargin;
        }

        view.setLayoutParams(params);
    }

    /**
     * <PRE>
     * Comment : <br>
     * (+)버튼을 눌렀을때 애니메이션 처리
     * (+)버튼은 45도 회전하며
     * 하위 메뉴는 startSubButtonAnimation를 각각 호출하여 화면에 나오게된다.
     *
     * @param open
     * @author kth
     * @version 1.0
     * @date 2011. 12. 22.
     * </PRE>
     */
    private void startMenuAnimation(boolean open) {
        Log.i(TAG, "startMenuAnimation()");
        Animation rotate;

        if (open)
            rotate = new RotateAnimation(
                    0, 45
                    , Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);
        else
            rotate = new RotateAnimation(
                    -45, 0
                    , Animation.RELATIVE_TO_SELF, 0.5f
                    , Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setInterpolator(AnimationUtils.loadInterpolator(this,
                android.R.anim.anticipate_overshoot_interpolator));
        rotate.setFillAfter(true);
        rotate.setDuration(duration);
        plus_button.startAnimation(rotate);

        for (int i = 0; i < buttons.size(); i++) {
            startSubButtonAnimation(i, open);
        }
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.camera: {
                Toast.makeText(context, "Camera Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.with: {
                Toast.makeText(context, "With Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.place: {
                Toast.makeText(context, "Place Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.music: {
                Toast.makeText(context, "Music Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.thought: {
                Toast.makeText(context, "Thought Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.sleep: {
                Toast.makeText(context, "Sleep Clicked", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        int index = buttons.indexOf(v);
        if (index > -1 && index < buttons.size())
            startSubButtonSelectedAnimation(index);
    }

    private void setTralations(View view, float x, float y, boolean needToRestoreScale) {
        Log.d(TAG, "setTralations()");

        view.setTranslationX(x);
        view.setTranslationY(y);

        if (needToRestoreScale) {
            view.setScaleX(1f);
            view.setScaleY(1f);
        }
    }
}
