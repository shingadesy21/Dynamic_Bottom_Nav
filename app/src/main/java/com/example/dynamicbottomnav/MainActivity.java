package com.example.dynamicbottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button add;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);
        add=findViewById(R.id.add_btn);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_Fragment()).commit();///shows the first tab contents.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(1000, 1000){

                    @Override
                    public void onTick(long l) {
                        try {
                            handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bottomNavigationView.getMenu().findItem(R.id.dynamic).setVisible(true);
                                }
                            },0000);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomNavigationView.getMenu().findItem(R.id.dynamic).setVisible(false);
                            }
                        },10000);
                    }
                }.start();
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected_fragment=null;
            ///there are first 4 tabs are static and Menu.NONE is dynmically added and removed.
            switch(menuItem.getItemId()){
                case R.id.home:
                    selected_fragment=new Home_Fragment();
                    break;
                case R.id.recent:
                    selected_fragment=new Recent_Fragment();
                    break;
                case R.id.favorites:
                    selected_fragment=new Favorite_Fragment();
                    break;
                case R.id.dynamic:
                    selected_fragment=new Dynamic_Fragment();
                    break;
            }
            ///replace the current layout to another one
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected_fragment).commit();

            return true;
        }
    };
}