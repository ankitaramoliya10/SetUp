package com.seatup.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.seatup.R;

public class DTextView extends android.support.v7.widget.AppCompatTextView {

    public DTextView(Context context) {
        super(context);

        TextViewHelper.setTypeface(context, this);
    }

    public DTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextViewHelper.setTypeface(context, this, attrs);
    }

    public static class TextViewHelper {
        private static Typeface typeface = null;
        private static int type;

        public static void setTypeface(Context context, TextView tvSelected, AttributeSet attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DTextView);
            try {
                type = ta.getInt(R.styleable.DTextView_textFontFace, 1);
                typeface = FontUtils.fontName(context, type);
            } finally {
                ta.recycle();
            }
            tvSelected.setTypeface(typeface);
        }

        public static void setTypeface(Context context, TextView tvSelected) {
            typeface = FontUtils.createTypeface(context, false);
            tvSelected.setTypeface(typeface);
        }
    }
}