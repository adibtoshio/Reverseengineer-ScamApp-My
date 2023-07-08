package q.rorbin.badgeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class QBadgeView extends View implements Badge {
  protected ViewGroup mActivityRoot;
  
  protected BadgeAnimator mAnimator;
  
  protected float mBackgroundBorderWidth;
  
  protected Paint mBadgeBackgroundBorderPaint;
  
  protected Paint mBadgeBackgroundPaint;
  
  protected RectF mBadgeBackgroundRect;
  
  protected PointF mBadgeCenter;
  
  protected int mBadgeGravity;
  
  protected int mBadgeNumber;
  
  protected float mBadgePadding;
  
  protected String mBadgeText;
  
  protected Paint.FontMetrics mBadgeTextFontMetrics;
  
  protected TextPaint mBadgeTextPaint;
  
  protected RectF mBadgeTextRect;
  
  protected float mBadgeTextSize;
  
  protected Bitmap mBitmapClip;
  
  protected int mColorBackground;
  
  protected int mColorBackgroundBorder;
  
  protected int mColorBadgeText;
  
  protected PointF mControlPoint;
  
  protected float mDefalutRadius;
  
  protected PointF mDragCenter;
  
  protected boolean mDragOutOfRange;
  
  protected Path mDragPath;
  
  protected int mDragQuadrant;
  
  protected Badge.OnDragStateChangedListener mDragStateChangedListener;
  
  protected boolean mDraggable;
  
  protected boolean mDragging;
  
  protected Drawable mDrawableBackground;
  
  protected boolean mDrawableBackgroundClip;
  
  protected boolean mExact;
  
  protected float mFinalDragDistance;
  
  protected float mGravityOffsetX;
  
  protected float mGravityOffsetY;
  
  protected int mHeight;
  
  protected List<PointF> mInnertangentPoints;
  
  protected PointF mRowBadgeCenter;
  
  protected boolean mShowShadow;
  
  protected View mTargetView;
  
  protected int mWidth;
  
  public QBadgeView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  QBadgeView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  QBadgeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void createClipLayer() {
    if (this.mBadgeText == null)
      return; 
    if (!this.mDrawableBackgroundClip)
      return; 
    if (this.mBitmapClip != null && !this.mBitmapClip.isRecycled())
      this.mBitmapClip.recycle(); 
    float f = getBadgeCircleRadius();
    if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
      this.mBitmapClip = Bitmap.createBitmap((int)f * 2, (int)f * 2, Bitmap.Config.ARGB_4444);
      Canvas canvas1 = new Canvas(this.mBitmapClip);
      canvas1.drawCircle(canvas1.getWidth() / 2.0F, canvas1.getHeight() / 2.0F, canvas1.getWidth() / 2.0F, this.mBadgeBackgroundPaint);
      return;
    } 
    this.mBitmapClip = Bitmap.createBitmap((int)(this.mBadgeTextRect.width() + this.mBadgePadding * 2), (int)(this.mBadgeTextRect.height() + this.mBadgePadding), Bitmap.Config.ARGB_4444);
    Canvas canvas = new Canvas(this.mBitmapClip);
    if (Build.VERSION.SDK_INT >= 21) {
      canvas.drawRoundRect(false, false, canvas.getWidth(), canvas.getHeight(), canvas.getHeight() / 2.0F, canvas.getHeight() / 2.0F, this.mBadgeBackgroundPaint);
      return;
    } 
    canvas.drawRoundRect(new RectF(false, false, canvas.getWidth(), canvas.getHeight()), canvas.getHeight() / 2.0F, canvas.getHeight() / 2.0F, this.mBadgeBackgroundPaint);
  }
  
  private void drawBadge(Canvas paramCanvas, PointF paramPointF, float paramFloat) {
    if (paramPointF.x == -1000 && paramPointF.y == -1000)
      return; 
    if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
      this.mBadgeBackgroundRect.left = paramPointF.x - (int)paramFloat;
      this.mBadgeBackgroundRect.top = paramPointF.y - (int)paramFloat;
      this.mBadgeBackgroundRect.right = paramPointF.x + (int)paramFloat;
      this.mBadgeBackgroundRect.bottom = paramPointF.y + (int)paramFloat;
      if (this.mDrawableBackground != null) {
        drawBadgeBackground(paramCanvas);
      } else {
        paramCanvas.drawCircle(paramPointF.x, paramPointF.y, paramFloat, this.mBadgeBackgroundPaint);
        if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > false)
          paramCanvas.drawCircle(paramPointF.x, paramPointF.y, paramFloat, this.mBadgeBackgroundBorderPaint); 
      } 
    } else {
      this.mBadgeBackgroundRect.left = paramPointF.x - this.mBadgeTextRect.width() / 2.0F + this.mBadgePadding;
      this.mBadgeBackgroundRect.top = paramPointF.y - this.mBadgeTextRect.height() / 2.0F + this.mBadgePadding * 0.5F;
      this.mBadgeBackgroundRect.right = paramPointF.x + this.mBadgeTextRect.width() / 2.0F + this.mBadgePadding;
      this.mBadgeBackgroundRect.bottom = paramPointF.y + this.mBadgeTextRect.height() / 2.0F + this.mBadgePadding * 0.5F;
      paramFloat = this.mBadgeBackgroundRect.height() / 2.0F;
      if (this.mDrawableBackground != null) {
        drawBadgeBackground(paramCanvas);
      } else {
        paramCanvas.drawRoundRect(this.mBadgeBackgroundRect, paramFloat, paramFloat, this.mBadgeBackgroundPaint);
        if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > false)
          paramCanvas.drawRoundRect(this.mBadgeBackgroundRect, paramFloat, paramFloat, this.mBadgeBackgroundBorderPaint); 
      } 
    } 
    if (!this.mBadgeText.isEmpty())
      paramCanvas.drawText(this.mBadgeText, paramPointF.x, (this.mBadgeBackgroundRect.bottom + this.mBadgeBackgroundRect.top - this.mBadgeTextFontMetrics.bottom - this.mBadgeTextFontMetrics.top) / 2.0F, (Paint)this.mBadgeTextPaint); 
  }
  
  private void drawBadgeBackground(Canvas paramCanvas) {
    this.mBadgeBackgroundPaint.setShadowLayer(false, false, false, 0);
    int k = (int)this.mBadgeBackgroundRect.left;
    int m = (int)this.mBadgeBackgroundRect.top;
    int i = (int)this.mBadgeBackgroundRect.right;
    int j = (int)this.mBadgeBackgroundRect.bottom;
    if (this.mDrawableBackgroundClip) {
      i = k + this.mBitmapClip.getWidth();
      j = m + this.mBitmapClip.getHeight();
      paramCanvas.saveLayer(k, m, i, j, (Paint)null, 31);
    } 
    this.mDrawableBackground.setBounds(k, m, i, j);
    this.mDrawableBackground.draw(paramCanvas);
    if (this.mDrawableBackgroundClip) {
      this.mBadgeBackgroundPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
      paramCanvas.drawBitmap(this.mBitmapClip, k, m, this.mBadgeBackgroundPaint);
      paramCanvas.restore();
      this.mBadgeBackgroundPaint.setXfermode((Xfermode)null);
      if (this.mBadgeText.isEmpty() || this.mBadgeText.length() == 1) {
        paramCanvas.drawCircle(this.mBadgeBackgroundRect.centerX(), this.mBadgeBackgroundRect.centerY(), this.mBadgeBackgroundRect.width() / 2.0F, this.mBadgeBackgroundBorderPaint);
        return;
      } 
      paramCanvas.drawRoundRect(this.mBadgeBackgroundRect, this.mBadgeBackgroundRect.height() / 2, this.mBadgeBackgroundRect.height() / 2, this.mBadgeBackgroundBorderPaint);
      return;
    } 
    paramCanvas.drawRect(this.mBadgeBackgroundRect, this.mBadgeBackgroundBorderPaint);
  }
  
  private void drawDragging(Canvas paramCanvas, float paramFloat1, float paramFloat2) {
    Path.Direction direction;
    float f1 = this.mDragCenter.y;
    float f2 = this.mRowBadgeCenter.y;
    float f3 = this.mDragCenter.x - this.mRowBadgeCenter.x;
    this.mInnertangentPoints.clear();
    if (f3 != false) {
      double d = ((f1 - f2) / f3);
      d = -1 / d;
      MathUtil.getInnertangentPoints(this.mDragCenter, paramFloat2, new Double(d), this.mInnertangentPoints);
      MathUtil.getInnertangentPoints(this.mRowBadgeCenter, paramFloat1, new Double(d), this.mInnertangentPoints);
    } else {
      MathUtil.getInnertangentPoints(this.mDragCenter, paramFloat2, new Double(0.0D), this.mInnertangentPoints);
      MathUtil.getInnertangentPoints(this.mRowBadgeCenter, paramFloat1, new Double(0.0D), this.mInnertangentPoints);
    } 
    this.mDragPath.reset();
    Path path = this.mDragPath;
    paramFloat2 = this.mRowBadgeCenter.x;
    f1 = this.mRowBadgeCenter.y;
    if (this.mDragQuadrant == 1 || this.mDragQuadrant == 2) {
      direction = Path.Direction.CCW;
    } else {
      direction = Path.Direction.CW;
    } 
    path.addCircle(paramFloat2, f1, paramFloat1, direction);
    this.mControlPoint.x = (this.mRowBadgeCenter.x + this.mDragCenter.x) / 2.0F;
    this.mControlPoint.y = (this.mRowBadgeCenter.y + this.mDragCenter.y) / 2.0F;
    this.mDragPath.moveTo(((PointF)this.mInnertangentPoints.get(2)).x, ((PointF)this.mInnertangentPoints.get(2)).y);
    this.mDragPath.quadTo(this.mControlPoint.x, this.mControlPoint.y, ((PointF)this.mInnertangentPoints.get(0)).x, ((PointF)this.mInnertangentPoints.get(0)).y);
    this.mDragPath.lineTo(((PointF)this.mInnertangentPoints.get(1)).x, ((PointF)this.mInnertangentPoints.get(1)).y);
    this.mDragPath.quadTo(this.mControlPoint.x, this.mControlPoint.y, ((PointF)this.mInnertangentPoints.get(3)).x, ((PointF)this.mInnertangentPoints.get(3)).y);
    this.mDragPath.lineTo(((PointF)this.mInnertangentPoints.get(2)).x, ((PointF)this.mInnertangentPoints.get(2)).y);
    this.mDragPath.close();
    paramCanvas.drawPath(this.mDragPath, this.mBadgeBackgroundPaint);
    if (this.mColorBackgroundBorder != 0 && this.mBackgroundBorderWidth > false) {
      int i;
      this.mDragPath.reset();
      this.mDragPath.moveTo(((PointF)this.mInnertangentPoints.get(2)).x, ((PointF)this.mInnertangentPoints.get(2)).y);
      this.mDragPath.quadTo(this.mControlPoint.x, this.mControlPoint.y, ((PointF)this.mInnertangentPoints.get(0)).x, ((PointF)this.mInnertangentPoints.get(0)).y);
      this.mDragPath.moveTo(((PointF)this.mInnertangentPoints.get(1)).x, ((PointF)this.mInnertangentPoints.get(1)).y);
      this.mDragPath.quadTo(this.mControlPoint.x, this.mControlPoint.y, ((PointF)this.mInnertangentPoints.get(3)).x, ((PointF)this.mInnertangentPoints.get(3)).y);
      if (this.mDragQuadrant == 1 || this.mDragQuadrant == 2) {
        f1 = ((PointF)this.mInnertangentPoints.get(2)).x - this.mRowBadgeCenter.x;
        paramFloat2 = this.mRowBadgeCenter.y - ((PointF)this.mInnertangentPoints.get(2)).y;
      } else {
        f1 = ((PointF)this.mInnertangentPoints.get(3)).x - this.mRowBadgeCenter.x;
        paramFloat2 = this.mRowBadgeCenter.y - ((PointF)this.mInnertangentPoints.get(3)).y;
      } 
      f2 = 'Ũ';
      double d = Math.atan((paramFloat2 / f1));
      if (this.mDragQuadrant - 1 == 0) {
        i = 4;
      } else {
        i = this.mDragQuadrant - 1;
      } 
      paramFloat2 = f2 - (float)MathUtil.radianToAngle(MathUtil.getTanRadian(d, i));
      if (Build.VERSION.SDK_INT >= 21) {
        this.mDragPath.addArc(this.mRowBadgeCenter.x - paramFloat1, this.mRowBadgeCenter.y - paramFloat1, this.mRowBadgeCenter.x + paramFloat1, this.mRowBadgeCenter.y + paramFloat1, paramFloat2, '´');
      } else {
        this.mDragPath.addArc(new RectF(this.mRowBadgeCenter.x - paramFloat1, this.mRowBadgeCenter.y - paramFloat1, this.mRowBadgeCenter.x + paramFloat1, this.mRowBadgeCenter.y + paramFloat1), paramFloat2, '´');
      } 
      paramCanvas.drawPath(this.mDragPath, this.mBadgeBackgroundBorderPaint);
    } 
  }
  
  private void findActivityRoot(View paramView) {
    if (paramView.getParent() != null && paramView.getParent() instanceof View) {
      findActivityRoot((View)paramView.getParent());
      return;
    } 
    if (paramView instanceof ViewGroup) {
      this.mActivityRoot = (ViewGroup)paramView;
      return;
    } 
  }
  
  private void findBadgeCenter() {
    float f;
    if (this.mBadgeTextRect.height() > this.mBadgeTextRect.width()) {
      f = this.mBadgeTextRect.height();
    } else {
      f = this.mBadgeTextRect.width();
    } 
    switch (this.mBadgeGravity) {
      default:
        initRowBadgeCenter();
        return;
      case 8388659:
        this.mBadgeCenter.x = this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
        this.mBadgeCenter.y = this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 8388691:
        this.mBadgeCenter.x = this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
        this.mBadgeCenter.y = this.mHeight - this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 8388661:
        this.mBadgeCenter.x = this.mWidth - this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
        this.mBadgeCenter.y = this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 8388693:
        this.mBadgeCenter.x = this.mWidth - this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
        this.mBadgeCenter.y = this.mHeight - this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 17:
        this.mBadgeCenter.x = this.mWidth / 2.0F;
        this.mBadgeCenter.y = this.mHeight / 2.0F;
      case 49:
        this.mBadgeCenter.x = this.mWidth / 2.0F;
        this.mBadgeCenter.y = this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 81:
        this.mBadgeCenter.x = this.mWidth / 2.0F;
        this.mBadgeCenter.y = this.mHeight - this.mGravityOffsetY + this.mBadgePadding + this.mBadgeTextRect.height() / 2.0F;
      case 8388627:
        this.mBadgeCenter.x = this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
        this.mBadgeCenter.y = this.mHeight / 2.0F;
      case 8388629:
        break;
    } 
    this.mBadgeCenter.x = this.mWidth - this.mGravityOffsetX + this.mBadgePadding + f / 2.0F;
    this.mBadgeCenter.y = this.mHeight / 2.0F;
  }
  
  private float getBadgeCircleRadius() {
    return this.mBadgeText.isEmpty() ? this.mBadgePadding : ((this.mBadgeText.length() == 1) ? ((this.mBadgeTextRect.height() > this.mBadgeTextRect.width()) ? (this.mBadgeTextRect.height() / 2.0F + this.mBadgePadding * 0.5F) : (this.mBadgeTextRect.width() / 2.0F + this.mBadgePadding * 0.5F)) : (this.mBadgeBackgroundRect.height() / 2.0F));
  }
  
  private void init() {
    setLayerType(1, (Paint)null);
    this.mBadgeTextRect = new RectF();
    this.mBadgeBackgroundRect = new RectF();
    this.mDragPath = new Path();
    this.mBadgeCenter = new PointF();
    this.mDragCenter = new PointF();
    this.mRowBadgeCenter = new PointF();
    this.mControlPoint = new PointF();
    this.mInnertangentPoints = new ArrayList<PointF>();
    this.mBadgeTextPaint = new TextPaint();
    this.mBadgeTextPaint.setAntiAlias(true);
    this.mBadgeTextPaint.setSubpixelText(true);
    this.mBadgeTextPaint.setFakeBoldText(true);
    this.mBadgeTextPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    this.mBadgeBackgroundPaint = new Paint();
    this.mBadgeBackgroundPaint.setAntiAlias(true);
    this.mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
    this.mBadgeBackgroundBorderPaint = new Paint();
    this.mBadgeBackgroundBorderPaint.setAntiAlias(true);
    this.mBadgeBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
    this.mColorBackground = -1552832;
    this.mColorBadgeText = -1;
    this.mBadgeTextSize = DisplayUtil.dp2px(getContext(), 11);
    this.mBadgePadding = DisplayUtil.dp2px(getContext(), 5);
    this.mBadgeNumber = 0;
    this.mBadgeGravity = 8388661;
    this.mGravityOffsetX = DisplayUtil.dp2px(getContext(), 5);
    this.mGravityOffsetY = DisplayUtil.dp2px(getContext(), 5);
    this.mFinalDragDistance = DisplayUtil.dp2px(getContext(), 90);
    this.mShowShadow = true;
    this.mDrawableBackgroundClip = false;
    if (Build.VERSION.SDK_INT >= 21)
      setTranslationZ('Ϩ'); 
  }
  
  private void initPaints() {
    showShadowImpl(this.mShowShadow);
    this.mBadgeBackgroundPaint.setColor(this.mColorBackground);
    this.mBadgeBackgroundBorderPaint.setColor(this.mColorBackgroundBorder);
    this.mBadgeBackgroundBorderPaint.setStrokeWidth(this.mBackgroundBorderWidth);
    this.mBadgeTextPaint.setColor(this.mColorBadgeText);
    this.mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
  }
  
  private void initRowBadgeCenter() {
    int[] arrayOfInt = new int[2];
    getLocationOnScreen(arrayOfInt);
    this.mBadgeCenter.x += arrayOfInt[0];
    this.mBadgeCenter.y += arrayOfInt[1];
  }
  
  private void measureText() {
    this.mBadgeTextRect.left = false;
    this.mBadgeTextRect.top = false;
    if (TextUtils.isEmpty(this.mBadgeText)) {
      this.mBadgeTextRect.right = false;
      this.mBadgeTextRect.bottom = false;
    } else {
      this.mBadgeTextPaint.setTextSize(this.mBadgeTextSize);
      this.mBadgeTextRect.right = this.mBadgeTextPaint.measureText(this.mBadgeText);
      this.mBadgeTextFontMetrics = this.mBadgeTextPaint.getFontMetrics();
      this.mBadgeTextRect.bottom = this.mBadgeTextFontMetrics.descent - this.mBadgeTextFontMetrics.ascent;
    } 
    createClipLayer();
  }
  
  private void onPointerUp() {
    if (this.mDragOutOfRange) {
      animateHide(this.mDragCenter);
      updataListener(5);
      return;
    } 
    reset();
    updataListener(4);
  }
  
  private void showShadowImpl(boolean paramBoolean) {
    Paint paint;
    int i = DisplayUtil.dp2px(getContext(), true);
    int j = DisplayUtil.dp2px(getContext(), 1.5F);
    switch (this.mDragQuadrant) {
      default:
        paint = this.mBadgeBackgroundPaint;
        if (paramBoolean) {
          k = DisplayUtil.dp2px(getContext(), 2.0F);
        } else {
          break;
        } 
        paint.setShadowLayer(k, i, j, 855638016);
        return;
      case 1:
        i = DisplayUtil.dp2px(getContext(), true);
        j = DisplayUtil.dp2px(getContext(), -1.5F);
      case 2:
        i = DisplayUtil.dp2px(getContext(), -1);
        j = DisplayUtil.dp2px(getContext(), -1.5F);
      case 3:
        i = DisplayUtil.dp2px(getContext(), -1);
        j = DisplayUtil.dp2px(getContext(), 1.5F);
      case 4:
        i = DisplayUtil.dp2px(getContext(), true);
        j = DisplayUtil.dp2px(getContext(), 1.5F);
    } 
    int k = 0;
    paint.setShadowLayer(k, i, j, 855638016);
  }
  
  private void updataListener(int paramInt) {
    if (this.mDragStateChangedListener != null)
      this.mDragStateChangedListener.onDragStateChanged(paramInt, this, this.mTargetView); 
  }
  
  protected void animateHide(PointF paramPointF) {
    if (this.mBadgeText == null)
      return; 
    if (this.mAnimator == null || !this.mAnimator.isRunning()) {
      screenFromWindow(true);
      this.mAnimator = new BadgeAnimator(createBadgeBitmap(), paramPointF, this);
      this.mAnimator.start();
      setBadgeNumber(0);
    } 
  }
  
  @Override
  public Badge bindTarget(View paramView) {
    if (paramView == null)
      throw new IllegalStateException("targetView can not be null"); 
    if (getParent() != null)
      ((ViewGroup)getParent()).removeView(this); 
    ViewParent viewParent = paramView.getParent();
    if (viewParent != null && viewParent instanceof ViewGroup) {
      this.mTargetView = paramView;
      if (viewParent instanceof BadgeContainer) {
        ((BadgeContainer)viewParent).addView(this);
        return this;
      } 
      ViewGroup viewGroup = (ViewGroup)viewParent;
      int i = viewGroup.indexOfChild(paramView);
      ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
      viewGroup.removeView(paramView);
      BadgeContainer badgeContainer = new BadgeContainer(this, getContext());
      badgeContainer.setId(paramView.getId());
      viewGroup.addView((View)badgeContainer, i, layoutParams);
      badgeContainer.addView(paramView);
      badgeContainer.addView(this);
      return this;
    } 
    throw new IllegalStateException("targetView must have a parent");
  }
  
  protected Bitmap createBadgeBitmap() {
    Bitmap bitmap = Bitmap.createBitmap((int)this.mBadgeBackgroundRect.width() + DisplayUtil.dp2px(getContext(), 3), (int)this.mBadgeBackgroundRect.height() + DisplayUtil.dp2px(getContext(), 3), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawBadge(canvas, new PointF(canvas.getWidth() / 2.0F, canvas.getHeight() / 2.0F), getBadgeCircleRadius());
    return bitmap;
  }
  
  @Override
  public Drawable getBadgeBackground() {
    return this.mDrawableBackground;
  }
  
  @Override
  public int getBadgeBackgroundColor() {
    return this.mColorBackground;
  }
  
  @Override
  public int getBadgeGravity() {
    return this.mBadgeGravity;
  }
  
  @Override
  public int getBadgeNumber() {
    return this.mBadgeNumber;
  }
  
  @Override
  public float getBadgePadding(boolean paramBoolean) {
    return paramBoolean ? DisplayUtil.px2dp(getContext(), this.mBadgePadding) : this.mBadgePadding;
  }
  
  @Override
  public String getBadgeText() {
    return this.mBadgeText;
  }
  
  @Override
  public int getBadgeTextColor() {
    return this.mColorBadgeText;
  }
  
  @Override
  public float getBadgeTextSize(boolean paramBoolean) {
    return paramBoolean ? DisplayUtil.px2dp(getContext(), this.mBadgeTextSize) : this.mBadgeTextSize;
  }
  
  @Override
  public PointF getDragCenter() {
    return (this.mDraggable && this.mDragging) ? this.mDragCenter : (PointF)null;
  }
  
  @Override
  public float getGravityOffsetX(boolean paramBoolean) {
    return paramBoolean ? DisplayUtil.px2dp(getContext(), this.mGravityOffsetX) : this.mGravityOffsetX;
  }
  
  @Override
  public float getGravityOffsetY(boolean paramBoolean) {
    return paramBoolean ? DisplayUtil.px2dp(getContext(), this.mGravityOffsetY) : this.mGravityOffsetY;
  }
  
  @Override
  public View getTargetView() {
    return this.mTargetView;
  }
  
  @Override
  public void hide(boolean paramBoolean) {
    if (paramBoolean && this.mActivityRoot != null) {
      animateHide(this.mRowBadgeCenter);
      return;
    } 
    setBadgeNumber(0);
  }
  
  @Override
  public boolean isDraggable() {
    return this.mDraggable;
  }
  
  @Override
  public boolean isExactMode() {
    return this.mExact;
  }
  
  @Override
  public boolean isShowShadow() {
    return this.mShowShadow;
  }
  
  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mActivityRoot == null)
      findActivityRoot(this.mTargetView); 
  }
  
  @Override
  protected void onDraw(Canvas paramCanvas) {
    float f;
    if (this.mAnimator != null && this.mAnimator.isRunning()) {
      this.mAnimator.draw(paramCanvas);
      return;
    } 
    if (this.mBadgeText != null) {
      initPaints();
      f = getBadgeCircleRadius();
      float f1 = this.mDefalutRadius * (true - MathUtil.getPointDistance(this.mRowBadgeCenter, this.mDragCenter) / this.mFinalDragDistance);
      if (this.mDraggable && this.mDragging) {
        boolean bool;
        this.mDragQuadrant = MathUtil.getQuadrant(this.mDragCenter, this.mRowBadgeCenter);
        showShadowImpl(this.mShowShadow);
        if (f1 >= DisplayUtil.dp2px(getContext(), 1.5F)) {
          bool = false;
        } else {
          bool = true;
        } 
        this.mDragOutOfRange = bool;
        if (bool) {
          updataListener(3);
          drawBadge(paramCanvas, this.mDragCenter, f);
          return;
        } 
        updataListener(2);
        drawDragging(paramCanvas, f1, f);
        drawBadge(paramCanvas, this.mDragCenter, f);
        return;
      } 
    } else {
      return;
    } 
    findBadgeCenter();
    drawBadge(paramCanvas, this.mBadgeCenter, f);
  }
  
  @Override
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    float f1;
    float f2;
    switch (paramMotionEvent.getActionMasked()) {
      default:
        if (!this.mDragging && !super.onTouchEvent(paramMotionEvent))
          return false; 
        break;
      case 0:
      case 5:
        f1 = paramMotionEvent.getX();
        f2 = paramMotionEvent.getY();
        if (this.mDraggable && paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()) == 0 && f1 > this.mBadgeBackgroundRect.left && f1 < this.mBadgeBackgroundRect.right && f2 > this.mBadgeBackgroundRect.top && f2 < this.mBadgeBackgroundRect.bottom && this.mBadgeText != null) {
          initRowBadgeCenter();
          this.mDragging = true;
          updataListener(1);
          this.mDefalutRadius = DisplayUtil.dp2px(getContext(), 7);
          getParent().requestDisallowInterceptTouchEvent(true);
          screenFromWindow(true);
          this.mDragCenter.x = paramMotionEvent.getRawX();
          this.mDragCenter.y = paramMotionEvent.getRawY();
        } 
      case 2:
        if (this.mDragging) {
          this.mDragCenter.x = paramMotionEvent.getRawX();
          this.mDragCenter.y = paramMotionEvent.getRawY();
          invalidate();
        } 
      case 1:
      case 3:
      case 6:
        if (paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()) == 0 && this.mDragging) {
          this.mDragging = false;
          onPointerUp();
        } 
    } 
    return true;
  }
  
  public void reset() {
    this.mDragCenter.x = -1000;
    this.mDragCenter.y = -1000;
    this.mDragQuadrant = 4;
    screenFromWindow(false);
    getParent().requestDisallowInterceptTouchEvent(false);
    invalidate();
  }
  
  protected void screenFromWindow(boolean paramBoolean) {
    if (getParent() != null)
      ((ViewGroup)getParent()).removeView(this); 
    if (paramBoolean) {
      this.mActivityRoot.addView(this, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
      return;
    } 
    bindTarget(this.mTargetView);
  }
  
  @Override
  public Badge setBadgeBackground(Drawable paramDrawable) {
    return setBadgeBackground(paramDrawable, false);
  }
  
  @Override
  public Badge setBadgeBackground(Drawable paramDrawable, boolean paramBoolean) {
    this.mDrawableBackgroundClip = paramBoolean;
    this.mDrawableBackground = paramDrawable;
    createClipLayer();
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgeBackgroundColor(int paramInt) {
    this.mColorBackground = paramInt;
    if (this.mColorBackground == 0) {
      this.mBadgeTextPaint.setXfermode((Xfermode)null);
      invalidate();
      return this;
    } 
    this.mBadgeTextPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgeGravity(int paramInt) {
    if (paramInt == 8388659 || paramInt == 8388661 || paramInt == 8388691 || paramInt == 8388693 || paramInt == 17 || paramInt == 49 || paramInt == 81 || paramInt == 8388627 || paramInt == 8388629) {
      this.mBadgeGravity = paramInt;
      invalidate();
      return this;
    } 
    throw new IllegalStateException("only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP , " + "Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM , Gravity.CENTER" + " , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ," + "Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END");
  }
  
  @Override
  public Badge setBadgeNumber(int paramInt) {
    this.mBadgeNumber = paramInt;
    if (this.mBadgeNumber < 0) {
      this.mBadgeText = "";
      measureText();
      invalidate();
      return this;
    } 
    if (this.mBadgeNumber > 99) {
      String str;
      if (this.mExact) {
        str = String.valueOf(this.mBadgeNumber);
      } else {
        str = "99+";
      } 
      this.mBadgeText = str;
      measureText();
      invalidate();
      return this;
    } 
    if (this.mBadgeNumber > 0 && this.mBadgeNumber <= 99) {
      this.mBadgeText = String.valueOf(this.mBadgeNumber);
      measureText();
      invalidate();
      return this;
    } 
    if (this.mBadgeNumber == 0)
      this.mBadgeText = (String)null; 
    measureText();
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgePadding(float paramFloat, boolean paramBoolean) {
    if (paramBoolean)
      paramFloat = DisplayUtil.dp2px(getContext(), paramFloat); 
    this.mBadgePadding = paramFloat;
    createClipLayer();
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgeText(String paramString) {
    this.mBadgeText = paramString;
    this.mBadgeNumber = 1;
    measureText();
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgeTextColor(int paramInt) {
    this.mColorBadgeText = paramInt;
    invalidate();
    return this;
  }
  
  @Override
  public Badge setBadgeTextSize(float paramFloat, boolean paramBoolean) {
    if (paramBoolean)
      paramFloat = DisplayUtil.dp2px(getContext(), paramFloat); 
    this.mBadgeTextSize = paramFloat;
    measureText();
    invalidate();
    return this;
  }
  
  @Override
  public Badge setExactMode(boolean paramBoolean) {
    this.mExact = paramBoolean;
    if (this.mBadgeNumber > 99)
      setBadgeNumber(this.mBadgeNumber); 
    return this;
  }
  
  @Override
  public Badge setGravityOffset(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    if (paramBoolean)
      paramFloat1 = DisplayUtil.dp2px(getContext(), paramFloat1); 
    this.mGravityOffsetX = paramFloat1;
    if (paramBoolean) {
      paramFloat1 = DisplayUtil.dp2px(getContext(), paramFloat2);
      this.mGravityOffsetY = paramFloat1;
      invalidate();
      return this;
    } 
    paramFloat1 = paramFloat2;
    this.mGravityOffsetY = paramFloat1;
    invalidate();
    return this;
  }
  
  @Override
  public Badge setGravityOffset(float paramFloat, boolean paramBoolean) {
    return setGravityOffset(paramFloat, paramFloat, paramBoolean);
  }
  
  @Override
  public Badge setOnDragStateChangedListener(Badge.OnDragStateChangedListener paramOnDragStateChangedListener) {
    if (paramOnDragStateChangedListener == null) {
      boolean bool1 = false;
      this.mDraggable = bool1;
      this.mDragStateChangedListener = paramOnDragStateChangedListener;
      return this;
    } 
    boolean bool = true;
    this.mDraggable = bool;
    this.mDragStateChangedListener = paramOnDragStateChangedListener;
    return this;
  }
  
  @Override
  public Badge setShowShadow(boolean paramBoolean) {
    this.mShowShadow = paramBoolean;
    invalidate();
    return this;
  }
  
  @Override
  public Badge stroke(int paramInt, float paramFloat, boolean paramBoolean) {
    this.mColorBackgroundBorder = paramInt;
    if (paramBoolean)
      paramFloat = DisplayUtil.dp2px(getContext(), paramFloat); 
    this.mBackgroundBorderWidth = paramFloat;
    invalidate();
    return this;
  }
  
  private class BadgeContainer extends ViewGroup {
    private final QBadgeView this$0;
    
    public BadgeContainer(QBadgeView this$0, Context param1Context) {
      super(param1Context);
      this.this$0 = this$0;
    }
    
    @Override
    protected void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      for (param1Int1 = 0;; param1Int1++) {
        if (param1Int1 >= getChildCount())
          return; 
        View view = getChildAt(param1Int1);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
      } 
    }
    
    @Override
    protected void onMeasure(int param1Int1, int param1Int2) {
      View view1 = (View)null;
      View view2 = (View)null;
      int i = 0;
      while (true) {
        if (i >= getChildCount()) {
          if (view1 == null) {
            super.onMeasure(param1Int1, param1Int2);
            return;
          } 
        } else {
          View view = getChildAt(i);
          if (!(view instanceof QBadgeView)) {
            view1 = view;
          } else {
            view2 = view;
          } 
          i++;
          continue;
        } 
        view1.measure(param1Int1, param1Int2);
        if (view2 != null)
          view2.measure(View.MeasureSpec.makeMeasureSpec(view1.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view1.getMeasuredHeight(), 1073741824)); 
        setMeasuredDimension(view1.getMeasuredWidth(), view1.getMeasuredHeight());
        return;
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\q\rorbin\badgeview\QBadgeView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */