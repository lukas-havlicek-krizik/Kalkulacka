package com.example.kalkulacka;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Deklarace proměnných pro prvky v aplikaci
    Spinner spinner;
    Button tlacitko;
    EditText cislo1;
    EditText cislo2;
    TextView vysledekOkno;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        //Přiřazení jednotlivých prvků v aplikaci podle ID
        spinner = findViewById(R.id.spinner);
        tlacitko = findViewById(R.id.tlacitko);
        cislo1 = findViewById(R.id.cislo1);
        cislo2 = findViewById(R.id.cislo2);
        vysledekOkno = findViewById(R.id.vysledek);
        checkBox = findViewById(R.id.checkBox);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Vytvoření adapteru s odkázáním na soubor strings.xml, kde se nachází nabídka pro spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerVyber, android.R.layout.simple_spinner_item);
        //Nastavení layoutu pro rozbalovací seznam
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Nastavení adapteru pro proměnnou spiner
        spinner.setAdapter(adapter);

        //Reagování na změnu výběru ve spinneru
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                //Převod výběru ve spinneru do proměnné "vyber" typu CharSequence
                CharSequence vyber = spinner.getSelectedItem().toString();
                //Podmínka: pokud uživatel zvolil možnost faktoriálu, políčko pro druhé číslo zmizí
                if(vyber.equals("a!")) {
                    cislo2.setVisibility(View.INVISIBLE);
                }else{
                    cislo2.setVisibility(View.VISIBLE);

                }
            }
            //Není potřeba vyplňovat, ale je nutné toto zde mít kvůli funkčnosti
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Reagování na zaškrtnutí checkboxu
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Podmínka: pokud je checkbox zaškrtnut, okno pro výsledky zmizí a výsledky se budou zobrazovat ve vyskakovacím okně
                if (isChecked) {
                    vysledekOkno.setVisibility(View.INVISIBLE);
                } else {
                    vysledekOkno.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    //Metoda, která se spouští při stisknutí na tlačítko "=" v aplikaci
    public void vypocet(View view) {
        //Převod výběru ve spinneru do proměnné "vyber" typu CharSequence
        CharSequence vyber = spinner.getSelectedItem().toString();
        //Vytvoření contextu pro Toast (vyskak. okno)
        Context context = getApplicationContext();
        //Vytvoření duration - jak dlouho Toast (vyskak. okno) bude trvat
        int duration = Toast.LENGTH_LONG;

        //Obsah pole pro první číslo je přiřazen String proměnné "cisloJedna"
        String cisloJedna = cislo1.getText().toString();
        //Deklarace a inicializace double proměnné "cislo1"
        double cislo1 = 0;

        //Obsah pole pro druhé číslo je přiřazen String proměnné "cisloDve"
        String cisloDve = cislo2.getText().toString();
        //Deklarace a inicializace double proměnné "cislo2"
        double cislo2 = 0;

        //Podmínka: pokud je pole pro první číslo prázdné a zároveň je vybráno počítání faktoriálu
        //          nebo pokud pole pro první a zároveň druhé číslo prázdné a zároveň je zvolena možnost faktoriálu
        //          zobrazí se vyskakovací okno s hláškou
        if ((cisloJedna.isEmpty() && vyber.equals("a!")) || (cisloJedna.isEmpty() && cisloDve.isEmpty() && vyber.equals("a!"))){
            Toast.makeText(context, "Musíte zadat číslo.", duration).show();
            return;

        //Podmínka: pokud je pole pro první nebo druhé číslo prázdné a zároveň byla zvolena možnost jakákoli jiná než faktoriál
        //          zobrazí se vyskakovací okno s hláškou
        }else if ((cisloJedna.isEmpty() || cisloDve.isEmpty()) && !vyber.equals("a!")) {
            Toast.makeText(context, "Musíte zadat obě čísla.", duration).show();
            return;
        }else {
            //Převod obsahu na číselnou hodnotu do proměnné "cislo1"
            cislo1 = Double.parseDouble(cisloJedna);

            //Podmínka: pokud je zvolena možnost faktoriál a zároveň je pole pro druhé číslo prázdné, přiřadí se proměnné "cislo2" 1
            //          pokud ne, je obsah převeden na číselnou hodnotu jako první číslo
            if(vyber.equals("a!") && cisloDve.isEmpty()){
                cislo2 = 1;
            }else {
                cislo2 = Double.parseDouble(cisloDve);
            }
        }

        //Deklarace proměnné výsledek
        double vysledek;

        //Podmínka: pokud byla zvolena možnost "a+b", provede se sčítání
        if(vyber.equals("a+b")){
            vysledek = (cislo1 + cislo2);
            CharSequence vypis = cisloJedna + " + " + cisloDve + " = " + vysledek;
            //Pokud bylo zaškrtnut checkbox, výsledek se zobrazí ve vyskakovacím okně,
            //pokud ne, zobrazí se v poli pro výsledek
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a-b", provede se odečítání
        }else if(vyber.equals("a-b")){
            vysledek = (cislo1-cislo2);
            CharSequence vypis = cisloJedna + " - " + cisloDve + " = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a*b", provede se násobení
        }else if(vyber.equals("a*b")){
            vysledek = (cislo1*cislo2);
            CharSequence vypis = cisloJedna + " × " + cisloDve + " = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a/b", provede se dělení
        }else if(vyber.equals("a/b")){
            if(cislo2!=0) {
                vysledek = (cislo1/cislo2);
                CharSequence vypis = cisloJedna + " ÷ " + cisloDve + " = " + vysledek;
                if(checkBox.isChecked()){
                    Toast.makeText(context, vypis, duration).show();
                    vysledekOkno.setText(vypis);
                }else {
                    vysledekOkno.setText(vypis);
                }
            //Podmínky, která ošetřuje, aby uživatel nemohl dělit nulou
            }else {
                CharSequence vypis = "Snažíte se dělit nulou.";
                if(checkBox.isChecked()){
                    Toast.makeText(context, vypis, duration).show();
                    vysledekOkno.setText(vypis);
                }else {
                    vysledekOkno.setText(vypis);
                }            }

        //Podmínka: pokud byla zvolena možnost "a%b", provede se modulo
        }else if(vyber.equals("a%b")){
            vysledek = (cislo1%cislo2);
            CharSequence vypis = cisloJedna + " % " + cisloDve + " = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a^b", provede se umocnění
        }else if(vyber.equals("a^b")){
            vysledek = Math.pow(cislo1, cislo2);
            CharSequence vypis = cisloJedna + " ^ " + cisloDve + " = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a^(1/b)" (-> pouze jiný zápis pro odmocninu), provede se odmocnění
        }else if(vyber.equals("a^(1/b)")){
            vysledek = Math.pow(cislo1, (1/cislo2));
            CharSequence vypis = cisloJedna + " ^ " + "1/" + cisloDve + " = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }

        //Podmínka: pokud byla zvolena možnost "a!", provede se faktoriál
        }else if(vyber.equals("a!")){
            vysledek = 1;
            for (int i = 1; i <= cislo1; i++) {
                vysledek = vysledek * i;
            }
            CharSequence vypis = cisloJedna + "! = " + vysledek;
            if(checkBox.isChecked()){
                Toast.makeText(context, vypis, duration).show();
                vysledekOkno.setText(vypis);
            }else {
                vysledekOkno.setText(vypis);
            }
        }



    }
}