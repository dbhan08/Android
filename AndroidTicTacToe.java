/* This project is an android tic tac toe game, in which the user is X and can manually choose their move
and the device makes a move as O
By:Deyvik Bhan
Date: 2/11/19
 */


package com.example.dbhan.androidttt;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Below gives each induvidual button a spot on the grid array
        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);
        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);
        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);
        // Below sets on click listener for each button
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                grid[x][y].setOnClickListener(this);

            }
        }
    }

    @Override
    public void onClick( View i) {
        Button b = (Button) i;
        // Button input initialized
        // Following code goes through grid array, and puts an x on the grid, if some conditions are met
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (b.equals(grid[x][y])) {
                    if (board[x][y] == BLANK) {
                        // Sets text, disables button, and records button as X_Move
                        b.setText("X");
                        b.setEnabled(false);
                        board[x][y] = X_MOVE;
                        // AI move if no one has won or there is no tie
                        if(!checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
                            aiMove();
                        }
                    }
                }
            }

        }
        // If x won
      if(checkWin(X_MOVE)) {

            Toast.makeText(this,"X Won", Toast.LENGTH_SHORT).show();
            clearBoard();

        } // If it is a tie
        else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
            Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
            clearBoard();
        }
    }
// Following function is responsble for ai move
    public void aiMove() {
        // Try to win
        if(checkSingleBlank(O_MOVE)) {
            // Tries to win before it tries to block
            return;
        }
        // Try to block
        if(checkSingleBlank(X_MOVE)) {
            return;
        }
        //play randomly
        // If there is no way to win, and there is nothing to block then just p;aces randomly
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if(board[x][y] == BLANK) {
                    list.add(x*10+y);
                }
            }
        }
        int choice = (int)(Math.random()*list.size());
        board[list.get(choice) / 10][list.get(choice)%10] = O_MOVE;
        grid[list.get(choice) / 10][list.get(choice)%10].setText("O");
        grid[list.get(choice) / 10][list.get(choice)%10].setEnabled(false);
         if(checkWin(O_MOVE)) {
             // Checks if O won

            Toast.makeText(this, "O Won", Toast.LENGTH_SHORT).show();
            clearBoard();
        } else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
            Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
            clearBoard();
        }
    }
    // Checks if there is a single blank spot between two O's or two X's
    public boolean checkSingleBlank(int player) {
        int oCount = 0;
        int blankCount = 0;
        int blankX = 0;
        int blankY = 0;
        // Integers which track
        // Checks columns wins
        for(int x = 0; x < 3; x++) {
             oCount = 0;
             blankCount = 0;
             blankX = 0;
             blankY = 0;
            for(int y = 0; y < 3; y++) {
                if(board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }

                if(board[x][y] == player) {
                    oCount++;
                }
            }
            if(oCount == 2 && blankCount == 1) {
                board[blankX][blankY] = O_MOVE;
                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
                if(checkWin(O_MOVE)) {

                    Toast.makeText(this, "O Won", Toast.LENGTH_SHORT).show();
                    clearBoard();
                } else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
                    Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
                    clearBoard();
                }
                return true;
            }
            // Checks if there is a single space between two O's or X's in collumns
        }
        //Checks rows win
        for(int y = 0; y < 3; y++) {
             oCount = 0;
             blankCount = 0;
             blankX = 0;
             blankY = 0;
            for(int x = 0; x < 3; x++) {
                if(board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }

                if(board[x][y] == player) {
                    oCount++;
                }
            }
            if(oCount == 2 && blankCount == 1) {
                board[blankX][blankY] = O_MOVE;
               


                grid[blankX][blankY].setText("O");
                grid[blankX][blankY].setEnabled(false);
                if(checkWin(O_MOVE)) {

                    Toast.makeText(this, "O Won", Toast.LENGTH_SHORT).show();
                    clearBoard();
                } else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
                    Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
                    clearBoard();
                }
                return true;
            }
        }
        // Checks if there is a single space between two O's or X's in collumns
        //Check for top left to bottom right diagnol
         oCount = 0;
         blankCount = 0;
         blankX = 0;
         blankY = 0;
            if(board[0][0] == BLANK) {
                blankCount++;
                blankX = 0;
                blankY = 0;
            }
            if(board[0][0] == player) {
                oCount++;
            }
        if(board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        if(board[1][1] == player) {
            oCount++;
        }
        if(board[2][2] == BLANK) {
            blankCount++;
            blankX = 2;
            blankY = 2;
        }
        if(board[2][2] == player) {
            oCount++;
        }
        if(oCount == 2 && blankCount == 1) {
            board[blankX][blankY] = O_MOVE;

            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            if(checkWin(O_MOVE)) {

                Toast.makeText(this, "O Won", Toast.LENGTH_SHORT).show();
                clearBoard();
            } else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
                Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
                clearBoard();
            }

            return true;
        }
        // Checks if there is a single space between two O's or X's in the left to right diagnol

        // Top right to the bottom left
        // Checks diagnol
        oCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;

        if(board[2][0] == BLANK) {
            blankCount++;
            blankX = 2;
            blankY = 0;
        }
        if(board[2][0] == player) {
            oCount++;
        }
        if(board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        if(board[1][1] == player) {
            oCount++;
        }
        if(board[0][2] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 2;
        }
        if(board[0][2] == player) {
            oCount++;
        }
        if(oCount == 2 && blankCount == 1) {
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            if(checkWin(O_MOVE)) {

                Toast.makeText(this, "O Won", Toast.LENGTH_SHORT).show();
                clearBoard();
            } else if(checkTie() && !checkWin(X_MOVE) && !checkWin(O_MOVE)) {
                Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show();
                clearBoard();
            }
            return true;
        }
        return false;
    }
    // Checks if there is a single space between two O's or X's in the right to left diagnol

    // Following code checks win
    public boolean checkWin(int player) {
        // Checks all the possible kinds of wins
        if(board[0][0] == player && board[0][1] == player && board[0][2] == player) {

            return true;
        }
        if(board[1][0] == player && board[1][1] == player && board[1][2] == player) {

            return true;
        }
        if(board[2][0] == player && board[2][1] == player && board[2][2] == player) {

            return true;
        }
        if(board[0][0] == player && board[1][0] == player && board[2][0] == player) {

            return true;
        }
        if(board[0][1] == player && board[1][1] == player && board[2][1] == player) {

            return true;
        }
        if(board[0][2] == player && board[1][2] == player && board[2][2] == player) {

            return true;
        }
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player) {

            return true;
        }
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player) {

            return true;
        }
        return false;
    }
    // Following function checks if there is a tie
    public boolean checkTie() {
        //Checks if all the possible spots are taken then  declares it a tie
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if(board[row][column] == BLANK) {
                    return false;
                }
            }
        }
        return true;
    }
    // Function below resets board
    public void clearBoard() {
        for(int a = 0; a < 3; a++) {
            for(int c = 0; c < 3; c++) {
                Button e = grid[a][c];
                e.setText("");
                e.setEnabled(true);
                board[a][c] = BLANK;

            }

        }
    }
}

