package com.mah.voicetext;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button speak;
    ListView options;
    ArrayList<String>results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speak = (Button)findViewById(R.id.bSpeak);
        speak.setOnClickListener(this);
        options = (ListView)findViewById(R.id.lvOptions);

        //configuration
        if (savedInstanceState != null) {
            results = savedInstanceState.getStringArrayList("A");
            if (results != null)
                options.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        }

    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        startActivityForResult(intent, 2017);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // retrieves data from the VoiceRecognizer
        if (requestCode == 2017 && resultCode == RESULT_OK) {
            results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            options.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, results));
        }
        super.onActivityResult(requestCode,resultCode, data);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // This should save all the data to solve configuration change problems
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("A", results);
    }
}
