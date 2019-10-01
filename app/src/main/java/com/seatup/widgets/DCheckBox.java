package com.seatup.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.seatup.R;

public class DCheckBox extends AppCompatCheckBox {

    public DCheckBox(Context context) {
        super(context);
        TextViewHelper.setTypeface(context, this);
    }

    public DCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextViewHelper.setTypeface(context, this, attrs);
    }

    public static class TextViewHelper {

        private static Typeface typeface = null;
        private static int type;

        public static void setTypeface(Context context, CheckBox chSelected, AttributeSet attrs) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DTextView);
            try {
                type = ta.getInt(R.styleable.DTextView_textFontFace, 1);

                typeface = FontUtils.fontName(context, type);


            } finally {
                ta.recycle();
            }

            chSelected.setTypeface(typeface);
        }

        public static void setTypeface(Context context, CheckBox chSelected) {
            typeface = FontUtils.createTypeface(context, false);
            chSelected.setTypeface(typeface);
        }

    }
}
