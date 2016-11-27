package br.edu.ifam.saf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codetroopers.betterpickers.hmspicker.HmsPickerBuilder;
import com.codetroopers.betterpickers.hmspicker.HmsPickerDialogFragment;

import java.util.Calendar;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.util.TimeFormatter;

public class FieldView extends LinearLayout implements HmsPickerDialogFragment.HmsPickerDialogHandlerV2 {
    public static final int TYPE_TIME_PICKER = 0x10000000;

    private EditText editText;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FieldView, 0, 0);

        String hintText = a.getString(R.styleable.FieldView_hint);
        int inputType = a.getInteger(R.styleable.FieldView_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        Drawable iconDrawable = a.getDrawable(R.styleable.FieldView_icon);
        Boolean border = a.getBoolean(R.styleable.FieldView_border, true);

        boolean enabled = a.getBoolean(R.styleable.FieldView_enabled, true);

        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.field_view, this, true);

        if (border) {
            setBackgroundResource(R.drawable.field_view_background);
        }
        int pad = context.getResources().getDimensionPixelSize(R.dimen.field_padding);

        setPadding(pad, pad, pad, pad);

        ImageView icon = (ImageView) getChildAt(0);
        icon.setImageDrawable(iconDrawable);

        editText = (EditText) getChildAt(1);

        int baseFormat;

        switch (inputType) {
            case EditorInfo.TYPE_DATETIME_VARIATION_DATE:
                baseFormat = EditorInfo.TYPE_CLASS_DATETIME;
                break;
            case EditorInfo.TYPE_NUMBER_VARIATION_NORMAL:
                baseFormat = EditorInfo.TYPE_CLASS_NUMBER;
                break;
            default:
                baseFormat = EditorInfo.TYPE_CLASS_TEXT;
        }
        if (inputType == TYPE_TIME_PICKER) {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            editText.setDuplicateParentStateEnabled(true);

            editText.setMovementMethod(null);
            editText.setKeyListener(null);

            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    HmsPickerBuilder hpb = new HmsPickerBuilder()
                            .addHmsPickerDialogHandler(FieldView.this)
                            .setFragmentManager(((AppCompatActivity) getContext()).getSupportFragmentManager())
                            .setStyleResId(R.style.BetterPickersDialogFragment);
                    hpb.show();
                }
            });

        } else {
            editText.setInputType(baseFormat | inputType);
        }


        editText.setHint(hintText);

        editText.setEnabled(enabled);

    }

    public FieldView(Context context) {
        this(context, null);
    }

    public void addTextChangeListener(TextWatcherAdapter textWatcherAdapter) {
        editText.addTextChangedListener(textWatcherAdapter);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(String text) {
        editText.setText(text);
    }

    @Override
    public void onDialogHmsSet(int reference, boolean isNegative, int hours, int minutes, int seconds) {

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 8);
        editText.setText(TimeFormatter.format(hours, minutes));

    }

    public static class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    }

}