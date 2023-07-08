package android.support.v4.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;

public abstract class SharedElementCallback {
  private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
  
  private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
  
  private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
  
  private static int MAX_IMAGE_SIZE = 1048576;
  
  private Matrix mTempMatrix;
  
  private static Bitmap createDrawableBitmap(Drawable paramDrawable) {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (i <= 0 || j <= 0)
      return null; 
    float f = Math.min(1.0F, MAX_IMAGE_SIZE / (i * j));
    if (paramDrawable instanceof BitmapDrawable && f == 1.0F)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    i = (int)(i * f);
    j = (int)(j * f);
    Bitmap bitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Rect rect = paramDrawable.getBounds();
    int k = rect.left;
    int m = rect.top;
    int n = rect.right;
    int i1 = rect.bottom;
    paramDrawable.setBounds(0, 0, i, j);
    paramDrawable.draw(canvas);
    paramDrawable.setBounds(k, m, n, i1);
    return bitmap;
  }
  
  public Parcelable onCaptureSharedElementSnapshot(View paramView, Matrix paramMatrix, RectF paramRectF) {
    Matrix matrix;
    float[] arrayOfFloat;
    if (paramView instanceof ImageView) {
      ImageView imageView = (ImageView)paramView;
      Drawable drawable1 = imageView.getDrawable();
      Drawable drawable2 = imageView.getBackground();
      if (drawable1 != null && drawable2 == null) {
        Bitmap bitmap = createDrawableBitmap(drawable1);
        if (bitmap != null) {
          Bundle bundle = new Bundle();
          bundle.putParcelable("sharedElement:snapshot:bitmap", (Parcelable)bitmap);
          bundle.putString("sharedElement:snapshot:imageScaleType", imageView.getScaleType().toString());
          if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
            matrix = imageView.getImageMatrix();
            arrayOfFloat = new float[9];
            matrix.getValues(arrayOfFloat);
            bundle.putFloatArray("sharedElement:snapshot:imageMatrix", arrayOfFloat);
          } 
          return (Parcelable)bundle;
        } 
      } 
    } 
    int j = Math.round(paramRectF.width());
    int i = Math.round(paramRectF.height());
    Parcelable parcelable2 = null;
    Parcelable parcelable1 = parcelable2;
    if (j > 0) {
      parcelable1 = parcelable2;
      if (i > 0) {
        float f = Math.min(1.0F, MAX_IMAGE_SIZE / (j * i));
        j = (int)(j * f);
        i = (int)(i * f);
        if (this.mTempMatrix == null)
          this.mTempMatrix = new Matrix(); 
        this.mTempMatrix.set((Matrix)arrayOfFloat);
        this.mTempMatrix.postTranslate(-paramRectF.left, -paramRectF.top);
        this.mTempMatrix.postScale(f, f);
        Bitmap bitmap = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.concat(this.mTempMatrix);
        matrix.draw(canvas);
        return (Parcelable)bitmap;
      } 
    } 
    return parcelable1;
  }
  
  public View onCreateSnapshotView(Context paramContext, Parcelable paramParcelable) {
    float[] arrayOfFloat;
    if (paramParcelable instanceof Bundle) {
      Bundle bundle = (Bundle)paramParcelable;
      Bitmap bitmap = (Bitmap)bundle.getParcelable("sharedElement:snapshot:bitmap");
      if (bitmap == null)
        return null; 
      null = new ImageView(paramContext);
      null.setImageBitmap(bitmap);
      null.setScaleType(ImageView.ScaleType.valueOf(bundle.getString("sharedElement:snapshot:imageScaleType")));
      if (null.getScaleType() == ImageView.ScaleType.MATRIX) {
        arrayOfFloat = bundle.getFloatArray("sharedElement:snapshot:imageMatrix");
        Matrix matrix = new Matrix();
        matrix.setValues(arrayOfFloat);
        null.setImageMatrix(matrix);
      } 
      return (View)null;
    } 
    if (arrayOfFloat instanceof Bitmap) {
      Bitmap bitmap = (Bitmap)arrayOfFloat;
      null = new ImageView((Context)null);
      null.setImageBitmap(bitmap);
      return (View)null;
    } 
    return null;
  }
  
  public void onMapSharedElements(List<String> paramList, Map<String, View> paramMap) {}
  
  public void onRejectSharedElements(List<View> paramList) {}
  
  public void onSharedElementEnd(List<String> paramList, List<View> paramList1, List<View> paramList2) {}
  
  public void onSharedElementStart(List<String> paramList, List<View> paramList1, List<View> paramList2) {}
  
  public void onSharedElementsArrived(List<String> paramList, List<View> paramList1, OnSharedElementsReadyListener paramOnSharedElementsReadyListener) {
    paramOnSharedElementsReadyListener.onSharedElementsReady();
  }
  
  public static interface OnSharedElementsReadyListener {
    void onSharedElementsReady();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\SharedElementCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */