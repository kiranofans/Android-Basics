package project.android_projects.com.multi_language_support_practice;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Utils.LocalManager;

public class MainActivity extends BaseActivity {
    private TextView dispTV;
    private View textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFloatingActionButton();

    }

    private void initContent(){
        textView = findViewById(R.id.text_tv);
        dispTV = textView.findViewById(R.id.display_tv);
        dispTV.setText(getString(R.string.action_language));
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
        if(id == R.id.action_lan_eng){
            setNewLocales(this,LocalManager.LAN_CODE_ENGLISH);
            return true;
        }else if(id == R.id.action_lan_fr){
            setNewLocales(this,LocalManager.LAN_CODE_FRENCH);
            return true;
        }else if(id == R.id.action_lan_zhTW){
            setNewLocales(this,LocalManager.LAN_CODE_TRADITIONAL_CHINESE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNewLocales(AppCompatActivity appCompatActivity,
                               @LocalManager.LocaleDef String lan){
        LocalManager.setNewLocale(appCompatActivity,lan);
        Intent intent = appCompatActivity.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    private void setFloatingActionButton(){

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
