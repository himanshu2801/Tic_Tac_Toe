package com.example.tictaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] button = new Button[3][3];
    private boolean player1Turn=true;
    private int roundcount = 0;
    private TextView s_player1;
    private TextView s_player2;
    private int player1point=0;
    private int player2point=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s_player1 = findViewById(R.id.player1);
        s_player2 = findViewById(R.id.player2);
        for (int i =0; i<3 ;i++){
            for (int j=0;j<3;j++){
                String s="button_" + i + j;
                int resid= getResources().getIdentifier(s,"id",getPackageName());
                button[i][j]= findViewById(resid);
                button[i][j].setOnClickListener(this);
            }
        }
        Button playagain = findViewById(R.id.playagain);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                playagame();
            }

            private void playagame() {
                reset();
                invisible();
            }

            private void invisible() {
                View b=findViewById(R.id.playagain);
                b.setVisibility(View.GONE);
            }
        });
        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                player1point=0;
                player2point=0;
                score();
            }
        });
        View c = findViewById(R.id.playagain);
        c.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if (player1Turn){
            ((Button) v).setText(" ");
            ((Button) v).setBackgroundResource(R.drawable.cross);
        }
        else{
            ((Button) v).setText("  ");
            ((Button) v).setBackgroundResource(R.drawable.zero);
        }
        roundcount++;
        if (check()){
            if (player1Turn){
                player1win();
                visible();
            }
            else{
                player2win();
                visible();
            }
        }else if(roundcount==9) {
            draw();
            visible();
        }
        else{
            player1Turn=!player1Turn;
        }
    }

    private void visible() {
        View b = findViewById(R.id.playagain);
        b.setVisibility(View.VISIBLE);
    }

    private void player1win() {
        player1point++;
        Toast.makeText(this, "player 1 win!", Toast.LENGTH_SHORT).show();
        score();
    }
    private void player2win(){
        player2point++;
        Toast.makeText(this, "player 2 win!", Toast.LENGTH_SHORT).show();
        score();

    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
    }

    private void reset() {
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                button[i][j].setText("");
                button[i][j].setBackgroundResource(R.drawable.reset);
            }
        }
        roundcount=0;
        player1Turn=true;
    }

    private void score() {
        s_player1.setText("player1: " + player1point);
        s_player2.setText("player2: " + player2point);

    }


    public boolean check(){
        String [][] field = new String[3][3];
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=button[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) &&field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) &&  field[1][1].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        if (field[0][2].equals(field[1][1]) &&  field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }
}
