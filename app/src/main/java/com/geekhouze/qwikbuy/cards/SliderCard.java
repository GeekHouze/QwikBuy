package com.geekhouze.qwikbuy.cards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;


import com.geekhouze.qwikbuy.item.ItemsActivity;
import com.geekhouze.qwikbuy.R;
import  com.geekhouze.qwikbuy.utils.DecodeBitmapTask;


public class SliderCard extends RecyclerView.ViewHolder implements DecodeBitmapTask.Listener, View.OnClickListener {

    private static int viewWidth = 0;
    private static int viewHeight = 0;

    private final ImageView imageView;

    private DecodeBitmapTask task;

    public SliderCard(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }



    void setContent(@DrawableRes final int resId) {
        if (viewWidth == 0) {
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    viewWidth = itemView.getWidth();
                    viewHeight = itemView.getHeight();
                    loadBitmap(resId);
                }
            });
        } else {
            loadBitmap(resId);
        }
    }


    void clearContent() {
        if (task != null) {
            task.cancel(true);
        }
    }

    private void loadBitmap(@DrawableRes int resId) {
        task = new DecodeBitmapTask(itemView.getResources(), resId, viewWidth, viewHeight, this);
        task.execute();
    }

    @Override
    public void onPostExecuted(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(), ItemsActivity.class);
        v.getContext().startActivity(intent);
    }
}