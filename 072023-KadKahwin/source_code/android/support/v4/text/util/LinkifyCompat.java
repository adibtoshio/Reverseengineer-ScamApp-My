package android.support.v4.text.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
  private static final Comparator<LinkSpec> COMPARATOR;
  
  private static final String[] EMPTY_STRING = new String[0];
  
  static {
    COMPARATOR = new Comparator<LinkSpec>() {
        public final int compare(LinkifyCompat.LinkSpec param1LinkSpec1, LinkifyCompat.LinkSpec param1LinkSpec2) {
          if (param1LinkSpec1.start >= param1LinkSpec2.start) {
            if (param1LinkSpec1.start > param1LinkSpec2.start)
              return 1; 
            if (param1LinkSpec1.end < param1LinkSpec2.end)
              return 1; 
            if (param1LinkSpec1.end <= param1LinkSpec2.end)
              return 0; 
          } 
          return -1;
        }
      };
  }
  
  private static void addLinkMovementMethod(@NonNull TextView paramTextView) {
    MovementMethod movementMethod = paramTextView.getMovementMethod();
    if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && paramTextView.getLinksClickable())
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance()); 
  }
  
  public static final void addLinks(@NonNull TextView paramTextView, @NonNull Pattern paramPattern, @Nullable String paramString) {
    addLinks(paramTextView, paramPattern, paramString, (String[])null, (Linkify.MatchFilter)null, (Linkify.TransformFilter)null);
  }
  
  public static final void addLinks(@NonNull TextView paramTextView, @NonNull Pattern paramPattern, @Nullable String paramString, @Nullable Linkify.MatchFilter paramMatchFilter, @Nullable Linkify.TransformFilter paramTransformFilter) {
    addLinks(paramTextView, paramPattern, paramString, (String[])null, paramMatchFilter, paramTransformFilter);
  }
  
  public static final void addLinks(@NonNull TextView paramTextView, @NonNull Pattern paramPattern, @Nullable String paramString, @Nullable String[] paramArrayOfString, @Nullable Linkify.MatchFilter paramMatchFilter, @Nullable Linkify.TransformFilter paramTransformFilter) {
    SpannableString spannableString = SpannableString.valueOf(paramTextView.getText());
    if (addLinks((Spannable)spannableString, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter)) {
      paramTextView.setText((CharSequence)spannableString);
      addLinkMovementMethod(paramTextView);
    } 
  }
  
  public static final boolean addLinks(@NonNull Spannable paramSpannable, int paramInt) {
    if (paramInt == 0)
      return false; 
    URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    for (int i = arrayOfURLSpan.length - 1; i >= 0; i--)
      paramSpannable.removeSpan(arrayOfURLSpan[i]); 
    if ((paramInt & 0x4) != 0)
      Linkify.addLinks(paramSpannable, 4); 
    ArrayList<LinkSpec> arrayList = new ArrayList();
    if ((paramInt & 0x1) != 0) {
      Pattern pattern = PatternsCompat.AUTOLINK_WEB_URL;
      Linkify.MatchFilter matchFilter = Linkify.sUrlMatchFilter;
      gatherLinks(arrayList, paramSpannable, pattern, new String[] { "http://", "https://", "rtsp://" }, matchFilter, null);
    } 
    if ((paramInt & 0x2) != 0)
      gatherLinks(arrayList, paramSpannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[] { "mailto:" }, null, null); 
    if ((paramInt & 0x8) != 0)
      gatherMapLinks(arrayList, paramSpannable); 
    pruneOverlaps(arrayList, paramSpannable);
    if (arrayList.size() == 0)
      return false; 
    for (LinkSpec linkSpec : arrayList) {
      if (linkSpec.frameworkAddedSpan == null)
        applyLink(linkSpec.url, linkSpec.start, linkSpec.end, paramSpannable); 
    } 
    return true;
  }
  
  public static final boolean addLinks(@NonNull Spannable paramSpannable, @NonNull Pattern paramPattern, @Nullable String paramString) {
    return addLinks(paramSpannable, paramPattern, paramString, (String[])null, (Linkify.MatchFilter)null, (Linkify.TransformFilter)null);
  }
  
  public static final boolean addLinks(@NonNull Spannable paramSpannable, @NonNull Pattern paramPattern, @Nullable String paramString, @Nullable Linkify.MatchFilter paramMatchFilter, @Nullable Linkify.TransformFilter paramTransformFilter) {
    return addLinks(paramSpannable, paramPattern, paramString, (String[])null, paramMatchFilter, paramTransformFilter);
  }
  
  public static final boolean addLinks(@NonNull Spannable paramSpannable, @NonNull Pattern paramPattern, @Nullable String paramString, @Nullable String[] paramArrayOfString, @Nullable Linkify.MatchFilter paramMatchFilter, @Nullable Linkify.TransformFilter paramTransformFilter) {
    // Byte code:
    //   0: aload_2
    //   1: astore #10
    //   3: aload_2
    //   4: ifnonnull -> 11
    //   7: ldc ''
    //   9: astore #10
    //   11: aload_3
    //   12: ifnull -> 23
    //   15: aload_3
    //   16: astore_2
    //   17: aload_3
    //   18: arraylength
    //   19: iconst_1
    //   20: if_icmpge -> 27
    //   23: getstatic android/support/v4/text/util/LinkifyCompat.EMPTY_STRING : [Ljava/lang/String;
    //   26: astore_2
    //   27: aload_2
    //   28: arraylength
    //   29: iconst_1
    //   30: iadd
    //   31: anewarray java/lang/String
    //   34: astore #11
    //   36: aload #11
    //   38: iconst_0
    //   39: aload #10
    //   41: getstatic java/util/Locale.ROOT : Ljava/util/Locale;
    //   44: invokevirtual toLowerCase : (Ljava/util/Locale;)Ljava/lang/String;
    //   47: aastore
    //   48: iconst_0
    //   49: istore #6
    //   51: iload #6
    //   53: aload_2
    //   54: arraylength
    //   55: if_icmpge -> 98
    //   58: aload_2
    //   59: iload #6
    //   61: aaload
    //   62: astore_3
    //   63: aload_3
    //   64: ifnonnull -> 87
    //   67: ldc ''
    //   69: astore_3
    //   70: aload #11
    //   72: iload #6
    //   74: iconst_1
    //   75: iadd
    //   76: aload_3
    //   77: aastore
    //   78: iload #6
    //   80: iconst_1
    //   81: iadd
    //   82: istore #6
    //   84: goto -> 51
    //   87: aload_3
    //   88: getstatic java/util/Locale.ROOT : Ljava/util/Locale;
    //   91: invokevirtual toLowerCase : (Ljava/util/Locale;)Ljava/lang/String;
    //   94: astore_3
    //   95: goto -> 70
    //   98: aload_1
    //   99: aload_0
    //   100: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   103: astore_1
    //   104: iconst_0
    //   105: istore #8
    //   107: aload_1
    //   108: invokevirtual find : ()Z
    //   111: ifeq -> 177
    //   114: aload_1
    //   115: invokevirtual start : ()I
    //   118: istore #6
    //   120: aload_1
    //   121: invokevirtual end : ()I
    //   124: istore #7
    //   126: aload #4
    //   128: ifnull -> 180
    //   131: aload #4
    //   133: aload_0
    //   134: iload #6
    //   136: iload #7
    //   138: invokeinterface acceptMatch : (Ljava/lang/CharSequence;II)Z
    //   143: istore #9
    //   145: iload #9
    //   147: ifeq -> 107
    //   150: aload_1
    //   151: iconst_0
    //   152: invokevirtual group : (I)Ljava/lang/String;
    //   155: aload #11
    //   157: aload_1
    //   158: aload #5
    //   160: invokestatic makeUrl : (Ljava/lang/String;[Ljava/lang/String;Ljava/util/regex/Matcher;Landroid/text/util/Linkify$TransformFilter;)Ljava/lang/String;
    //   163: iload #6
    //   165: iload #7
    //   167: aload_0
    //   168: invokestatic applyLink : (Ljava/lang/String;IILandroid/text/Spannable;)V
    //   171: iconst_1
    //   172: istore #8
    //   174: goto -> 107
    //   177: iload #8
    //   179: ireturn
    //   180: iconst_1
    //   181: istore #9
    //   183: goto -> 145
  }
  
  public static final boolean addLinks(@NonNull TextView paramTextView, int paramInt) {
    if (paramInt == 0)
      return false; 
    CharSequence charSequence = paramTextView.getText();
    if (charSequence instanceof Spannable) {
      if (addLinks((Spannable)charSequence, paramInt)) {
        addLinkMovementMethod(paramTextView);
        return true;
      } 
      return false;
    } 
    SpannableString spannableString = SpannableString.valueOf(charSequence);
    if (addLinks((Spannable)spannableString, paramInt)) {
      addLinkMovementMethod(paramTextView);
      paramTextView.setText((CharSequence)spannableString);
      return true;
    } 
    return false;
  }
  
  private static void applyLink(String paramString, int paramInt1, int paramInt2, Spannable paramSpannable) {
    paramSpannable.setSpan(new URLSpan(paramString), paramInt1, paramInt2, 33);
  }
  
  private static void gatherLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable, Pattern paramPattern, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter) {
    Matcher matcher = paramPattern.matcher((CharSequence)paramSpannable);
    while (matcher.find()) {
      int i = matcher.start();
      int j = matcher.end();
      if (paramMatchFilter == null || paramMatchFilter.acceptMatch((CharSequence)paramSpannable, i, j)) {
        LinkSpec linkSpec = new LinkSpec();
        linkSpec.url = makeUrl(matcher.group(0), paramArrayOfString, matcher, paramTransformFilter);
        linkSpec.start = i;
        linkSpec.end = j;
        paramArrayList.add(linkSpec);
      } 
    } 
  }
  
  private static final void gatherMapLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable) {
    String str = paramSpannable.toString();
    int i = 0;
    while (true) {
      try {
        String str1 = WebView.findAddress(str);
        if (str1 != null) {
          int j = str.indexOf(str1);
          if (j < 0)
            return; 
          LinkSpec linkSpec = new LinkSpec();
          int k = str1.length() + j;
          linkSpec.start = j + i;
          linkSpec.end = i + k;
          str = str.substring(k);
          i += k;
          try {
            str1 = URLEncoder.encode(str1, "UTF-8");
            linkSpec.url = "geo:0,0?q=" + str1;
            paramArrayList.add(linkSpec);
          } catch (UnsupportedEncodingException unsupportedEncodingException) {}
          continue;
        } 
      } catch (UnsupportedOperationException unsupportedOperationException) {
        return;
      } 
      break;
    } 
  }
  
  private static String makeUrl(@NonNull String paramString, @NonNull String[] paramArrayOfString, Matcher paramMatcher, @Nullable Linkify.TransformFilter paramTransformFilter) {
    // Byte code:
    //   0: iconst_1
    //   1: istore #6
    //   3: aload_3
    //   4: ifnull -> 164
    //   7: aload_3
    //   8: aload_2
    //   9: aload_0
    //   10: invokeinterface transformUrl : (Ljava/util/regex/Matcher;Ljava/lang/String;)Ljava/lang/String;
    //   15: astore_2
    //   16: iconst_0
    //   17: istore #4
    //   19: iload #4
    //   21: aload_1
    //   22: arraylength
    //   23: if_icmpge -> 156
    //   26: aload_2
    //   27: iconst_1
    //   28: iconst_0
    //   29: aload_1
    //   30: iload #4
    //   32: aaload
    //   33: iconst_0
    //   34: aload_1
    //   35: iload #4
    //   37: aaload
    //   38: invokevirtual length : ()I
    //   41: invokevirtual regionMatches : (ZILjava/lang/String;II)Z
    //   44: ifeq -> 147
    //   47: aload_2
    //   48: astore_0
    //   49: iload #6
    //   51: istore #5
    //   53: aload_2
    //   54: iconst_0
    //   55: iconst_0
    //   56: aload_1
    //   57: iload #4
    //   59: aaload
    //   60: iconst_0
    //   61: aload_1
    //   62: iload #4
    //   64: aaload
    //   65: invokevirtual length : ()I
    //   68: invokevirtual regionMatches : (ZILjava/lang/String;II)Z
    //   71: ifne -> 110
    //   74: new java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial <init> : ()V
    //   81: aload_1
    //   82: iload #4
    //   84: aaload
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: aload_1
    //   90: iload #4
    //   92: aaload
    //   93: invokevirtual length : ()I
    //   96: invokevirtual substring : (I)Ljava/lang/String;
    //   99: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: invokevirtual toString : ()Ljava/lang/String;
    //   105: astore_0
    //   106: iload #6
    //   108: istore #5
    //   110: aload_0
    //   111: astore_2
    //   112: iload #5
    //   114: ifne -> 145
    //   117: aload_0
    //   118: astore_2
    //   119: aload_1
    //   120: arraylength
    //   121: ifle -> 145
    //   124: new java/lang/StringBuilder
    //   127: dup
    //   128: invokespecial <init> : ()V
    //   131: aload_1
    //   132: iconst_0
    //   133: aaload
    //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: aload_0
    //   138: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: invokevirtual toString : ()Ljava/lang/String;
    //   144: astore_2
    //   145: aload_2
    //   146: areturn
    //   147: iload #4
    //   149: iconst_1
    //   150: iadd
    //   151: istore #4
    //   153: goto -> 19
    //   156: iconst_0
    //   157: istore #5
    //   159: aload_2
    //   160: astore_0
    //   161: goto -> 110
    //   164: aload_0
    //   165: astore_2
    //   166: goto -> 16
  }
  
  private static final void pruneOverlaps(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable) {
    int j = 0;
    URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    int i;
    for (i = 0; i < arrayOfURLSpan.length; i++) {
      LinkSpec linkSpec = new LinkSpec();
      linkSpec.frameworkAddedSpan = arrayOfURLSpan[i];
      linkSpec.start = paramSpannable.getSpanStart(arrayOfURLSpan[i]);
      linkSpec.end = paramSpannable.getSpanEnd(arrayOfURLSpan[i]);
      paramArrayList.add(linkSpec);
    } 
    Collections.sort(paramArrayList, COMPARATOR);
    int k = paramArrayList.size();
    while (true) {
      if (j < k - 1) {
        LinkSpec linkSpec1 = paramArrayList.get(j);
        LinkSpec linkSpec2 = paramArrayList.get(j + 1);
        if (linkSpec1.start <= linkSpec2.start && linkSpec1.end > linkSpec2.start) {
          if (linkSpec2.end <= linkSpec1.end) {
            i = j + 1;
          } else if (linkSpec1.end - linkSpec1.start > linkSpec2.end - linkSpec2.start) {
            i = j + 1;
          } else if (linkSpec1.end - linkSpec1.start < linkSpec2.end - linkSpec2.start) {
            i = j;
          } else {
            i = -1;
          } 
          if (i != -1) {
            URLSpan uRLSpan = ((LinkSpec)paramArrayList.get(i)).frameworkAddedSpan;
            if (uRLSpan != null)
              paramSpannable.removeSpan(uRLSpan); 
            paramArrayList.remove(i);
            k--;
            continue;
          } 
        } 
        j++;
        continue;
      } 
      return;
    } 
  }
  
  private static class LinkSpec {
    int end;
    
    URLSpan frameworkAddedSpan;
    
    int start;
    
    String url;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LinkifyMask {}
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\tex\\util\LinkifyCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */