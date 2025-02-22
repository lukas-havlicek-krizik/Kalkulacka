package com.example.kalkulacka;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    Button tlacitko;
    EditText cislo1;
    EditText cislo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        tlacitko = findViewById(R.id.tlacitko);
        cislo1 = findViewById(R.id.cislo1);
        cislo2 = findViewById(R.id.cislo2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerVyber, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                CharSequence vyber = spinner.getSelectedItem().toString();
                if(vyber.equals("faktorial")) {
                    cislo2.setVisibility(View.INVISIBLE);
                }else{
                    cislo2.setVisibility(View.VISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void vypocet(View view){
        CharSequence vyber = spinner.getSelectedItem().toString();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        String cisloJedna = cislo1.getText().toString();
        double cislo1 = Double.parseDouble(cisloJedna);

        String cisloDve = cislo2.getText().toString();
        double cislo2 = Double.parseDouble(cisloDve);

        double vysledek;

        if(vyber.equals("+")){
            vysledek = (cislo1+cislo2);
            CharSequence vypis = cisloJedna + " + " + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("-")){
            vysledek = (cislo1-cislo2);
            CharSequence vypis = cisloJedna + " - " + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("*")){
            vysledek = (cislo1*cislo2);
            CharSequence vypis = cisloJedna + " × " + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("/")){
            if(cislo2!=0) {
                vysledek = (cislo1/cislo2);
                CharSequence vypis = cisloJedna + " ÷ " + cisloDve + " = " + vysledek;
                Toast.makeText(context, vypis, duration).show();

            }else {
                CharSequence vypis = "Erorr. Snažíte se dělit nulou.";
                Toast.makeText(context, vypis, duration).show();
            }

        }else if(vyber.equals("modulo")){

            vysledek = (cislo1%cislo2);
            CharSequence vypis = cisloJedna + " % " + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("n-tá mocnina")){

            vysledek = Math.pow(cislo1, cislo2);
            CharSequence vypis = cisloJedna + " ^ " + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("n-tá odmocnina")){

            vysledek = Math.pow(cislo1, (1/cislo2));
            CharSequence vypis = cisloJedna + " ^ " + "1/" + cisloDve + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }else if(vyber.equals("faktorial")){

            vysledek = 1;
            for (int i = 1; i <= cislo1; i++) {
                vysledek = vysledek * i;
            }
            CharSequence vypis = cisloJedna + "!" + " = " + vysledek;
            Toast.makeText(context, vypis, duration).show();

        }

    }
}