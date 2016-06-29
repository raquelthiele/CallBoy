package br.com.ramada.callboy;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by danie on 28/06/2016.
 */
public class SpellingBee extends Service implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    public static TextToSpeech tts;
    private String msg;


    public SpellingBee(){

    }

    public SpellingBee(Context context, String msg){
        tts = new TextToSpeech(context, this);
        this.msg = msg;
    }



    @SuppressLint("NewApi")
    @Override
    public int onStartCommand (Intent intent, int flags, int startId){
        if(intent != null){
            this.msg = intent.getStringExtra("msg");
        }
        tts = new TextToSpeech(this, this);
        Log.d("msgIRRITADA", "ODIO");
        return START_STICKY;
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            final Locale myLocale = new Locale("pt", "BR");
            int result = tts.setLanguage(myLocale);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("msgTTS", "This Language is not supported");
            } else {
                Log.e("msgTTS", "Initilization Successful");
            }

            anuncia(msg);

        } else {
            Log.e("msgTTS", "Initilization Failed!");
        }

    }

    @Override
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void anuncia(String msg) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("msg", "Entrei em if com " + msg);
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            Log.d("msg", "Entrei em else com " + msg);
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}
