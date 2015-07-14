package bugs.bugs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import static bugs.bugs.R.id.numberPickerm;


public class GameActivity extends ActionBarActivity {

    final private static String[] state = {".", ">", "<", "^", "v", "#", "rT", "bT", "gT", "rB", "bB", "gB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent recieveIntent = getIntent();
        raws = recieveIntent.getIntExtra(StartWindow.SENDER_RAWS, 5);
        columns = recieveIntent.getIntExtra(StartWindow.SENDER_COLUMNS, 5);
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setText("Start Game");
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    GameActivity.this.startGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        board = (GridLayout) findViewById(R.id.board);

        board.setColumnCount(columns);
        board.setRowCount(raws);
        onBoard = new Button[raws * columns + 1];
        for (int rawId = 0; rawId < raws; rawId++) {
            for (int columnId = 0; columnId < columns; columnId++) {
                final int raw = rawId;
                final int column = columnId;
                final Button newButton = new Button(this);
                newButton.setText(".");
                newButton.setId(100500 + rawId * columns + columnId);
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        Button tmpButton = (Button) findViewById(id);
                        String currentText = tmpButton.getText().toString();
                        if (tmpButton.getCurrentTextColor() == Color.RED)
                            currentText = "r" + currentText;
                        else if (tmpButton.getCurrentTextColor() == Color.BLUE) {
                            currentText = "b" + currentText;
                        } else if (tmpButton.getCurrentTextColor() == Color.GREEN) {
                            currentText = "g" + currentText;
                        }

                        String newText = ".";
                        int N = state.length;
                        for (int i = 0; i < N; i++) {
                            if (state[i].compareTo(currentText) == 0) {
                                newText = state[(i + 1) % N];
                            }
                        }
                        tmpButton.setTextColor(Color.BLACK);
                        if (newText.length() == 1) {
                            tmpButton.setText(newText);
                        } else {
                            tmpButton.setText(newText.substring(1, 2));
                            switch (newText.charAt(0)) {
                                case 'r':
                                    tmpButton.setTextColor(Color.RED);
                                    break;
                                case 'b':
                                    tmpButton.setTextColor(Color.BLUE);
                                    break;
                                case 'g':
                                    tmpButton.setTextColor(Color.GREEN);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
                onBoard[rawId * columns + columnId] = newButton;
                board.addView(onBoard[rawId * columns + columnId]);
            }
        }
    }

    private void startGame() throws Exception {
        Board myBoard = new Board(board, raws, columns);
        for (int i = 0; i < raws; i++)
            for (int j = 0; j < columns; j++) {
                Button tmpButton = onBoard[i * columns + j];
                String text = tmpButton.getText().toString();
                int color = tmpButton.getCurrentTextColor();
                if (text.compareTo("#") == 0) {
                    myBoard.addObject(new SimpleObstacle(new BoardPosition(i, j)));
                } else if (text.compareTo("B") == 0){
                    int clr = 0;
                    for (; clr < 3; clr++)
                        if (colors[clr] == color)
                            break;
                    myBoard.addBug(new BoardPosition(i, j), clr);
                    myBoard.addObject(new Arrow(new BoardPosition(i, j), Direction.RIGHT));
                } else if (text.compareTo("T") == 0) {
                    int clr = 0;
                    for (; clr < 3; clr++)
                        if (colors[clr] == color)
                            break;
                    myBoard.addObject(new Trap(new BoardPosition(i, j), clr));
                } else {
                    switch (text.charAt(0)) {
                        case '>':
                            myBoard.addObject(new Arrow(new BoardPosition(i ,j), Direction.RIGHT));
                            break;
                        case '^':
                            myBoard.addObject(new Arrow(new BoardPosition(i ,j), Direction.UP));
                            break;
                        case '<':
                            myBoard.addObject(new Arrow(new BoardPosition(i ,j), Direction.LEFT));
                            break;
                        case 'v':
                            myBoard.addObject(new Arrow(new BoardPosition(i ,j), Direction.DOWN));
                            break;
                    }
                }
            }


        for (int rd = 0; rd < 3; rd++) {
            String[][] mArea = myBoard.runGame();
            for (int i = 0; i < raws; i++) {
                for (int j = 0; j < columns; j++) {
                    updateButtonInformation(i, j, mArea[i][j]);
                }
            }
            board.refreshDrawableState();
        }
        String score = "You got " + Integer.toString(myBoard.getScore()) + " points";
        Toast.makeText(this, score, Toast.LENGTH_SHORT).show();
    }

    private void updateButtonInformation(int i, int j, String newText) {
        Button tmpButton = onBoard[i * columns + j];
        tmpButton.setTextColor(Color.BLACK);
        if (newText.length() == 1) {
            tmpButton.setText(newText);
        } else {
            tmpButton.setText(newText.substring(1, 2));
            switch (newText.charAt(0)) {
                case 'r':
                    tmpButton.setTextColor(Color.RED);
                    break;
                case 'b':
                    tmpButton.setTextColor(Color.BLUE);
                    break;
                case 'g':
                    tmpButton.setTextColor(Color.GREEN);
                    break;
                default:
                    break;
            }
        }
        onBoard[i * columns + j] = tmpButton;
    }


    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN};
    private int raws;
    private int columns;
    private Button[] onBoard;
    private GridLayout board;
}
