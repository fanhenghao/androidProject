package com.fhh.technology.module.home.animation.other;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

public class AnimationOtherActivity extends BaseActivity {

    @BindView(R.id.iv_rotate_arrow)
    ImageView mIvRotateArrow;
    @BindView(R.id.tv_rotate_animation)
    TextView mTvRotateAnimation;
    @BindView(R.id.iv_translate_arrow)
    ImageView mIvTranslateArrow;
    @BindView(R.id.tv_translate_animation)
    TextView mTvTranslateAnimation;
    @BindView(R.id.iv_animation_set)
    ImageView mIvAnimationSet;
    @BindView(R.id.tv_animation_set)
    TextView mTvAnimationSet;
    @BindView(R.id.iv_attribute_move_arrow)
    ImageView mIvAttributeMoveArrow;
    @BindView(R.id.tv_attribute_move_animation)
    TextView mTvAttributeMoveAnimation;
    @BindView(R.id.iv_attribute_set_arrow)
    ImageView mIvAttributeSetArrow;
    @BindView(R.id.tv_attribute_rotate_animation)
    TextView mTvAttributeRotateAnimation;
    @BindView(R.id.tv_attribute_alpha_animation)
    TextView mTvAttributeAlphaAnimation;
    @BindView(R.id.tv_attribute_scale_animation)
    TextView mTvAttributeScaleAnimation;
    @BindView(R.id.tv_attribute_group_animation)
    TextView mTvAttributeGroupAnimation;
    @BindView(R.id.iv_animation_xml)
    ImageView mIvAnimationXml;
    @BindView(R.id.tv_animation_xml)
    TextView mTvAnimationXml;


    private float mRotateForm = 0.0f;
    private float mRotateTo = 90.0f;
    private float mTranslateForm = Animation.RELATIVE_TO_SELF;
    private float mTranslateTo = 90.0f;
    private float mTranslateSize = 270.0f;
    private float mScaleFromX = 1f;
    private float mScaleToX = 0.8f;
    private float mScaleFromY = 1f;
    private float mScaleToY = 0.8f;
    private float mAlphaForm = 1.0f;
    private float mAlphaTo = 0.3f;
    private float mRotateStart = 0.0f;
    private float mRotateEnd = 90.0f;
    //属性动画
    private float mAttributeTranslateFormX = 0.0f;
    private float mAttributeTranslateToX = 90.0f;
    private float mAttributeRotateFrom = 0.0f;
    private float mAttributeRotateTo = 100.0f;
    private float mAttributeScaleFormX = 1.0f;
    private float mAttributeScaleToX = 0.7f;
    private float mAttributeScaleFromY = 1.0f;
    private float mAttributeScaleToY = 0.7f;
    private float mAttributeAlphaFrom = 1.0f;
    private float mAttributeAlphaTo = 0.4f;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AnimationOtherActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_animation_other;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_animation), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.backgroundColor = R.color.bg_animation;
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        normalAnimation();
        attributeAnimation();
        xmlAnimation();
    }

    /**
     * xml布局动画(用的少)
     */
    private void xmlAnimation() {
        mTvAnimationXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") Animator animator = AnimatorInflater.loadAnimator(mActivity, R.anim.layout_animation);
                animator.setTarget(mIvAnimationXml);
                animator.start();
            }
        });
    }

    /**
     * 属性动画
     */
    private void attributeAnimation() {
        //属性动画-移动动画
        mTvAttributeMoveAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvAttributeMoveArrow, "translationX", mAttributeTranslateFormX, mAttributeTranslateToX);
                if (mAttributeTranslateToX < 270.0f) {
                    mAttributeTranslateToX += 90.0f;
                    mAttributeTranslateFormX += 90.0f;
                } else {
                    mAttributeTranslateToX = 90.0f;
                    mAttributeTranslateFormX = 0.0f;
                }
                objectAnimator.setDuration(1500);
                objectAnimator.start();
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mTvAttributeMoveAnimation.setText(getString(R.string.animation_other_arrow_translate) + (int) ((float) animation.getAnimatedValue()));
                    }
                });
            }
        });
        mIvAttributeMoveArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mActivity, "ObjectAnimation属性动画的点击事件");
            }
        });
        //属性动画--旋转动画
        mTvAttributeRotateAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "rotation", mAttributeRotateFrom, mAttributeRotateTo);
                rotateAnimator.setDuration(600);
                mAttributeRotateFrom += 100.0f;
                mAttributeRotateTo += 100.0f;
                rotateAnimator.start();
            }
        });
        //属性动画--缩放动画
        mTvAttributeScaleAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现属性动画的组合动画效果
                //方法一：通过PropertyValuesHolder的ofFloat的方法，去实现某一种动画，多个PropertyValuesHolder最后通过ObjectAnimator的ObjectAnimator.ofPropertyValuesHolder()的方法来得到animation，
                //可以添加一个插值器，设置时间然后开启动画
                //方法二：首先使用创建多个要用到的ObjectAnimator，再去new AnimatorSet，AnimatorSet可以指定多个ObjectAnimator的执行各种顺序，这种方法要更灵活一些
                PropertyValuesHolder scaleXAnimation = PropertyValuesHolder.ofFloat("scaleX", mAttributeScaleFormX, mAttributeScaleToX);
                PropertyValuesHolder scaleYAnimation = PropertyValuesHolder.ofFloat("scaleY", mAttributeScaleFromY, mAttributeScaleToY);
                ObjectAnimator scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(mIvAttributeSetArrow, scaleXAnimation, scaleYAnimation);
                if (mAttributeScaleToX < 1.0f) {
                    mAttributeScaleFormX -= 0.3f;
                    mAttributeScaleToX += 0.3f;
                    mAttributeScaleFromY -= 0.3f;
                    mAttributeScaleToY += 0.3f;
                } else {
                    mAttributeScaleFormX += 0.3f;
                    mAttributeScaleToX -= 0.3f;
                    mAttributeScaleFromY += 0.3f;
                    mAttributeScaleToY -= 0.3f;
                }
                scaleAnimation.setInterpolator(new OvershootInterpolator());//添加插值器，OvershootInterpolator有将动画回弹的效果
                scaleAnimation.setDuration(800).start();
                //AnimationSet和AnimatorSet的区别
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(scaleAnimation);
//                animatorSet.setDuration(800);
//                animatorSet.start();
            }
        });
        //属性动画--透明度动画
        mTvAttributeAlphaAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "alpha", mAttributeAlphaFrom, mAttributeAlphaTo);
                if (mAttributeAlphaTo < 1.0f) {
                    mAttributeAlphaFrom -= 0.6;
                    mAttributeAlphaTo += 0.6;
                } else {
                    mAttributeAlphaFrom += 0.6;
                    mAttributeAlphaTo -= 0.6;
                }
                alphaAnimation.setDuration(800).start();
            }
        });
        //属性动画--组合动画（旋转，缩放，透明度）
        mTvAttributeGroupAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "rotation", mAttributeRotateFrom, mAttributeRotateTo);
                ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "scaleX", mAttributeScaleFormX, mAttributeScaleToX);
                ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "scaleY", mAttributeScaleFromY, mAttributeScaleToY);
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mIvAttributeSetArrow, "alpha", mAttributeAlphaFrom, mAttributeAlphaTo);
                mAttributeRotateFrom += 100.0f;
                mAttributeRotateTo += 100.0f;
                if (mAttributeScaleToX < 1.0f) {
                    mAttributeScaleFormX -= 0.3f;
                    mAttributeScaleToX += 0.3f;
                    mAttributeScaleFromY -= 0.3f;
                    mAttributeScaleToY += 0.3f;
                } else {
                    mAttributeScaleFormX += 0.3f;
                    mAttributeScaleToX -= 0.3f;
                    mAttributeScaleFromY += 0.3f;
                    mAttributeScaleToY -= 0.3f;
                }
                if (mAttributeAlphaTo < 1.0f) {
                    mAttributeAlphaFrom -= 0.6;
                    mAttributeAlphaTo += 0.6;
                } else {
                    mAttributeAlphaFrom += 0.6;
                    mAttributeAlphaTo -= 0.6;
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(1200);
                animatorSet.playTogether(scaleXAnimation, scaleYAnimation);
                animatorSet.play(rotate).after(alphaAnimation);
                animatorSet.start();
            }
        });
    }

    /**
     * 补间动画
     */
    private void normalAnimation() {
        //旋转动画
        mTvRotateAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnimation = new RotateAnimation(mRotateForm, mRotateTo, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setFillAfter(true);//保存上一次旋转后的位置，从上一次的位置开始旋转
                rotateAnimation.setDuration(600);
                mRotateForm += 90;
                mRotateTo += 90;
                mIvRotateArrow.startAnimation(rotateAnimation);
                rotateAnimation.setAnimationListener(new MyAnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        super.onAnimationStart(animation);
                        mTvRotateAnimation.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        mTvRotateAnimation.setEnabled(true);
                    }
                });
            }
        });
        //移动动画
        mTvTranslateAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation translateAnimation = new TranslateAnimation(mTranslateForm, mTranslateTo, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF);
                translateAnimation.setFillAfter(true);//保存上一次移动后的位置，从上一次的位置开始移动
                translateAnimation.setDuration(800);
                if (mTranslateSize > mTranslateTo) {
                    mTranslateForm = mTranslateTo;
                    mTranslateTo += 90.0f;
                } else {
                    mTranslateForm = 0.0f;
                    mTranslateTo = 90.0f;
                }
                mIvTranslateArrow.startAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new MyAnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        super.onAnimationStart(animation);
                        mTvTranslateAnimation.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        mTvTranslateAnimation.setEnabled(true);
                    }
                });
            }
        });
        mIvTranslateArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mActivity, "TranslateAnimation补间动画，位置不变");
            }
        });
        mTvAnimationSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationSet = new AnimationSet(true);
//                //缩放动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(mScaleFromX, mScaleToX, mScaleFromY, mScaleToY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                if (mScaleToX < 1) {
                    mScaleFromX -= 0.2f;
                    mScaleToX += 0.2f;
                    mScaleFromY -= 0.2f;
                    mScaleToY += 0.2f;
                } else {
                    mScaleFromX += 0.2f;
                    mScaleToX -= 0.2f;
                    mScaleFromY += 0.2f;
                    mScaleToY -= 0.2f;
                }
                //透明度动画
                AlphaAnimation alphaAnimation = new AlphaAnimation(mAlphaForm, mAlphaTo);
                if (mAlphaForm < 1) {
                    mAlphaTo -= 0.7f;
                    mAlphaForm += 0.7;
                } else {
                    mAlphaTo += 0.7f;
                    mAlphaForm -= 0.7;
                }
                //旋转动画
                RotateAnimation rotateAnimation = new RotateAnimation(mRotateStart, mRotateEnd, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mRotateStart += 90.0f;
                mRotateEnd += 90.0f;
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(rotateAnimation);
                animationSet.setDuration(1000);
                animationSet.setFillAfter(true);
                mIvAnimationSet.startAnimation(animationSet);
                animationSet.setAnimationListener(new MyAnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        super.onAnimationStart(animation);
                        mTvAnimationSet.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        mTvAnimationSet.setEnabled(true);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyActivity() {

    }

    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
