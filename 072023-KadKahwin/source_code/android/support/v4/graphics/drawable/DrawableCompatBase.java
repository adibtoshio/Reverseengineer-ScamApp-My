package android.support.v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@TargetApi(9)
@RequiresApi(9)
class DrawableCompatBase {
  public static void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws IOException, XmlPullParserException {
    paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet);
  }
  
  public static void setTint(Drawable paramDrawable, int paramInt) {
    if (paramDrawable instanceof TintAwareDrawable)
      ((TintAwareDrawable)paramDrawable).setTint(paramInt); 
  }
  
  public static void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList) {
    if (paramDrawable instanceof TintAwareDrawable)
      ((TintAwareDrawable)paramDrawable).setTintList(paramColorStateList); 
  }
  
  public static void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode) {
    if (paramDrawable instanceof TintAwareDrawable)
      ((TintAwareDrawable)paramDrawable).setTintMode(paramMode); 
  }
  
  public static Drawable wrapForTinting(Drawable paramDrawable) {
    Drawable drawable = paramDrawable;
    if (!(paramDrawable instanceof TintAwareDrawable))
      drawable = new DrawableWrapperGingerbread(paramDrawable); 
    return drawable;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\graphics\drawable\DrawableCompatBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */