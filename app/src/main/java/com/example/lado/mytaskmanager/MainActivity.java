package com.example.lado.mytaskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int id_To_Update = 0;
    EditText input;
    String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating alert Dialog with one Button
        //final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        //alertDialog.setTitle("Add a new Task");

        // Setting Dialog Message
        //alertDialog.setMessage("What do you want to do?");

        final DBHelper dbHelper = new DBHelper(MainActivity.this);
        input = new EditText(MainActivity.this);

        final TextView textView = (TextView) findViewById(R.id.TODO);
        task = String.valueOf(input.getText());

//        for(int ii = 0; ii<dbHelper.getAllCotacts().size(); ii++) {
//            textView.setMovementMethod(new ScrollingMovementMethod());
//            textView.setText(dbHelper.getAllCotacts().get(ii));
//        }

        StringBuilder jokeStringBuilder = new StringBuilder();
        for (String s : dbHelper.getAllCotacts()) {
            jokeStringBuilder.append(s + "\n");
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(jokeStringBuilder.toString());

        //make button clickable
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Setting Dialog Message, and Dialog Title, also add edittext in alertDialog and show everything
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Add new Task").setMessage("What do you want to do?").setView(input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(dbHelper.insertTask(task)) {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            Log.d("ADDING", "ADDING NEW TASK " + task);
                            Log.d("ALLCONTACTS", "ALL " + dbHelper.getAllCotacts());
                        }
                    }
                }).setNegativeButton("Cancel", null).create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
