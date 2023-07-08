package com.androlua;

import android.graphics.Bitmap;
import com.a.a.a.a.a.a.a;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class GifDecoder extends Thread {
  public static final int STATUS_FINISH = -1;
  
  public static final int STATUS_FORMAT_ERROR = 1;
  
  public static final int STATUS_OPEN_ERROR = 2;
  
  public static final int STATUS_PARSING = 0;
  
  private boolean A = false;
  
  private byte[] B = new byte[256];
  
  private int C = 0;
  
  private int D = 0;
  
  private int E = 0;
  
  private boolean F = false;
  
  private int G = 0;
  
  private int H;
  
  private short[] I;
  
  private byte[] J;
  
  private byte[] K;
  
  private byte[] L;
  
  private GifFrame M;
  
  private int N;
  
  private GifAction O = null;
  
  private byte[] P = null;
  
  private InputStream a;
  
  private int b;
  
  private boolean c;
  
  private int d;
  
  private int e = 1;
  
  private int[] f;
  
  private int[] g;
  
  private int[] h;
  
  public int height;
  
  private int i;
  
  private int j;
  
  private int k;
  
  private int l;
  
  private boolean m;
  
  private boolean n;
  
  private int o;
  
  private int p;
  
  private int q;
  
  private int r;
  
  private int s;
  
  private int t;
  
  private int u;
  
  private int v;
  
  private int w;
  
  public int width;
  
  private Bitmap x;
  
  private Bitmap y;
  
  private GifFrame z = null;
  
  public GifDecoder(InputStream paramInputStream, GifAction paramGifAction) {
    this.a = paramInputStream;
    this.O = paramGifAction;
  }
  
  public GifDecoder(String paramString, GifAction paramGifAction) {
    this.a = new FileInputStream(paramString);
    this.O = paramGifAction;
  }
  
  public GifDecoder(byte[] paramArrayOfbyte, GifAction paramGifAction) {
    this.P = paramArrayOfbyte;
    this.O = paramGifAction;
  }
  
  private void a() {
    int[] arrayOfInt = new int[this.width * this.height];
    int i = this.E;
    int k = 0;
    if (i > 0) {
      if (this.E == 3) {
        Bitmap bitmap;
        i = this.N - 2;
        if (i > 0) {
          bitmap = getFrameImage(i - 1);
        } else {
          bitmap = null;
        } 
        this.y = bitmap;
      } 
      if (this.y != null) {
        this.y.getPixels(arrayOfInt, 0, this.width, 0, 0, this.width, this.height);
        if (this.E == 2) {
          boolean bool;
          if (!this.F) {
            bool = this.k;
          } else {
            bool = false;
          } 
          for (int i1 = 0; i1 < this.w; i1++) {
            int i2 = (this.u + i1) * this.width + this.t;
            int i3 = this.v;
            for (i = i2; i < i3 + i2; i++)
              arrayOfInt[i] = bool; 
          } 
        } 
      } 
    } 
    int j = 0;
    int n = 1;
    int m;
    for (m = 8; k < this.s; m = i1) {
      int i1;
      if (this.n) {
        i = j;
        int i2 = n;
        i1 = m;
        if (j >= this.s) {
          i2 = n + 1;
          switch (i2) {
            default:
              i = j;
              i1 = m;
              break;
            case 4:
              i = 1;
              i1 = 2;
              break;
            case 3:
              i = 2;
              i1 = 4;
              break;
            case 2:
              i = 4;
              i1 = m;
              break;
          } 
        } 
        m = i + i1;
        j = i;
        n = i2;
        i = m;
      } else {
        i = j;
        j = k;
        i1 = m;
      } 
      j += this.q;
      if (j < this.height) {
        int i3 = j * this.width;
        int i2 = this.p + i3;
        m = this.r + i2;
        j = m;
        if (this.width + i3 < m)
          j = this.width + i3; 
        for (m = this.r * k; i2 < j; m++) {
          i3 = this.L[m];
          i3 = this.h[i3 & 0xFF];
          if (i3 != 0)
            arrayOfInt[i2] = i3; 
          i2++;
        } 
      } 
      k++;
      j = i;
    } 
    this.x = Bitmap.createBitmap(arrayOfInt, this.width, this.height, Bitmap.Config.ARGB_4444);
  }
  
  private int[] a(int paramInt) {
    int j = paramInt * 3;
    byte[] arrayOfByte = new byte[j];
    int k = 0;
    try {
      i = this.a.read(arrayOfByte);
    } catch (Exception exception) {
      a.a(exception);
      i = 0;
    } 
    if (i < j) {
      this.b = 1;
      return null;
    } 
    int[] arrayOfInt = new int[256];
    j = 0;
    for (int i = k; i < paramInt; i++) {
      k = j + 1;
      j = arrayOfByte[j];
      int m = k + 1;
      arrayOfInt[i] = (j & 0xFF) << 16 | 0xFF000000 | (arrayOfByte[k] & 0xFF) << 8 | arrayOfByte[m] & 0xFF;
      j = m + 1;
    } 
    return arrayOfInt;
  }
  
  private int b() {
    this.a = new ByteArrayInputStream(this.P);
    this.P = null;
    return c();
  }
  
  private int c() {
    f();
    if (this.a != null) {
      k();
      if (!e()) {
        i();
        if (this.N < 0) {
          this.b = 1;
        } else {
          this.b = -1;
          this.O.parseOk(true, -1);
          try {
            this.a.close();
          } catch (Exception exception) {
            a.a(exception);
          } 
        } 
      } 
      this.O.parseOk(false, -1);
    } else {
      this.b = 2;
      this.O.parseOk(false, -1);
      return this.b;
    } 
    try {
      this.a.close();
    } catch (Exception exception) {
      a.a(exception);
    } 
  }
  
  private void d() {
    int i13 = this.r * this.s;
    if (this.L == null || this.L.length < i13)
      this.L = new byte[i13]; 
    if (this.I == null)
      this.I = new short[4096]; 
    if (this.J == null)
      this.J = new byte[4096]; 
    if (this.K == null)
      this.K = new byte[4097]; 
    int i = g();
    int i12 = 1 << i;
    int i9 = i12 + 2;
    int i10 = (1 << ++i) - 1;
    int j;
    for (j = 0; j < i12; j++) {
      this.I[j] = 0;
      this.J[j] = (byte)j;
    } 
    int k = i;
    int i7 = i9;
    j = i10;
    int m = -1;
    int i11 = 0;
    int n = 0;
    int i2 = 0;
    int i5 = 0;
    int i3 = 0;
    int i6 = 0;
    int i8 = 0;
    int i4 = 0;
    int i1 = i12;
    while (i11 < i13) {
      int i14;
      if (!n) {
        if (i2 < k) {
          int i17 = i5;
          if (!i5) {
            i17 = h();
            if (i17 <= 0)
              break; 
            i6 = 0;
          } 
          i3 += (this.B[i6] & 0xFF) << i2;
          i2 += 8;
          i6++;
          i5 = i17 - 1;
          continue;
        } 
        i14 = i3 & j;
        i3 >>= k;
        int i15 = i2 - k;
        if (i14 > i7 || i14 == i12 + 1)
          break; 
        if (i14 == i1) {
          k = i;
          i7 = i9;
          j = i10;
          m = -1;
          i2 = i15;
          continue;
        } 
        if (m == -1) {
          this.K[n] = this.J[i14];
          m = i14;
          i8 = m;
          n++;
          i2 = i15;
          continue;
        } 
        if (i14 == i7) {
          byte[] arrayOfByte1 = this.K;
          int i17 = n + 1;
          arrayOfByte1[n] = (byte)i8;
          i2 = m;
          n = i17;
        } else {
          i2 = i14;
        } 
        while (i2 > i1) {
          this.K[n] = this.J[i2];
          i2 = this.I[i2];
          n++;
        } 
        int i16 = i1;
        i8 = this.J[i2] & 0xFF;
        if (i7 >= 4096)
          break; 
        byte[] arrayOfByte = this.K;
        i2 = n + 1;
        byte b = (byte)i8;
        arrayOfByte[n] = b;
        this.I[i7] = (short)m;
        this.J[i7] = b;
        if ((++i7 & j) == 0) {
          i1 = k;
          n = j;
          if (i7 < 4096) {
            i1 = k + 1;
            n = j + i7;
          } 
        } else {
          n = j;
          i1 = k;
        } 
        m = i14;
        i14 = i2;
        i2 = i15;
        k = i1;
        j = n;
        i1 = i16;
      } else {
        i14 = n;
      } 
      n = i14 - 1;
      this.L[i4] = this.K[n];
      i11++;
      i4++;
    } 
    while (i4 < i13) {
      this.L[i4] = 0;
      i4++;
    } 
  }
  
  private boolean e() {
    return (this.b != 0);
  }
  
  private void f() {
    this.b = 0;
    this.N = 0;
    this.M = null;
    this.f = null;
    this.g = null;
  }
  
  private int g() {
    try {
      return this.a.read();
    } catch (Exception exception) {
      this.b = 1;
      return 0;
    } 
  }
  
  private int h() {
    this.C = g();
    int k = this.C;
    int j = 0;
    int i = 0;
    if (k > 0) {
      try {
        while (i < this.C) {
          j = this.a.read(this.B, i, this.C - i);
          if (j == -1)
            break; 
          i += j;
        } 
      } catch (Exception exception) {
        a.a(exception);
      } 
      j = i;
      if (i < this.C) {
        this.b = 1;
        j = i;
      } 
    } 
    return j;
  }
  
  private void i() {
    boolean bool = false;
    while (!bool && !e()) {
      int i = g();
      if (i != 0) {
        if (i != 33) {
          if (i != 44) {
            if (i != 59) {
              this.b = 1;
              continue;
            } 
            bool = true;
            continue;
          } 
          l();
          continue;
        } 
        i = g();
        if (i != 249) {
          if (i == 255) {
            h();
            String str = "";
            for (i = 0; i < 11; i++) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(str);
              stringBuilder.append((char)this.B[i]);
              str = stringBuilder.toString();
            } 
            if (str.equals("NETSCAPE2.0")) {
              n();
              continue;
            } 
          } 
          q();
          continue;
        } 
        j();
      } 
    } 
  }
  
  private void j() {
    g();
    int i = g();
    this.D = (i & 0x1C) >> 2;
    int j = this.D;
    boolean bool = true;
    if (j == 0)
      this.D = 1; 
    if ((i & 0x1) == 0)
      bool = false; 
    this.F = bool;
    this.G = o() * 10;
    this.H = g();
    g();
  }
  
  private void k() {
    String str = "";
    for (int i = 0; i < 6; i++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append((char)g());
      str = stringBuilder.toString();
    } 
    if (!str.startsWith("GIF")) {
      this.b = 1;
      return;
    } 
    m();
    if (this.c && !e()) {
      this.f = a(this.d);
      this.j = this.f[this.i];
    } 
  }
  
  private void l() {
    boolean bool;
    this.p = o();
    this.q = o();
    this.r = o();
    this.s = o();
    int i = g();
    if ((i & 0x80) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.m = bool;
    if ((i & 0x40) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.n = bool;
    this.o = 2 << (i & 0x7);
    if (this.m) {
      this.g = a(this.o);
      this.h = this.g;
    } else {
      this.h = this.f;
      if (this.i == this.H)
        this.j = 0; 
    } 
    if (this.F) {
      i = this.h[this.H];
      this.h[this.H] = 0;
    } else {
      i = 0;
    } 
    if (this.h == null)
      this.b = 1; 
    if (e())
      return; 
    d();
    q();
    if (e())
      return; 
    this.N++;
    this.x = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_4444);
    a();
    if (this.M == null) {
      this.M = new GifFrame(this.x, this.G);
      this.z = this.M;
    } else {
      GifFrame gifFrame;
      for (gifFrame = this.M; gifFrame.nextFrame != null; gifFrame = gifFrame.nextFrame);
      gifFrame.nextFrame = new GifFrame(this.x, this.G);
    } 
    if (this.F)
      this.h[this.H] = i; 
    p();
    this.O.parseOk(true, this.N);
  }
  
  private void m() {
    boolean bool;
    this.width = o();
    this.height = o();
    int i = g();
    if ((i & 0x80) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.c = bool;
    this.d = 2 << (i & 0x7);
    this.i = g();
    this.l = g();
  }
  
  private void n() {
    do {
      h();
      if (this.B[0] != 1)
        continue; 
      this.e = this.B[1] & 0xFF | (this.B[2] & 0xFF) << 8;
    } while (this.C > 0 && !e());
  }
  
  private int o() {
    return g() | g() << 8;
  }
  
  private void p() {
    this.E = this.D;
    this.t = this.p;
    this.u = this.q;
    this.v = this.r;
    this.w = this.s;
    this.y = this.x;
    this.k = this.j;
    this.D = 0;
    this.F = false;
    this.G = 0;
    this.g = null;
  }
  
  private void q() {
    do {
      h();
    } while (this.C > 0 && !e());
  }
  
  public void free() {
    // Byte code:
    //   0: aload_0
    //   1: getfield M : Lcom/androlua/GifDecoder$GifFrame;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 28
    //   9: aload_1
    //   10: aconst_null
    //   11: putfield image : Landroid/graphics/Bitmap;
    //   14: aload_0
    //   15: aload_0
    //   16: getfield M : Lcom/androlua/GifDecoder$GifFrame;
    //   19: getfield nextFrame : Lcom/androlua/GifDecoder$GifFrame;
    //   22: putfield M : Lcom/androlua/GifDecoder$GifFrame;
    //   25: goto -> 0
    //   28: aload_0
    //   29: getfield a : Ljava/io/InputStream;
    //   32: ifnull -> 47
    //   35: aload_0
    //   36: getfield a : Ljava/io/InputStream;
    //   39: invokevirtual close : ()V
    //   42: aload_0
    //   43: aconst_null
    //   44: putfield a : Ljava/io/InputStream;
    //   47: aload_0
    //   48: aconst_null
    //   49: putfield P : [B
    //   52: return
    //   53: astore_1
    //   54: goto -> 42
    // Exception table:
    //   from	to	target	type
    //   35	42	53	java/lang/Exception
  }
  
  public GifFrame getCurrentFrame() {
    return this.z;
  }
  
  public int getDelay(int paramInt) {
    this.G = -1;
    if (paramInt >= 0 && paramInt < this.N) {
      GifFrame gifFrame = getFrame(paramInt);
      if (gifFrame != null)
        this.G = gifFrame.delay; 
    } 
    return this.G;
  }
  
  public int[] getDelays() {
    GifFrame gifFrame = this.M;
    int[] arrayOfInt = new int[this.N];
    for (int i = 0; gifFrame != null && i < this.N; i++) {
      arrayOfInt[i] = gifFrame.delay;
      gifFrame = gifFrame.nextFrame;
    } 
    return arrayOfInt;
  }
  
  public GifFrame getFrame(int paramInt) {
    GifFrame gifFrame = this.M;
    for (int i = 0; gifFrame != null; i++) {
      if (i == paramInt)
        return gifFrame; 
      gifFrame = gifFrame.nextFrame;
    } 
    return null;
  }
  
  public int getFrameCount() {
    return this.N;
  }
  
  public Bitmap getFrameImage(int paramInt) {
    GifFrame gifFrame = getFrame(paramInt);
    return (gifFrame == null) ? null : gifFrame.image;
  }
  
  public Bitmap getImage() {
    return getFrameImage(0);
  }
  
  public int getLoopCount() {
    return this.e;
  }
  
  public int getStatus() {
    return this.b;
  }
  
  public GifFrame next() {
    GifFrame gifFrame;
    if (!this.A) {
      this.A = true;
      return this.M;
    } 
    if (this.b == 0) {
      if (this.z.nextFrame != null) {
        gifFrame = this.z.nextFrame;
      } else {
        return this.z;
      } 
    } else {
      this.z = this.z.nextFrame;
      if (this.z == null) {
        gifFrame = this.M;
      } else {
        return this.z;
      } 
    } 
    this.z = gifFrame;
    return this.z;
  }
  
  public boolean parseOk() {
    return (this.b == -1);
  }
  
  public void reset() {
    this.z = this.M;
  }
  
  public void run() {
    if (this.a != null) {
      c();
      return;
    } 
    if (this.P != null)
      b(); 
  }
  
  public static interface GifAction {
    void parseOk(boolean param1Boolean, int param1Int);
  }
  
  public static class GifFrame {
    public int delay;
    
    public Bitmap image;
    
    public GifFrame nextFrame = null;
    
    public GifFrame(Bitmap param1Bitmap, int param1Int) {
      this.image = param1Bitmap;
      this.delay = param1Int;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\GifDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */