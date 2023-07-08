package com.b.a.a;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import com.b.a.b.b;
import com.b.a.b.m;

public class i extends g {
  private final a e;
  
  private final a f;
  
  private final a g;
  
  private boolean h = false;
  
  private boolean i = false;
  
  private boolean j = false;
  
  private boolean k = false;
  
  private int l;
  
  public i(c paramc) {
    super(paramc);
    DisplayMetrics displayMetrics = paramc.getContext().getResources().getDisplayMetrics();
    this.l = (int)TypedValue.applyDimension(2, (float)(c.d * 1.5D), displayMetrics);
    this.e = new a(this);
    this.f = new a(this);
    this.g = new a(this);
  }
  
  private void a(a parama, MotionEvent paramMotionEvent) {
    int j = parama.c((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()).a();
    if (j >= 0) {
      this.c.moveCaret(j);
      Rect rect = this.c.c(j);
      parama.a(rect.left + this.c.getPaddingLeft(), rect.bottom + this.c.getPaddingTop());
    } 
  }
  
  public Rect a() {
    return this.e.a;
  }
  
  public void a(Canvas paramCanvas) {
    if (!this.c.isSelectText2()) {
      this.e.d();
      this.f.e();
      this.g.e();
      if (!this.j) {
        Rect rect = this.c.c(this.c.getCaretPosition());
        int j = rect.left;
        int k = this.c.getPaddingLeft();
        int m = rect.bottom;
        int n = this.c.getPaddingTop();
        this.e.b(j + k, m + n);
      } 
      if (this.k)
        this.e.a(paramCanvas, this.j); 
      this.k = false;
      return;
    } 
    this.e.e();
    this.f.d();
    this.g.d();
    if (!this.h || !this.i) {
      Rect rect = this.c.c(this.c.getSelectionStart());
      int j = rect.left;
      int k = this.c.getPaddingLeft();
      int m = rect.bottom;
      int n = this.c.getPaddingTop();
      this.f.b(j + k, m + n);
      rect = this.c.c(this.c.getSelectionEnd());
      j = rect.left;
      k = this.c.getPaddingLeft();
      m = rect.bottom;
      n = this.c.getPaddingTop();
      this.g.b(j + k, m + n);
    } 
    this.f.a(paramCanvas, this.h);
    this.g.a(paramCanvas, this.h);
  }
  
  public void a(b paramb) {
    this.e.a(paramb.a(b.a.f));
  }
  
  public boolean a(MotionEvent paramMotionEvent) {
    this.j = false;
    this.h = false;
    this.i = false;
    this.e.c();
    this.f.c();
    this.g.c();
    super.a(paramMotionEvent);
    return true;
  }
  
  public boolean onDoubleTap(MotionEvent paramMotionEvent) {
    int j = (int)paramMotionEvent.getX() + this.c.getScrollX();
    int k = (int)paramMotionEvent.getY() + this.c.getScrollY();
    if (this.e.e(j, k)) {
      this.c.selectText(true);
      return true;
    } 
    return this.f.e(j, k) ? true : super.onDoubleTap(paramMotionEvent);
  }
  
  public boolean onDown(MotionEvent paramMotionEvent) {
    super.onDown(paramMotionEvent);
    if (!this.d) {
      int j = (int)paramMotionEvent.getX() + this.c.getScrollX();
      int k = (int)paramMotionEvent.getY() + this.c.getScrollY();
      this.j = this.e.e(j, k);
      this.h = this.f.e(j, k);
      this.i = this.g.e(j, k);
      if (this.j) {
        this.k = true;
        this.e.d(j, k);
        a a1 = this.e;
        a1.b();
        return true;
      } 
      if (this.h) {
        this.f.d(j, k);
        this.c.focusSelectionStart();
        a a1 = this.f;
        a1.b();
        return true;
      } 
      if (this.i) {
        this.g.d(j, k);
        this.c.focusSelectionEnd();
        a a1 = this.g;
        a1.b();
        return true;
      } 
    } 
    return true;
  }
  
  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
    if (this.j || this.h || this.i) {
      a(paramMotionEvent2);
      return true;
    } 
    return super.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
  }
  
  public void onLongPress(MotionEvent paramMotionEvent) {
    onDoubleTap(paramMotionEvent);
  }
  
  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
    if (this.j) {
      if ((paramMotionEvent2.getAction() & 0xFF) == 1) {
        a(paramMotionEvent2);
        return true;
      } 
      this.k = true;
      a(this.e, paramMotionEvent2);
      return true;
    } 
    if (this.h) {
      if ((paramMotionEvent2.getAction() & 0xFF) == 1) {
        a(paramMotionEvent2);
        return true;
      } 
      a(this.f, paramMotionEvent2);
      return true;
    } 
    if (this.i) {
      if ((paramMotionEvent2.getAction() & 0xFF) == 1) {
        a(paramMotionEvent2);
        return true;
      } 
      a(this.g, paramMotionEvent2);
      return true;
    } 
    return super.onScroll(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
  }
  
  public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
    int j = (int)paramMotionEvent.getX() + this.c.getScrollX();
    int k = (int)paramMotionEvent.getY() + this.c.getScrollY();
    if (!this.e.e(j, k) && !this.f.e(j, k)) {
      if (this.g.e(j, k))
        return true; 
      this.k = true;
      return super.onSingleTapUp(paramMotionEvent);
    } 
    return true;
  }
  
  private class a {
    public final Rect a = new Rect(a(), 0, 0, this.d.bottom + this.c);
    
    private final int c = i.a(this.b) / 3;
    
    private final Rect d = new Rect(0, 0, i.a(this.b), i.a(this.b));
    
    private int e = 0;
    
    private int f = 0;
    
    private int g = 0;
    
    private int h = 0;
    
    private int i = 0;
    
    private int j = 0;
    
    private final Paint k = new Paint();
    
    private boolean l;
    
    public a(i this$0) {
      this.k.setColor(this$0.c.getColorScheme().a(b.a.f));
      this.k.setAntiAlias(true);
    }
    
    private void f() {
      int k;
      int m;
      int n;
      int j = this.g + a();
      if (j >= this.e) {
        m = this.e;
        k = j + 1;
        j = m;
      } else {
        k = this.e + 1;
      } 
      if (this.h >= this.f) {
        m = this.f;
        n = this.h;
      } else {
        m = this.h;
        n = this.f;
      } 
      this.b.c.invalidate(j, m, k, n);
      b();
    }
    
    public final int a() {
      return this.d.right / 2;
    }
    
    public void a(int param1Int) {
      this.k.setColor(param1Int);
    }
    
    public void a(int param1Int1, int param1Int2) {
      f();
      b(param1Int1, param1Int2);
      f();
    }
    
    public void a(Canvas param1Canvas, boolean param1Boolean) {
      int j = a();
      param1Canvas.drawLine(this.e, this.f, (this.g + j), (this.h + j), this.k);
      float f = (this.e - j);
      int k = this.f;
      int m = j / 2;
      param1Canvas.drawArc(new RectF(f, (k - m - this.c), (this.g + j * 2), (this.h + m)), 60.0F, 60.0F, true, this.k);
      param1Canvas.drawOval(new RectF(this.g, this.h, (this.g + this.d.right), (this.h + this.d.bottom)), this.k);
    }
    
    public void b() {
      Rect rect = new Rect(this.g, this.h, this.g + this.d.right, this.h + this.d.bottom);
      this.b.c.invalidate(rect);
    }
    
    public void b(int param1Int1, int param1Int2) {
      this.e = param1Int1;
      this.f = param1Int2;
      this.g = param1Int1 - a();
      this.h = param1Int2 + this.c;
    }
    
    public m c(int param1Int1, int param1Int2) {
      param1Int1 = this.b.a(param1Int1) - this.i + a();
      param1Int2 = this.b.b(param1Int2) - this.j - this.c - 2;
      return new m(this.b.c.a(param1Int1, param1Int2), this.b.c.b(param1Int1, param1Int2));
    }
    
    public void c() {
      this.i = 0;
      this.j = 0;
    }
    
    public void d() {
      this.l = true;
    }
    
    public void d(int param1Int1, int param1Int2) {
      this.i = param1Int1 - this.g;
      this.j = param1Int2 - this.h;
    }
    
    public void e() {
      this.l = false;
    }
    
    public boolean e(int param1Int1, int param1Int2) {
      return (this.l && param1Int1 >= this.g && param1Int1 < this.g + this.d.right && param1Int2 >= this.h && param1Int2 < this.h + this.d.bottom);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\b\a\a\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */