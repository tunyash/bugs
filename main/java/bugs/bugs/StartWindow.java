package bugs.bugs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class StartWindow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_window);
        TextView textView = (TextView) findViewById(R.id.HelloMessage);
        textView.setText("Hi, chose the size of the board, and we will start soon =)");
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setText("Let's GO!");
        startButton.setOnClickListener(startButtonListener);
    }

    View.OnClickListener startButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toStartGameActivity(view);
        }
    };

        private void toStartGameActivity(View view) {
            Intent intent = new Intent(this, GameActivity.class);
            EditText numberOfColumns = (EditText) findViewById(R.id.numberOfColumns);
            EditText numberOfRaws = (EditText) findViewById(R.id.numberOfRaws);

            int raws = Integer.parseInt(numberOfRaws.getText().toString());
            int columns = Integer.parseInt(numberOfColumns.getText().toString());
            intent.putExtra(SENDER_COLUMNS, columns);
            intent.putExtra(SENDER_RAWS, raws);
            startActivity(intent);
        }


    public static String SENDER_RAWS = "MAIN_RAWS";
    public static String SENDER_COLUMNS = "MAIN_COLUMNS";


}
