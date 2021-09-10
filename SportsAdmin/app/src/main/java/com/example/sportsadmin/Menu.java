package com.example.sportsadmin;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;




public class Menu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void go1(View view) //κείμενο για ποδόσφαιρο
    {
        Intent intent = new Intent(this, SetText.class);
        intent.putExtra("origin", "Football"); //έξτρα παράμετρος σε intent, δείχνει από ποιο κουμπί προήλθε
        startActivity(intent);
    }

    public void go2(View view) //κείμενο για μπάσκετ
    {
        Intent intent = new Intent(this, SetText.class);
        intent.putExtra("origin", "Basketball"); //έξτρα παράμετρος σε intent, δείχνει από ποιο κουμπί προήλθε
        startActivity(intent);
    }

    public void go3(View view) //κείμενο για τένις
    {
        Intent intent = new Intent(this, SetText.class);
        intent.putExtra("origin", "Tennis"); //έξτρα παράμετρος σε intent, δείχνει από ποιο κουμπί προήλθε
        startActivity(intent);
    }

    public void go4(View view) //κείμενο για βόλεϋ
    {
        Intent intent = new Intent(this, SetText.class);
        intent.putExtra("origin", "Volleyball"); //έξτρα παράμετρος σε intent, δείχνει από ποιο κουμπί προήλθε
        startActivity(intent);
    }
}
