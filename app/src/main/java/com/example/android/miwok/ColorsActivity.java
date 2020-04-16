/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


//    // Listen for changes in AudioManager focus and respond accordingly
//    AudioManager.OnAudioFocusChangeListener afChangeListener =
//            new AudioManager.OnAudioFocusChangeListener() {
//                public void onAudioFocusChange(int focusChange) {
//                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
//                        // Pause playback because your Audio Focus was
//                        // temporarily stolen, but will be back soon.
//                        // i.e. for a phone call
//                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//                        // Stop playback, because you lost the Audio Focus.
//                        // i.e. the user started some other playback app
//                        // Remember to unregister your controls/buttons here.
//                        // And release the kra — Audio Focus!
//                        // You’re done.
//                        am.abandonAudioFocus(afChangeListener);
//                    } else if (focusChange ==
//                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
//                        // Lower the volume, because something else is also
//                        // playing audio over you.
//                        // i.e. for notifications or navigation directions
//                        // Depending on your audio playback, you may prefer to
//                        // pause playback here instead. You do you.
//                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                        // Resume playback, because you hold the Audio Focus
//                        // again!
//                        // i.e. the phone call ended or the nav directions
//                        // are finished
//                        // If you implement ducking and lower the volume, be
//                        // sure to return it to normal here, as well.
//                    }
//                }
//            };
//
//    // Request Focus, and register the listener that we're going to utilized for
//    // handling changes in audio state.
//    //
//    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//    int result = am.requestAudioFocus(
//            afChangeListener,
//            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);


//    // Noisy listener
//    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            releaseMediaPlayer();
//        }
//    };
//
//    // On Play
//    IntentFilter filter = new IntentFilter((AudioManager.ACTION_AUDIO_BECOMING_NOISY));

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return super.registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.mipmap.color_red, R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.mipmap.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.mipmap.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.mipmap.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.mipmap.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.mipmap.color_white, R.raw.color_white));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(NumbersActivity.this, "List item clicked", Toast.LENGTH_SHORT);
                releaseMediaPlayer();
                Word word = words.get(position);
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    public void play(View view) {

    }
}