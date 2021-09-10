package com.example.sports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tracker extends AppCompatActivity implements LocationListener //implements LocationListener
{
    LocationManager locationManager;//ορισμός global μεταβλητής της class LocationManager
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    ImageView imageView1, imageView2, imageView3, imageView4;//ορισμός global μεταβλητών της class Imageview
    CountDownTimer timer; //global για να μπορεί να τον σταματήσει το κουμπί Gps off
    private double x, y; //μεταβλητές για latitude και longitude, global για να τις βλέπει και ο timer
    private double oldX, oldY; //μεταβλητές για προηγούμενη τιμή latitude + longitude
    boolean gpsCopied = false; //χρήση στην 1η αντιγραφή x, y σε oldX, oldY
    boolean gpsSent = false; //με αυτό θα βλέπει η onTick αν στάλθηκαν καινούριες συντεταγμένες ανάμεσα σε αυτό και το προηγ. Tick
    boolean gpsOn = false;//true όταν πατηθεί το κουμπί Gps On
    boolean timerInstantiated = false;//true όταν γίνει instatiation στον timer


    @Override
    protected void onStop() //σε κλείσιμο του activity
    {
        super.onStop();
        if(timerInstantiated == true) //cancel θα γίνει μόνο αν έχει γίνει instantiated o timer, αλλιώς NullPointerException!
        {
            timer.cancel(); //σταματά να τρέχει ο timer στο παρασκήνιο, δεν μας πειράζει αν έχει γίνει ήδη cancel
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        textView1 = findViewById(R.id.textView6);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        imageView1.setVisibility(View.INVISIBLE); //αρχικά αόρατα τα 4 κουμπιά + labels
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);
        textView5.setVisibility(View.INVISIBLE);
        textView6.setVisibility(View.INVISIBLE);
        textView7.setVisibility(View.INVISIBLE);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE); //χρειάζεται και cast
    }

    public void gpsOn(View view)
    {
        if(gpsOn == true) //είναι πατημένο ήδη το κουμπί
        {
            Toast.makeText(this, "Gps is already on", Toast.LENGTH_SHORT).show();
        }
        else //gps δεν έχει ανοίξει καθόλου ή έχει ανοίξει αλλά έκλεισε
        {
            gpsOn = true; //θα την εξετάσει η method gpsOff
            Toast.makeText(this, "Gps is on", Toast.LENGTH_SHORT).show();
            //αν έχουμε ήδη το permission του χρήστη
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,0,this);
            }
            else
            {
                //αν δεν έχουμε το permission του χρήστη, το ζητάμε
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 457);
            }

            timer = new CountDownTimer(300000, 1000)
                    //διάρκεια 5 λεπτών, αν τελειώσει, ο χρήστης θα ενημερωθεί. Αν το activity κλείσει νωρίτερα, γίνεται cancel στα ticks
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    if(gpsSent == false) //δεν έχουν "σταλθεί" νέες συντεταγμένες
                    {
                        textView1.setVisibility(View.INVISIBLE);
                        imageView1.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.INVISIBLE);
                        imageView2.setVisibility(View.INVISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                        imageView3.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.INVISIBLE);
                        imageView4.setVisibility(View.INVISIBLE);
                    }
                    else//έχουν "σταλθεί" νέες συντεταγμένες
                    {
                        if (x > (oldX + 0.000001)) //κίνηση βόρεια μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                        {
                            textView1.setVisibility(View.VISIBLE);
                            imageView1.setVisibility(View.VISIBLE);
                            textView3.setVisibility(View.INVISIBLE);
                            imageView3.setVisibility(View.INVISIBLE);
                            if (y > (oldY + 0.000001)) //κίνηση ανατολικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView2.setVisibility(View.VISIBLE);
                                imageView2.setVisibility(View.VISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                            else if (y < (oldY - 0.000001)) //κίνηση δυτικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView4.setVisibility(View.VISIBLE);
                                imageView4.setVisibility(View.VISIBLE);
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                            }
                            else //αμελητέα κίνηση σε οριζόντιο άξονα
                            {
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                        }
                        else if (x < (oldX - 0.000001)) //κίνηση νότια μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                        {
                            textView3.setVisibility(View.VISIBLE);
                            imageView3.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.INVISIBLE);
                            imageView1.setVisibility(View.INVISIBLE);

                            if (y > (oldY + 0.000001)) //κίνηση ανατολικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView2.setVisibility(View.VISIBLE);
                                imageView2.setVisibility(View.VISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                            else if (y < (oldY - 0.000001)) //κίνηση δυτικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView4.setVisibility(View.VISIBLE);
                                imageView4.setVisibility(View.VISIBLE);
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                            }
                            else //αμελητέα κίνηση σε οριζόντιο άξονα
                            {
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                        }
                        else //αμελητέα κίνηση σε κάθετο άξονα
                        {
                            textView1.setVisibility(View.INVISIBLE);
                            imageView1.setVisibility(View.INVISIBLE);
                            textView3.setVisibility(View.INVISIBLE);
                            imageView3.setVisibility(View.INVISIBLE);

                            if (y > (oldY + 0.000001)) //κίνηση ανατολικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView2.setVisibility(View.VISIBLE);
                                imageView2.setVisibility(View.VISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                            else if (y < (oldY - 0.000001)) //κίνηση δυτικά μεγαλύτερη ενός ορίου σε σχέση με προηγούμενη
                            {
                                textView4.setVisibility(View.VISIBLE);
                                imageView4.setVisibility(View.VISIBLE);
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                            }
                            else //αμελητέα κίνηση σε οριζόντιο άξονα
                            {
                                textView2.setVisibility(View.INVISIBLE);
                                imageView2.setVisibility(View.INVISIBLE);
                                textView4.setVisibility(View.INVISIBLE);
                                imageView4.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                    oldX = x;//μεταβλητές παίρνουν τιμή x και y, που θα αλλάξει όμως πριν το επόμενο tick
                    oldY = y;
                    gpsSent = false; //ώστε να μην αλλάξει κάτι αν δεν έρθει νέα πληροφορία
                }

                @Override
                public void onFinish()
                {
                    imageView1.setVisibility(View.INVISIBLE); //ξανά αόρατα τα 4 κουμπιά + labels
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    textView3.setVisibility(View.INVISIBLE);
                    textView4.setVisibility(View.INVISIBLE);
                    textView7.setVisibility(View.INVISIBLE);
                    Toast.makeText(Tracker.this, "Direction tracker says \'Goodbye\'.", Toast.LENGTH_LONG).show();
                }
            };
            timer.start(); //εκκίνηση του timer
            timerInstantiated = true; //αλλάζει τιμή το global πεδίο, έγινε instantiation στον timer
            textView5.setVisibility(View.VISIBLE); //εμφάνιση των 2 textView για παρουσιάση συντεταγμένων
            textView6.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.VISIBLE);
        }

    }

    public void gpsOff(View view) //απενεργοποίηση gps
    {
        if(gpsOn == true) //αν έχει πατηθεί ήδη το Gps On
        {
            imageView1.setVisibility(View.INVISIBLE); //αρχικά αόρατα τα 4 κουμπιά + labels
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
            textView1.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            textView5.setVisibility(View.INVISIBLE);
            textView6.setVisibility(View.INVISIBLE);
            textView7.setVisibility(View.INVISIBLE);
            locationManager.removeUpdates(this);
            textView1.setText(""); //καθαρισμός textView με συντεταγμένες
            Toast.makeText(this, "Gps turned off", Toast.LENGTH_SHORT).show();
            timer.cancel(); //cancel στον timer, δεν μας πειράζει να εκτελεστεί ακόμα κι αν έχει γίνει ήδη cancel
            gpsOn = false; //true θα ξαναγίνει αν πατηθεί το αντίστοιχο κουμπί
        }
        else //δεν έχει ανοιχτεί το gps
        {
            Toast.makeText(this, "Gps is already off", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        x = location.getLatitude(); //αποθηκεύουμε γεωγρ.πλάτος και μήκος στις μεταβλητές
        y = location.getLongitude();
        if(gpsCopied == false)
        {
            oldX = x; //αντιγραφή τους μόνο μια φορά, για το 1ο onTick που θα ακολουθήσει
            oldY = y;
            gpsCopied = true;
        }
        gpsSent = true; //άλλαξαν τα oldX, oldY άρα νέα πληροφορία προς onTick
        textView6.setText(String.valueOf(x) + ", " + String.valueOf(y));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
