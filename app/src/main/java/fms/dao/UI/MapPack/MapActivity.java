package fms.dao.UI.MapPack;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import fms.dao.Model.currentUser;
import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.LoginPack.MainActivity;
import fms.dao.UI.LoginPack.loginFragment;

public class MapActivity extends AppCompatActivity implements loginFragment.OnLoginComplete{
    private Fragment map;
    private currentUser user;
    private String startEventId;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setTitle("Map Activity");
        FragmentManager fragmentManager = getSupportFragmentManager();
        startEventId = globalHelper.getInstance().getMarkerEventSelectedEvent();
        googleMap = globalHelper.getInstance().getCurrentMap();
        map = fragmentManager.findFragmentById(R.id.fragment_map);
        if (map == null){
            map = new MapFragment();
            fragmentManager.beginTransaction().replace(R.id.mapView, map).commit();
        }
        fragmentManager.beginTransaction().replace(R.id.mapView, map).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        MenuItem doubleUpArrow = menu.findItem(R.id.double_up_arrow);
        Drawable doubleUpIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_up).sizeDp(24).color(Color.WHITE);
        doubleUpArrow.setIcon(doubleUpIcon);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.double_up_arrow:
                globalHelper.getInstance().setMapActivity(false);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendUserData(currentUser user){
        this.user = user;
    }

}
