package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView tvAboutText,tvAboutUsText;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        tvAboutText = (TextView) findViewById(R.id.tvAboutText);
        tvAboutUsText = (TextView) findViewById(R.id.tvAboutUs);

        AboutActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        String textAbout = "Na budżet obywatelski IV edycji przewidziano 40 mln zł z czego na zadania osiedlowe 30.050.000,00 zł, a na zadania ponadosiedlowe 9.950.000,00 zł.\n" +
                "\n" +
                "W bieżącym roku mieszkańcy naszego miasta w okresie od 7 marca do 20 kwietnia będą mogli zgłosić takie propozycje zadań do realizacji w 2017 r., które, w ich przekonaniu, najbardziej odpowiadają potrzebom osiedlowym albo ponadosiedlowym.\n" +
                "\n" +
                "Propozycje zadań, które przeszły analizę merytoryczną, m.in. co do możliwości ich realizacji w zgodzie z prawem i w ciągu jednego roku budżetowego, zostaną poddane pod głosowanie, które będzie trwało 9 dni, od 10 do 18 września 2016 r. - w przypadku głosowania papierowego oraz 30 dni, od 10 września do 9 października 2016 r. - w przypadku głosowania internetowego.\n" +
                "\n" +
                "Te zadania, które uzyskały największą liczbę głosów i zmieściły się w limicie środków przeznaczonych na budżet obywatelski, będą  zrealizowane w 2017 r.\n" +
                "\n" +
                "W ten sposób mieszkańcy Łodzi mogą bezpośrednio wpływać na to, jak są wydawane środki z budżetu miasta.";
        String textAboutUs = "Pozdrawiamy, zespół CivicProject";
        tvAboutText.setText(textAbout);
        tvAboutUsText.setText(textAboutUs);
    }
}
