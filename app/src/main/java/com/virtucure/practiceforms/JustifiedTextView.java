package com.virtucure.practiceforms;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by AJITI on 6/20/2016.
 */
public class JustifiedTextView extends TextView implements Justify.Justified {

    private static final int MAX_SPANS = 512;

    private boolean mMeasuring = false;
    private Typeface mTypeface = null;
    private float mTextSize = 0f;
    private float mTextScaleX = 0f;
    private boolean mFakeBold = false;
    private int mWidth = 0;

    private int[] mSpanStarts = new int[MAX_SPANS];
    private int[] mSpanEnds = new int[MAX_SPANS];
    private Justify.ScaleSpan[] mScaleSpans = new Justify.ScaleSpan[MAX_SPANS];

    public JustifiedTextView(final @NotNull Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(getMovementMethod() == null) super.setMovementMethod(new LinkMovementMethod());
    }

    public JustifiedTextView(final @NotNull Context context, final AttributeSet attrs) {
        super(context, attrs);
        if(getMovementMethod() == null) super.setMovementMethod(new LinkMovementMethod());
    }

    public JustifiedTextView(final @NotNull Context context) {
        super(context);
        super.setMovementMethod(new LinkMovementMethod());
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!mMeasuring) {
            final Typeface typeface = getTypeface();
            final float textSize = getTextSize();
            final float textScaleX = getTextScaleX();
            final boolean fakeBold = getPaint().isFakeBoldText();

            if(mTypeface != typeface || mTextSize != textSize || mTextScaleX != textScaleX || mFakeBold != fakeBold) {
                final int width = MeasureSpec.getSize(widthMeasureSpec);
                if(width > 0 && width != mWidth) {
                    mTypeface = typeface;
                    mTextSize = textSize;
                    mTextScaleX = textScaleX;
                    mFakeBold = fakeBold;
                    mWidth = width;
                    mMeasuring = true;
                    try {
                        Justify.setupScaleSpans(this, mSpanStarts, mSpanEnds, mScaleSpans);
                    }
                    finally {
                        mMeasuring = false;
                    }
                }
            }
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        final Layout layout = getLayout();
        if(layout != null) {
            Justify.setupScaleSpans(this, mSpanStarts, mSpanEnds, mScaleSpans);
        }
    }

    @Override
    @NonNull
    public TextView getTextView() {
        return this;
    }

    @Override
    public float getMaxProportion() {
        return Justify.DEFAULT_MAX_PROPORTION;
    }


    /*    private int lineY;
    private int viewWidth;

    public JustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        textPaint.setTypeface(getTypeface());
        viewWidth = getMeasuredWidth();
        String text = getText().toString();
        lineY = 0;
        lineY += getTextSize();
        Layout layout = getLayout();
        for(int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String line = text.substring(lineStart, lineEnd);
            float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
            if(needScale(line)) {
                drawScaledText(canvas, lineStart, line, width);
            }
            else {
                canvas.drawText(line, 0, lineY, textPaint);
            }
            lineY += getLineHeight();
        }

    }

    private void drawScaledText(Canvas canvas, int lineStart, String line, float lineWidth) {
        float x = 0;
        if(isFirstLineOfParagraph(lineStart, line)) {
            String blank = " ";
            canvas.drawText(blank, x, lineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blank, getPaint());
            x += bw;
            line = line.substring(3);
        }
        float d = (viewWidth - lineWidth) / line.length() - 1;
        for(int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, lineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' ' && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if(line==null || line.length() == 0)
            return false;
        else
            return line.charAt(line.length() - 1) != '\n';
    }*/
}