package com.android.cglib.dx;

import com.android.cglib.dx.c.b.p;
import com.android.cglib.dx.c.b.r;
import com.android.cglib.dx.c.d.e;

public enum BinaryOp {
  ADD {
    p rop(e param1e) {
      return r.g(param1e);
    }
  },
  AND,
  DIVIDE,
  MULTIPLY,
  OR,
  REMAINDER,
  SHIFT_LEFT,
  SHIFT_RIGHT,
  SUBTRACT {
    p rop(e param1e) {
      return r.h(param1e);
    }
  },
  UNSIGNED_SHIFT_RIGHT,
  XOR;
  
  static {
    MULTIPLY = new null("MULTIPLY", 2);
    DIVIDE = new null("DIVIDE", 3);
    REMAINDER = new null("REMAINDER", 4);
    AND = new null("AND", 5);
    OR = new null("OR", 6);
    XOR = new null("XOR", 7);
    SHIFT_LEFT = new null("SHIFT_LEFT", 8);
    SHIFT_RIGHT = new null("SHIFT_RIGHT", 9);
    UNSIGNED_SHIFT_RIGHT = new null("UNSIGNED_SHIFT_RIGHT", 10);
    $VALUES = new BinaryOp[] { 
        ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, AND, OR, XOR, SHIFT_LEFT, SHIFT_RIGHT, 
        UNSIGNED_SHIFT_RIGHT };
  }
  
  abstract p rop(e parame);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\BinaryOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */