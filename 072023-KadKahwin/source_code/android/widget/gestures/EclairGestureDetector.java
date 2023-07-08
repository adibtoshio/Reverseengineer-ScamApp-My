package android.widget.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;

@TargetApi(5)
public class EclairGestureDetector extends CupcakeGestureDetector {
  private static final int INVALID_POINTER_ID = -1;
  
  private int mActivePointerId = -1;
  
  private int mActivePointerIndex = 0;
  
  public EclairGestureDetector(Context paramContext) {
    super(paramContext);
  }
  
  @Override
  float getActiveX(MotionEvent paramMotionEvent) {
    try {
      return paramMotionEvent.getX(this.mActivePointerIndex);
    } catch (Exception exception) {
      return paramMotionEvent.getX();
    } 
  }
  
  @Override
  float getActiveY(MotionEvent paramMotionEvent) {
    try {
      return paramMotionEvent.getY(this.mActivePointerIndex);
    } catch (Exception exception) {
      return paramMotionEvent.getY();
    } 
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getAction : ()I
    //   4: sipush #255
    //   7: iand
    //   8: tableswitch default -> 52, 0 -> 82, 1 -> 94, 2 -> 52, 3 -> 94, 4 -> 52, 5 -> 52, 6 -> 102
    //   52: aload_0
    //   53: getfield mActivePointerId : I
    //   56: iconst_m1
    //   57: if_icmpeq -> 163
    //   60: aload_0
    //   61: getfield mActivePointerId : I
    //   64: istore_2
    //   65: aload_0
    //   66: aload_1
    //   67: iload_2
    //   68: invokevirtual findPointerIndex : (I)I
    //   71: putfield mActivePointerIndex : I
    //   74: aload_0
    //   75: aload_1
    //   76: invokespecial onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   79: istore_3
    //   80: iload_3
    //   81: ireturn
    //   82: aload_0
    //   83: aload_1
    //   84: iconst_0
    //   85: invokevirtual getPointerId : (I)I
    //   88: putfield mActivePointerId : I
    //   91: goto -> 52
    //   94: aload_0
    //   95: iconst_m1
    //   96: putfield mActivePointerId : I
    //   99: goto -> 52
    //   102: aload_1
    //   103: invokevirtual getAction : ()I
    //   106: invokestatic getPointerIndex : (I)I
    //   109: istore_2
    //   110: aload_1
    //   111: iload_2
    //   112: invokevirtual getPointerId : (I)I
    //   115: aload_0
    //   116: getfield mActivePointerId : I
    //   119: if_icmpne -> 155
    //   122: iload_2
    //   123: ifne -> 158
    //   126: iconst_1
    //   127: istore_2
    //   128: aload_0
    //   129: aload_1
    //   130: iload_2
    //   131: invokevirtual getPointerId : (I)I
    //   134: putfield mActivePointerId : I
    //   137: aload_0
    //   138: aload_1
    //   139: iload_2
    //   140: invokevirtual getX : (I)F
    //   143: putfield mLastTouchX : F
    //   146: aload_0
    //   147: aload_1
    //   148: iload_2
    //   149: invokevirtual getY : (I)F
    //   152: putfield mLastTouchY : F
    //   155: goto -> 52
    //   158: iconst_0
    //   159: istore_2
    //   160: goto -> 128
    //   163: iconst_0
    //   164: istore_2
    //   165: goto -> 65
    //   168: astore_1
    //   169: iconst_1
    //   170: ireturn
    // Exception table:
    //   from	to	target	type
    //   74	80	168	java/lang/IllegalArgumentException
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\gestures\EclairGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */