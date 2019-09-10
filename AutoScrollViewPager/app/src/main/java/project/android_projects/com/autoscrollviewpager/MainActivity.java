package project.android_projects.com.autoscrollviewpager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//Hide support action bar

        AutoScrollPagerAdapter pagerAdp = new AutoScrollPagerAdapter(getSupportFragmentManager());

        AutoScrollViewPager pager = findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdp);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        //Start auto scroll
        pager.startAutoScroll();

        //Set auto scrolling time in milliseconds
        pager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);

        //enable recycling using true
        pager.setCycleScroll(true);
    }
}
