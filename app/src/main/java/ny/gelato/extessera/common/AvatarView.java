package ny.gelato.extessera.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import java.io.File;

import ny.gelato.extessera.R;

import static android.support.design.R.attr.borderWidth;


public class AvatarView extends AppCompatImageView {

    /*
    * Path of them image to be clipped (to be shown)
    * */
    Path clipPath;

    /*
    * Place holder drawable (with background color and initials)
    * */
    Drawable drawable;

    /*
    * Contains initials of the member
    * */
    String text = "?";

    /*
    * Used to set size and color of the member initials
    * */
    TextPaint textPaint;

    /*
    * Used as background of the initials with avatar specific color
    * */
    Paint backgroundPaint;

    /*
    * Shape to be drawn
    * */
    int shape;

    /*
    * Constants to define shape
    * */
    protected static final int CIRCLE = 0;
    protected static final int SQUARE = 1;

    /*
    * User whose avatar should be displayed
    * */
    AvatarInterface avatar;

    /*
    * Bounds of the canvas in float
    * Used to set bounds of member initial and background
    * */
    RectF rectF;

    int border;


    public AvatarView(Context context) {
        super(context);
    }


    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(attrs);
        init(context);
    }


    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(attrs);
        init(context);
    }


    private void getAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AvatarView,
                0, 0);

        try {

            /*
            * Get the shape and set shape field accordingly
            * */
            String avatarShape = a.getString(R.styleable.AvatarView_avatar_shape);

            /*
            * If the attribute is not specified, consider circle shape
            * */
            if (avatarShape == null) {
                shape = SQUARE;
            } else {
                if (getContext().getString(R.string.square).equals(avatarShape)) {
                    shape = SQUARE;
                } else {
                    shape = CIRCLE;
                }
            }
        } finally {
            a.recycle();
        }
    }


    /*
    * Initialize fields
    * */
    protected void init(Context context) {

        clipPath = new Path();
        rectF = new RectF();

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
    }


    /*
    * Get User object and
     * set values based on the avatar
    * This is the only exposed method to the developer
    * */
    public void setViewModel(AvatarInterface avatar) {
        if (this.avatar != null && !this.avatar.equals(avatar)) {
            clipPath = new Path();
            if (avatar.isInspired()) {
                rectF = new RectF(rectF.left + border, rectF.top + border, rectF.right - border, rectF.bottom - border);
            } else
                rectF = new RectF(rectF.left - border, rectF.top - border, rectF.right + border, rectF.bottom + border);
        }
        this.avatar = avatar;
        setValues();
        invalidate();
    }


    /*
    * Set avatar specific fields in here
    * */
    private void setValues() {

        if (shape == SQUARE)
            setBackground(getResources().getDrawable(R.drawable.avatar_background_square));
        else setBackground(getResources().getDrawable(R.drawable.avatar_background_circle));

        /*
        * avatar specific color for initial background
        * */
        backgroundPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));

        /*
        * Initials of member
        * */
        text = avatar.getName().isEmpty() ? "?" : avatar.getName().substring(0, 1);

        setDrawable();

        if (!avatar.getImagePath().isEmpty()) {
            Glide.with(getContext())
                    .load(new File(avatar.getImagePath()))
                    .placeholder(drawable)
                    .centerCrop()
                    .into(this);

        } else if (!avatar.getImageUrl().isEmpty()) {
            Glide.with(getContext())
                    .load(avatar.getImageUrl())
                    .placeholder(drawable)
                    .centerCrop()
                    .into(this);
        } else {
            setImageDrawable(drawable);
        }
    }


    /*
    * Create placeholder drawable
    * */
    private void setDrawable() {
        drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

                float centerX = canvas.getWidth() * 0.5f;
                float centerY = canvas.getHeight() * 0.5f;

        /*
        * To draw text
        * */
                float textWidth = textPaint.measureText(text) * 0.5f;
                float textBaseLineHeight = textPaint.getFontMetrics().ascent * -0.4f;

            /*
            * Draw the background color before drawing initials text
            * */
                if (shape == SQUARE) {
                    canvas.drawRoundRect(rectF, 0, 0, backgroundPaint);
                } else {
                    canvas.drawCircle(centerX,
                            centerY,
                            Math.max(canvas.getWidth() / 2, textWidth / 2),
                            backgroundPaint);
                }

            /*
            * Draw the text above the background color
            * */
                canvas.drawText(text, centerX - textWidth, centerY + textBaseLineHeight, textPaint);
            }


            @Override
            public void setAlpha(int alpha) {

            }


            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }


            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };
    }


    /*
     * Set the canvas bounds here
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        border = (int) (2 * scale + 0.5f);
        if (avatar != null && avatar.isInspired()) {
            rectF.set(border, border, width - border, width - border);
        } else rectF.set(0, 0, width, width);
        textPaint.setTextSize(width / 5f * getResources().getDisplayMetrics().scaledDensity);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (shape == SQUARE) {
            canvas.drawRoundRect(rectF, 0, 0, backgroundPaint);
            clipPath.addRoundRect(rectF, 0, 0, Path.Direction.CW);
        } else {
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2) - borderWidth, backgroundPaint);
            clipPath.addCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2), Path.Direction.CW);
        }
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
