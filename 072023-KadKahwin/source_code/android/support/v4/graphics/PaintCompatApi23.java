package android.support.v4.graphics;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@RequiresApi(23)
class PaintCompatApi23 {
  static boolean hasGlyph(@NonNull Paint paramPaint, @NonNull String paramString) {
    return paramPaint.hasGlyph(paramString);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\graphics\PaintCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */