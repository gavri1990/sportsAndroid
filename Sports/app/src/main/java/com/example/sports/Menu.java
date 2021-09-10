package com.example.sports;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;

import java.util.ArrayList;

public class Menu extends AppCompatActivity
{
    MyTTS tts; //μεταβλητή της class MyTTS, που προσθέσαμε σε project
    static MediaPlayer mediaPlayer1; //ορισμός static μεταβλητής για αναπαραγωγή ήχου mp3
    static MediaPlayer mediaPlayer2;
    static MediaPlayer mediaPlayer3;
    static MediaPlayer mediaPlayer4;
    final static int REQCODE = 987;

   @Override
    protected void onCreate(Bundle savedInstanceState)
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       tts = new MyTTS(this); //κλήση constructor της class MyTTS, δίνουμε το context
        mediaPlayer1 = MediaPlayer.create(this, R.raw.ucl); //instantiation της μεταβλητής
        mediaPlayer2 = MediaPlayer.create(this, R.raw.kobedunks);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.wimbledon);
        mediaPlayer4 = MediaPlayer.create(this, R.raw.letsgo);
    }

    public void go1(View view)
    {
        Intent intent = new Intent(this, Football.class);
        startActivity(intent);
        mediaPlayer1.start();
    }

    public void go2(View view)
    {
        Intent intent = new Intent(this, Basketball.class);
        startActivity(intent);
        mediaPlayer2.start();
    }

    public void go3(View view)
    {
        Intent intent = new Intent(this, Tennis.class);
        startActivity(intent);
        mediaPlayer3.start();
    }

    public void  go4(View view)
    {
        Intent intent = new Intent(this, Volleyball.class);
        startActivity(intent);
        mediaPlayer4.start();
    }

    public void  go5(View view)
    {
        Intent intent = new Intent(this, Tracker.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQCODE && resultCode == RESULT_OK) //αν λάβει input χρήστη
        {
            ArrayList<String> matches =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(matches.contains("football")) //αν το "football" βρίσκεται στη λίστα αποτελεσμάτων
            {
                Intent intent = new Intent(this, Football.class);
                startActivity(intent);
                mediaPlayer1.start();
            }
            else if(matches.contains("basketball") || matches.contains("basket") || matches.contains("μπάσκετ"))
            {
                Intent intent = new Intent(this, Basketball.class);
                startActivity(intent);
                mediaPlayer2.start();
            }
            else if(matches.contains("tennis") || matches.contains("τέννις"))
            {
                Intent intent = new Intent(this, Tennis.class);
                startActivity(intent);
                mediaPlayer3.start();
            }
            else if(matches.contains("volleyball") || matches.contains("volley") || matches.contains("βόλεϊ"))
            {
                Intent intent = new Intent(this, Volleyball.class);
                startActivity(intent);
                mediaPlayer4.start();
            }
            else if(matches.contains("location") || matches.contains("show location"))
            {
                Intent intent = new Intent(this, Tracker.class);
                startActivity(intent);
            }
            else //αν το input δεν αφορά σε ένα από τα 4 αθλήματα ούτε το Location
            {
                tts.speak("Please try again"); //προφορική μετάδοση μηνύματος
            }
        }
    }

    public void listen(View view)//σε πάτημα κουμπιού για προφορικό μήνυμα μετάβασης
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//χρήση υπηρεσίας του λειτουργικού
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Which sport you want to learn about?");
        startActivityForResult(intent, REQCODE);
    }
}
