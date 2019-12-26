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

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.example.leanback.R;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * 背景图更换Hepler
 */
public class BackgroundHelper {

    private final Handler mHandler = new Handler();

    private final Runnable mUpdateBackgroundAction = new Runnable() {
        @Override
        public void run() {
            if (mBackgroundURL != null) {
                updateBackground(mBackgroundURL);
            }
        }
    };

    private Activity mActivity;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private String mBackgroundURL;

    private Drawable mDefaultBackground;
    private GlideBackgroundManagerTarget mBackgroundTarget;
    private RequestOptions options;

    public BackgroundHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(mActivity);
        mBackgroundManager.attach(mActivity.getWindow());
        mBackgroundTarget = new GlideBackgroundManagerTarget(mBackgroundManager);
        mDefaultBackground = ContextCompat.getDrawable(mActivity, R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        options= new RequestOptions()
                .centerCrop()
                .override(mMetrics.widthPixels, mMetrics.heightPixels)
//                .transform(BlurTransform.getInstance(mActivity))
//                .transform(new BlurTransformation())
                .error(mDefaultBackground);
    }

    public void release() {
        mHandler.removeCallbacksAndMessages(null);
        mBackgroundManager.release();
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.mBackgroundURL = backgroundUrl;
        scheduleUpdate();
    }

    static class GlideBackgroundManagerTarget extends SimpleTarget<Drawable> {
        BackgroundManager mBackgroundManager;

        public GlideBackgroundManagerTarget(BackgroundManager mBackgroundManager) {
            this.mBackgroundManager = mBackgroundManager;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            GlideBackgroundManagerTarget that = (GlideBackgroundManagerTarget) o;

            return mBackgroundManager.equals(that.mBackgroundManager);
        }

        @Override
        public int hashCode() {
            return mBackgroundManager.hashCode();
        }

        @Override
        public void onLoadStarted(@Nullable Drawable placeholder) {
            Log.d("updateBackground","onLoadStarted");
            super.onLoadStarted(placeholder);
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            Log.e("updateBackground","onLoadFailed");
            mBackgroundManager.setDrawable(errorDrawable);
            super.onLoadFailed(errorDrawable);
        }

        @Override
        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            Log.d("updateBackground","onResourceReady");
            mBackgroundManager.setDrawable(resource);

        }
    }

    protected void setDefaultBackground(Drawable background) {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground(int resourceId) {
        mDefaultBackground = ContextCompat.getDrawable(mActivity, resourceId);
    }

    protected void updateBackground(String url) {
        GlideApp.with(mActivity)
                .load(url)
                .apply(options)
                .into(mBackgroundTarget);
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(mActivity).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(mActivity).setDrawable(mDefaultBackground);
    }

    private void scheduleUpdate() {
        mHandler.removeCallbacks(mUpdateBackgroundAction);
        mHandler.postDelayed(mUpdateBackgroundAction, 200L);
    }
}
