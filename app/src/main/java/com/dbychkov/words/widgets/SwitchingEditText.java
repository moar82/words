package com.dbychkov.words.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.dbychkov.words.R;
import com.dbychkov.words.util.KeyboardUtils;

/**
 * View for saving textual information
 */
public class SwitchingEditText extends ViewSwitcher implements View.OnClickListener {

    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final int DEFAULT_KEYBOARD_DELAY_DURATION = 200;
    private static final int DEFAULT_TEXT_SIZE_SP = 16;

    private int animationDuration = DEFAULT_ANIMATION_DURATION;
    private int keyboardDelay = DEFAULT_KEYBOARD_DELAY_DURATION;
    private float textSize = 0;

    private AnimationSet slideInRight;
    private AnimationSet slideOutLeft;
    private AnimationSet slideInLeft;
    private AnimationSet slideOutRight;
    private View inflatedView;
    private Button editButton;
    private Button saveButton;
    private TextView textView;
    private EditText editView;
    private OnItemSavedListener listener;

    public SwitchingEditText(Context context) {
        super(context);
        initView();
    }

    public SwitchingEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs);
        initView();
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.EditableTextField);
            animationDuration = styledAttrs.getInteger(R.styleable.EditableTextField_animationDuration, DEFAULT_ANIMATION_DURATION);
            keyboardDelay = styledAttrs.getInteger(R.styleable.EditableTextField_keyboardDelay, DEFAULT_KEYBOARD_DELAY_DURATION);
            textSize = styledAttrs.getDimensionPixelSize(R.styleable.EditableTextField_textSize, 0);

            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatedView = inflater.inflate(R.layout.view_switcher, this, true);
        initViews();
        setTextSize();
        initListeners();
        initAnimations();
    }

    private void initViews() {
        editButton = (Button) inflatedView.findViewById(R.id.edit);
        saveButton = (Button) inflatedView.findViewById(R.id.save);
        textView = (TextView) inflatedView.findViewById(R.id.text_view);
        editView = (EditText) inflatedView.findViewById(R.id.edit_view);
    }

    private void initListeners() {
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    private void initAnimations() {
        slideInRight = buildSlideInRightAnimationSet();
        slideOutLeft = buildSlideOutLeftAnimationSet();
        slideInLeft = buildSlideInLeftAnimationSet();
        slideOutRight = buildSlideOutRightAnimationSet();
    }

    private AnimationSet buildSlideInLeftAnimationSet() {
        return buildTranslateAndAlphaAnimationSet(-1, 0, 0, 1);
    }

    private AnimationSet buildSlideInRightAnimationSet() {
        return buildTranslateAndAlphaAnimationSet(1, 0, 0, 1);
    }

    private AnimationSet buildSlideOutLeftAnimationSet() {
        return buildTranslateAndAlphaAnimationSet(0, -1, 1, 0);
    }

    private AnimationSet buildSlideOutRightAnimationSet() {
        return buildTranslateAndAlphaAnimationSet(0, 1, 1, 0);
    }

    private AnimationSet buildTranslateAndAlphaAnimationSet(float fromXDelta, float toXDelta,
                                                            float fromAlpha, float toAlpha) {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromXDelta, Animation.RELATIVE_TO_PARENT, toXDelta,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0

        );
        translateAnimation.setDuration(animationDuration);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(animationDuration);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setFillAfter(true);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    public void setTextSize() {
        if (textSize > 0) {
            editButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            saveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            editView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        } else {
            editButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP);
            saveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP);
            editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP);
        }
    }

    public void setElementText(String text, String hint) {
        if (TextUtils.isEmpty(text) || text.equals(hint)){
            editView.setHint(hint);
            textView.setText(hint);
        } else{
            editView.setText(text);
            textView.setText(text);
        }
    }

    public void setListener(OnItemSavedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            setEditAnimation();
            showNext();
            showKeyboardForEditView();

        } else if (v.getId() == R.id.save) {
            setSaveAnimation();
            String newText = editView.getText().toString();
            setElementText(newText, editView.getHint().toString());
            if (listener != null) {
                listener.onItemSaved(newText);
            }
            showNext();
            hideKeyboardForEditView();
        }
    }

    private void showKeyboardForEditView() {
        editView.postDelayed(new Runnable() {
            @Override
            public void run() {
                editView.setSelection(editView.getText().length());
                editView.requestFocus();
                KeyboardUtils.show(getContext());
            }
        }, keyboardDelay);
    }

    private void hideKeyboardForEditView() {
        editView.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.hide(getContext(), editView);
            }
        }, keyboardDelay);
    }

    private void setEditAnimation() {
        setInAnimation(slideInRight);
        setOutAnimation(slideOutLeft);
    }

    private void setSaveAnimation() {
        setInAnimation(slideInLeft);
        setOutAnimation(slideOutRight);
    }

    public interface OnItemSavedListener {
        void onItemSaved(String newValue);
    }
}