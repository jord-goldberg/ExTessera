package ny.gelato.extessera.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * Created by jord.goldberg on 2/14/17.
 */

public class SquareFrameLayout extends FrameLayout {


    public SquareFrameLayout(Context context) {
        super(context);
    }


    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }
}
