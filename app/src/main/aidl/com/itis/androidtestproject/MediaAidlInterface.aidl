// MediaAidlInterface.aidl
package com.itis.androidtestproject;

interface MediaAidlInterface {

    void start(in Song song);

    void play(int millis);

    void pause();

    void previous();

    void next();

    void stop();

    boolean isPlaying();

    Song getCurrentSong();

    void setCurrentSong(in Song song);
}

parcelable Song;