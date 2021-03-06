package com.aAronQInk.Walls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aAronQInk.Walls.R;
import com.aAronQInk.Walls.adapters.PagerAdapter;
import com.aAronQInk.Walls.fragments.CollectionsPhotoFragment;
import com.aAronQInk.Walls.fragments.CuratedPhotoFragment;
import com.aAronQInk.Walls.fragments.NewPhotoFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view) NavigationView mNavigationView;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpTabLayoutAndViewPager();
        setUpNavigationView();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu_white_24dp);
    }

    private void setUpTabLayoutAndViewPager() {
        mTabLayout.setupWithViewPager(mViewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(NewPhotoFragment.getInstance(), getString(R.string.tab_new));
        pagerAdapter.addFragment(CuratedPhotoFragment.getInstance(), getString(R.string.tab_featured));
        pagerAdapter.addFragment(CollectionsPhotoFragment.getInstance(), getString(R.string.tab_collections));

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mNavigationView.setCheckedItem(R.id.nav_new);
                        break;
                    case 1:
                        mNavigationView.setCheckedItem(R.id.nav_featured);
                        break;
                    case 2:
                        mNavigationView.setCheckedItem(R.id.nav_collections);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_new:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_featured:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_collections:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.nav_settings:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        intent = SettingsActivity.newIntent(getApplicationContext());
                        startActivity(intent);
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
