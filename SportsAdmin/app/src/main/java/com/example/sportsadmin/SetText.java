package com.example.sportsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetText extends AppCompatActivity
{
    static MediaPlayer mediaPlayer1; //ορισμός static μεταβλητής για αναπαραγωγή ήχου mp3
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText editText; //ορισμός global μεταβλητής της class SetText
    TextView textView2;//ορισμός global μεταβλητής της class TextView
    Button button2, button6;//ορισμός global μεταβλητών της class Button
    DatabaseReference myRef1 = database.getReference("Texts/Football"); //μεταβλητές με paths για Database
    DatabaseReference myRef2 = database.getReference("Texts/Basketball");
    DatabaseReference myRef3 = database.getReference("Texts/Tennis");
    DatabaseReference myRef4 = database.getReference("Texts/Volleyball");
    private String origin; //private μεταβλητή, σε αυτή θα αποθηκεύεται όρισμα από activity αρχικού menu



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_text);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.successfulsound);//instantiation της μεταβλητής
        editText = findViewById(R.id.editText);
        textView2 = findViewById(R.id.textView2);
        button2 = findViewById(R.id.button2);
        button6 = findViewById(R.id.button6);
        editText.setVisibility(View.INVISIBLE);//αρχικά αόρατο το editText και το κουμπί για save
        button2.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();    //και εδώ object της class της Intent
        origin = intent.getStringExtra("origin"); //για να δεχτεί την παράμετρο του άλλου activity
        if(origin.equals("Football")) //εμφάνιση κειμένου ανάλογου με κουμπί που πατήθηκε σε menu
        {
            myRef1.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    textView2.setText(dataSnapshot.getValue().toString()); //διάβασμα από database σε textView
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(origin.equals("Basketball"))
        {
            myRef2.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    textView2.setText(dataSnapshot.getValue().toString()); //διάβασμα από database σε textView
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(origin.equals("Tennis"))
        {
            myRef3.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    textView2.setText(dataSnapshot.getValue().toString()); //διάβασμα από database σε textView
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(origin.equals("Volleyball"))
        {
            myRef4.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    textView2.setText(dataSnapshot.getValue().toString()); //διάβασμα από database σε textView
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void edit(View View) //κουμπί για να δοθεί η δυνατότητα εγγραφής κειμένου
    {
        editText.setText(textView2.getText().toString()); //αντιγραφή από textView που το πήρε από database
        textView2.setVisibility(android.view.View.INVISIBLE); //κρύψιμο textView και κουμπιού για edit
        button6.setVisibility(android.view.View.INVISIBLE);
        editText.setVisibility(android.view.View.VISIBLE);//εμφάνιση του editText και του κουμπιού save
        button2.setVisibility(android.view.View.VISIBLE);
    }


    public void save(View view) //κουμπί για εγγραφή κειμένου στη Realtime Database
    {
        if(origin.equals("Football")) //σειρά από if-else για να δούμε σε ποιον κόμβο database θα γράψουμε
        {
            myRef1.setValue(editText.getText().toString());
        }
        else if(origin.equals("Basketball"))
        {
            myRef2.setValue(editText.getText().toString());
        }
        else if(origin.equals("Tennis"))
        {
            myRef3.setValue(editText.getText().toString());
        }
        else if(origin.equals("Volleyball"))
        {
            myRef4.setValue(editText.getText().toString());

        }
        //μετά από οποιαδήποτε περίπτωση εκτελούνται τα παρακάτω
        mediaPlayer1.start(); // αναπαραγωγή ήχου μετά το επιτυχές save
        Toast.makeText(this, "New text saved successfully", Toast.LENGTH_LONG).show();//μήνυμα
        textView2.setText(editText.getText().toString()); //αντιγραφή νέου κειμένου και στο textView
        editText.setVisibility(View.INVISIBLE); //κρύβονται ξανά το editText και το κουμπί save
        button2.setVisibility(View.INVISIBLE);
        textView2.setVisibility(android.view.View.VISIBLE);//εμφάνιση του textView και του κουμπιού edit
        button6.setVisibility(android.view.View.VISIBLE);
    }

}
