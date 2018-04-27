package fms.dao.UI.LoginPack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fms.dao.Model.currentUser;
import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.FilterPack.FilterActivity;
import fms.dao.UI.MapPack.MapFragment;
import fms.dao.UI.SearchPack.searchActivity;
import fms.dao.UI.SettingsPack.settingsActivity;

public class MainActivity extends AppCompatActivity implements  loginFragment.OnLoginComplete{
    private currentUser user;
    private Toolbar toolbar;
    private MenuItem search;
    private MenuItem filter;
    private MenuItem settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Main Activity");
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

        if(!globalHelper.getInstance().isLoggedIn()){
            Fragment login = fm.findFragmentById(R.id.main_container);
            if(login == null){
                user = new currentUser();
                login = new loginFragment();

                fm.beginTransaction().replace(R.id.main_container, login).commit();
            }
        } else {
            Fragment map = fm.findFragmentById(R.id.fragment_map);
            if(map == null){
                map = new MapFragment();
                user = globalHelper.getInstance().getUser();
                fm.beginTransaction().replace(R.id.main_container, map).commit();
            }else {
                fm.beginTransaction().replace(R.id.main_container, map).commit();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.settings_id:
                Intent intent1 = new Intent(this, settingsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.filter_id:
                Intent intent2 = new Intent(this, FilterActivity.class);
                startActivity(intent2);
                return true;
            case R.id.search_id:
                Intent intent3 = new Intent(this, searchActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void sendUserData(currentUser user){
        this.user = user;
    }

}
