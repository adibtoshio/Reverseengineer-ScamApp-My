package com.b.a.a;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.CharacterPickerDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import com.b.a.b.d;
import com.b.a.b.e;
import com.b.a.b.f;
import com.b.a.b.i;
import com.b.a.b.k;
import com.b.a.b.m;
import com.b.a.b.n;
import com.b.a.b.q;
import java.util.List;

public class c extends View implements e.a {
  public static final int SCROLL_DOWN = 1;
  
  public static final int SCROLL_LEFT = 2;
  
  public static final int SCROLL_RIGHT = 3;
  
  public static final int SCROLL_UP = 0;
  
  protected static float a = 0.75F;
  
  protected static float b = 0.5F;
  
  protected static int c = 4;
  
  protected static int d = 16;
  
  protected static long e = 250L;
  
  private static SparseArray<String> u = new SparseArray();
  
  private int A = 0;
  
  private Paint B;
  
  private int C = 0;
  
  private int D = 0;
  
  private boolean E = false;
  
  private b F;
  
  private ClipboardManager G;
  
  private float H = 1.0F;
  
  private int I;
  
  private int J;
  
  private f K;
  
  private int L;
  
  private Typeface M = Typeface.DEFAULT;
  
  private Typeface N = Typeface.DEFAULT_BOLD;
  
  private Typeface O = Typeface.create(Typeface.DEFAULT, 2);
  
  private char P;
  
  private boolean Q;
  
  private Paint R;
  
  private int S;
  
  private final Runnable T = new Runnable(this) {
      public void run() {
        c.a(this.a).c();
        if (!this.a.d())
          this.a.postDelayed(c.b(this.a), c.e); 
      }
    };
  
  private final Runnable U = new Runnable(this) {
      public void run() {
        c.a(this.a).d();
        if (!this.a.c())
          this.a.postDelayed(c.c(this.a), c.e); 
      }
    };
  
  private final Runnable V = new Runnable(this) {
      public void run() {
        c.a(this.a).b(false);
        if (this.a.i > 0 && c.d(this.a) == this.a.h.b(this.a.i - 1))
          this.a.postDelayed(c.e(this.a), c.e); 
      }
    };
  
  private final Runnable W = new Runnable(this) {
      public void run() {
        c.a(this.a).a(false);
        if (!this.a.e() && c.d(this.a) == this.a.h.b(this.a.i + 1))
          this.a.postDelayed(c.f(this.a), c.e); 
      }
    };
  
  private int aa;
  
  private long ab;
  
  private boolean ac = false;
  
  private MotionEvent ad;
  
  private float ae;
  
  private float af;
  
  protected boolean f = false;
  
  protected g g = new g(this);
  
  protected f h = new f(this);
  
  protected int i = 0;
  
  protected int j = -1;
  
  protected int k = -1;
  
  protected int l = c;
  
  protected com.b.a.b.b m = (com.b.a.b.b)new d();
  
  protected boolean n = false;
  
  protected boolean o = false;
  
  protected boolean p = true;
  
  protected int q = 4;
  
  protected boolean r = false;
  
  protected a s;
  
  protected boolean t = true;
  
  private final Scroller v;
  
  private a w;
  
  private b x;
  
  private n y;
  
  private e z;
  
  static {
    u.put(65, "ÀÁÂÄÆÃÅĄĀ");
    u.put(67, "ÇĆČ");
    u.put(68, "Ď");
    u.put(69, "ÈÉÊËĘĚĒ");
    u.put(71, "Ğ");
    u.put(76, "Ł");
    u.put(73, "ÌÍÎÏĪİ");
    u.put(78, "ÑŃŇ");
    u.put(79, "ØŒÕÒÓÔÖŌ");
    u.put(82, "Ř");
    u.put(83, "ŚŠŞ");
    u.put(84, "Ť");
    u.put(85, "ÙÚÛÜŮŪ");
    u.put(89, "ÝŸ");
    u.put(90, "ŹŻŽ");
    u.put(97, "àáâäæãåąā");
    u.put(99, "çćč");
    u.put(100, "ď");
    u.put(101, "èéêëęěē");
    u.put(103, "ğ");
    u.put(105, "ìíîïīı");
    u.put(108, "ł");
    u.put(110, "ñńň");
    u.put(111, "øœõòóôöō");
    u.put(114, "ř");
    u.put(115, "§ßśšş");
    u.put(116, "ť");
    u.put(117, "ùúûüůū");
    u.put(121, "ýÿ");
    u.put(122, "źżž");
    u.put(61185, "…¥•®©±[]{}\\|");
    u.put(47, "\\");
    u.put(49, "¹½⅓¼⅛");
    u.put(50, "²⅔");
    u.put(51, "³¾⅜");
    u.put(52, "⁴");
    u.put(53, "⅝");
    u.put(55, "⅞");
    u.put(48, "ⁿ∅");
    u.put(36, "¢£€¥₣₤₱");
    u.put(37, "‰");
    u.put(42, "†‡");
    u.put(45, "–—");
    u.put(43, "±");
    u.put(40, "[{<");
    u.put(41, "]}>");
    u.put(33, "¡");
    u.put(34, "“”«»˝");
    u.put(63, "¿");
    u.put(44, "‚„");
    u.put(61, "≠≈∞");
    u.put(60, "≤«‹");
    u.put(62, "≥»›");
  }
  
  public c(Context paramContext) {
    super(paramContext);
    this.v = new Scroller(paramContext);
    f();
  }
  
  public c(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    this.v = new Scroller(paramContext);
    f();
  }
  
  public c(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.v = new Scroller(paramContext);
    f();
  }
  
  private int a(Canvas paramCanvas) {
    return (paramCanvas.getClipBounds()).top / a();
  }
  
  private int a(Canvas paramCanvas, char paramChar, int paramInt1, int paramInt2) {
    int i = this.B.getColor();
    int j = getAdvance(paramChar, paramInt1);
    if (paramInt1 > getScrollX() || paramInt1 < getScrollX() + getContentWidth()) {
      String str;
      switch (paramChar) {
        default:
          if (this.P != '\000') {
            char c1 = this.P;
            float f3 = paramInt1;
            float f4 = paramInt2;
            Paint paint1 = this.B;
            paramCanvas.drawText(new char[] { c1, paramChar }, 0, 2, f3, f4, paint1);
            this.P = Character.MIN_VALUE;
            return j;
          } 
          break;
        case '?':
        case '?':
          this.P = paramChar;
          return j;
        case ' ':
          if (this.o) {
            this.B.setColor(this.m.a(com.b.a.b.b.a.i));
            str = "·";
          } else {
            paramCanvas.drawText(" ", 0, 1, paramInt1, paramInt2, this.B);
            return j;
          } 
          paramCanvas.drawText(str, 0, 1, paramInt1, paramInt2, this.B);
          this.B.setColor(i);
          return j;
        case '\n':
        case '￿':
          if (this.o) {
            this.B.setColor(this.m.a(com.b.a.b.b.a.i));
            str = "↵";
            paramCanvas.drawText(str, 0, 1, paramInt1, paramInt2, this.B);
            this.B.setColor(i);
            return j;
          } 
          return j;
        case '\t':
          if (this.o) {
            this.B.setColor(this.m.a(com.b.a.b.b.a.i));
            str = "»";
            paramCanvas.drawText(str, 0, 1, paramInt1, paramInt2, this.B);
            this.B.setColor(i);
            return j;
          } 
          return j;
      } 
      float f1 = paramInt1;
      float f2 = paramInt2;
      Paint paint = this.B;
      paramCanvas.drawText(new char[] { paramChar }, 0, 1, f1, f2, paint);
    } 
    return j;
  }
  
  private int a(Canvas paramCanvas, String paramString, int paramInt1, int paramInt2) {
    paramCanvas.drawText(paramString, paramInt1, paramInt2, this.R);
    return 0;
  }
  
  private void a(char paramChar) {
    a a1;
    if (Character.isLowerCase(paramChar) && paramChar == this.h.charAt(this.i - 1)) {
      this.w.a('\b');
      a1 = this.w;
      paramChar = Character.toUpperCase(paramChar);
    } else {
      a1 = this.w;
    } 
    a1.a(paramChar);
  }
  
  private void a(float paramFloat1, float paramFloat2) {
    int j = (int)paramFloat1 + getScrollX();
    int k = (int)paramFloat2 + getScrollY();
    int i = Math.max(getMaxScrollX(), getScrollX());
    if (j <= i) {
      i = j;
      if (j < 0)
        i = 0; 
    } 
    j = Math.max(getMaxScrollY(), getScrollY());
    if (k <= j) {
      j = k;
      if (k < 0)
        j = 0; 
    } 
    smoothScrollTo(i, j);
  }
  
  private void a(int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.isShiftPressed() && !isSelectText()) {
      h();
      this.w.c(true);
    } else if (!paramKeyEvent.isShiftPressed() && isSelectText()) {
      i();
      this.w.c(false);
    } 
    switch (paramInt) {
      default:
        return;
      case 22:
        this.w.a(false);
        return;
      case 21:
        this.w.b(false);
        return;
      case 20:
        this.w.c();
        return;
      case 19:
        break;
    } 
    this.w.d();
  }
  
  private void a(Canvas paramCanvas, int paramInt1, int paramInt2) {
    int i = this.B.getColor();
    this.I = paramInt1;
    this.J = paramInt2;
    int j = this.m.a(com.b.a.b.b.a.g);
    this.B.setColor(j);
    a(paramCanvas, paramInt1 - 1, paramInt2, 2);
    this.B.setColor(i);
  }
  
  private void a(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3) {
    Paint.FontMetricsInt fontMetricsInt = this.B.getFontMetricsInt();
    paramCanvas.drawRect(paramInt1, (fontMetricsInt.ascent + paramInt2), (paramInt1 + paramInt3), (paramInt2 + fontMetricsInt.descent), this.B);
  }
  
  private void a(String paramString, boolean paramBoolean) {
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    Selection.setSelection((Spannable)spannableStringBuilder, 0);
    CharacterPickerDialog characterPickerDialog = new CharacterPickerDialog(getContext(), this, (Editable)spannableStringBuilder, paramString, true);
    characterPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener(this, spannableStringBuilder, paramBoolean) {
          public void onDismiss(DialogInterface param1DialogInterface) {
            if (this.a.length() > 0) {
              if (this.b)
                c.a(this.c).a('\b'); 
              c.a(this.c).a(this.a.charAt(0));
            } 
          }
        });
    characterPickerDialog.show();
  }
  
  private int b(Canvas paramCanvas) {
    return ((paramCanvas.getClipBounds()).bottom - 1) / a();
  }
  
  private int b(Canvas paramCanvas, char paramChar, int paramInt1, int paramInt2) {
    int i = this.B.getColor();
    int j = getAdvance(paramChar);
    this.B.setColor(this.m.a(com.b.a.b.b.a.d));
    a(paramCanvas, paramInt1, paramInt2, j);
    this.B.setColor(this.m.a(com.b.a.b.b.a.c));
    a(paramCanvas, paramChar, paramInt1, paramInt2);
    this.B.setColor(i);
    return j;
  }
  
  private void b(char paramChar) {
    char c1;
    if (Character.isUpperCase(this.h.charAt(this.i - 1))) {
      c1 = Character.toUpperCase(paramChar);
    } else {
      c1 = paramChar;
    } 
    String str = (String)u.get(c1);
    if (str != null) {
      this.w.f();
      a(str, true);
      return;
    } 
    this.w.a(paramChar);
  }
  
  private void c(Canvas paramCanvas) {
    m m1;
    Paint paint;
    Typeface typeface1;
    Typeface typeface2;
    int i6 = a(paramCanvas);
    int i2 = this.h.d(i6);
    if (i2 < 0)
      return; 
    this.h.length();
    if (isWordWrap()) {
      i = this.h.c(i2) + 1;
    } else {
      i = i6 + 1;
    } 
    int k = i;
    if (this.E) {
      Paint paint1 = this.R;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.h.f());
      stringBuilder.append(" ");
      this.D = (int)paint1.measureText(stringBuilder.toString());
    } 
    int i8 = b(paramCanvas);
    int i5 = getPaintBaseline(i6);
    List<m> list = this.h.i();
    q.a(list.isEmpty() ^ true, "No spans to paint in TextWarrior.paint()");
    if (list.isEmpty())
      list.add(new m(0, 0)); 
    m m2 = list.get(0);
    int i1 = list.size();
    int j = 0;
    int i = 1;
    while (true) {
      j += m2.a();
      if (i < i1) {
        m1 = list.get(i);
        i++;
      } else {
        m1 = null;
      } 
      if (m1 == null || j > i2)
        break; 
      m2 = m1;
    } 
    int i3 = m2.b();
    int m = m2.b();
    if (m != 1) {
      if (m != 30) {
        paint = this.B;
        typeface2 = this.M;
      } else {
        paint = this.B;
        typeface2 = this.O;
      } 
    } else {
      paint = this.B;
      typeface2 = this.N;
    } 
    paint.setTypeface(typeface2);
    m = this.m.a(m2.b());
    this.B.setColor(m);
    int i4 = this.h.f();
    if (this.E) {
      this.R.setColor(this.m.a(com.b.a.b.b.a.i));
      paramCanvas.drawLine((this.D - this.aa / 2), getScrollY(), (this.D - this.aa / 2), (getScrollY() + getHeight()), this.R);
      if (getMaxScrollY() > getHeight()) {
        int i10 = getScrollY() + getHeight() * getScrollY() / getMaxScrollY();
        int i9 = getScrollY() + getHeight() * (getScrollY() + getHeight()) / getMaxScrollY();
        m = i9;
        if (i9 - i10 < this.S / 4)
          m = this.S / 4 + i10; 
        paramCanvas.drawLine((this.D - this.aa / 2 - this.S / 4), i10, (this.D - this.aa / 2 - this.S / 4), m, this.R);
      } 
    } 
    m = j;
    if (i3 != 1) {
      if (i3 != 30) {
        typeface1 = this.M;
      } else {
        typeface1 = this.O;
      } 
    } else {
      typeface1 = this.N;
    } 
    this.B.setTypeface(typeface1);
    int i7 = 0;
    j = i2;
    i2 = i;
    i = m;
    m = i7;
    while (i6 <= i8) {
      m m3;
      Typeface typeface5;
      int i11 = this.h.g(i6);
      if (i6 >= i4)
        break; 
      if (this.E && k != m) {
        a(paramCanvas, String.valueOf(k), 0, i5);
        m = k;
      } 
      i7 = this.D;
      typeface2 = typeface1;
      int i9 = i3;
      int i10 = i2;
      boolean bool = false;
      i3 = i;
      i2 = j;
      j = i9;
      i = i10;
      m m5 = m1;
      i9 = i11;
      i10 = bool;
      Typeface typeface4 = typeface2;
      while (i10 < i9) {
        m m6;
        Typeface typeface6;
        Typeface typeface7;
        m m7;
        if (m5 != null && i2 >= i3) {
          i3 += m5.a();
          i11 = m5.b();
          if (j != i11) {
            Typeface typeface;
            if (i11 != 1) {
              if (i11 != 30) {
                typeface = this.M;
              } else {
                typeface = this.O;
              } 
            } else {
              typeface = this.N;
            } 
            typeface2 = typeface4;
            if (typeface4 != typeface) {
              this.B.setTypeface(typeface);
              typeface2 = typeface;
            } 
            j = this.m.a(i11);
            this.B.setColor(j);
            typeface4 = typeface2;
          } 
          if (i < i1) {
            j = i + 1;
            m5 = list.get(i);
            i = j;
          } else {
            m5 = null;
          } 
          typeface2 = typeface4;
          j = i11;
          m6 = m5;
          typeface7 = typeface2;
        } else {
          typeface2 = typeface7;
          m7 = m6;
          typeface6 = typeface2;
        } 
        if (i2 == this.i)
          a(paramCanvas, i7, i5); 
        char c1 = this.h.charAt(i2);
        if (this.w.b(i2)) {
          i11 = b(paramCanvas, c1, i7, i5);
        } else {
          i11 = a(paramCanvas, c1, i7, i5);
        } 
        i7 += i11;
        i2++;
        i10++;
        typeface2 = typeface6;
        m3 = m7;
        typeface5 = typeface2;
      } 
      i9 = k;
      if (this.h.charAt(i2 - 1) == '\n')
        i9 = k + 1; 
      i5 += a();
      if (i7 > this.C)
        this.C = i7; 
      i6++;
      i7 = i;
      i10 = i2;
      i = i3;
      typeface2 = typeface5;
      m m4 = m3;
      k = i9;
      i3 = j;
      Typeface typeface3 = typeface2;
      i2 = i7;
      j = i10;
    } 
    e(paramCanvas);
    if (!isWordWrap())
      d(paramCanvas); 
  }
  
  private void d(int paramInt1, int paramInt2) {
    boolean bool;
    if (paramInt1 <= paramInt2 && paramInt1 >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    q.a(bool, "Invalid startRow and/or endRow");
    Rect rect = this.g.a();
    Paint.FontMetricsInt fontMetricsInt = this.B.getFontMetricsInt();
    invalidate(0, Math.max(0, paramInt1 * a() + getPaddingTop() - Math.max(rect.top, fontMetricsInt.descent)), getScrollX() + getWidth(), paramInt2 * a() + getPaddingTop() + rect.bottom);
  }
  
  private void d(Canvas paramCanvas) {
    // Byte code:
    //   0: invokestatic d : ()Ljava/util/ArrayList;
    //   3: astore #9
    //   5: aload #9
    //   7: ifnull -> 353
    //   10: aload #9
    //   12: invokevirtual isEmpty : ()Z
    //   15: ifeq -> 19
    //   18: return
    //   19: aload_1
    //   20: invokevirtual getClipBounds : ()Landroid/graphics/Rect;
    //   23: astore #8
    //   25: aload #8
    //   27: getfield top : I
    //   30: istore_3
    //   31: aload #8
    //   33: getfield bottom : I
    //   36: istore #4
    //   38: aconst_null
    //   39: astore #8
    //   41: aload #9
    //   43: invokevirtual iterator : ()Ljava/util/Iterator;
    //   46: astore #11
    //   48: aload #11
    //   50: invokeinterface hasNext : ()Z
    //   55: ifeq -> 226
    //   58: aload #11
    //   60: invokeinterface next : ()Ljava/lang/Object;
    //   65: checkcast android/graphics/Rect
    //   68: astore #10
    //   70: aload #10
    //   72: getfield top : I
    //   75: iconst_1
    //   76: iadd
    //   77: aload_0
    //   78: invokevirtual a : ()I
    //   81: imul
    //   82: istore #5
    //   84: aload #10
    //   86: getfield bottom : I
    //   89: aload_0
    //   90: invokevirtual a : ()I
    //   93: imul
    //   94: istore #6
    //   96: iload #6
    //   98: iload_3
    //   99: if_icmplt -> 48
    //   102: iload #5
    //   104: iload #4
    //   106: if_icmple -> 112
    //   109: goto -> 48
    //   112: aload_0
    //   113: aload #10
    //   115: getfield left : I
    //   118: invokevirtual b : (I)Lcom/b/a/b/m;
    //   121: invokevirtual a : ()I
    //   124: aload_0
    //   125: aload #10
    //   127: getfield right : I
    //   130: invokevirtual b : (I)Lcom/b/a/b/m;
    //   133: invokevirtual a : ()I
    //   136: invokestatic min : (II)I
    //   139: istore #7
    //   141: aload #8
    //   143: astore #9
    //   145: aload #10
    //   147: getfield left : I
    //   150: aload_0
    //   151: getfield i : I
    //   154: if_icmpge -> 199
    //   157: aload #8
    //   159: astore #9
    //   161: aload #10
    //   163: getfield right : I
    //   166: aload_0
    //   167: getfield i : I
    //   170: if_icmplt -> 199
    //   173: aload #8
    //   175: ifnull -> 195
    //   178: aload #8
    //   180: astore #9
    //   182: aload #8
    //   184: getfield left : I
    //   187: aload #10
    //   189: getfield left : I
    //   192: if_icmpge -> 199
    //   195: aload #10
    //   197: astore #9
    //   199: iload #7
    //   201: i2f
    //   202: fstore_2
    //   203: aload_1
    //   204: fload_2
    //   205: iload #5
    //   207: i2f
    //   208: fload_2
    //   209: iload #6
    //   211: i2f
    //   212: aload_0
    //   213: getfield R : Landroid/graphics/Paint;
    //   216: invokevirtual drawLine : (FFFFLandroid/graphics/Paint;)V
    //   219: aload #9
    //   221: astore #8
    //   223: goto -> 48
    //   226: aload #8
    //   228: ifnull -> 353
    //   231: aload #8
    //   233: getfield top : I
    //   236: iconst_1
    //   237: iadd
    //   238: aload_0
    //   239: invokevirtual a : ()I
    //   242: imul
    //   243: istore #5
    //   245: aload #8
    //   247: getfield bottom : I
    //   250: aload_0
    //   251: invokevirtual a : ()I
    //   254: imul
    //   255: istore #6
    //   257: iload #6
    //   259: iload_3
    //   260: if_icmplt -> 353
    //   263: iload #5
    //   265: iload #4
    //   267: if_icmple -> 271
    //   270: return
    //   271: aload_0
    //   272: aload #8
    //   274: getfield left : I
    //   277: invokevirtual b : (I)Lcom/b/a/b/m;
    //   280: invokevirtual a : ()I
    //   283: aload_0
    //   284: aload #8
    //   286: getfield right : I
    //   289: invokevirtual b : (I)Lcom/b/a/b/m;
    //   292: invokevirtual a : ()I
    //   295: invokestatic min : (II)I
    //   298: istore_3
    //   299: aload_0
    //   300: getfield R : Landroid/graphics/Paint;
    //   303: aload_0
    //   304: getfield m : Lcom/b/a/b/b;
    //   307: getstatic com/b/a/b/b$a.e : Lcom/b/a/b/b$a;
    //   310: invokevirtual a : (Lcom/b/a/b/b$a;)I
    //   313: invokevirtual setColor : (I)V
    //   316: iload_3
    //   317: i2f
    //   318: fstore_2
    //   319: aload_1
    //   320: fload_2
    //   321: iload #5
    //   323: i2f
    //   324: fload_2
    //   325: iload #6
    //   327: i2f
    //   328: aload_0
    //   329: getfield R : Landroid/graphics/Paint;
    //   332: invokevirtual drawLine : (FFFFLandroid/graphics/Paint;)V
    //   335: aload_0
    //   336: getfield R : Landroid/graphics/Paint;
    //   339: aload_0
    //   340: getfield m : Lcom/b/a/b/b;
    //   343: getstatic com/b/a/b/b$a.i : Lcom/b/a/b/b$a;
    //   346: invokevirtual a : (Lcom/b/a/b/b$a;)I
    //   349: invokevirtual setColor : (I)V
    //   352: return
    //   353: return
  }
  
  private void e(Canvas paramCanvas) {
    if (this.n) {
      int i = getPaintBaseline(this.A);
      int j = this.B.getColor();
      this.B.setColor(this.m.a(com.b.a.b.b.a.h));
      a(paramCanvas, 0, i, Math.max(this.C, getContentWidth()));
      this.B.setColor(j);
    } 
  }
  
  private final boolean e(int paramInt1, int paramInt2) {
    return (paramInt1 >= 0 && paramInt1 < getWidth() && paramInt2 >= 0 && paramInt2 < getHeight());
  }
  
  private int f(int paramInt) {
    int j = View.MeasureSpec.getMode(paramInt);
    int i = View.MeasureSpec.getSize(paramInt);
    paramInt = i;
    if (j != 1073741824) {
      paramInt = i;
      if (j != Integer.MIN_VALUE) {
        paramInt = Integer.MAX_VALUE;
        q.a("MeasureSpec cannot be UNSPECIFIED. Setting dimensions to max.");
      } 
    } 
    return paramInt;
  }
  
  private void f() {
    this.ac = ((AccessibilityManager)getContext().getSystemService("accessibility")).isTouchExplorationEnabled();
    this.w = new a();
    this.G = (ClipboardManager)getContext().getSystemService("clipboard");
    this.B = new Paint();
    this.B.setAntiAlias(true);
    this.B.setTextSize(d);
    this.R = new Paint();
    this.R.setAntiAlias(true);
    this.R.setTextSize(d);
    setLongClickable(true);
    setFocusableInTouchMode(true);
    setHapticFeedbackEnabled(true);
    this.y = new n(this) {
        public void a(int param1Int) {}
      };
    this.z = new e(this) {
        public void a(boolean param1Boolean, int param1Int1, int param1Int2) {
          if (param1Boolean) {
            c.g(this.a).a();
            return;
          } 
          c.g(this.a).b();
        }
      };
    this.K = new f(this) {
        public void a(CharSequence param1CharSequence, int param1Int1, int param1Int2) {
          if (c.h(this.a)) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16);
            accessibilityEvent.setFromIndex(param1Int1 - param1Int2);
            accessibilityEvent.setRemovedCount(param1Int2);
            accessibilityEvent.setBeforeText((CharSequence)this.a.h);
            this.a.sendAccessibilityEventUnchecked(accessibilityEvent);
          } 
          this.a.s.b();
        }
        
        public void a(String param1String, int param1Int1, int param1Int2) {
          if (c.h(this.a)) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16);
            accessibilityEvent.setFromIndex(param1Int1 - 1);
            accessibilityEvent.setAddedCount(1);
            this.a.sendAccessibilityEventUnchecked(accessibilityEvent);
          } 
          this.a.s.b();
        }
        
        public void b(CharSequence param1CharSequence, int param1Int1, int param1Int2) {
          if (c.h(this.a)) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16);
            accessibilityEvent.setFromIndex(param1Int1 - param1Int2);
            accessibilityEvent.setAddedCount(param1Int2);
            this.a.sendAccessibilityEventUnchecked(accessibilityEvent);
          } 
          if (!this.a.t)
            return; 
          for (param1Int1 = this.a.i; param1Int1 >= 0; param1Int1--) {
            char c1 = this.a.h.charAt(param1Int1 - 1);
            if (!Character.isLetterOrDigit(c1) && c1 != '_' && c1 != '.')
              break; 
          } 
          if (this.a.i - param1Int1 > 0) {
            this.a.s.a(this.a.h.subSequence(param1Int1, this.a.i - param1Int1));
            return;
          } 
          this.a.s.b();
        }
      };
    g();
    this.F = new b(this);
    this.s = new a(this);
    this.s.a(i.g());
    invalidate();
  }
  
  private void g() {
    this.i = 0;
    this.A = 0;
    this.C = 0;
    this.w.c(false);
    this.w.f();
    this.h.h();
    if (getContentWidth() > 0 || !this.h.j())
      this.h.k(); 
    this.y.a(0);
    scrollTo(0, 0);
  }
  
  private void g(int paramInt) {
    boolean bool;
    if (paramInt >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    q.a(bool, "Invalid startRow");
    Rect rect = this.g.a();
    Paint.FontMetricsInt fontMetricsInt = this.B.getFontMetricsInt();
    invalidate(0, Math.max(0, paramInt * a() + getPaddingTop() - Math.max(rect.top, fontMetricsInt.descent)), getScrollX() + getWidth(), getScrollY() + getHeight());
  }
  
  private void h() {
    d(this.A, this.A + 1);
  }
  
  private boolean h(int paramInt) {
    boolean bool;
    if (paramInt >= 0 && paramInt < this.h.g()) {
      bool = true;
    } else {
      bool = false;
    } 
    q.a(bool, "Invalid charOffset given");
    int i = i(paramInt);
    paramInt = j(paramInt);
    if (i == 0 && paramInt == 0)
      return false; 
    scrollBy(paramInt, i);
    return true;
  }
  
  private int i(int paramInt) {
    paramInt = this.h.b(paramInt) * a();
    int i = a() + paramInt;
    return (paramInt < getScrollY()) ? (paramInt - getScrollY()) : ((i > getScrollY() + getContentHeight()) ? (i - getScrollY() - getContentHeight()) : 0);
  }
  
  private void i() {
    d(this.h.b(this.j), this.h.b(this.k) + 1);
  }
  
  private int j(int paramInt) {
    m m = b(paramInt);
    int i = m.a();
    paramInt = m.b();
    if (paramInt > getScrollX() + getContentWidth()) {
      paramInt = paramInt - getScrollX() - getContentWidth();
    } else {
      paramInt = 0;
    } 
    if (i < getScrollX() + this.S)
      paramInt = i - getScrollX() - this.S; 
    return paramInt;
  }
  
  protected int a() {
    Paint.FontMetricsInt fontMetricsInt = this.B.getFontMetricsInt();
    return fontMetricsInt.descent - fontMetricsInt.ascent;
  }
  
  protected int a(int paramInt) {
    if (this.o)
      return this.l * (int)this.B.measureText("·", 0, "·".length()); 
    paramInt = (paramInt - this.D) / this.aa;
    int i = this.l;
    return (this.l - paramInt % i) * this.aa;
  }
  
  int a(int paramInt1, int paramInt2) {
    paramInt2 /= a();
    if (paramInt2 > this.h.f())
      return this.h.g() - 1; 
    int m = this.h.d(paramInt2);
    if (m < 0)
      return -1; 
    if (paramInt1 < 0)
      return m; 
    String str = this.h.a(paramInt2);
    int i = this.D;
    int i1 = str.length();
    int j = 0;
    int k;
    for (k = 0; j < i1; k = paramInt2) {
      char c2;
      char c1 = str.charAt(j);
      switch (c1) {
        default:
          if (k) {
            paramInt2 = 0;
            break;
          } 
          paramInt2 = getCharAdvance(c1);
          i += paramInt2;
          paramInt2 = k;
          break;
        case '?':
        case '?':
          c2 = str.charAt(j + 1);
          i += (int)this.B.measureText(new char[] { c1, c2 }, 0, 2);
          paramInt2 = 1;
          break;
        case ' ':
          paramInt2 = getSpaceAdvance();
          i += paramInt2;
          paramInt2 = k;
          break;
        case '\n':
        case '￿':
          paramInt2 = getEOLAdvance();
          i += paramInt2;
          paramInt2 = k;
          break;
        case '\t':
          paramInt2 = a(i);
          i += paramInt2;
          paramInt2 = k;
          break;
      } 
      if (i >= paramInt1)
        break; 
      j++;
    } 
    return (j < str.length()) ? (m + j) : (m + j - 1);
  }
  
  void a(boolean paramBoolean) {
    InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService("input_method");
    if (paramBoolean) {
      inputMethodManager.showSoftInput(this, 0);
      return;
    } 
    inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
  }
  
  int b(int paramInt1, int paramInt2) {
    paramInt2 /= a();
    int i = this.h.d(paramInt2);
    if (i >= 0) {
      if (paramInt1 < 0)
        return -1; 
      String str = this.h.a(paramInt2);
      int i1 = str.length();
      int k = 0;
      int m = 0;
      int j = 0;
      while (k < i1) {
        char c2;
        char c1 = str.charAt(k);
        switch (c1) {
          default:
            if (m) {
              paramInt2 = 0;
              break;
            } 
            paramInt2 = getCharAdvance(c1);
            j += paramInt2;
            paramInt2 = m;
            break;
          case '?':
          case '?':
            c2 = str.charAt(k + 1);
            j += (int)this.B.measureText(new char[] { c1, c2 }, 0, 2);
            paramInt2 = 1;
            break;
          case ' ':
            paramInt2 = getSpaceAdvance();
            j += paramInt2;
            paramInt2 = m;
            break;
          case '\n':
          case '￿':
            paramInt2 = getEOLAdvance();
            j += paramInt2;
            paramInt2 = m;
            break;
          case '\t':
            paramInt2 = a(j);
            j += paramInt2;
            paramInt2 = m;
            break;
        } 
        if (j >= paramInt1)
          break; 
        k++;
        m = paramInt2;
      } 
      if (k < str.length())
        return i + k; 
    } 
    return -1;
  }
  
  protected m b(int paramInt) {
    int k = this.h.b(paramInt);
    int i1 = this.h.d(k);
    int i = this.D;
    int j = this.D;
    String str = this.h.a(k);
    int i2 = str.length();
    k = 0;
    int m = 0;
    while (i1 + m <= paramInt && m < i2) {
      char c2;
      char c1 = str.charAt(m);
      switch (c1) {
        default:
          if (k != 0) {
            i = j;
            k = 0;
            break;
          } 
          i = getCharAdvance(c1);
          i += j;
          break;
        case '?':
        case '?':
          c2 = str.charAt(m + 1);
          i = (int)this.B.measureText(new char[] { c1, c2 }, 0, 2) + j;
          k = 1;
          break;
        case ' ':
          i = getSpaceAdvance();
          i += j;
          break;
        case '\n':
        case '￿':
          i = getEOLAdvance();
          i += j;
          break;
        case '\t':
          i = a(j);
          i += j;
          break;
      } 
      m++;
      int i3 = i;
      i = j;
      j = i3;
    } 
    return new m(i, j);
  }
  
  void b() {
    removeCallbacks(this.T);
    removeCallbacks(this.U);
    removeCallbacks(this.V);
    removeCallbacks(this.W);
  }
  
  Rect c(int paramInt) {
    if (paramInt < 0 || paramInt >= this.h.g())
      return new Rect(-1, -1, -1, -1); 
    int i = this.h.b(paramInt) * a();
    int j = a();
    m m = b(paramInt);
    return new Rect(m.a(), i, m.b(), j + i);
  }
  
  void c(int paramInt1, int paramInt2) {
    this.v.fling(getScrollX(), getScrollY(), paramInt1, paramInt2, 0, getMaxScrollX(), 0, getMaxScrollY());
    postInvalidate();
  }
  
  protected boolean c() {
    return (this.A == 0);
  }
  
  public void cancelSpanning() {
    this.w.b();
  }
  
  public void computeScroll() {
    if (this.v.computeScrollOffset()) {
      scrollTo(this.v.getCurrX(), this.v.getCurrY());
      postInvalidate();
    } 
  }
  
  protected int computeVerticalScrollOffset() {
    return getScrollY();
  }
  
  protected int computeVerticalScrollRange() {
    return this.h.f() * a() + getPaddingTop() + getPaddingBottom();
  }
  
  public void copy() {
    if (this.j != this.k)
      this.w.b(this.G); 
    selectText(false);
  }
  
  public void copy(ClipboardManager paramClipboardManager) {
    this.w.b(paramClipboardManager);
  }
  
  public AccessibilityNodeInfo createAccessibilityNodeInfo() {
    AccessibilityNodeInfo accessibilityNodeInfo = super.createAccessibilityNodeInfo();
    if (Build.VERSION.SDK_INT > 20) {
      accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
      accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
      accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
      accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY);
    } else if (Build.VERSION.SDK_INT > 15) {
      accessibilityNodeInfo.addAction(4096);
      accessibilityNodeInfo.addAction(8192);
      accessibilityNodeInfo.addAction(256);
      accessibilityNodeInfo.addAction(512);
    } 
    if (Build.VERSION.SDK_INT >= 18)
      accessibilityNodeInfo.setTextSelection(getSelectionStart(), getSelectionEnd()); 
    accessibilityNodeInfo.setFocusable(true);
    if (Build.VERSION.SDK_INT >= 18)
      accessibilityNodeInfo.setEditable(true); 
    if (Build.VERSION.SDK_INT >= 19)
      accessibilityNodeInfo.setMultiLine(true); 
    return accessibilityNodeInfo;
  }
  
  public f createDocumentProvider() {
    return new f(this.h);
  }
  
  public void cut() {
    if (this.j != this.k)
      this.w.a(this.G); 
  }
  
  public void cut(ClipboardManager paramClipboardManager) {
    this.w.a(paramClipboardManager);
  }
  
  protected boolean d() {
    return (this.A == this.h.f() - 1);
  }
  
  boolean d(int paramInt) {
    switch (paramInt) {
      default:
        q.a("Invalid scroll direction");
        return false;
      case 3:
        removeCallbacks(this.W);
        if (!e() && this.A == this.h.b(this.i + 1)) {
          Runnable runnable = this.W;
          post(runnable);
          return true;
        } 
        return false;
      case 2:
        removeCallbacks(this.V);
        if (this.i > 0 && this.A == this.h.b(this.i - 1)) {
          Runnable runnable = this.V;
          post(runnable);
          return true;
        } 
        return false;
      case 1:
        removeCallbacks(this.T);
        if (!d()) {
          Runnable runnable = this.T;
          post(runnable);
          return true;
        } 
        return false;
      case 0:
        break;
    } 
    removeCallbacks(this.U);
    if (!c()) {
      Runnable runnable = this.U;
      post(runnable);
      return true;
    } 
    return false;
  }
  
  protected int e(int paramInt) {
    boolean bool;
    int i = this.h.b(paramInt);
    if (i >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    q.a(bool, "Invalid char offset given to getColumn");
    return paramInt - this.h.d(i);
  }
  
  protected boolean e() {
    return (this.i == this.h.g() - 1);
  }
  
  public void focusCaret() {
    h(this.i);
  }
  
  public void focusSelectionEnd() {
    this.w.d(false);
  }
  
  public void focusSelectionStart() {
    this.w.d(true);
  }
  
  public void format() {
    selectText(false);
    CharSequence charSequence = com.b.a.b.a.a((CharSequence)new f(this.h), this.q);
    this.h.d();
    this.h.a(0, this.h.g() - 1, System.nanoTime());
    this.h.a(charSequence.toString().toCharArray(), 0, System.nanoTime());
    this.h.e();
    this.h.h();
    respan();
    invalidate();
  }
  
  public int getAdvance(char paramChar) {
    float f1;
    switch (paramChar) {
      default:
        if (this.P != '\000') {
          char c1 = this.P;
          float f2 = this.B.measureText(new char[] { c1, paramChar }, 0, 2);
          return (int)f2;
        } 
        f1 = this.B.measureText(new char[] { paramChar }, 0, 1);
        return (int)f1;
      case ' ':
        return getSpaceAdvance();
      case '\n':
      case '￿':
        return getEOLAdvance();
      case '\t':
        return getTabAdvance();
      case '?':
      case '?':
        break;
    } 
    return 0;
  }
  
  public int getAdvance(char paramChar, int paramInt) {
    float f1;
    switch (paramChar) {
      default:
        if (this.P != '\000') {
          char c1 = this.P;
          float f2 = this.B.measureText(new char[] { c1, paramChar }, 0, 2);
          return (int)f2;
        } 
        f1 = this.B.measureText(new char[] { paramChar }, 0, 1);
        return (int)f1;
      case ' ':
        return getSpaceAdvance();
      case '\n':
      case '￿':
        return getEOLAdvance();
      case '\t':
        return a(paramInt);
      case '?':
      case '?':
        break;
    } 
    return 0;
  }
  
  public int getAutoIndentWidth() {
    return this.q;
  }
  
  public int getCaretPosition() {
    return this.i;
  }
  
  public int getCaretRow() {
    return this.A;
  }
  
  public int getCaretX() {
    return this.I;
  }
  
  public int getCaretY() {
    return this.J;
  }
  
  public int getCharAdvance(char paramChar) {
    return (int)this.B.measureText(new char[] { paramChar }, 0, 1);
  }
  
  public com.b.a.b.b getColorScheme() {
    return this.m;
  }
  
  protected int getContentHeight() {
    return getHeight() - getPaddingTop() - getPaddingBottom();
  }
  
  protected int getContentWidth() {
    return getWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  protected int getEOLAdvance() {
    if (this.o) {
      float f2 = this.B.measureText("↵", 0, "↵".length());
      return (int)f2;
    } 
    float f1 = a * this.B.measureText(" ", 0, 1);
    return (int)f1;
  }
  
  public int getLeftOffset() {
    return this.D;
  }
  
  public int getLength() {
    return this.h.g();
  }
  
  int getMaxScrollX() {
    return isWordWrap() ? this.D : Math.max(0, this.C - getContentWidth() + (this.g.a()).right + this.S);
  }
  
  int getMaxScrollY() {
    return Math.max(0, this.h.f() * a() - getContentHeight() / 2 + (this.g.a()).bottom);
  }
  
  protected int getNumVisibleRows() {
    return (int)Math.ceil(getContentHeight() / a());
  }
  
  public int getPaintBaseline(int paramInt) {
    Paint.FontMetricsInt fontMetricsInt = this.B.getFontMetricsInt();
    return (paramInt + 1) * a() - fontMetricsInt.descent;
  }
  
  public final int getRowWidth() {
    return getContentWidth() - this.D;
  }
  
  public int getSelectionEnd() {
    return (this.k < 0) ? this.i : this.k;
  }
  
  public int getSelectionStart() {
    return (this.j < 0) ? this.i : this.j;
  }
  
  protected int getSpaceAdvance() {
    return this.o ? (int)this.B.measureText("·", 0, "·".length()) : this.aa;
  }
  
  protected int getTabAdvance() {
    if (this.o) {
      int k = this.l;
      int m = (int)this.B.measureText("·", 0, "·".length());
      return k * m;
    } 
    int i = this.l;
    int j = this.aa;
    return i * j;
  }
  
  public float getTextSize() {
    return this.B.getTextSize();
  }
  
  public int getTopOffset() {
    return this.L;
  }
  
  public Parcelable getUiState() {
    return new c(this);
  }
  
  public float getZoom() {
    return this.H;
  }
  
  public boolean hasLayout() {
    return (getWidth() == 0);
  }
  
  public boolean inSelectionRange(int paramInt) {
    return this.w.b(paramInt);
  }
  
  public boolean isAccessibilityEnabled() {
    return this.ac;
  }
  
  public boolean isEdited() {
    return this.f;
  }
  
  public boolean isFlingScrolling() {
    return this.v.isFinished() ^ true;
  }
  
  public boolean isSaveEnabled() {
    return true;
  }
  
  public final boolean isSelectText() {
    return this.w.g();
  }
  
  public final boolean isSelectText2() {
    return this.w.h();
  }
  
  public boolean isShowLineNumbers() {
    return this.E;
  }
  
  public boolean isWordWrap() {
    return this.h.j();
  }
  
  public void moveCaret(int paramInt) {
    this.w.a(paramInt);
  }
  
  public void moveCaretDown() {
    this.w.c();
  }
  
  public void moveCaretLeft() {
    this.w.b(false);
  }
  
  public void moveCaretRight() {
    this.w.a(false);
  }
  
  public void moveCaretUp() {
    this.w.d();
  }
  
  public boolean onCheckIsTextEditor() {
    return true;
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo) {
    paramEditorInfo.inputType = 131073;
    paramEditorInfo.imeOptions = 1342177286;
    if (this.x == null) {
      this.x = new b(this, this);
    } else {
      this.x.a();
    } 
    return (InputConnection)this.x;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.clipRect(getScrollX() + getPaddingLeft(), getScrollY() + getPaddingTop(), getScrollX() + getWidth() - getPaddingRight(), getScrollY() + getHeight() - getPaddingBottom());
    paramCanvas.translate(getPaddingLeft(), getPaddingTop());
    c(paramCanvas);
    paramCanvas.restore();
    this.g.a(paramCanvas);
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect) {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    h();
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent) {
    if ((paramMotionEvent.getSource() & 0x2) == 0 || paramMotionEvent.getAction() != 8)
      return super.onGenericMotionEvent(paramMotionEvent); 
    a(0.0F, -paramMotionEvent.getAxisValue(9) * a());
    return true;
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent) {
    if (this.ac) {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      int i = paramMotionEvent.getAction();
      if (i != 7) {
        switch (i) {
          case 10:
            this.g.a(paramMotionEvent);
            break;
          case 9:
            this.ad = paramMotionEvent;
            break;
        } 
      } else {
        this.g.onScroll(this.ad, paramMotionEvent, this.ae - f1, this.af - f2);
      } 
      this.ae = f1;
      this.af = f2;
    } 
    return super.onHoverEvent(paramMotionEvent);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (this.g.a(paramInt, paramKeyEvent))
      return true; 
    if (d.b(paramKeyEvent)) {
      a(paramInt, paramKeyEvent);
      return true;
    } 
    if (paramInt == 63 || paramInt == 61185) {
      a((String)u.get(61185), false);
      return true;
    } 
    char c1 = d.a(paramKeyEvent);
    if (c1 == '\000')
      return super.onKeyDown(paramInt, paramKeyEvent); 
    paramInt = paramKeyEvent.getRepeatCount();
    if (paramInt == 1) {
      if (this.r) {
        a(c1);
        return true;
      } 
      b(c1);
      return true;
    } 
    if (paramInt == 0 || (this.r && !Character.isLowerCase(c1)) || (!this.r && u.get(c1) == null))
      this.w.a(c1); 
    return true;
  }
  
  public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent) {
    if (this.r && paramKeyEvent.getRepeatCount() == 1 && paramKeyEvent.getAction() == 0) {
      char c1 = d.a(paramKeyEvent);
      if (Character.isLowerCase(c1) && c1 == Character.toLowerCase(this.h.charAt(this.i - 1))) {
        this.w.a('\b');
        this.w.a(Character.toUpperCase(c1));
        return true;
      } 
    } 
    return super.onKeyPreIme(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    return this.g.b(paramInt, paramKeyEvent) ? true : super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramBoolean) {
      boolean bool;
      Rect rect = new Rect();
      getWindowVisibleDisplayFrame(rect);
      this.L = rect.top + rect.height() - getHeight();
      if (!this.Q)
        respan(); 
      if (paramInt3 > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.Q = bool;
      invalidate();
      this.s.c(getWidth() / 2);
    } 
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(f(paramInt1), f(paramInt2));
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.h.j() && paramInt3 != paramInt1)
      this.h.k(); 
    this.w.e();
    if (paramInt2 < paramInt4)
      h(this.i); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (isFocused()) {
      this.g.b(paramMotionEvent);
      return true;
    } 
    if ((paramMotionEvent.getAction() & 0xFF) == 1 && e((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()))
      requestFocus(); 
    return true;
  }
  
  public boolean onTrackballEvent(MotionEvent paramMotionEvent) {
    int j;
    int i = Math.round(paramMotionEvent.getX());
    int k = Math.round(paramMotionEvent.getY());
    while (true) {
      j = i;
      if (i > 0) {
        this.w.a(false);
        i--;
        continue;
      } 
      break;
    } 
    while (true) {
      i = k;
      if (j < 0) {
        this.w.b(false);
        j++;
        continue;
      } 
      break;
    } 
    while (true) {
      j = i;
      if (i > 0) {
        this.w.c();
        i--;
        continue;
      } 
      break;
    } 
    while (j < 0) {
      this.w.d();
      j++;
    } 
    return true;
  }
  
  public void paste() {
    CharSequence charSequence = this.G.getText();
    if (charSequence != null)
      this.w.a(charSequence.toString()); 
  }
  
  public void paste(String paramString) {
    this.w.a(paramString);
  }
  
  public boolean performAccessibilityAction(int paramInt, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT < 16)
      return super.performAccessibilityAction(paramInt, paramBundle); 
    if (paramInt != 256) {
      if (paramInt != 512)
        return super.performAccessibilityAction(paramInt, paramBundle); 
      paramInt = paramBundle.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
      if (paramInt != 1) {
        if (paramInt != 4)
          return true; 
        moveCaretUp();
        return true;
      } 
      moveCaretLeft();
      return true;
    } 
    paramInt = paramBundle.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
    if (paramInt != 1) {
      if (paramInt != 4)
        return true; 
      moveCaretDown();
      return true;
    } 
    moveCaretRight();
    return true;
  }
  
  public void replaceText(int paramInt1, int paramInt2, String paramString) {
    this.h.d();
    this.w.a(paramInt1, paramInt2, paramString);
    this.w.f();
    this.h.e();
  }
  
  public void respan() {
    this.w.a();
  }
  
  public void restoreUiState(Parcelable paramParcelable) {
    paramParcelable = paramParcelable;
    int i = ((c)paramParcelable).a;
    if (((c)paramParcelable).d) {
      post(new Runnable(this, ((c)paramParcelable).e, ((c)paramParcelable).f, i) {
            public void run() {
              this.d.setSelectionRange(this.a, this.b - this.a);
              if (this.c < this.b)
                this.d.focusSelectionStart(); 
            }
          });
      return;
    } 
    post(new Runnable(this, i) {
          public void run() {
            this.b.moveCaret(this.a);
          }
        });
  }
  
  public void selectAll() {
    this.w.a(0, this.h.g() - 1, false, true);
  }
  
  public void selectText(boolean paramBoolean) {
    a a1;
    if (this.w.g() && !paramBoolean) {
      i();
      a1 = this.w;
      paramBoolean = false;
    } else if (!this.w.g() && paramBoolean) {
      h();
      a1 = this.w;
      paramBoolean = true;
    } else {
      return;
    } 
    a1.c(paramBoolean);
  }
  
  public void setAutoComplete(boolean paramBoolean) {
    this.t = paramBoolean;
  }
  
  public void setAutoIndent(boolean paramBoolean) {
    this.p = paramBoolean;
  }
  
  public void setAutoIndentWidth(int paramInt) {
    this.q = paramInt;
  }
  
  public void setBoldTypeface(Typeface paramTypeface) {
    this.N = paramTypeface;
  }
  
  public void setChirality(boolean paramBoolean) {
    this.g.a(paramBoolean);
  }
  
  public void setColorScheme(com.b.a.b.b paramb) {
    this.m = paramb;
    this.g.a(paramb);
    setBackgroundColor(paramb.a(com.b.a.b.b.a.b));
  }
  
  public void setDocumentProvider(f paramf) {
    this.h = paramf;
    g();
    this.w.b();
    this.w.a();
    invalidate();
    if (this.ac)
      setContentDescription((CharSequence)this.h); 
  }
  
  public void setEdited(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public void setHighlightCurrentRow(boolean paramBoolean) {
    this.n = paramBoolean;
    h();
  }
  
  public void setItalicTypeface(Typeface paramTypeface) {
    this.O = paramTypeface;
  }
  
  public void setLongPressCaps(boolean paramBoolean) {
    this.r = paramBoolean;
  }
  
  public void setNavigationMethod(g paramg) {
    this.g = paramg;
  }
  
  public void setNonPrintingCharVisibility(boolean paramBoolean) {
    if (this.o ^ paramBoolean) {
      this.o = paramBoolean;
      if (this.h.j())
        this.h.k(); 
      this.w.e();
      if (!h(this.i))
        invalidate(); 
    } 
  }
  
  public void setOnSelectionChangedListener(e parame) {
    this.z = parame;
  }
  
  public void setRowListener(n paramn) {
    this.y = paramn;
  }
  
  public void setSelection(int paramInt1, int paramInt2) {
    this.w.a(paramInt1, paramInt2, true, false);
  }
  
  public void setSelectionRange(int paramInt1, int paramInt2) {
    this.w.a(paramInt1, paramInt2, true, true);
  }
  
  public void setShowLineNumbers(boolean paramBoolean) {
    this.E = paramBoolean;
  }
  
  public void setTabSpaces(int paramInt) {
    if (paramInt < 0)
      return; 
    this.l = paramInt;
    if (this.h.j())
      this.h.k(); 
    this.w.e();
    if (!h(this.i))
      invalidate(); 
  }
  
  public void setTextSize(int paramInt) {
    if (paramInt > 8 && paramInt < 80) {
      float f1 = paramInt;
      if (f1 == this.B.getTextSize())
        return; 
      double d2 = a();
      double d3 = getAdvance('a');
      this.H = (paramInt / d);
      this.B.setTextSize(f1);
      this.R.setTextSize(f1);
      if (this.h.j())
        this.h.k(); 
      this.w.e();
      double d1 = getScrollX();
      d3 = getAdvance('a') / d3;
      double d4 = getScrollY();
      d2 = a() / d2;
      scrollTo((int)(d1 * d3), (int)(d4 * d2));
      this.S = (int)this.B.measureText("a");
      this.aa = (int)this.B.measureText(" ");
      invalidate();
    } 
  }
  
  public void setTypeface(Typeface paramTypeface) {
    this.M = paramTypeface;
    this.N = Typeface.create(paramTypeface, 1);
    this.O = Typeface.create(paramTypeface, 2);
    this.B.setTypeface(paramTypeface);
    this.R.setTypeface(paramTypeface);
    if (this.h.j())
      this.h.k(); 
    this.w.e();
    if (!h(this.i))
      invalidate(); 
  }
  
  public void setWordWrap(boolean paramBoolean) {
    this.h.a(paramBoolean);
    if (paramBoolean) {
      this.C = 0;
      scrollTo(0, 0);
    } 
    this.w.e();
    if (!h(this.i))
      invalidate(); 
  }
  
  public void setZoom(float paramFloat) {
    if (paramFloat > 0.5D && paramFloat < 5.0F) {
      if (paramFloat == this.H)
        return; 
      this.H = paramFloat;
      int i = (int)(paramFloat * d);
      Paint paint = this.B;
      paramFloat = i;
      paint.setTextSize(paramFloat);
      this.R.setTextSize(paramFloat);
      if (this.h.j())
        this.h.k(); 
      this.w.e();
      this.S = (int)this.B.measureText("a");
      invalidate();
    } 
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2) {
    if (getHeight() == 0)
      return; 
    if (AnimationUtils.currentAnimationTimeMillis() - this.ab > 250L) {
      int i = getScrollY();
      int j = getScrollX();
      this.v.startScroll(j, i, paramInt1, paramInt2);
      postInvalidate();
    } else {
      if (!this.v.isFinished())
        this.v.abortAnimation(); 
      scrollBy(paramInt1, paramInt2);
    } 
    this.ab = AnimationUtils.currentAnimationTimeMillis();
  }
  
  public final void smoothScrollTo(int paramInt1, int paramInt2) {
    smoothScrollBy(paramInt1 - getScrollX(), paramInt2 - getScrollY());
  }
  
  public void stopFlingScrolling() {
    this.v.forceFinished(true);
  }
  
  private class a implements k.a {
    private final k b = new k(this);
    
    private boolean c = false;
    
    private boolean d;
    
    private a(c this$0) {}
    
    private void b(int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield a : Lcom/b/a/a/c;
      //   4: invokestatic h : (Lcom/b/a/a/c;)Z
      //   7: ifeq -> 100
      //   10: getstatic android/os/Build$VERSION.SDK_INT : I
      //   13: bipush #16
      //   15: if_icmplt -> 100
      //   18: invokestatic obtain : ()Landroid/view/accessibility/AccessibilityRecord;
      //   21: pop
      //   22: ldc 131072
      //   24: invokestatic obtain : (I)Landroid/view/accessibility/AccessibilityEvent;
      //   27: astore #4
      //   29: iload_1
      //   30: iload_2
      //   31: isub
      //   32: istore_3
      //   33: iload_3
      //   34: iload_3
      //   35: imul
      //   36: iconst_1
      //   37: if_icmpne -> 46
      //   40: aload #4
      //   42: iconst_1
      //   43: invokevirtual setMovementGranularity : (I)V
      //   46: iload_1
      //   47: iload_2
      //   48: if_icmple -> 64
      //   51: sipush #512
      //   54: istore_3
      //   55: aload #4
      //   57: iload_3
      //   58: invokevirtual setAction : (I)V
      //   61: goto -> 71
      //   64: sipush #256
      //   67: istore_3
      //   68: goto -> 55
      //   71: aload #4
      //   73: iload_1
      //   74: iload_2
      //   75: invokestatic min : (II)I
      //   78: invokevirtual setFromIndex : (I)V
      //   81: aload #4
      //   83: iload_1
      //   84: iload_2
      //   85: invokestatic max : (II)I
      //   88: invokevirtual setToIndex : (I)V
      //   91: aload_0
      //   92: getfield a : Lcom/b/a/a/c;
      //   95: aload #4
      //   97: invokevirtual sendAccessibilityEventUnchecked : (Landroid/view/accessibility/AccessibilityEvent;)V
      //   100: aload_0
      //   101: getfield c : Z
      //   104: ifne -> 108
      //   107: return
      //   108: iload_1
      //   109: aload_0
      //   110: getfield a : Lcom/b/a/a/c;
      //   113: getfield k : I
      //   116: if_icmpge -> 162
      //   119: iload_2
      //   120: aload_0
      //   121: getfield a : Lcom/b/a/a/c;
      //   124: getfield k : I
      //   127: if_icmple -> 153
      //   130: aload_0
      //   131: getfield a : Lcom/b/a/a/c;
      //   134: aload_0
      //   135: getfield a : Lcom/b/a/a/c;
      //   138: getfield k : I
      //   141: putfield j : I
      //   144: aload_0
      //   145: getfield a : Lcom/b/a/a/c;
      //   148: iload_2
      //   149: putfield k : I
      //   152: return
      //   153: aload_0
      //   154: getfield a : Lcom/b/a/a/c;
      //   157: iload_2
      //   158: putfield j : I
      //   161: return
      //   162: iload_2
      //   163: aload_0
      //   164: getfield a : Lcom/b/a/a/c;
      //   167: getfield j : I
      //   170: if_icmpge -> 144
      //   173: aload_0
      //   174: getfield a : Lcom/b/a/a/c;
      //   177: aload_0
      //   178: getfield a : Lcom/b/a/a/c;
      //   181: getfield j : I
      //   184: putfield k : I
      //   187: goto -> 153
    }
    
    private char[] j() {
      int i = this.a.h.c(this.a.i);
      int j = this.a.h.e(i);
      this.a.h.f(j);
      boolean bool = false;
      for (i = 0; this.a.h.a(); i++) {
        char c1 = this.a.h.b();
        if ((c1 != ' ' && c1 != '\t') || j + i >= this.a.i)
          break; 
      } 
      int m = i + this.a.q * com.b.a.b.a.a(this.a.h.subSequence(j, this.a.i - j));
      if (m < 0)
        return new char[] { '\n' }; 
      char[] arrayOfChar = new char[m + 1];
      arrayOfChar[0] = '\n';
      this.a.h.f(j);
      i = bool;
      while (i < m)
        arrayOfChar[++i] = ' '; 
      return arrayOfChar;
    }
    
    private void k() {
      int i = c.d(this.a);
      e();
      if (!c.b(this.a, this.a.i)) {
        c.a(this.a, i, i + 1);
        c.m(this.a);
      } 
      f();
    }
    
    public void a() {
      this.b.a(this.a.h);
    }
    
    public void a(char param1Char) {
      // Byte code:
      //   0: aload_0
      //   1: getfield c : Z
      //   4: ifeq -> 16
      //   7: aload_0
      //   8: invokevirtual i : ()V
      //   11: iconst_1
      //   12: istore_2
      //   13: goto -> 18
      //   16: iconst_0
      //   17: istore_2
      //   18: aload_0
      //   19: getfield a : Lcom/b/a/a/c;
      //   22: invokestatic d : (Lcom/b/a/a/c;)I
      //   25: istore_3
      //   26: aload_0
      //   27: getfield a : Lcom/b/a/a/c;
      //   30: getfield h : Lcom/b/a/b/f;
      //   33: iload_3
      //   34: invokevirtual d : (I)I
      //   37: istore #4
      //   39: iload_1
      //   40: bipush #8
      //   42: if_icmpeq -> 351
      //   45: iload_1
      //   46: bipush #10
      //   48: if_icmpeq -> 176
      //   51: aload_0
      //   52: getfield a : Lcom/b/a/a/c;
      //   55: getfield h : Lcom/b/a/b/f;
      //   58: iload_1
      //   59: aload_0
      //   60: getfield a : Lcom/b/a/a/c;
      //   63: getfield i : I
      //   66: invokestatic nanoTime : ()J
      //   69: invokevirtual a : (CIJ)V
      //   72: aload_0
      //   73: iconst_1
      //   74: invokevirtual a : (Z)V
      //   77: aload_0
      //   78: getfield a : Lcom/b/a/a/c;
      //   81: invokestatic i : (Lcom/b/a/a/c;)Lcom/b/a/a/f;
      //   84: astore #5
      //   86: new java/lang/StringBuilder
      //   89: dup
      //   90: invokespecial <init> : ()V
      //   93: astore #6
      //   95: aload #6
      //   97: iload_1
      //   98: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   101: pop
      //   102: aload #6
      //   104: ldc ''
      //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   109: pop
      //   110: aload #5
      //   112: aload #6
      //   114: invokevirtual toString : ()Ljava/lang/String;
      //   117: aload_0
      //   118: getfield a : Lcom/b/a/a/c;
      //   121: getfield i : I
      //   124: iconst_1
      //   125: invokeinterface b : (Ljava/lang/CharSequence;II)V
      //   130: aload_0
      //   131: getfield a : Lcom/b/a/a/c;
      //   134: getfield h : Lcom/b/a/b/f;
      //   137: invokevirtual j : ()Z
      //   140: ifeq -> 585
      //   143: iload_3
      //   144: istore_2
      //   145: iload #4
      //   147: aload_0
      //   148: getfield a : Lcom/b/a/a/c;
      //   151: getfield h : Lcom/b/a/b/f;
      //   154: iload_3
      //   155: invokevirtual d : (I)I
      //   158: if_icmpeq -> 165
      //   161: iload_3
      //   162: iconst_1
      //   163: isub
      //   164: istore_2
      //   165: aload_0
      //   166: getfield a : Lcom/b/a/a/c;
      //   169: iload_2
      //   170: invokestatic a : (Lcom/b/a/a/c;I)V
      //   173: goto -> 585
      //   176: aload_0
      //   177: getfield a : Lcom/b/a/a/c;
      //   180: getfield p : Z
      //   183: ifeq -> 232
      //   186: aload_0
      //   187: invokespecial j : ()[C
      //   190: astore #5
      //   192: aload_0
      //   193: getfield a : Lcom/b/a/a/c;
      //   196: getfield h : Lcom/b/a/b/f;
      //   199: aload #5
      //   201: aload_0
      //   202: getfield a : Lcom/b/a/a/c;
      //   205: getfield i : I
      //   208: invokestatic nanoTime : ()J
      //   211: invokevirtual a : ([CIJ)V
      //   214: aload_0
      //   215: aload_0
      //   216: getfield a : Lcom/b/a/a/c;
      //   219: getfield i : I
      //   222: aload #5
      //   224: arraylength
      //   225: iadd
      //   226: invokevirtual a : (I)V
      //   229: goto -> 258
      //   232: aload_0
      //   233: getfield a : Lcom/b/a/a/c;
      //   236: getfield h : Lcom/b/a/b/f;
      //   239: iload_1
      //   240: aload_0
      //   241: getfield a : Lcom/b/a/a/c;
      //   244: getfield i : I
      //   247: invokestatic nanoTime : ()J
      //   250: invokevirtual a : (CIJ)V
      //   253: aload_0
      //   254: iconst_1
      //   255: invokevirtual a : (Z)V
      //   258: iload_3
      //   259: istore_2
      //   260: aload_0
      //   261: getfield a : Lcom/b/a/a/c;
      //   264: getfield h : Lcom/b/a/b/f;
      //   267: invokevirtual j : ()Z
      //   270: ifeq -> 295
      //   273: iload_3
      //   274: istore_2
      //   275: iload #4
      //   277: aload_0
      //   278: getfield a : Lcom/b/a/a/c;
      //   281: getfield h : Lcom/b/a/b/f;
      //   284: iload_3
      //   285: invokevirtual d : (I)I
      //   288: if_icmpeq -> 295
      //   291: iload_3
      //   292: iconst_1
      //   293: isub
      //   294: istore_2
      //   295: aload_0
      //   296: getfield a : Lcom/b/a/a/c;
      //   299: invokestatic i : (Lcom/b/a/a/c;)Lcom/b/a/a/f;
      //   302: astore #5
      //   304: new java/lang/StringBuilder
      //   307: dup
      //   308: invokespecial <init> : ()V
      //   311: astore #6
      //   313: aload #6
      //   315: iload_1
      //   316: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   319: pop
      //   320: aload #6
      //   322: ldc ''
      //   324: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   327: pop
      //   328: aload #5
      //   330: aload #6
      //   332: invokevirtual toString : ()Ljava/lang/String;
      //   335: aload_0
      //   336: getfield a : Lcom/b/a/a/c;
      //   339: getfield i : I
      //   342: iconst_1
      //   343: invokeinterface a : (Ljava/lang/String;II)V
      //   348: goto -> 165
      //   351: iload_2
      //   352: ifeq -> 358
      //   355: goto -> 585
      //   358: aload_0
      //   359: getfield a : Lcom/b/a/a/c;
      //   362: getfield i : I
      //   365: ifle -> 585
      //   368: aload_0
      //   369: getfield a : Lcom/b/a/a/c;
      //   372: invokestatic i : (Lcom/b/a/a/c;)Lcom/b/a/a/f;
      //   375: astore #5
      //   377: new java/lang/StringBuilder
      //   380: dup
      //   381: invokespecial <init> : ()V
      //   384: astore #6
      //   386: aload #6
      //   388: iload_1
      //   389: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   392: pop
      //   393: aload #6
      //   395: ldc ''
      //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   400: pop
      //   401: aload #5
      //   403: aload #6
      //   405: invokevirtual toString : ()Ljava/lang/String;
      //   408: aload_0
      //   409: getfield a : Lcom/b/a/a/c;
      //   412: getfield i : I
      //   415: iconst_1
      //   416: invokeinterface a : (Ljava/lang/CharSequence;II)V
      //   421: aload_0
      //   422: getfield a : Lcom/b/a/a/c;
      //   425: getfield h : Lcom/b/a/b/f;
      //   428: aload_0
      //   429: getfield a : Lcom/b/a/a/c;
      //   432: getfield i : I
      //   435: iconst_1
      //   436: isub
      //   437: invokestatic nanoTime : ()J
      //   440: invokevirtual a : (IJ)V
      //   443: aload_0
      //   444: getfield a : Lcom/b/a/a/c;
      //   447: getfield h : Lcom/b/a/b/f;
      //   450: aload_0
      //   451: getfield a : Lcom/b/a/a/c;
      //   454: getfield i : I
      //   457: iconst_2
      //   458: isub
      //   459: invokevirtual charAt : (I)C
      //   462: ldc 55357
      //   464: if_icmpeq -> 491
      //   467: aload_0
      //   468: getfield a : Lcom/b/a/a/c;
      //   471: getfield h : Lcom/b/a/b/f;
      //   474: aload_0
      //   475: getfield a : Lcom/b/a/a/c;
      //   478: getfield i : I
      //   481: iconst_2
      //   482: isub
      //   483: invokevirtual charAt : (I)C
      //   486: ldc 55356
      //   488: if_icmpne -> 518
      //   491: aload_0
      //   492: getfield a : Lcom/b/a/a/c;
      //   495: getfield h : Lcom/b/a/b/f;
      //   498: aload_0
      //   499: getfield a : Lcom/b/a/a/c;
      //   502: getfield i : I
      //   505: iconst_2
      //   506: isub
      //   507: invokestatic nanoTime : ()J
      //   510: invokevirtual a : (IJ)V
      //   513: aload_0
      //   514: iconst_1
      //   515: invokevirtual b : (Z)V
      //   518: aload_0
      //   519: iconst_1
      //   520: invokevirtual b : (Z)V
      //   523: aload_0
      //   524: getfield a : Lcom/b/a/a/c;
      //   527: invokestatic d : (Lcom/b/a/a/c;)I
      //   530: iload_3
      //   531: if_icmpge -> 551
      //   534: aload_0
      //   535: getfield a : Lcom/b/a/a/c;
      //   538: aload_0
      //   539: getfield a : Lcom/b/a/a/c;
      //   542: invokestatic d : (Lcom/b/a/a/c;)I
      //   545: invokestatic a : (Lcom/b/a/a/c;I)V
      //   548: goto -> 585
      //   551: aload_0
      //   552: getfield a : Lcom/b/a/a/c;
      //   555: getfield h : Lcom/b/a/b/f;
      //   558: invokevirtual j : ()Z
      //   561: ifeq -> 585
      //   564: iload_3
      //   565: istore_2
      //   566: iload #4
      //   568: aload_0
      //   569: getfield a : Lcom/b/a/a/c;
      //   572: getfield h : Lcom/b/a/b/f;
      //   575: iload_3
      //   576: invokevirtual d : (I)I
      //   579: if_icmpeq -> 165
      //   582: goto -> 161
      //   585: aload_0
      //   586: getfield a : Lcom/b/a/a/c;
      //   589: iconst_1
      //   590: invokevirtual setEdited : (Z)V
      //   593: aload_0
      //   594: invokevirtual a : ()V
      //   597: return
    }
    
    public void a(int param1Int) {
      if (param1Int < 0 || param1Int >= this.a.h.g()) {
        q.a("Invalid caret position");
        return;
      } 
      b(this.a.i, param1Int);
      this.a.i = param1Int;
      k();
    }
    
    void a(int param1Int1, int param1Int2) {
      int i = this.a.i - param1Int1;
      param1Int1 = i;
      if (i < 0)
        param1Int1 = 0; 
      i = this.a.i + param1Int2;
      int j = this.a.h.g() - 1;
      param1Int2 = i;
      if (i > j)
        param1Int2 = j; 
      b(param1Int1, param1Int2 - param1Int1, "");
    }
    
    void a(int param1Int1, int param1Int2, String param1String) {
      int i1;
      boolean bool1;
      boolean bool = this.c;
      boolean bool2 = false;
      if (bool) {
        j = this.a.h.b(this.a.j);
        i1 = this.a.h.d(j);
        i = this.a.k - this.a.j;
        if (i > 0) {
          this.a.i = this.a.j;
          this.a.h.a(this.a.j, i, System.nanoTime());
          if (j != c.d(this.a)) {
            i = 0;
          } else {
            i = 1;
          } 
          bool1 = true;
        } else {
          bool1 = false;
          i = 1;
        } 
        c(false);
      } else {
        j = c.d(this.a);
        i1 = this.a.h.d(c.d(this.a));
        bool1 = false;
        i = 1;
      } 
      int m = j;
      int n = i1;
      int i2 = i;
      if (param1Int2 > 0) {
        i2 = this.a.h.b(param1Int1);
        m = j;
        n = i1;
        if (i2 < j) {
          n = this.a.h.d(i2);
          m = i2;
        } 
        if (m != c.d(this.a))
          i = 0; 
        this.a.i = param1Int1;
        this.a.h.a(param1Int1, param1Int2, System.nanoTime());
        bool1 = true;
        i2 = i;
      } 
      param1Int2 = m;
      int i = n;
      int j = bool1;
      if (param1String != null) {
        param1Int2 = m;
        i = n;
        j = bool1;
        if (param1String.length() > 0) {
          param1Int1 = this.a.h.b(param1Int1);
          param1Int2 = m;
          if (param1Int1 < m) {
            n = this.a.h.d(param1Int1);
            param1Int2 = param1Int1;
          } 
          this.a.h.a(param1String.toCharArray(), this.a.i, System.nanoTime());
          c c1 = this.a;
          c1.i += param1String.length();
          j = 1;
          i = n;
        } 
      } 
      if (j != 0) {
        this.a.setEdited(true);
        a();
      } 
      param1Int1 = c.d(this.a);
      e();
      if (param1Int1 != c.d(this.a)) {
        param1Int1 = bool2;
      } else {
        param1Int1 = i2;
      } 
      if (!c.b(this.a, this.a.i)) {
        j = param1Int2;
        if (this.a.h.j()) {
          j = param1Int2;
          if (i != this.a.h.d(param1Int2))
            j = param1Int2 - 1; 
        } 
        if (param1Int1 != 0 && !this.a.h.j()) {
          c.a(this.a, c.d(this.a), c.d(this.a) + 1);
          return;
        } 
        c.a(this.a, j);
      } 
    }
    
    public void a(int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2) {
      boolean bool;
      if (param1Int1 >= 0 && param1Int2 <= this.a.h.g() - 1 && param1Int2 >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      q.a(bool, "Invalid range to select");
      if (this.c) {
        c.p(this.a);
      } else {
        c.m(this.a);
        if (param1Boolean2) {
          c(true);
        } else {
          this.c = true;
        } 
      } 
      this.a.j = param1Int1;
      this.a.k = this.a.j + param1Int2;
      this.a.i = this.a.k;
      f();
      e();
      if (param1Boolean2)
        c.o(this.a).a(g(), this.a.j, this.a.k); 
      param1Boolean2 = c.b(this.a, this.a.k);
      if (param1Boolean1)
        param1Boolean2 = c.b(this.a, this.a.j); 
      if (!param1Boolean2)
        c.p(this.a); 
    }
    
    public void a(ClipboardManager param1ClipboardManager) {
      b(param1ClipboardManager);
      i();
    }
    
    public void a(String param1String) {
      if (param1String == null)
        return; 
      this.a.h.d();
      i();
      int j = c.d(this.a);
      int i = this.a.h.d(j);
      this.a.h.a(param1String.toCharArray(), this.a.i, System.nanoTime());
      this.a.h.e();
      c c1 = this.a;
      c1.i += param1String.length();
      e();
      this.a.setEdited(true);
      a();
      f();
      if (!c.b(this.a, this.a.i)) {
        if (this.a.h.j() && i != this.a.h.d(j)) {
          i = j - 1;
        } else {
          i = j;
        } 
        if (j == c.d(this.a) && !this.a.h.j()) {
          c.a(this.a, i, i + 1);
          return;
        } 
        c.a(this.a, i);
      } 
    }
    
    public void a(List<m> param1List) {
      this.a.post(new Runnable(this, param1List) {
            public void run() {
              this.b.a.h.a(this.a);
              this.b.a.invalidate();
            }
          });
    }
    
    public void a(boolean param1Boolean) {
      if (!this.a.e()) {
        int i = c.d(this.a);
        c c1 = this.a;
        c1.i++;
        e();
        b(this.a.i - 1, this.a.i);
        if (!c.b(this.a, this.a.i))
          c.a(this.a, i, c.d(this.a) + 1); 
        if (!param1Boolean)
          f(); 
      } 
    }
    
    public void b() {
      this.b.b();
    }
    
    void b(int param1Int1, int param1Int2, String param1String) {
      boolean bool1;
      boolean bool = this.c;
      boolean bool2 = false;
      if (bool) {
        j = this.a.h.b(this.a.j);
        i1 = this.a.h.d(j);
        i = this.a.k - this.a.j;
        if (i > 0) {
          this.a.i = this.a.j;
          this.a.h.a(this.a.j, i, System.nanoTime());
          if (j != c.d(this.a)) {
            i = 0;
          } else {
            i = 1;
          } 
          bool1 = true;
        } else {
          bool1 = false;
          i = 1;
        } 
        c(false);
      } else {
        j = c.d(this.a);
        i1 = this.a.h.d(c.d(this.a));
        bool1 = false;
        i = 1;
      } 
      int m = j;
      int n = i1;
      int i2 = i;
      if (param1Int2 > 0) {
        i2 = this.a.h.b(param1Int1);
        m = j;
        n = i1;
        if (i2 < j) {
          n = this.a.h.d(i2);
          m = i2;
        } 
        if (m != c.d(this.a))
          i = 0; 
        this.a.i = param1Int1;
        this.a.h.a(param1Int1, param1Int2, System.nanoTime());
        bool1 = true;
        i2 = i;
      } 
      int i = m;
      int j = n;
      int i1 = bool1;
      if (param1String != null) {
        i = m;
        j = n;
        i1 = bool1;
        if (param1String.length() > 0) {
          param1Int1 = this.a.h.b(param1Int1);
          i = m;
          if (param1Int1 < m) {
            n = this.a.h.d(param1Int1);
            i = param1Int1;
          } 
          this.a.h.a(param1String.toCharArray(), this.a.i, System.nanoTime());
          c c1 = this.a;
          c1.i += param1String.length();
          i1 = 1;
          j = n;
        } 
      } 
      c.i(this.a).b(param1String, this.a.i, param1String.length() - param1Int2);
      if (i1 != 0) {
        this.a.setEdited(true);
        a();
      } 
      param1Int1 = c.d(this.a);
      e();
      if (param1Int1 != c.d(this.a)) {
        param1Int1 = bool2;
      } else {
        param1Int1 = i2;
      } 
      if (!c.b(this.a, this.a.i)) {
        param1Int2 = i;
        if (this.a.h.j()) {
          param1Int2 = i;
          if (j != this.a.h.d(i))
            param1Int2 = i - 1; 
        } 
        if (param1Int1 != 0 && !this.a.h.j()) {
          c.a(this.a, c.d(this.a), c.d(this.a) + 1);
          return;
        } 
        c.a(this.a, param1Int2);
      } 
    }
    
    public void b(ClipboardManager param1ClipboardManager) {
      if (this.c && this.a.j < this.a.k)
        param1ClipboardManager.setText(this.a.h.subSequence(this.a.j, this.a.k - this.a.j)); 
    }
    
    public void b(boolean param1Boolean) {
      if (this.a.i > 0) {
        int i = c.d(this.a);
        c c1 = this.a;
        c1.i--;
        e();
        b(this.a.i + 1, this.a.i);
        if (!c.b(this.a, this.a.i))
          c.a(this.a, c.d(this.a), i + 1); 
        if (!param1Boolean)
          f(); 
      } 
    }
    
    public boolean b(int param1Int) {
      int i = this.a.j;
      boolean bool2 = false;
      if (i < 0)
        return false; 
      boolean bool1 = bool2;
      if (this.a.j <= param1Int) {
        bool1 = bool2;
        if (param1Int < this.a.k)
          bool1 = true; 
      } 
      return bool1;
    }
    
    String c(int param1Int) {
      int i = this.a.h.g();
      if (this.a.i + param1Int > i - 1) {
        CharSequence charSequence1 = this.a.h.subSequence(this.a.i, i - this.a.i - 1);
        return charSequence1.toString();
      } 
      CharSequence charSequence = this.a.h.subSequence(this.a.i, param1Int);
      return charSequence.toString();
    }
    
    public void c() {
      if (!this.a.d()) {
        int i = this.a.i;
        int j = c.d(this.a);
        int m = j + 1;
        int n = this.a.e(i);
        int i1 = this.a.h.g(j);
        int i2 = this.a.h.g(m);
        if (n < i2) {
          c c1 = this.a;
          c1.i += i1;
        } else {
          c c1 = this.a;
          c1.i += i1 - n + i2 - 1;
        } 
        c.j(this.a);
        b(i, this.a.i);
        if (!c.b(this.a, this.a.i))
          c.a(this.a, j, m + 1); 
        c.k(this.a).a(m);
        f();
      } 
    }
    
    public void c(boolean param1Boolean) {
      byte b;
      c c1;
      if (!(this.c ^ param1Boolean))
        return; 
      if (param1Boolean) {
        this.a.j = this.a.i;
        c1 = this.a;
        b = this.a.i;
      } else {
        c1 = this.a;
        b = -1;
        c1.j = -1;
        c1 = this.a;
      } 
      c1.k = b;
      this.c = param1Boolean;
      this.d = param1Boolean;
      c.o(this.a).a(param1Boolean, this.a.getSelectionStart(), this.a.getSelectionEnd());
    }
    
    String d(int param1Int) {
      int i = this.a.i - param1Int;
      param1Int = i;
      if (i < 0)
        param1Int = 0; 
      return this.a.h.subSequence(param1Int, this.a.i - param1Int).toString();
    }
    
    public void d() {
      if (!this.a.c()) {
        int i = this.a.i;
        int j = c.d(this.a);
        int m = j - 1;
        int n = this.a.e(i);
        int i1 = this.a.h.g(m);
        if (n < i1) {
          c c1 = this.a;
          c1.i -= i1;
        } else {
          c c1 = this.a;
          c1.i -= n + 1;
        } 
        c.l(this.a);
        b(i, this.a.i);
        if (!c.b(this.a, this.a.i))
          c.a(this.a, m, j + 1); 
        c.k(this.a).a(m);
        f();
      } 
    }
    
    public void d(boolean param1Boolean) {
      if (this.c) {
        int i;
        c c1;
        if (param1Boolean && this.a.i != this.a.j) {
          c1 = this.a;
          i = this.a.j;
        } else if (!param1Boolean && this.a.i != this.a.k) {
          c1 = this.a;
          i = this.a.k;
        } else {
          return;
        } 
        c1.i = i;
        k();
        return;
      } 
    }
    
    void e() {
      int i = this.a.h.b(this.a.i);
      if (c.d(this.a) != i) {
        c.c(this.a, i);
        c.k(this.a).a(i);
      } 
    }
    
    public void f() {
      ((InputMethodManager)this.a.getContext().getSystemService("input_method")).restartInput(this.a);
      if (c.n(this.a) != null && c.n(this.a).b())
        c.n(this.a).a(); 
    }
    
    public final boolean g() {
      return this.c;
    }
    
    public final boolean h() {
      return this.d;
    }
    
    public void i() {
      if (!this.c)
        return; 
      int i = this.a.k - this.a.j;
      if (i > 0) {
        boolean bool;
        int j = this.a.h.b(this.a.j);
        int m = this.a.h.d(j);
        if (this.a.h.b(this.a.k) == j) {
          bool = true;
        } else {
          bool = false;
        } 
        c.i(this.a).a("", this.a.i, i);
        this.a.h.a(this.a.j, i, System.nanoTime());
        this.a.i = this.a.j;
        e();
        this.a.setEdited(true);
        a();
        c(false);
        f();
        if (!c.b(this.a, this.a.i)) {
          i = j;
          if (this.a.h.j()) {
            i = j;
            if (m != this.a.h.d(j))
              i = j - 1; 
          } 
          if (bool && !this.a.h.j()) {
            c.a(this.a, i, i + 1);
            return;
          } 
          c.a(this.a, i);
          return;
        } 
      } else {
        c(false);
        c.m(this.a);
      } 
    }
  }
  
  class null implements Runnable {
    null(c this$0, List param1List) {}
    
    public void run() {
      this.b.a.h.a(this.a);
      this.b.a.invalidate();
    }
  }
  
  private class b extends BaseInputConnection {
    private boolean b = false;
    
    private int c = 0;
    
    public b(c this$0, c param1c1) {
      super(param1c1, true);
    }
    
    public void a() {
      this.c = 0;
      this.b = false;
      this.a.h.e();
    }
    
    public boolean b() {
      return this.b;
    }
    
    public boolean commitText(CharSequence param1CharSequence, int param1Int) {
      c.a(this.a).b(this.a.getCaretPosition() - this.c, this.c, param1CharSequence.toString());
      this.c = 0;
      this.a.h.e();
      if (param1Int > 1) {
        c.a(this.a).a(this.a.i + param1Int - 1);
      } else if (param1Int <= 0) {
        c.a(this.a).a(this.a.i - param1CharSequence.length() - param1Int);
      } 
      this.b = false;
      return true;
    }
    
    public boolean deleteSurroundingText(int param1Int1, int param1Int2) {
      if (this.c != 0)
        Log.i("lua", "Warning: Implmentation of InputConnection.deleteSurroundingText will not skip composing text"); 
      c.a(this.a).a(param1Int1, param1Int2);
      return true;
    }
    
    public boolean finishComposingText() {
      a();
      return true;
    }
    
    public int getCursorCapsMode(int param1Int) {
      // Byte code:
      //   0: iconst_1
      //   1: istore #5
      //   3: iload_1
      //   4: sipush #8192
      //   7: iand
      //   8: sipush #8192
      //   11: if_icmpne -> 67
      //   14: aload_0
      //   15: getfield a : Lcom/b/a/a/c;
      //   18: getfield i : I
      //   21: iconst_1
      //   22: isub
      //   23: istore_3
      //   24: iload_3
      //   25: iflt -> 48
      //   28: invokestatic a : ()Lcom/b/a/b/h;
      //   31: aload_0
      //   32: getfield a : Lcom/b/a/a/c;
      //   35: getfield h : Lcom/b/a/b/f;
      //   38: iload_3
      //   39: invokevirtual charAt : (I)C
      //   42: invokevirtual b : (C)Z
      //   45: ifeq -> 169
      //   48: iload_1
      //   49: sipush #16384
      //   52: iand
      //   53: sipush #16384
      //   56: if_icmpne -> 63
      //   59: sipush #24576
      //   62: ireturn
      //   63: sipush #8192
      //   66: ireturn
      //   67: invokestatic a : ()Lcom/b/a/b/h;
      //   70: astore #6
      //   72: aload_0
      //   73: getfield a : Lcom/b/a/a/c;
      //   76: getfield i : I
      //   79: iconst_1
      //   80: isub
      //   81: istore_1
      //   82: iconst_0
      //   83: istore_3
      //   84: iload #5
      //   86: istore #4
      //   88: iload_1
      //   89: iflt -> 160
      //   92: aload_0
      //   93: getfield a : Lcom/b/a/a/c;
      //   96: getfield h : Lcom/b/a/b/f;
      //   99: iload_1
      //   100: invokevirtual charAt : (I)C
      //   103: istore_2
      //   104: iload_2
      //   105: bipush #10
      //   107: if_icmpne -> 117
      //   110: iload #5
      //   112: istore #4
      //   114: goto -> 160
      //   117: aload #6
      //   119: iload_2
      //   120: invokevirtual b : (C)Z
      //   123: ifne -> 149
      //   126: iload_3
      //   127: ifeq -> 143
      //   130: iload #5
      //   132: istore #4
      //   134: aload #6
      //   136: iload_2
      //   137: invokevirtual c : (C)Z
      //   140: ifne -> 160
      //   143: iconst_0
      //   144: istore #4
      //   146: goto -> 160
      //   149: iload_3
      //   150: iconst_1
      //   151: iadd
      //   152: istore_3
      //   153: iload_1
      //   154: iconst_1
      //   155: isub
      //   156: istore_1
      //   157: goto -> 84
      //   160: iload #4
      //   162: ifeq -> 169
      //   165: sipush #16384
      //   168: ireturn
      //   169: iconst_0
      //   170: ireturn
    }
    
    public CharSequence getTextAfterCursor(int param1Int1, int param1Int2) {
      return c.a(this.a).c(param1Int1);
    }
    
    public CharSequence getTextBeforeCursor(int param1Int1, int param1Int2) {
      return c.a(this.a).d(param1Int1);
    }
    
    public boolean performContextMenuAction(int param1Int) {
      switch (param1Int) {
        default:
          switch (param1Int) {
            default:
              return false;
            case 16908328:
            case 16908329:
              break;
          } 
          break;
        case 16908322:
          this.a.paste();
        case 16908321:
          this.a.copy();
        case 16908320:
          this.a.cut();
        case 16908319:
          break;
      } 
      this.a.selectAll();
    }
    
    public boolean reportFullscreenMode(boolean param1Boolean) {
      return false;
    }
    
    public boolean sendKeyEvent(KeyEvent param1KeyEvent) {
      int i = param1KeyEvent.getKeyCode();
      if (i != 59) {
        switch (i) {
          default:
            switch (i) {
              default:
                return super.sendKeyEvent(param1KeyEvent);
              case 123:
                this.a.moveCaret(this.a.h.length());
                return true;
              case 122:
                break;
            } 
            this.a.moveCaret(0);
            return true;
          case 22:
            this.a.moveCaretRight();
            return true;
          case 21:
            this.a.moveCaretLeft();
            return true;
          case 20:
            this.a.moveCaretDown();
            return true;
          case 19:
            break;
        } 
        this.a.moveCaretUp();
        return true;
      } 
      if (this.a.isSelectText()) {
        this.a.selectText(false);
        return true;
      } 
      this.a.selectText(true);
      return true;
    }
    
    public boolean setComposingText(CharSequence param1CharSequence, int param1Int) {
      this.b = true;
      if (!this.a.h.c())
        this.a.h.d(); 
      c.a(this.a).b(this.a.getCaretPosition() - this.c, this.c, param1CharSequence.toString());
      this.c = param1CharSequence.length();
      if (param1Int > 1) {
        c.a(this.a).a(this.a.i + param1Int - 1);
        return true;
      } 
      if (param1Int <= 0)
        c.a(this.a).a(this.a.i - param1CharSequence.length() - param1Int); 
      return true;
    }
    
    public boolean setSelection(int param1Int1, int param1Int2) {
      if (param1Int1 == param1Int2) {
        c.a(this.a).a(param1Int1);
        return true;
      } 
      c.a(this.a).a(param1Int1, param1Int2 - param1Int1, false, true);
      return true;
    }
  }
  
  public static class c implements Parcelable {
    public static final Parcelable.Creator<c> CREATOR = new Parcelable.Creator<c>() {
        public c.c a(Parcel param2Parcel) {
          return new c.c(param2Parcel);
        }
        
        public c.c[] a(int param2Int) {
          return new c.c[param2Int];
        }
      };
    
    final int a;
    
    final int b;
    
    final int c;
    
    final boolean d;
    
    final int e;
    
    final int f;
    
    private c(Parcel param1Parcel) {
      boolean bool;
      this.a = param1Parcel.readInt();
      this.b = param1Parcel.readInt();
      this.c = param1Parcel.readInt();
      if (param1Parcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.d = bool;
      this.e = param1Parcel.readInt();
      this.f = param1Parcel.readInt();
    }
    
    public c(c param1c) {
      this.a = param1c.getCaretPosition();
      this.b = param1c.getScrollX();
      this.c = param1c.getScrollY();
      this.d = param1c.isSelectText();
      this.e = param1c.getSelectionStart();
      this.f = param1c.getSelectionEnd();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
  }
  
  static final class null implements Parcelable.Creator<c> {
    public c.c a(Parcel param1Parcel) {
      return new c.c(param1Parcel);
    }
    
    public c.c[] a(int param1Int) {
      return new c.c[param1Int];
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\b\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */