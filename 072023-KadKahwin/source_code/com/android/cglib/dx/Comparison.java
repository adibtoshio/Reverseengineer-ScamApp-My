package com.android.cglib.dx;

import com.android.cglib.dx.c.b.p;
import com.android.cglib.dx.c.b.r;
import com.android.cglib.dx.c.d.e;

public enum Comparison {
  EQ,
  GE,
  GT,
  LE,
  LT {
    p rop(e param1e) {
      return r.c(param1e);
    }
  },
  NE;
  
  static {
    LE = new null("LE", 1);
    EQ = new null("EQ", 2);
    GE = new null("GE", 3);
    GT = new null("GT", 4);
    NE = new null("NE", 5);
    $VALUES = new Comparison[] { LT, LE, EQ, GE, GT, NE };
  }
  
  abstract p rop(e parame);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\Comparison.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */