package com.eyeem.chips;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 6/4/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickableSpan extends StyleSpan implements BubbleSpan {
   private Object data;
   private ForegroundColorSpan activeSpan;
   private ForegroundColorSpan inactiveSpan;;

   public ClickableSpan(int activeColor, int inactiveColor) {
      super(Typeface.BOLD);
      activeSpan = new ForegroundColorSpan(activeColor);
      inactiveSpan = new ForegroundColorSpan(inactiveColor);
   }

   @Override
   public void setPressed(boolean value, Spannable s) {
      if (value) {
         s.removeSpan(inactiveSpan);
         s.setSpan(activeSpan, s.getSpanStart(this), s.getSpanEnd(this), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      } else {
         s.removeSpan(activeSpan);
         s.setSpan(inactiveSpan, s.getSpanStart(this), s.getSpanEnd(this), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
   }

   @Override
   public void resetWidth(int width) {
      // noop
   }

   @Override
   public ArrayList<Rect> rect(ILayoutCallback callback) {
      ArrayList<Rect> result = new ArrayList<Rect>();
      int spanStart = callback.getSpannable().getSpanStart(this);
      int spanEnd = callback.getSpannable().getSpanEnd(this);
      int startLine = callback.getLine(spanStart);
      int endLine = callback.getLine(spanEnd);
      int currentPos = spanStart;
      for (int i = startLine; i <= endLine; i++) {
         Point startPoint = callback.getCursorPosition(currentPos);
         currentPos = callback.getLineEnd(i);
         Point endPoint = callback.getCursorPosition(currentPos-1);
         int h = callback.getLineHeight();
         result.add(new Rect(startPoint.x, startPoint.y, endPoint.x, endPoint.y+h));
      }
      return result;
   }

   @Override
   public void redraw(Canvas canvas) {}

   @Override
   public void setData(Object data) {
      this.data = data;
   }
}
