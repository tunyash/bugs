package bugs.bugs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity {

    final private static String[] state = {".", ">", "<", "^", "v", "#", "rT", "bT", "gT", "rB", "bB", "gB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent recieveIntent = getIntent();
        final int raws = recieveIntent.getIntExtra(StartWindow.SENDER_RAWS, 5);
        final int columns = recieveIntent.getIntExtra(StartWindow.SENDER_COLUMNS, 5);
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setText("Start Game");
        GridLayout board = (GridLayout) findViewById(R.id.board);

        board.setColumnCount(columns);
        board.setRowCount(raws);
        Button[] onBoard = new Button[raws * columns + 1];
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



}
