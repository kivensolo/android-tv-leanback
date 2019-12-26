/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.leanback.fastlane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.leanback.R;
import com.android.example.leanback.data.Video;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class CardPresenter extends Presenter {

    public static final String TAG = "CardPresenter";
    private static int CARD_WIDTH = 300;
    private static int CARD_HEIGHT = 300;

    private static Context mContext;

    static class ViewHolder extends Presenter.ViewHolder {

        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private RequestOptions sharedOptions;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.filmi);
            sharedOptions = new RequestOptions()
                    .placeholder(R.drawable.filmi) //设置“加载中”状态时显示的图片
                    .override(CARD_WIDTH, CARD_HEIGHT) //指定大小，无视imageView大小
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   //既缓存全尺寸又缓存其他尺寸
                    .error(mDefaultCardImage); //设置“加载失败”状态时显示的图片
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage(String url) {

            GlideApp.with(mContext)
//                    .asBitmap()
                    .load(url)
//                    .load("http://p1.pstatp.com/large/166200019850062839d3")
//                    .listener(new PictureLoadListener())
//                    .transition(BitmapTransitionOptions.withCrossFade(400))//适用于Bitmap，过渡动画持续400ms
                    .apply(sharedOptions)
                    .into(mCardView.getMainImageView());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        Log.d(TAG, "creating viewholder");
        mContext = viewGroup.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setMainImageAdjustViewBounds(true);
        cardView.setFocusableInTouchMode(true);
        ((TextView) cardView.findViewById(R.id.content_text)).setTextColor(Color.LTGRAY);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        // Data binding logic
        Video video = (Video) o;
        ((ViewHolder) viewHolder).mCardView.setTitleText(video.getTitle());
        ((ViewHolder) viewHolder).mCardView.setContentText(video.getDescription());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        ((ViewHolder) viewHolder).updateCardViewImage(video.getThumbUrl());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    static class PictureLoadListener implements RequestListener<Bitmap>{
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            //加载失败
            Log.e(TAG,"onImageLoadFailed ");
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            Log.d(TAG,"onResourceReady ");
            //加载成功，resource为加载到的图片
            //如果return true，则不会再回调Target的onResourceReady（也就是不再往下传递），
            //imageView也就不会显示加载到的图片了。
            return false;
        }
    }
}
