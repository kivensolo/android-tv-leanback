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

package com.android.example.leanback.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VideoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "video.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] insert = new String[]{"INSERT INTO [video_item] ([description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ( 'Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain''t no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.', 'Big Buck Bunny', 'Movie', 5, 'http://b.hiphotos.baidu.com/image/pic/item/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');",
            "INSERT INTO [video_item] ( [description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ('Sintel is a short computer animated film by the Blender Institute, part of the Blender Foundation', 'Sintel', 'Movie', 0, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577185700152&di=587eede04b8b028fb9d9a9ae8606c9ae&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20101122%2FImg277820080.jpg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');",
            "INSERT INTO [video_item] ([description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ('Terminator 2: The judgement day', 'Terminator 2', 'Movies', 0, 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=133729498,3309348889&fm=26&gp=0.jpg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');",
            "INSERT INTO [video_item] ( [description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ( 'With the help of Lieutenant Jim Gordon and District Attorney Harvey Dent, Batman sets out to destroy organized crime in Gotham for good. ', 'The Dark Knight', 'Movies', 0, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577185799037&di=2fa69cac3c4640a911aea088f8d09e70&imgtype=0&src=http%3A%2F%2Fgz.crystaledu.com%2Fuploadfile%2F2018%2F0823%2F20180823022250463.jpeg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');",
            "INSERT INTO [video_item] ([description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ( 'Man of Steel is a 2013 superhero film based on the DC Comics character Superman, co-produced by Legendary Pictures and Syncopy Films, distributed by Warner Bros. ', 'Man of Steel', 'Movies', 0, 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2050109908,668672621&fm=26&gp=0.jpg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');",
            "INSERT INTO [video_item] ( [description], [title], [category], [rating], [thumb_img_url], [year], [tags], [content_url]) VALUES ( 'The Amazing Spider-Man is a 2012 American superhero film based on the Marvel Comics character Spider-Man and sharing the title of the character longest-running comic book of the same name ', 'The Amazing Spider-Man', 'Movies', 0, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577185883018&di=db9bd35e44fa2424d1781da7f7565fab&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201503%2F11%2F20150311201041_w2NPH.jpeg', 2000, 'latest, featured', 'http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool''s%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4');"};

    public VideoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + AbstractVideoItemProvider.Tables.VIDEO_ITEM + " ("
                + VideoItemContract.VideoItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VideoItemContract.VideoItemColumns.DESCRIPTION + " TEXT NOT NULL,"
                + VideoItemContract.VideoItemColumns.TITLE + " TEXT NOT NULL DEFAULT '',"
                + VideoItemContract.VideoItemColumns.CATEGORY + " TEXT NOT NULL DEFAULT 'Movies',"
                + VideoItemContract.VideoItemColumns.RATING + " INTEGER DEFAULT 0,"
                + VideoItemContract.VideoItemColumns.THUMB_IMG_URL + " TEXT,"
                + VideoItemContract.VideoItemColumns.YEAR + " INTEGER DEFAULT 2000,"
                + VideoItemContract.VideoItemColumns.TAGS + " TEXT,"
                + VideoItemContract.VideoItemColumns.CONTENT_URL + " TEXT NOT NULL"
                + ")";
        Log.d("VideoDatabase", sql);

        db.execSQL(sql);
        inserData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AbstractVideoItemProvider.Tables.VIDEO_ITEM);
        onCreate(db);
    }

    public void inserData(SQLiteDatabase db) {
        for (String record : insert) {
            db.execSQL(record);
        }

    }
}
