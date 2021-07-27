package com.example.e_learingmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.e_learingmanagementsystem.ui.student.StudentAccountFragment;
import com.example.e_learingmanagementsystem.ui.student.StudentCalenderFragment;
import com.example.e_learingmanagementsystem.ui.student.StudentChatFragment;
import com.example.e_learingmanagementsystem.ui.student.StudentHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class StudentNavigationDrawer extends AppCompatActivity {

    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_navigation_drawer_activity);

        //this line hide statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new StudentHomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment =  new StudentHomeFragment();
                        break;
                    case R.id.nav_account:
                        fragment =  new StudentAccountFragment();
                        break;
                    case R.id.nav_calender:
                        fragment =  new StudentCalenderFragment();
                        break;
                    case R.id.nav_chat:
                        fragment =  new StudentChatFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });
    }
}