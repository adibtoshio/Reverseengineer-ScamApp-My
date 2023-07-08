package android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GifView extends View {
  private int a;
  
  private Movie b;
  
  private long c;
  
  private int d;
  
  private float e;
  
  private float f;
  
  private float g;
  
  private int h;
  
  private int i;
  
  private volatile boolean j;
  
  private boolean k = true;
  
  private String l;
  
  public GifView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public GifView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public GifView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    a(paramContext, paramAttributeSet, paramInt);
  }
  
  @SuppressLint({"NewApi"})
  private void a() {
    if (this.k) {
      if (Build.VERSION.SDK_INT >= 16) {
        postInvalidateOnAnimation();
        return;
      } 
      invalidate();
    } 
  }
  
  @SuppressLint({"NewApi"})
  private void a(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    this.a = -1;
    this.j = false;
    if (this.a != -1)
      this.b = Movie.decodeStream(getResources().openRawResource(this.a)); 
  }
  
  private void a(Canvas paramCanvas) {
    this.b.setTime(this.d);
    paramCanvas.save(1);
    paramCanvas.scale(this.g, this.g);
    this.b.draw(paramCanvas, this.e / this.g, this.f / this.g);
    paramCanvas.restore();
  }
  
  private void b() {
    long l = SystemClock.uptimeMillis();
    if (this.c == 0L)
      this.c = l; 
    int j = this.b.duration();
    int i = j;
    if (j == 0)
      i = 1000; 
    this.d = (int)((l - this.c) % i);
  }
  
  public String getGifPath() {
    return this.l;
  }
  
  public int getGifResource() {
    return this.a;
  }
  
  public boolean isPaused() {
    return this.j;
  }
  
  public boolean isPlaying() {
    return this.j ^ true;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    if (this.b != null) {
      if (!this.j) {
        b();
        a(paramCanvas);
        a();
        return;
      } 
      a(paramCanvas);
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.e = (getWidth() - this.h) / 2.0F;
    this.f = (getHeight() - this.i) / 2.0F;
    if (getVisibility() == 0) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.k = paramBoolean;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield b : Landroid/graphics/Movie;
    //   4: ifnull -> 138
    //   7: aload_0
    //   8: getfield b : Landroid/graphics/Movie;
    //   11: invokevirtual width : ()I
    //   14: istore #5
    //   16: aload_0
    //   17: getfield b : Landroid/graphics/Movie;
    //   20: invokevirtual height : ()I
    //   23: istore #6
    //   25: iload_1
    //   26: invokestatic getMode : (I)I
    //   29: ifeq -> 53
    //   32: iload_1
    //   33: invokestatic getSize : (I)I
    //   36: istore_1
    //   37: iload #5
    //   39: iload_1
    //   40: if_icmple -> 53
    //   43: iload #5
    //   45: i2f
    //   46: iload_1
    //   47: i2f
    //   48: fdiv
    //   49: fstore_3
    //   50: goto -> 55
    //   53: fconst_1
    //   54: fstore_3
    //   55: iload_2
    //   56: invokestatic getMode : (I)I
    //   59: ifeq -> 84
    //   62: iload_2
    //   63: invokestatic getSize : (I)I
    //   66: istore_1
    //   67: iload #6
    //   69: iload_1
    //   70: if_icmple -> 84
    //   73: iload #6
    //   75: i2f
    //   76: iload_1
    //   77: i2f
    //   78: fdiv
    //   79: fstore #4
    //   81: goto -> 87
    //   84: fconst_1
    //   85: fstore #4
    //   87: aload_0
    //   88: fconst_1
    //   89: fload_3
    //   90: fload #4
    //   92: invokestatic max : (FF)F
    //   95: fdiv
    //   96: putfield g : F
    //   99: aload_0
    //   100: iload #5
    //   102: i2f
    //   103: aload_0
    //   104: getfield g : F
    //   107: fmul
    //   108: f2i
    //   109: putfield h : I
    //   112: aload_0
    //   113: iload #6
    //   115: i2f
    //   116: aload_0
    //   117: getfield g : F
    //   120: fmul
    //   121: f2i
    //   122: putfield i : I
    //   125: aload_0
    //   126: getfield h : I
    //   129: istore_1
    //   130: aload_0
    //   131: getfield i : I
    //   134: istore_2
    //   135: goto -> 148
    //   138: aload_0
    //   139: invokevirtual getSuggestedMinimumWidth : ()I
    //   142: istore_1
    //   143: aload_0
    //   144: invokevirtual getSuggestedMinimumHeight : ()I
    //   147: istore_2
    //   148: aload_0
    //   149: iload_1
    //   150: iload_2
    //   151: invokevirtual setMeasuredDimension : (II)V
    //   154: return
  }
  
  @SuppressLint({"NewApi"})
  public void onScreenStateChanged(int paramInt) {
    super.onScreenStateChanged(paramInt);
    boolean bool = true;
    if (paramInt != 1)
      bool = false; 
    this.k = bool;
    a();
  }
  
  @SuppressLint({"NewApi"})
  protected void onVisibilityChanged(View paramView, int paramInt) {
    boolean bool;
    super.onVisibilityChanged(paramView, paramInt);
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.k = bool;
    a();
  }
  
  protected void onWindowVisibilityChanged(int paramInt) {
    boolean bool;
    super.onWindowVisibilityChanged(paramInt);
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.k = bool;
    a();
  }
  
  public void pause() {
    if (!this.j) {
      this.j = true;
      invalidate();
    } 
  }
  
  public void play() {
    if (this.j) {
      this.j = false;
      this.c = SystemClock.uptimeMillis() - this.d;
      invalidate();
    } 
  }
  
  public void setGifPath(String paramString) {
    this.l = paramString;
    try {
      this.b = Movie.decodeStream(new FileInputStream(paramString));
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } 
    requestLayout();
  }
  
  public void setGifResource(int paramInt) {
    this.a = paramInt;
    this.b = Movie.decodeStream(getResources().openRawResource(this.a));
    requestLayout();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\GifView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */