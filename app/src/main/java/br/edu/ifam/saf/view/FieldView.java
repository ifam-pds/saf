package br.edu.ifam.saf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.edu.ifam.saf.R;

public class FieldView extends LinearLayout {

    private EditText editText;

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FieldView, 0, 0);

        String hintText = a.getString(R.styleable.FieldView_hint);
        int inputType = a.getInteger(R.styleable.FieldView_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        Drawable iconDrawable = a.getDrawable(R.styleable.FieldView_icon);
        Boolean border = a.getBoolean(R.styleable.FieldView_border, true);

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

        editText.setInputType(baseFormat | inputType);

        editText.setHint(hintText);


    }

    public FieldView(Context context) {
        this(context, null);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getText() {
        return editText.getText().toString();
    }

}