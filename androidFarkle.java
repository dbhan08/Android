/*
The following code, is the code of an android farkle gae. Where you roll 6 dice which can be scored
in various ways, and you can stop the round and add to your total score.
By:Deyvik Bhan
Date:2/20/19



 */


package com.example.dbhan.androidfarkle;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Initializes variables
    ImageButton[] buttons = new ImageButton[6];
    int[] buttonState = new int[6];
    int[] dieImages = new int[6];
    int[] dieValue = new int[6];
    // Arrays that hold different attributes of images
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_DIE = 2;
    // Final integers that hold value of what kind of die each image is
    Button roll;
    Button score;
    Button stop;
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;
    // Initializes different parts of layout
    int currentScore;
    int totalScore;
    int currentRound;
    // INfo which will be displayed on app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons[0] = (ImageButton)this.findViewById(R.id.imageButton1);
        buttons[1] = (ImageButton)this.findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton)this.findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton)this.findViewById(R.id.imageButton4);
        buttons[4] = (ImageButton)this.findViewById(R.id.imageButton5);
        buttons[5] = (ImageButton)this.findViewById(R.id.imageButton6);
        // Sets a image button to a corresponding spot in an array
        for (int a = 0; a < buttons.length; a ++) {
            buttons[a].setOnClickListener(this);
            buttons[a].setEnabled(false);
            buttons[a].setBackgroundColor(Color.LTGRAY);

        }
        // Set certain properties for all imagebuttons
        roll =(Button)this.findViewById(R.id.button1);
        roll.setOnClickListener(this);
        score =(Button)this.findViewById(R.id.button2);
        score.setOnClickListener(this);
        score.setEnabled(false);
        stop =(Button)this.findViewById(R.id.button3);
        stop.setOnClickListener(this);
        stop.setEnabled(false);
        currentScoreTV = (TextView) this.findViewById(R.id.textView1);
        totalScoreTV = (TextView) this.findViewById(R.id.textView2);
        currentRoundTV = (TextView) this.findViewById(R.id.textView3);

        dieImages[0] = R.drawable.one;
        dieImages[1] = R.drawable.two;
        dieImages[2] = R.drawable.three;
        dieImages[3] = R.drawable.four;
        dieImages[4] = R.drawable.five;
        dieImages[5] = R.drawable.six;
        // Sets each die image to a drawable
    }

    @Override
    public void onClick(View v) {
        if (v.equals(roll)) {
            // If roll is clicked
            for (int a = 0; a < buttons.length; a++) {
                if (buttonState[a] == HOT_DIE) {
                    int choice = (int) (Math.random() * 6);
                    dieValue[a] = choice;
                    buttons[a].setImageResource(dieImages[choice]);
                    buttons[a].setEnabled(true);
                    roll.setEnabled(false);
                    score.setEnabled(true);
                    stop.setEnabled(false);

                }
            }
        } else if (v.equals(score)) {
            // If score is clicked
            int[] valueCounts = new int[7];
            for (int a = 0; a < buttonState.length; a++) {
                if (buttonState[a] == SCORE_DIE) {
                    valueCounts[dieValue[a] + 1]++;

                }

            }
            if (valueCounts[2] > 0 && valueCounts[2] < 3 ||
                    valueCounts[3] > 0 && valueCounts[3] < 3 ||
                    valueCounts[4] > 0 && valueCounts[4] < 3 ||
                    valueCounts[6] > 0 && valueCounts[6] < 3) {
                // If dice can not be scored
                // INVALID DIE DETECTED
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Invalid die selected");
                alertDialogBuilder
                        .setMessage("You can only select scoring dice")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else if (valueCounts[1] == 0 && valueCounts[2] == 0 && valueCounts[3] == 0 &&
                    valueCounts[4] == 0 && valueCounts[5] == 0 && valueCounts[6] == 0) {
                // IF dice can't be scored as well

                AlertDialog.Builder AlertDialog;
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("No score!");
                alertDialogBuilder
                        .setMessage("Forfeit score and go to next round?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                currentScore = 0;
                                currentRound++;
                                currentScoreTV.setText("Current Score:" + currentScore);
                                currentRoundTV.setText("Current Round:" + currentRound);
                                resetDice();
                                // Goes to next round and resets dice
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else {
                // Below scores the dice based on the rules of the game
                if (valueCounts[1] < 3) {
                    currentScore += (valueCounts[1] * 100);
                }
                if (valueCounts[5] < 3) {
                    currentScore += (valueCounts[5] * 50);
                }
                if (valueCounts[1] >= 3) {
                    currentScore += (1000 * (valueCounts[1] - 2));
                }
                if (valueCounts[2] >= 3) {
                    currentScore += (200 * (valueCounts[2] - 2));
                }
                if (valueCounts[3] >= 3) {
                    currentScore += (300 * (valueCounts[3] - 2));
                }
                if (valueCounts[4] >= 3) {
                    currentScore += (400 * (valueCounts[4] - 2));
                }
                if (valueCounts[5] >= 3) {
                    currentScore += (500 * (valueCounts[5] - 2));
                }
                if (valueCounts[6] >= 3) {
                    currentScore += (600 * (valueCounts[6] - 2));
                }
                currentScoreTV.setText("Current Score:" + currentScore);
                for (int a = 0; a < buttons.length; a++) {
                    if (buttonState[a] == SCORE_DIE) {
                        buttonState[a] = LOCKED_DIE;
                        buttons[a].setBackgroundColor(Color.BLUE);
                        buttons[a].setEnabled(false);
                    }
                }
                roll.setEnabled(true);
                stop.setEnabled(true);
                score.setEnabled(false);

                int lockedCount = 0;
                for (int a = 0; a < buttons.length; a++) {
                    if (buttonState[a] == LOCKED_DIE) {
                        lockedCount++;

                    }

                }
                // What to do if all dice become locked(can not roll anymore)
                if (lockedCount == 6) {
                    for (int a = 0; a < buttons.length; a++) {
                        buttonState[a] = HOT_DIE;
                        buttons[a].setBackgroundColor(Color.LTGRAY);

                    }
                    roll.setEnabled(true);
                    stop.setEnabled(true);
                    score.setEnabled(false);

                }

            }
        } else if (v.equals(stop)) {
            // If stop is clicked
                totalScore += currentScore;
                currentScore = 0;
                currentScoreTV.setText("Current Score:" + currentScore);
                totalScoreTV.setText("Total Score:" + totalScore);
                currentRound++;
                currentRoundTV.setText("Current Round:" + currentRound);
                resetDice();
                // resets dice as well as changes round, resets current score, and changes total score

            } else {
                for (int a = 0; a < buttons.length; a++) {
                    if (v.equals(buttons[a])) {
                        if (buttonState[a] == HOT_DIE) {
                            buttons[a].setBackgroundColor(Color.RED);
                            buttonState[a] = SCORE_DIE;
                            // If button is clicked

                        } else {
                            buttons[a].setBackgroundColor(Color.LTGRAY);
                            buttonState[a] = HOT_DIE;
                            // If not clicked
                        }

                    }

                }

            }

        }


// Below resets the dice to original state
    private void resetDice() {
        for(int a = 0; a < buttons.length; a++) {
            buttons[a].setEnabled(false);
            buttonState[a] = HOT_DIE;
            buttons[a].setBackgroundColor(Color.LTGRAY);

        }
        roll.setEnabled(true);
        stop.setEnabled(false);
        score.setEnabled(false);
    }
}
