package com.example.sports;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

class MyTTS
{
    private TextToSpeech tts;
    private TextToSpeech.OnInitListener listener =
            new TextToSpeech.OnInitListener()
    {
        @Override
        public void onInit(int status)
        {
            tts.setLanguage(Locale.ENGLISH);
        }
    };

    public MyTTS(Context context)  //constructor που θα παίρνει ως παράμετρο το context της activity
    {
        tts = new TextToSpeech(context, listener);
    }

    public void speak(String text) //String ως παράμετρος για να διαβαστεί προφορικά
    {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
    }

    public void stopText() //μέθοδος για παύση προφορικής ανάγνωσης
    {
        tts.stop();
    }
}
