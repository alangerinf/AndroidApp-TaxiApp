package com.apptaxi.clientedemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Sistemas on 10/11/2015.
 */
public class SoundManager {
    private Context pContext;
    private SoundPool sndPool;
    private float rate = 1.0f;
    private float leftVolumen = 1.0f;
    private float rightVolumen = 1.0f;

    public SoundManager(Context appContext){
        sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC,100);
        pContext = appContext;
    }

    public int load(int idSonido){
        return sndPool.load(pContext,idSonido,1);
    }

    public void play(int idSonido)
    {
        sndPool.play(idSonido, leftVolumen, rightVolumen, 1, 0, rate);
    }
    public void unloadAll()
    {
        sndPool.release();
    }
}
