package com.a.a.a;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import com.a.a.b.g;
import com.a.a.b.h;
import com.a.a.b.j;
import java.util.ArrayList;

public class a {
  private static h c = j.g();
  
  private c a;
  
  private Context b;
  
  private ListPopupWindow d;
  
  private a e;
  
  private Filter f;
  
  private int g;
  
  private int h;
  
  private int i;
  
  private CharSequence j;
  
  private int k;
  
  private GradientDrawable l;
  
  private int m;
  
  public a(c paramc) {
    this.a = paramc;
    this.b = paramc.getContext();
    d();
  }
  
  private void d() {
    this.d = new ListPopupWindow(this.b);
    this.d.setAnchorView(this.a);
    this.e = new a(this, this.b, 17367043);
    this.d.setAdapter((ListAdapter)this.e);
    this.f = this.e.getFilter();
    d(300);
    TypedArray typedArray = this.b.getTheme().obtainStyledAttributes(new int[] { 16842801, 16842806 });
    int i = typedArray.getColor(0, 16711935);
    int j = typedArray.getColor(1, 16711935);
    typedArray.recycle();
    this.l = new GradientDrawable();
    this.l.setColor(i);
    this.l.setCornerRadius(4.0F);
    this.l.setStroke(1, j);
    a(j);
    this.d.setBackgroundDrawable((Drawable)this.l);
    this.d.setOnItemClickListener(new AdapterView.OnItemClickListener(this) {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            a.a(this.a).replaceText(a.a(this.a).getCaretPosition() - a.b(this.a).length(), a.b(this.a).length(), ((TextView)param1View).getText().toString());
            a.c(this.a).a();
            this.a.b();
          }
        });
  }
  
  private void d(int paramInt) {
    if (this.h != paramInt) {
      this.h = paramInt;
      this.d.setHeight(paramInt);
    } 
  }
  
  private void e(int paramInt) {
    paramInt = Math.min(paramInt, this.a.getWidth() / 2);
    if (this.i != paramInt) {
      this.i = paramInt;
      this.d.setHorizontalOffset(paramInt);
    } 
  }
  
  private void f(int paramInt) {
    int j = 0 - this.d.getHeight();
    int i = paramInt;
    if (paramInt > j) {
      this.a.scrollBy(0, paramInt - j);
      i = j;
    } 
    if (this.g != i) {
      this.g = i;
      this.d.setVerticalOffset(i);
    } 
  }
  
  public void a() {
    if (!this.d.isShowing())
      this.d.show(); 
    this.d.getListView().setFadingEdgeLength(0);
  }
  
  public void a(int paramInt) {
    this.m = paramInt;
    this.l.setStroke(1, paramInt);
    this.d.setBackgroundDrawable((Drawable)this.l);
  }
  
  public void a(h paramh) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: putstatic com/a/a/a/a.c : Lcom/a/a/b/h;
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: astore_1
    //   10: aload_0
    //   11: monitorexit
    //   12: aload_1
    //   13: athrow
    // Exception table:
    //   from	to	target	type
    //   2	6	9	finally
  }
  
  public void a(CharSequence paramCharSequence) {
    this.e.b();
    this.f.filter(paramCharSequence);
  }
  
  public void b() {
    if (this.d.isShowing())
      this.d.dismiss(); 
  }
  
  public void b(int paramInt) {
    this.k = paramInt;
    this.l.setColor(paramInt);
    this.d.setBackgroundDrawable((Drawable)this.l);
  }
  
  public void c(int paramInt) {
    this.d.setWidth(paramInt);
  }
  
  class a extends ArrayAdapter<String> implements Filterable {
    private int b;
    
    private g c = new g();
    
    private DisplayMetrics d;
    
    public a(a this$0, Context param1Context, int param1Int) {
      super(param1Context, param1Int);
      setNotifyOnChange(false);
      this.d = param1Context.getResources().getDisplayMetrics();
    }
    
    public void a() {
      this.c.a();
    }
    
    public void b() {
      this.c.b();
    }
    
    public int c() {
      if (this.b != 0)
        return this.b; 
      TextView textView = (TextView)((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(17367043, null);
      textView.measure(0, 0);
      this.b = textView.getMeasuredHeight();
      return this.b;
    }
    
    public Filter getFilter() {
      return new Filter(this) {
          protected Filter.FilterResults performFiltering(CharSequence param2CharSequence) {
            String str;
            ArrayList<CharSequence> arrayList = new ArrayList();
            param2CharSequence = String.valueOf(param2CharSequence).toLowerCase();
            String[] arrayOfString = param2CharSequence.split("\\.");
            int m = arrayOfString.length;
            int k = 0;
            int j = 0;
            int i = 0;
            if (m == 2) {
              String str1 = arrayOfString[0];
              str = arrayOfString[1];
              param2CharSequence = str;
              if (a.c().f(str1)) {
                String[] arrayOfString1 = a.c().a(str1);
                j = arrayOfString1.length;
                while (true) {
                  param2CharSequence = str;
                  if (i < j) {
                    param2CharSequence = arrayOfString1[i];
                    if (param2CharSequence.toLowerCase().startsWith(str))
                      arrayList.add(param2CharSequence); 
                    i++;
                    continue;
                  } 
                  break;
                } 
              } 
            } else if (str.length == 1) {
              if (param2CharSequence.charAt(param2CharSequence.length() - 1) == '.') {
                String str1 = param2CharSequence.substring(0, param2CharSequence.length() - 1);
                str = "";
                param2CharSequence = str;
                if (a.c().f(str1)) {
                  String[] arrayOfString1 = a.c().a(str1);
                  j = arrayOfString1.length;
                  i = k;
                  while (true) {
                    param2CharSequence = str;
                    if (i < j) {
                      arrayList.add(arrayOfString1[i]);
                      i++;
                      continue;
                    } 
                    break;
                  } 
                } 
              } else {
                for (String str1 : a.c().b()) {
                  if (str1.toLowerCase().startsWith((String)param2CharSequence))
                    arrayList.add(str1); 
                } 
                for (String str1 : a.c().d()) {
                  if (str1.indexOf((String)param2CharSequence) == 0)
                    arrayList.add(str1); 
                } 
                String[] arrayOfString1 = a.c().c();
                k = arrayOfString1.length;
                for (i = j; i < k; i++) {
                  String str1 = arrayOfString1[i];
                  if (str1.toLowerCase().startsWith((String)param2CharSequence))
                    arrayList.add(str1); 
                } 
              } 
            } 
            a.a(this.a.a, param2CharSequence);
            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = arrayList;
            filterResults.count = arrayList.size();
            return filterResults;
          }
          
          protected void publishResults(CharSequence param2CharSequence, Filter.FilterResults param2FilterResults) {
            if (param2FilterResults != null && param2FilterResults.count > 0 && !a.a.a(this.a).c()) {
              this.a.clear();
              this.a.addAll((ArrayList)param2FilterResults.values);
              int i = a.a(this.a.a).getCaretY();
              int j = a.a(this.a.a).a() / 2;
              int k = a.a(this.a.a).getScrollY();
              a.a(this.a.a, this.a.c() * Math.min(2, param2FilterResults.count));
              a.b(this.a.a, a.a(this.a.a).getCaretX() - a.a(this.a.a).getScrollX());
              a.c(this.a.a, i + j - k - a.a(this.a.a).getHeight());
              this.a.notifyDataSetChanged();
              this.a.a.a();
              return;
            } 
            this.a.notifyDataSetInvalidated();
          }
        };
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      TextView textView = (TextView)super.getView(param1Int, param1View, param1ViewGroup);
      textView.setTextColor(a.d(this.a));
      return (View)textView;
    }
  }
  
  class null extends Filter {
    null(a this$0) {}
    
    protected Filter.FilterResults performFiltering(CharSequence param1CharSequence) {
      String str;
      ArrayList<CharSequence> arrayList = new ArrayList();
      param1CharSequence = String.valueOf(param1CharSequence).toLowerCase();
      String[] arrayOfString = param1CharSequence.split("\\.");
      int m = arrayOfString.length;
      int k = 0;
      int j = 0;
      int i = 0;
      if (m == 2) {
        String str1 = arrayOfString[0];
        str = arrayOfString[1];
        param1CharSequence = str;
        if (a.c().f(str1)) {
          String[] arrayOfString1 = a.c().a(str1);
          j = arrayOfString1.length;
          while (true) {
            param1CharSequence = str;
            if (i < j) {
              param1CharSequence = arrayOfString1[i];
              if (param1CharSequence.toLowerCase().startsWith(str))
                arrayList.add(param1CharSequence); 
              i++;
              continue;
            } 
            break;
          } 
        } 
      } else if (str.length == 1) {
        if (param1CharSequence.charAt(param1CharSequence.length() - 1) == '.') {
          String str1 = param1CharSequence.substring(0, param1CharSequence.length() - 1);
          str = "";
          param1CharSequence = str;
          if (a.c().f(str1)) {
            String[] arrayOfString1 = a.c().a(str1);
            j = arrayOfString1.length;
            i = k;
            while (true) {
              param1CharSequence = str;
              if (i < j) {
                arrayList.add(arrayOfString1[i]);
                i++;
                continue;
              } 
              break;
            } 
          } 
        } else {
          for (String str1 : a.c().b()) {
            if (str1.toLowerCase().startsWith((String)param1CharSequence))
              arrayList.add(str1); 
          } 
          for (String str1 : a.c().d()) {
            if (str1.indexOf((String)param1CharSequence) == 0)
              arrayList.add(str1); 
          } 
          String[] arrayOfString1 = a.c().c();
          k = arrayOfString1.length;
          for (i = j; i < k; i++) {
            String str1 = arrayOfString1[i];
            if (str1.toLowerCase().startsWith((String)param1CharSequence))
              arrayList.add(str1); 
          } 
        } 
      } 
      a.a(this.a.a, param1CharSequence);
      Filter.FilterResults filterResults = new Filter.FilterResults();
      filterResults.values = arrayList;
      filterResults.count = arrayList.size();
      return filterResults;
    }
    
    protected void publishResults(CharSequence param1CharSequence, Filter.FilterResults param1FilterResults) {
      if (param1FilterResults != null && param1FilterResults.count > 0 && !a.a.a(this.a).c()) {
        this.a.clear();
        this.a.addAll((ArrayList)param1FilterResults.values);
        int i = a.a(this.a.a).getCaretY();
        int j = a.a(this.a.a).a() / 2;
        int k = a.a(this.a.a).getScrollY();
        a.a(this.a.a, this.a.c() * Math.min(2, param1FilterResults.count));
        a.b(this.a.a, a.a(this.a.a).getCaretX() - a.a(this.a.a).getScrollX());
        a.c(this.a.a, i + j - k - a.a(this.a.a).getHeight());
        this.a.notifyDataSetChanged();
        this.a.a.a();
        return;
      } 
      this.a.notifyDataSetInvalidated();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */