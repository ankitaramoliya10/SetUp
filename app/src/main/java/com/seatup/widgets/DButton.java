package com.seatup.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import com.seatup.R;

public class DButton extends AppCompatButton {


    public DButton(Context context) {
        super(context);
        TextViewHelper.setTypeface(context, this);
    }

    public DButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextViewHelper.setTypeface(context, this, attrs);

    }

    public static class TextViewHelper {

        private static Typeface typeface = null;
        private static int type;

        public static void setTypeface(Context context, Button btnSelected, AttributeSet attrs) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DTextView);
            try {
                type = ta.getInt(R.styleable.DTextView_textFontFace, 1);
                typeface = FontUtils.fontName(context, type);

            } finally {
                ta.recycle();
            }
            btnSelected.setTypeface(typeface);
        }

        public static void setTypeface(Context context, Button btnSelected) {
            typeface = FontUtils.createTypeface(context, false);
            btnSelected.setTypeface(typeface);
        }

    }
}