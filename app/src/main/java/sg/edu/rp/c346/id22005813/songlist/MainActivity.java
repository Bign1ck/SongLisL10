package sg.edu.rp.c346.id22005813.songlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnShowList;
    EditText editSong, editSinger, editYear;
    RadioGroup chooseRating;
    ListView list;

    private DBHelper dbHelper;
    private ArrayAdapter<Task> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShow);
        editSong = findViewById(R.id.editSong);
        editSinger = findViewById(R.id.editSinger);
        editYear = findViewById(R.id.editYear);
        chooseRating = findViewById(R.id.chooseStars);
        list = findViewById(R.id.lv);

        dbHelper = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editSong.getText().toString();
                String singer = editSinger.getText().toString();
                String year = editYear.getText().toString();
                int rating = getSelectedRating();

                Task task = new Task(title, singer, year, rating);
                dbHelper.insertTask(task);

                Toast.makeText(MainActivity.this, "Task inserted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Task> taskList = dbHelper.getTasks();

                taskAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, taskList);
                list.setAdapter(taskAdapter);
            }
        });
    }

    private int getSelectedRating() {
        int selectedId = chooseRating.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return Integer.parseInt(radioButton.getText().toString());
    }
}

