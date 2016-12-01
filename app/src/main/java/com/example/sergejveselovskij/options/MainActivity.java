package com.example.sergejveselovskij.options;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;

import org.kpfu.options.OptionsComputing;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    EditText aField;
    EditText bField;
    EditText rField;
    EditText KField;
    EditText B0Field;
    EditText S0Field;
    EditText NField;

    Button start;
    Button increase;
    Button decrease;
    Button restart;

    ViewAnimator switcher;
    OptionsComputing optionsComputing;
    LinearLayout steps_c;
    int stepNumber = 0;

    Double a;
    Double b;
    Double r;
    Double K;
    Double B0;
    Double S0;
    int N;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        aField = (EditText) findViewById(R.id.a_edit_text);
        bField = (EditText) findViewById(R.id.b_edit_text);
        rField = (EditText) findViewById(R.id.r_edit_text);
        KField = (EditText) findViewById(R.id.K_edit_text);
        B0Field = (EditText) findViewById(R.id.B0_edit_text);
        S0Field = (EditText) findViewById(R.id.S0_edit_text);
        NField = (EditText) findViewById(R.id.N_edit_text);

        switcher = (ViewAnimator) findViewById(R.id.switcher_view);
        start = (Button) findViewById(R.id.start_button);
        increase = (Button) findViewById(R.id.increase_button);
        decrease = (Button) findViewById(R.id.decrease_button);
        steps_c = (LinearLayout) findViewById(R.id.steps_container);
        restart = (Button) findViewById(R.id.restart_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aField.getText().length() == 0 || bField.getText().length() == 0 ||rField.getText().length() == 0
                        ||KField.getText().length() == 0 ||B0Field.getText().length() == 0 ||S0Field.getText().length() == 0 ||NField.getText().length() == 0){
                    //show error dialog
                    Snackbar.make(findViewById(R.id.content_main),"Заполните все поля",Snackbar.LENGTH_SHORT).show();
                }
                else{

                    try {a = Double.parseDouble(aField.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения a",Snackbar.LENGTH_SHORT).show(); return;}
                    try {b = Double.parseDouble(bField.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения b",Snackbar.LENGTH_SHORT).show();return;}
                    try {r = Double.parseDouble(rField.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения r",Snackbar.LENGTH_SHORT).show();return;}
                    try {K = Double.parseDouble(KField.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения K",Snackbar.LENGTH_SHORT).show();return;}
                    try {B0 = Double.parseDouble(B0Field.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения B0",Snackbar.LENGTH_SHORT).show();return;}
                    try {S0 = Double.parseDouble(S0Field.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения S0",Snackbar.LENGTH_SHORT).show();return;}
                    try {N = Integer.parseInt(NField.getText().toString());}
                    catch(Exception e){Snackbar.make(findViewById(R.id.content_main),"Неверный формат значения N",Snackbar.LENGTH_SHORT).show();return;}
                    optionsComputing = new OptionsComputing(a,b,r,K,B0,S0,N);
                    if (switcher.getDisplayedChild() != 1){
                        switcher.showNext();
                        if (getCurrentFocus() != null) {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsComputing.nextStep(OptionsComputing.SharePriceBehavior.RISE);
                View container = LayoutInflater.from(MainActivity.this).inflate(R.layout.first_view_holder, (ViewGroup) findViewById(R.id.steps_container), false);
                ((TextView) container.findViewById(R.id.step_text)).setText(optionsComputing.getIterations().get(optionsComputing.getIterations().size()-1).toString());
                steps_c.addView(container);
                stepNumber++;
                if (stepNumber == N){
                    if (switcher.getDisplayedChild() != 2){
                        switcher.showNext();
                    }
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsComputing.nextStep(OptionsComputing.SharePriceBehavior.DROP);
                View container = LayoutInflater.from(MainActivity.this).inflate(R.layout.first_view_holder, (ViewGroup) findViewById(R.id.steps_container), false);
                ((TextView) container.findViewById(R.id.step_text)).setText(optionsComputing.getIterations().get(optionsComputing.getIterations().size()-1).toString());
                steps_c.addView(container);
                stepNumber++;
                if (stepNumber == N){
                    if (switcher.getDisplayedChild() != 2){
                        switcher.showNext();
                    }
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switcher.getDisplayedChild() != 0){
                    switcher.showNext();
                    optionsComputing = null;
                    steps_c.removeAllViews();
                    stepNumber = 0;
                }
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
