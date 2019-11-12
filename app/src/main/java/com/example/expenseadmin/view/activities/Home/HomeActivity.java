package com.example.expenseadmin.view.activities.Home;

import android.content.Intent;
import android.os.Bundle;

import com.example.expenseadmin.R;
import com.example.expenseadmin.view.activities.signInUp.SignInActivity;
import com.example.expenseadmin.view.fragments.home.HomeFragment;
import com.example.expenseadmin.view.fragments.aboutUs.AboutUsFragment;
import com.example.expenseadmin.view.fragments.addProject.AddProjectFragment;
import com.example.expenseadmin.view.fragments.contactUs.ContactUsFragment;
import com.example.expenseadmin.view.fragments.favorites.FavoriteFragment;
import com.example.expenseadmin.view.fragments.notifications.NotificationsFragment;
import com.example.expenseadmin.view.fragments.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_home)
    Toolbar toolbar;
    @BindView(R.id.container)
    DrawerLayout drawer;
    @BindView(R.id.nav_drawer_view)
    NavigationView navigationView;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView navView;
    private Fragment currFragment;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            setFragments(new HomeFragment(), AnimationStates.BOTTOM_TO_TOP);
        }
        init();
    }

    public enum AnimationStates {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, BOTTOM_TO_TOP
    }

    private void init() {

        ButterKnife.bind(this);
        navDrawerConfig();
        bottomNavConfig();
    }

    private void bottomNavConfig() {
        try {
            navView.setOnNavigationItemSelectedListener(menuItem -> {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        try {
                            if (currFragment instanceof HomeFragment) {
                                break;
                            } else {
                                setFragments(new HomeFragment(), AnimationStates.LEFT_TO_RIGHT);
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    case R.id.navigation_notifications:
                        try {
                            if (currFragment instanceof NotificationsFragment) break;
                            if (fragment instanceof HomeFragment)
                                setFragments(new NotificationsFragment(), AnimationStates.RIGHT_TO_LEFT);
                            else
                                setFragments(new NotificationsFragment(), AnimationStates.LEFT_TO_RIGHT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.navigation_favorite:
                        try {
                            if (currFragment instanceof FavoriteFragment) break;
                            if (fragment instanceof ProfileFragment)
                                setFragments(new FavoriteFragment(), AnimationStates.LEFT_TO_RIGHT);
                            else
                                setFragments(new FavoriteFragment(), AnimationStates.RIGHT_TO_LEFT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.navigation_profile:
                        try {
                            if (currFragment instanceof ProfileFragment) break;
                            setFragments(new ProfileFragment(), AnimationStates.RIGHT_TO_LEFT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void navDrawerConfig() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            AnimationStates states = AnimationStates.BOTTOM_TO_TOP;
            switch (menuItem.getItemId()) {
                case R.id.add_project_nav:
                    if (currFragment instanceof AddProjectFragment) break;
                    setFragments(new AddProjectFragment(), states);
                    break;
                case R.id.about_us_nav:
                    if (currFragment instanceof AboutUsFragment) break;
                    setFragments(new AboutUsFragment(), states);
                    break;
                case R.id.contact_us_nav:
                    if (currFragment instanceof ContactUsFragment) break;
                    setFragments(new ContactUsFragment(), states);
                    break;
                case R.id.sign_out:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setFragments(Fragment fragment, AnimationStates state) {
        try {
            currFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (state == AnimationStates.RIGHT_TO_LEFT)
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

            else if (state == AnimationStates.LEFT_TO_RIGHT)
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);

            else if (state == AnimationStates.BOTTOM_TO_TOP)
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);

            transaction.replace(R.id.fragment_container, fragment, TAG_FRAGMENT);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof HomeFragment) {
            finish();
        } else {
            if (fragment instanceof AboutUsFragment || fragment instanceof ContactUsFragment
                    || fragment instanceof AddProjectFragment) {
                navView.setSelectedItemId(navView.getSelectedItemId());
            } else if (fragment instanceof FavoriteFragment || fragment instanceof NotificationsFragment
                    || fragment instanceof ProfileFragment) {
                navView.setSelectedItemId(R.id.navigation_home);
            }
        }
    }

}