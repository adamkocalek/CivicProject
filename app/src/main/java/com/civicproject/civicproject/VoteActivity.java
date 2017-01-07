package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class VoteActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rules);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView tvVote = (TextView) findViewById(R.id.tvVote);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        DateParser dateParser = new DateParser();
        String dateNotifications = dateParser.dateNotification();

        if (dateNotifications.equals("null")) {
            tvDate.setText("Wystąpił błąd przy przetwarzaniu daty, przepraszamy.");
        } else {
            tvDate.setText(dateParser.dateNotification() + "");
        }


        String tvVoteText = "Piąta, pełnoprawna odsłona niezwykle popularnej serii gier akcji, nad której rozwojem pieczę sprawuje studio Rockstar North we współpracy z koncernem Take Two Interactive. " +
                "Miejscem akcji Grand Theft Auto V jest fikcyjne miasto Los Santos (wzorowane na Los Angeles), a fabuła koncentruje się na perypetiach trójki bohaterów: Michaela De Santy, Trevora Philipsa " +
                "i Franklina Clintona, którym nieobce są zatargi z prawem. Twórcy gry pozostali wierni sandboksowemu modelowi rozgrywki, pozwalając graczom na dużą swobodę w wykonywaniu zadań i " +
                "poruszaniu się po wirtualnym mieście. Koszty produkcji i promocji tytułu oszacowane zostały na ponad 360 milionów dolarów, co pobiło wszystkie wcześniejsze rekordy w branży gier wideo. " +
                "Wychodzę na ulicę z moim pierwszym pistoletem i wsiadam do zaparkowanego niedbale auta. Wtem na ulicę wpada samochód i ścigający go radiowóz. Z zaciekawieniem ruszam za obydwoma pojazdami, " +
                "by zobaczyć w akcji metody działania organów ścigania. Policjanci mają duże problemy z opanowaniem sytuacji, więc jako wzorowy obywatel postanawiam pomóc w zatrzymaniu. Sprawnie taranuję wóz " +
                "i po chwili stoję przed nim, czekając na dalszy rozwój wydarzeń. Jeden z bandziorów wysiada i szykuje się do ucieczki – włączam wsteczny i tylnym zderzakiem perswaduję mu pozostanie na miejscu. " +
                "Mundurowi dokańczają dzieła za pomocą ołowiu. Wychodzą, przyglądają się zwłokom. Jeden z nich nieomal zostaje potrącony przez przejeżdżające kombi. „Niedzielny kierowca” – myślę i odruchowo wyciągam pistolet, " +
                "żeby wymierzyć karę. Funkcjonariusze również chwytają za broń, celując we mnie. Panowie, spokojnie, to nieporozumienie!";

        tvVote.setMovementMethod(new ScrollingMovementMethod());
        tvVote.setText(tvVoteText);

    }
}
