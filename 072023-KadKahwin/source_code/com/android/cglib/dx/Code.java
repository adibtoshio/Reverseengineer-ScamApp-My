package com.android.cglib.dx;

import com.android.cglib.dx.c.b.c;
import com.android.cglib.dx.c.b.f;
import com.android.cglib.dx.c.b.j;
import com.android.cglib.dx.c.b.k;
import com.android.cglib.dx.c.b.n;
import com.android.cglib.dx.c.b.p;
import com.android.cglib.dx.c.b.r;
import com.android.cglib.dx.c.b.s;
import com.android.cglib.dx.c.b.t;
import com.android.cglib.dx.c.b.u;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.l;
import com.android.cglib.dx.c.d.b;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.c.d.d;
import com.android.cglib.dx.c.d.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Code {
  private final MethodId<?, ?> a;
  
  private final List<Label> b = new ArrayList<Label>();
  
  private Label c;
  
  private boolean d;
  
  private final Local<?> e;
  
  private final List<Local<?>> f = new ArrayList<Local<?>>();
  
  private final List<Local<?>> g = new ArrayList<Local<?>>();
  
  private s h = s.a;
  
  private final List<TypeId<?>> i = new ArrayList<TypeId<?>>();
  
  private final List<Label> j = new ArrayList<Label>();
  
  private b k = b.a;
  
  Code(DexMaker.b paramb) {
    this.a = paramb.a;
    if (paramb.a()) {
      this.e = null;
    } else {
      this.e = Local.a(this, this.a.a);
      this.f.add(this.e);
    } 
    for (TypeId<?> typeId : this.a.d.a)
      this.f.add(Local.a(this, typeId)); 
    this.c = new Label();
    a(this.c);
    this.c.c = true;
  }
  
  private <T> Local<T> a(Local<?> paramLocal, TypeId<T> paramTypeId) {
    if (!paramLocal.a.equals(paramTypeId)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("requested ");
      stringBuilder.append(paramTypeId);
      stringBuilder.append(" but was ");
      stringBuilder.append(paramLocal.a);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return (Local)paramLocal;
  }
  
  private static n a(Local<?> paramLocal, Local<?>[] paramArrayOfLocal) {
    byte b1;
    byte b2 = 0;
    if (paramLocal != null) {
      b1 = 1;
    } else {
      b1 = 0;
    } 
    n n = new n(paramArrayOfLocal.length + b1);
    int i = b2;
    if (paramLocal != null) {
      n.a(0, paramLocal.b());
      i = b2;
    } 
    while (i < paramArrayOfLocal.length) {
      n.a(i + b1, paramArrayOfLocal[i].b());
      i++;
    } 
    return n;
  }
  
  private p a(c paramc1, c paramc2) {
    if (paramc1.c() == 6) {
      int i = paramc2.c();
      if (i != 8) {
        switch (i) {
          default:
            return r.a((d)paramc2, (d)paramc1);
          case 3:
            return r.bu;
          case 2:
            break;
        } 
        return r.bt;
      } 
      return r.bv;
    } 
  }
  
  private b a(List<TypeId<?>> paramList) {
    b b1 = new b(paramList.size());
    for (int i = 0; i < paramList.size(); i++)
      b1.a(i, ((TypeId)paramList.get(i)).b); 
    return b1;
  }
  
  private void a(Label paramLabel) {
    if (paramLabel.b == this)
      return; 
    if (paramLabel.b != null)
      throw new IllegalArgumentException("Cannot adopt label; it belongs to another Code"); 
    paramLabel.b = this;
    this.b.add(paramLabel);
  }
  
  private void a(Label paramLabel, List<Label> paramList) {
    Label label = new Label();
    a(label);
    this.c.e = label;
    this.c.f = paramLabel;
    this.c.d = paramList;
    this.c = label;
    this.c.c = true;
  }
  
  private void a(Local<?> paramLocal, boolean paramBoolean) {
    p p;
    if (paramBoolean) {
      p = r.d((d)paramLocal.a.b);
    } else {
      p = r.c((d)paramLocal.a.b);
    } 
    a((f)new k(p, this.h, paramLocal.b(), n.a));
  }
  
  private void a(f paramf) {
    a(paramf, (Label)null);
  }
  
  private void a(f paramf, Label paramLabel) {
    if (this.c == null || !this.c.c)
      throw new IllegalStateException("no current label"); 
    this.c.a.add(paramf);
    int i = paramf.d().b();
    if (i != 6) {
      switch (i) {
        default:
          throw new IllegalArgumentException();
        case 4:
          if (paramLabel == null)
            throw new IllegalArgumentException("branch == null"); 
          a(paramLabel, Collections.emptyList());
          return;
        case 3:
          if (paramLabel == null)
            throw new IllegalArgumentException("branch == null"); 
          this.c.e = paramLabel;
          this.c = null;
          return;
        case 2:
          if (paramLabel != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected branch: ");
            stringBuilder.append(paramLabel);
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
          this.c = null;
          return;
        case 1:
          break;
      } 
      if (paramLabel != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected branch: ");
        stringBuilder.append(paramLabel);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return;
    } 
    if (paramLabel != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unexpected branch: ");
      stringBuilder.append(paramLabel);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    a((Label)null, new ArrayList<Label>(this.j));
  }
  
  private <D, R> void a(p paramp, MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    a((f)new t(paramp, this.h, a(paramLocal1, paramVarArgs), (e)this.k, (a)paramMethodId.f));
    if (paramLocal != null)
      a(paramLocal, false); 
  }
  
  private void d() {
    Iterator<Label> iterator = this.b.iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      Label label = iterator.next();
      if (label.a()) {
        iterator.remove();
        continue;
      } 
      label.b();
      label.g = i;
    } 
  }
  
  void a() {
    if (this.d)
      throw new AssertionError(); 
    this.d = true;
    Iterator<Local<?>> iterator1 = this.g.iterator();
    int i;
    for (i = 0; iterator1.hasNext(); i += ((Local)iterator1.next()).a(i));
    ArrayList<j> arrayList = new ArrayList();
    Iterator<Local<?>> iterator2 = this.f.iterator();
    int j = i;
    while (iterator2.hasNext()) {
      Local local = iterator2.next();
      l l = l.a(j - i);
      j += local.a(j);
      arrayList.add(new j(r.b((d)local.a.b), this.h, local.b(), n.a, (a)l));
    } 
    ((Label)this.b.get(0)).a.addAll(0, arrayList);
  }
  
  public void addCatchClause(TypeId<? extends Throwable> paramTypeId, Label paramLabel) {
    StringBuilder stringBuilder;
    if (this.i.contains(paramTypeId)) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Already caught: ");
      stringBuilder.append(paramTypeId);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    a((Label)stringBuilder);
    this.i.add(paramTypeId);
    this.k = a(this.i);
    this.j.add(stringBuilder);
  }
  
  public void aget(Local<?> paramLocal1, Local<?> paramLocal2, Local<Integer> paramLocal) {
    a((f)new u(r.k((d)paramLocal1.a.b), this.h, n.a(paramLocal2.b(), paramLocal.b()), (e)this.k));
    a(paramLocal1, true);
  }
  
  public void aput(Local<?> paramLocal1, Local<Integer> paramLocal, Local<?> paramLocal2) {
    a((f)new u(r.l((d)paramLocal2.a.b), this.h, n.a(paramLocal2.b(), paramLocal1.b(), paramLocal.b()), (e)this.k));
  }
  
  public <T> void arrayLength(Local<Integer> paramLocal, Local<T> paramLocal1) {
    a((f)new u(r.bC, this.h, n.a(paramLocal1.b()), (e)this.k));
    a(paramLocal, true);
  }
  
  int b() {
    Iterator<Local<?>> iterator = this.f.iterator();
    int i;
    for (i = 0; iterator.hasNext(); i += ((Local)iterator.next()).a());
    return i;
  }
  
  c c() {
    if (!this.d)
      a(); 
    d();
    c c = new c(this.b.size());
    for (int i = 0; i < this.b.size(); i++)
      c.a(i, ((Label)this.b.get(i)).c()); 
    return c;
  }
  
  public void cast(Local<?> paramLocal1, Local<?> paramLocal2) {
    if ((paramLocal2.getType()).b.j()) {
      a((f)new t(r.ch, this.h, n.a(paramLocal2.b()), (e)this.k, (a)paramLocal1.a.c));
      a(paramLocal1, true);
      return;
    } 
    a((f)new k(a(paramLocal2.a.b, paramLocal1.a.b), this.h, paramLocal1.b(), paramLocal2.b()));
  }
  
  public <T> void compare(Comparison paramComparison, Label paramLabel, Local<T> paramLocal1, Local<T> paramLocal2) {
    a(paramLabel);
    a((f)new k(paramComparison.rop((e)b.a(paramLocal1.a.b, paramLocal2.a.b)), this.h, null, n.a(paramLocal1.b(), paramLocal2.b())), paramLabel);
  }
  
  public <T extends Number> void compareFloatingPoint(Local<Integer> paramLocal, Local<T> paramLocal1, Local<T> paramLocal2, int paramInt) {
    StringBuilder stringBuilder;
    p p;
    if (paramInt == 1) {
      p = r.i((d)paramLocal1.a.b);
    } else if (paramInt == -1) {
      p = r.h((d)paramLocal1.a.b);
    } else {
      stringBuilder = new StringBuilder();
      stringBuilder.append("expected 1 or -1 but was ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    a((f)new k(p, this.h, stringBuilder.b(), n.a(paramLocal1.b(), paramLocal2.b())));
  }
  
  public void compareLongs(Local<Integer> paramLocal, Local<Long> paramLocal1, Local<Long> paramLocal2) {
    a((f)new k(r.bc, this.h, paramLocal.b(), n.a(paramLocal1.b(), paramLocal2.b())));
  }
  
  public <T> Local<T> getParameter(int paramInt, TypeId<T> paramTypeId) {
    int i = paramInt;
    if (this.e != null)
      i = paramInt + 1; 
    return a(this.f.get(i), paramTypeId);
  }
  
  public <T> Local<T> getThis(TypeId<T> paramTypeId) {
    if (this.e == null)
      throw new IllegalStateException("static methods cannot access 'this'"); 
    return a(this.e, paramTypeId);
  }
  
  public <D, V> void iget(FieldId<D, V> paramFieldId, Local<V> paramLocal, Local<D> paramLocal1) {
    a((f)new t(r.n((d)paramLocal.a.b), this.h, n.a(paramLocal1.b()), (e)this.k, (a)paramFieldId.e));
    a(paramLocal, true);
  }
  
  public void instanceOfType(Local<?> paramLocal1, Local<?> paramLocal2, TypeId<?> paramTypeId) {
    a((f)new t(r.ci, this.h, n.a(paramLocal2.b()), (e)this.k, (a)paramTypeId.c));
    a(paramLocal1, true);
  }
  
  public <D, R> void invokeDirect(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    a(r.d(paramMethodId.b(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, R> void invokeInterface(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    a(r.e(paramMethodId.b(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <R> void invokeStatic(MethodId<?, R> paramMethodId, Local<? super R> paramLocal, Local<?>... paramVarArgs) {
    a(r.a(paramMethodId.b(true)), paramMethodId, paramLocal, null, paramVarArgs);
  }
  
  public <D, R> void invokeSuper(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    a(r.c(paramMethodId.b(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, R> void invokeVirtual(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    a(r.b(paramMethodId.b(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, V> void iput(FieldId<D, V> paramFieldId, Local<D> paramLocal, Local<V> paramLocal1) {
    a((f)new t(r.o((d)paramLocal1.a.b), this.h, n.a(paramLocal1.b(), paramLocal.b()), (e)this.k, (a)paramFieldId.e));
  }
  
  public void jump(Label paramLabel) {
    a(paramLabel);
    a((f)new k(r.s, this.h, null, n.a), paramLabel);
  }
  
  public <T> void loadConstant(Local<T> paramLocal, T paramT) {
    p p;
    if (paramT == null) {
      p = r.r;
    } else {
      p = r.e((d)paramLocal.a.b);
    } 
    if (p.b() == 1) {
      a((f)new j(p, this.h, paramLocal.b(), n.a, (a)Constants.a(paramT)));
      return;
    } 
    a((f)new t(p, this.h, n.a, (e)this.k, (a)Constants.a(paramT)));
    a(paramLocal, true);
  }
  
  public void mark(Label paramLabel) {
    a(paramLabel);
    if (paramLabel.c)
      throw new IllegalStateException("already marked"); 
    paramLabel.c = true;
    if (this.c != null)
      jump(paramLabel); 
    this.c = paramLabel;
  }
  
  public void monitorEnter(Local<?> paramLocal) {
    a((f)new u(r.bE, this.h, n.a(paramLocal.b()), (e)this.k));
  }
  
  public void monitorExit(Local<?> paramLocal) {
    a((f)new u(r.bE, this.h, n.a(paramLocal.b()), (e)this.k));
  }
  
  public <T> void move(Local<T> paramLocal1, Local<T> paramLocal2) {
    a((f)new k(r.a((d)paramLocal2.a.b), this.h, paramLocal1.b(), paramLocal2.b()));
  }
  
  public <T> void newArray(Local<T> paramLocal, Local<Integer> paramLocal1) {
    a((f)new t(r.m((d)paramLocal.a.b), this.h, n.a(paramLocal1.b()), (e)this.k, (a)paramLocal.a.c));
    a(paramLocal, true);
  }
  
  public <T> void newInstance(Local<T> paramLocal, MethodId<T, Void> paramMethodId, Local<?>... paramVarArgs) {
    if (paramLocal == null)
      throw new IllegalArgumentException(); 
    a((f)new t(r.bY, this.h, n.a, (e)this.k, (a)paramMethodId.a.c));
    a(paramLocal, true);
    invokeDirect(paramMethodId, null, paramLocal, paramVarArgs);
  }
  
  public <T> Local<T> newLocal(TypeId<T> paramTypeId) {
    if (this.d)
      throw new IllegalStateException("Cannot allocate locals after adding instructions"); 
    Local<T> local = Local.a(this, paramTypeId);
    this.g.add(local);
    return local;
  }
  
  public <T1, T2> void op(BinaryOp paramBinaryOp, Local<T1> paramLocal1, Local<T1> paramLocal2, Local<T2> paramLocal) {
    p p = paramBinaryOp.rop((e)b.a(paramLocal2.a.b, paramLocal.a.b));
    n n = n.a(paramLocal2.b(), paramLocal.b());
    if (p.b() == 1) {
      a((f)new k(p, this.h, paramLocal1.b(), n));
      return;
    } 
    a((f)new u(p, this.h, n, (e)this.k));
    a(paramLocal1, true);
  }
  
  public <T> void op(UnaryOp paramUnaryOp, Local<T> paramLocal1, Local<T> paramLocal2) {
    a((f)new k(paramUnaryOp.rop(paramLocal2.a), this.h, paramLocal1.b(), paramLocal2.b()));
  }
  
  public Label removeCatchClause(TypeId<? extends Throwable> paramTypeId) {
    int i = this.i.indexOf(paramTypeId);
    if (i == -1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("No catch clause: ");
      stringBuilder.append(paramTypeId);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    this.i.remove(i);
    this.k = a(this.i);
    return this.j.remove(i);
  }
  
  public void returnValue(Local<?> paramLocal) {
    if (!paramLocal.a.equals(this.a.b)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("declared ");
      stringBuilder.append(this.a.b);
      stringBuilder.append(" but returned ");
      stringBuilder.append(paramLocal.a);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    a((f)new k(r.j((d)paramLocal.a.b), this.h, null, n.a(paramLocal.b())));
  }
  
  public void returnVoid() {
    if (!this.a.b.equals(TypeId.VOID)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("declared ");
      stringBuilder.append(this.a.b);
      stringBuilder.append(" but returned void");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    a((f)new k(r.bw, this.h, null, n.a));
  }
  
  public <V> void sget(FieldId<?, V> paramFieldId, Local<V> paramLocal) {
    a((f)new t(r.p((d)paramLocal.a.b), this.h, n.a, (e)this.k, (a)paramFieldId.e));
    a(paramLocal, true);
  }
  
  public <V> void sput(FieldId<?, V> paramFieldId, Local<V> paramLocal) {
    a((f)new t(r.q((d)paramLocal.a.b), this.h, n.a(paramLocal.b()), (e)this.k, (a)paramFieldId.e));
  }
  
  public void throwValue(Local<? extends Throwable> paramLocal) {
    a((f)new u(r.bD, this.h, n.a(paramLocal.b()), (e)this.k));
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\Code.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */