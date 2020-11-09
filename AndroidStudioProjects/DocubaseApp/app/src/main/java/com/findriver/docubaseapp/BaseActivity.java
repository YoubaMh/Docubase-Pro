package com.findriver.docubaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private BottomNavigationView bottomNavifationView;
    private NavController navController;
    private ImageButton bt_menu;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        init();

        NavigationUI.setupWithNavController(bottomNavifationView, navController);

        bottomNavifationView.setSelectedItemId(2);
    }

    private void initListener() {

    }

    public void init(){
        bottomNavifationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        bt_menu = findViewById(R.id.bt_menu);
    }



   public void showMenu(View view){
       PopupMenu popupMenu = new PopupMenu(this, view);
       popupMenu.setOnMenuItemClickListener(this);
       popupMenu.inflate(R.menu.menu_option);
       popupMenu.show();
   }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        sharedPreferences = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        switch(menuItem.getItemId()){
            case R.id.admin:
                if(sharedPreferences.getString("role","").equals("admin")){
                    Intent i = new Intent(BaseActivity.this, AdminActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(this, "Vous n'êtes pas un admin", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.teacher:
                if(sharedPreferences.getString("role","").equals("enseignant")){
                    Intent i = new Intent(BaseActivity.this, TeacherActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(this, "Vous n'êtes pas un enseignant", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return false;
        }
    }
}
