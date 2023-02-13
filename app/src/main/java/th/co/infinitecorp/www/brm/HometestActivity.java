package th.co.infinitecorp.www.brm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import th.co.infinitecorp.www.brm.Fragment.DetailBookingFragment;
import th.co.infinitecorp.www.brm.Fragment.HomeFragment;
import th.co.infinitecorp.www.brm.Fragment.ProfileFragment;
import th.co.infinitecorp.www.brm.Model.Remember;
import th.co.infinitecorp.www.brm.Model.Userinfo;
import Utils.Cookie;
import th.co.infinitecorp.www.brm.menu.DrawerAdapter;
import th.co.infinitecorp.www.brm.menu.DrawerItem;
import th.co.infinitecorp.www.brm.menu.SimpleItem;
import th.co.infinitecorp.www.brm.menu.SpaceItem;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public class HometestActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_BOOKING = 2;
    private static final int POS_LOGOUT = 4;
    private static final int POS_LOGIN = 3;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    TextView txt_progressbar,title_toolbar;
    ProgressDialog progressbar;
    private SlidingRootNav slidingRootNav;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometest);
        progressbar = new ProgressDialog(HometestActivity.this);

        //toolbar edit

        toolbar = findViewById(R.id.toolbar);
        title_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title) ;
       toolbar.setTitle("");
        setSupportActionBar(toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_PROFILE),
                createItemFor(POS_BOOKING),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {
        if(position==POS_LOGIN){
            slidingRootNav.closeMenu();
            Intent newActivity = new Intent(HometestActivity.this,LoginActivity.class);
            startActivity(newActivity);
        }
        else if(position==POS_LOGOUT){
            Intent newActivity = new Intent(HometestActivity.this,MainActivity.class);
            slidingRootNav.closeMenu();
            startActivity(newActivity);

            Intent select = new Intent(HometestActivity.this, MainActivity.class);
            progressbar.show();
            progressbar.setContentView(R.layout.progress_dialog);
            txt_progressbar = (TextView) progressbar.findViewById(R.id.txt_progressbar);
            txt_progressbar.setText("Logging out...");
            progressbar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                    progressbar.cancel();
                    Remember remember_save = new Remember();
                    remember_save.setRemember_status(false);
                    Cookie.SaveRemember(getApplication(),remember_save);
                    Userinfo userinfosave = new Userinfo();
                    userinfosave.setDepartmentName("");
                    userinfosave.setEmail("");
                    userinfosave.setID("");
                    userinfosave.setName("");
                    userinfosave.setPassword("");
                    userinfosave.setUserName("");
                    userinfosave.setPhoneNumber("");
                    Cookie.SaveUserinfo(getApplication(),userinfosave);

                    startActivity(select);



        }
        else if(position==POS_HOME){
            slidingRootNav.closeMenu();
//            toolbar.setTitle("HOME");
           // toolbar.setTitle("BOOKING ROOM MEETING");
            Fragment selectedScreen =HomeFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        else if(position==POS_BOOKING){
            slidingRootNav.closeMenu();
//            toolbar.setTitle("MY BOOKING");
           // toolbar.setTitle("BOOKING ROOM MEETING");
            Fragment selectedScreen = DetailBookingFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }
        else if(position==POS_PROFILE){
            slidingRootNav.closeMenu();
//            toolbar.setTitle("MY PROFILE");
           // toolbar.setTitle("BOOKING ROOM MEETING");
            Fragment selectedScreen = ProfileFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);
        }

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
