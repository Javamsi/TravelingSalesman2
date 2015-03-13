package com.example.vamsij.travelingsalesman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.view.View.OnCreateContextMenuListener;
import java.util.*;

public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle b = getIntent().getExtras();
        Integer.valueOf(0);
        if (b != null) {
            Integer integer = Integer.valueOf(b.getInt("Spinner_num"));
            ((GameView)findViewById(R.id.gameView)).setSpinnerNum(integer.intValue());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    public void endActivityClick(GameView gv)
    {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();

    }

    public void Undo() {
        final GameView gView = (GameView) this.findViewById(R.id.gameView);
        gView.onClickUndo();
    }

    public void Clear() {
        final GameView gView = (GameView) this.findViewById(R.id.gameView);
        gView.onClickClear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.Undo:
                Undo();
                break;
            case R.id.Clear:
                Clear();
                break;
            case R.id.Quit:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
