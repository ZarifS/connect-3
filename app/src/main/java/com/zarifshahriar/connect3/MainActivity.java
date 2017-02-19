package com.zarifshahriar.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int player;
    private int[] moves = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[][] winningMoves = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private boolean active;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tapped = Integer.parseInt(counter.getTag().toString()) - 1;
        if (moves[tapped] == 0 && active) {
            moves[tapped] = player;
            counter.setTranslationY(-1000f);
            int tmp = player;
            if (player == 1) {
                Log.i("Turn Phase:", "1");
                counter.setImageResource(R.drawable.yellow);
                player = 2;
            } else {
                Log.i("Turn Phase:", "2");
                counter.setImageResource(R.drawable.red);
                player = 1;
            }
            counter.animate().translationYBy(1000f).setDuration(500);

            if (checkResult(tmp)) {
                System.out.println("Player:" + tmp + " Won!");
            } else checkDraw();
        }
    }

    private boolean checkResult(int player) {
        for (int[] winningMove : winningMoves) {
            if (moves[winningMove[0]] == moves[winningMove[1]]
                    && moves[winningMove[1]] == moves[winningMove[2]]
                    && moves[winningMove[2]] == player) {
                String winner = "Yellow is the Winner!";
                if (player == 2) winner = "Red is the Winner!";
                displayMessage(winner);
                return true;
            }
        }
        return false;
    }

    private void displayMessage(String message) {
        active = false;
        TextView text = (TextView) findViewById(R.id.winMessage);
        text.setText(message);
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setTranslationY(-1000f);
        layout.animate().translationYBy(1000f).setDuration(500);
        layout.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        player = 1;
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < grid.getChildCount(); i++) {
             moves[i]=0; // sets moves to all 0
            ((ImageView) grid.getChildAt(i)).setImageResource(0); // sets images to empty
        }
        active = true;
    }

    public boolean checkDraw() {
        for (int move : moves) {
            if (move == 0) return false;
        }
        displayMessage("It's a Draw!");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        player = 1;
        active = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
