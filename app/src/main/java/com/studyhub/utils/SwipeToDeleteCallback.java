package com.studyhub.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final SwipeAction swipeAction;
    private final Drawable deleteIcon;
    private final Paint clearPaint;
    private final int intrinsicWidth;
    private final int intrinsicHeight;

    public interface SwipeAction {
        void onSwiped(int position);
    }

    public SwipeToDeleteCallback(Context context, SwipeAction swipeAction) {
        super(0, ItemTouchHelper.LEFT);
        this.swipeAction = swipeAction;
        
        // You can use a standard android icon or a custom one if available.
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete);
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        
        clearPaint = new Paint();
        clearPaint.setColor(Color.parseColor("#F44336")); // Material Red
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            swipeAction.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();

        // Draw red background
        if (dX < 0) { // Swiping left
            RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            c.drawRect(background, clearPaint);

            // Calculate position of delete icon
            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;

            // Draw the delete icon
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteIcon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
