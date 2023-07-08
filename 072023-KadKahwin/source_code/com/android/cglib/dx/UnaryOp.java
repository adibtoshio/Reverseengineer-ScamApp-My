package com.android.cglib.dx;

import com.android.cglib.dx.c.b.p;
import com.android.cglib.dx.c.b.r;
import com.android.cglib.dx.c.d.d;

public enum UnaryOp {
  NEGATE,
  NOT {
    p rop(TypeId<?> param1TypeId) {
      return r.g((d)param1TypeId.b);
    }
  };
  
  static {
    NEGATE = new null("NEGATE", 1);
    $VALUES = new UnaryOp[] { NOT, NEGATE };
  }
  
  abstract p rop(TypeId<?> paramTypeId);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\UnaryOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */