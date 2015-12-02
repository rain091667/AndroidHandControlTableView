package viewlib.rainhong.handctviewlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HandControlTableView extends View {
    private boolean TouchEventFlag;
    private int ZoneLevelQuantity;
    private OnLevelDegreeChangedListener LevelDegreeListener;
    private boolean ReturnOriginPosition;
    private boolean XYAxisDrawVisible;
    private boolean IndicatorDrawVisible;
    private boolean LineDrawVisible;
    private boolean CircleDrawVisible;

    private int ZoneRange;
    private int Circle_radius;
    private float Origin_Position;
    private float ViewLength;
    private float OnTouch_X;
    private float OnTouch_Y;
    private double Math_Degree;
    private int Math_DegreeResult;
    private double Math_Circle_X_Radius, Math_Circle_Y_Radius;
    private double Math_Circle_Result, Math_Circle_Compare;
    private boolean Math_LevelFlag;
    private Paint CirclePaint;
    private Paint LinePaint;
    private Paint IndicatorPaint;
    private Paint XYAxisPaint;

    public interface OnLevelDegreeChangedListener {
        void onLevelDegreeChanged(int level, int degree);
    }

    public HandControlTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    private void Init() {
        this.TouchEventFlag = false;
        this.ZoneLevelQuantity = 1;
        this.LevelDegreeListener = null;
        this.ZoneRange = 0;
        this.Math_Circle_X_Radius = this.Math_Circle_Y_Radius = 0;
        this.Math_Circle_Result = this.Math_Circle_Compare = 0;
        this.Math_Degree = 0;
        this.Math_DegreeResult = 0;
        this.ReturnOriginPosition = true;
        this.Math_LevelFlag = false;
        this.XYAxisDrawVisible = true;
        this.IndicatorDrawVisible = true;
        this.LineDrawVisible = true;
        this.CircleDrawVisible = true;
        this.CirclePaint = new Paint();
        setCircleMode(CircleMode.CircleSTROKE);
        setCircleLineStrokeWidth(3);
        setCircleColor(Color.BLACK);
        this.LinePaint = new Paint();
        setLineStrokeWidth(3);
        setLineColor(Color.BLACK);
        this.IndicatorPaint = new Paint();
        this.IndicatorPaint.setStrokeWidth(3);
        setIndicatorColor(Color.RED);
        this.XYAxisPaint = new Paint();
        this.XYAxisPaint.setStrokeWidth(3);
        setXYAxisColor(Color.BLACK);
    }

    /**
     * Set Hand Control Table View TouchEvent.
     * @param Flag Set Touch Event Enable or not. Default: false.
     */
    public void setTouchEventEnabled(boolean Flag) {
        this.TouchEventFlag = Flag;
    }

    /**
     * Set Hand Control Table View Value Change Listener.
     * @param l Set Value Change Listener.
     */
    public void setOnLevelDegreeChangedListener(OnLevelDegreeChangedListener l) {
        this.LevelDegreeListener = l;
    }

    /**
     * Set Hand Control Table View ZoneLevelQuantity.
     * @param level Set ZoneLevelQuantity. Default: 1. Data Range: 1 - unlimited.
     */
    public void setZoneLevelQuantity(int level) {
        if(level > 0) {
            this.ZoneLevelQuantity = level;
            this.ZoneRange = (int)((ViewLength/2)/(ZoneLevelQuantity + 1));
            this.Circle_radius = ZoneRange;
            invalidate();
        }
    }

    /**
     * Set Hand Control Table View Circle Style.
     * @param mode Set Circle Style. Default: CircleSTROKE Type: CircleFILL, CircleSTROKE, CircleFILL_AND_STROKE.
     */
    public void setCircleMode(CircleMode mode) {
        if(mode == CircleMode.CircleFILL) this.CirclePaint.setStyle(Paint.Style.FILL);
        else if(mode == CircleMode.CircleSTROKE) this.CirclePaint.setStyle(Paint.Style.STROKE);
        else if(mode == CircleMode.CircleFILL_AND_STROKE) this.CirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * Set Hand Control Table View Circle Line Stroke Width.
     * @param StrokeWidth Set Circle Line Stroke Width. Default: 3. Data Range: 1 - unlimited.
     */
    public void setCircleLineStrokeWidth(float StrokeWidth) {
        if(StrokeWidth > 0) {
            this.CirclePaint.setStrokeWidth(StrokeWidth);
        }
    }

    /**
     * Set Hand Control Table View Circle Color.
     * @param color Set Circle Color. Default: BLACK
     */
    public void setCircleColor(int color) {
        this.CirclePaint.setColor(color);
    }

    /**
     * Set Hand Control Table View Line Stroke Width.
     * @param StrokeWidth Set Line Stroke Width. Default: 3. Data Range: 1 - unlimited.
     */
    public void setLineStrokeWidth(float StrokeWidth) {
        if(StrokeWidth > 0) {
            this.LinePaint.setStrokeWidth(StrokeWidth);
        }
    }

    /**
     * Set Hand Control Table View Line Color.
     * @param color Set Line Color. Default: BLACK
     */
    public void setLineColor(int color) {
        this.LinePaint.setColor(color);
    }

    /**
     * Set Hand Control Table View Indicator Color.
     * @param color Set Indicator Color. Default: RED
     */
    public void setIndicatorColor(int color) {
        this.IndicatorPaint.setColor(color);
    }

    /**
     * Set Hand Control Table View XYAxis Color.
     * @param color Set XYAxis Color. Default: BLACK
     */
    public void setXYAxisColor(int color) {
        this.XYAxisPaint.setColor(color);
    }

    /**
     * Set Hand Control Table View Touch Position Return Origin Position Event Enabled or not.
     * @param Enabled Touch Position Return Origin Position Enabled or not. Default: true
     */
    public void setReturnOriginEnabled(boolean Enabled) {
        this.ReturnOriginPosition = Enabled;
    }

    /**
     * Set Hand Control Table View XY Axis Visible.
     * @param Flag Visible true or false. Default: true.
     */
    public void setXAxisDrawVisible(boolean Flag) {
        this.XYAxisDrawVisible = Flag;
    }

    /**
     * Set Hand Control Table View Indicator Visible.
     * @param Flag Visible true or false. Default: true.
     */
    public void setIndicatorDrawVisible(boolean Flag) {
        this.IndicatorDrawVisible = Flag;
    }

    /**
     * Set Hand Control Table View Line Visible.
     * @param Flag Visible true or false. Default: true.
     */
    public void setLineDrawVisible(boolean Flag) {
        this.LineDrawVisible = Flag;
    }

    /**
     * Set Hand Control Table View Circle Visible.
     * @param Flag Visible true or false. Default: true.
     */
    public void setCircleDrawVisible(boolean Flag) {
        this.CircleDrawVisible = Flag;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.ViewLength = Math.min(w,h);
        this.Origin_Position = ViewLength/2;
        this.ZoneRange = (int)((ViewLength/2)/(ZoneLevelQuantity + 1));
        this.Circle_radius = ZoneRange;
        this.OnTouch_X = Origin_Position;
        this.OnTouch_Y = Origin_Position;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void ActionLevelDegree(float x, float y) {
        if(LevelDegreeListener != null) {
            Math_Degree = Math.toDegrees(Math.atan2(x - Origin_Position, y - Origin_Position));
            if(Math_Degree > 0.0) Math_DegreeResult = 180 - (int)Math_Degree;
            else Math_DegreeResult = 180 + (int)Math.abs(Math_Degree);

            Math_LevelFlag = false;
            Math_Circle_X_Radius = Math.pow(Math.abs(x - Origin_Position), 2);
            Math_Circle_Y_Radius = Math.pow(Math.abs(y - Origin_Position), 2);
            Math_Circle_Result = Math_Circle_X_Radius + Math_Circle_Y_Radius;
            for(int i=ZoneLevelQuantity; i>0; i--) {
                Math_Circle_Compare = Math.pow(Circle_radius*i, 2);
                if(Math_Circle_Result > Math_Circle_Compare) {
                    LevelDegreeListener.onLevelDegreeChanged(i, Math_DegreeResult);
                    Math_LevelFlag = true;
                    break;
                }
            }
            if(!Math_LevelFlag) LevelDegreeListener.onLevelDegreeChanged(0, Math_DegreeResult);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(CircleDrawVisible)
            for(int i=1;i<=ZoneLevelQuantity;i++)
                canvas.drawCircle(Origin_Position, Origin_Position, Circle_radius*i, CirclePaint);

        if(LineDrawVisible)
            canvas.drawLine(Origin_Position, Origin_Position, OnTouch_X, OnTouch_Y, LinePaint);

        if(XYAxisDrawVisible) {
            canvas.drawLine(0, ViewLength / 2, ViewLength, ViewLength / 2, XYAxisPaint);
            canvas.drawLine(ViewLength / 2, 0, ViewLength / 2, ViewLength, XYAxisPaint);
        }

        if(IndicatorDrawVisible) {
            canvas.drawLine(OnTouch_X - 10, OnTouch_Y, OnTouch_X + 10, OnTouch_Y, IndicatorPaint);
            canvas.drawLine(OnTouch_X, OnTouch_Y - 10, OnTouch_X, OnTouch_Y + 10, IndicatorPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!TouchEventFlag) return true;
        OnTouch_X = event.getX();
        OnTouch_Y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                if(ReturnOriginPosition) {
                    OnTouch_X = Origin_Position;
                    OnTouch_Y = Origin_Position;
                }
                break;
            }
        }
        ActionLevelDegree(OnTouch_X, OnTouch_Y);
        invalidate();
        return true;
    }
}
