package com.josvlaar.android.amadeus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddPlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist_activity);

        Spinner variable1Spinner = (Spinner) findViewById(R.id.variable1Spinner);
        Spinner variable2Spinner = (Spinner) findViewById(R.id.variable2Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.playlist_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        variable1Spinner.setAdapter(adapter);
        variable2Spinner.setAdapter(adapter);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
                SmartPlaylist playlist = new SmartPlaylist();
                TextView nameInput = (TextView) findViewById(R.id.nameInput);
                Spinner variable1Spinner = (Spinner) findViewById(R.id.variable1Spinner);
                Spinner variable2Spinner = (Spinner) findViewById(R.id.variable2Spinner);
                TextView value1Input = (TextView) findViewById(R.id.value1Input);
                TextView value2Input = (TextView) findViewById(R.id.value2Input);

                String variable1 = (String) variable1Spinner.getSelectedItem();
                String value1 = value1Input.getText().toString();
                String variable2 = (String) variable2Spinner.getSelectedItem();
                String value2 = value2Input.getText().toString();

                playlist.setName(nameInput.getText().toString());

                if (variable1 != "None" && value1.length() > 0) {
                    playlist.setVariable1(variable1);
                    playlist.setValue1(value1);
                }
                if (variable2 != "None" && value2.length() > 0) {
                    playlist.setVariable2(variable2);
                    playlist.setValue2(value2);
                }
                db.insert(playlist);
                finish();
            }
        });
    }
}
