package com.example.sports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Basketball extends AppCompatActivity
{
    MyTTS tts; //μεταβλητή της class MyTTS, που προσθέσαμε σε project
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Texts/Basketball");//μεταβλητή με path για Database
    TextView textView1; //ορισμός global μεταβλητής της class TextView


    @Override
    protected void onStop()
    {
        super.onStop();
        Menu.mediaPlayer2.pause(); //παύση αναπαραγωγής σε αρχική Activity
        Menu.mediaPlayer2.seekTo(0);
        tts.stopText(); //κλήση μεθόδου της class MyTTS για παύση αναπαραγωγής
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketball);
        tts = new MyTTS(this); //κλήση constructor της class MyTTS, δίνουμε το context του Activity
        textView1 = findViewById(R.id.textView6);
        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                textView1.setText(dataSnapshot.getValue().toString()); //διάβασμα από τη Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Now playing: Kobe dunks by Kevin Harlan", Toast.LENGTH_LONG).show();
    }


    public void speak(View view) //σε πάτημα κουμπιού για text to speech
    {
        if(textView1.getText().toString().equals("")) //αν δεν υπάρχει κείμενο
        {
            Toast.makeText(this, "No text to read", Toast.LENGTH_LONG).show();
        }
        else
        {
            tts.speak(textView1.getText().toString()); //προφορική μετάδοση κειμένου αθλήματος
        }
    }
}
