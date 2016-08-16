package com.athlete.android.ritik.alertdialogproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Locale locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Configuration config = getBaseContext().getResources().getConfiguration();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = settings.getString("LANG","");

        if(!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }

    public void openAlertDialog(View view)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage("Do you want to perform this action?");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Toast.makeText(MainActivity.this,"You have pressed yes Button...",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Toast.makeText(MainActivity.this,"You have pressed No Button...",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
                Toast.makeText(MainActivity.this,"You have pressed Cancel Button...",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    public void openAlertDialogCustom(View view)
    {
        AlertDialog customAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_alert_dialog,null);
        EditText editText = (EditText) dialogView.findViewById(R.id.editText_alertDialog);

        customAlertDialog.setView(dialogView);
        customAlertDialog.setTitle("Custom Alert Dialog");
        customAlertDialog.setMessage("Enter Text below");

        customAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Your file has been saved successfully...",Toast.LENGTH_SHORT).show();
            }
        });

        customAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"you have rejected to save file...",Toast.LENGTH_SHORT).show();
            }
        });

        customAlertDialog.show();
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
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_change_language:
                showChangeLangDialog();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void showChangeLangDialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.select_language_spinner,null);

        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.language_spinner);

        alertDialog.setView(dialogView);
        alertDialog.setTitle(getResources().getString(R.string.language_dialog_title));
        alertDialog.setMessage(getResources().getString(R.string.language_dialog_message));

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int langPos = spinner.getSelectedItemPosition();
                switch (langPos)
                {
                    case 0:
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG","en").commit();
                        setLangRecreate("en");
                        return;
                    case 1:
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG","hi").commit();
                        setLangRecreate("hi");
                        return;
                    default:
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG","en").commit();
                        setLangRecreate("en");
                        return;

                }
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    public void setLangRecreate(String lang)
    {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(lang);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}
