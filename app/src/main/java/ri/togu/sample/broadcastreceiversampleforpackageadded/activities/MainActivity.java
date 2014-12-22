package ri.togu.sample.broadcastreceiversampleforpackageadded.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ri.togu.sample.broadcastreceiversampleforpackageadded.R;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayAdapter<String> mAdapter;
    private SearchView mSearchView;
    private String mSearchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate myPid:" + android.os.Process.myPid());

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, installedAppList());
        ListView installedListView = (ListView) findViewById(R.id.activity_main_app_installed_listView);
        installedListView.setAdapter(mAdapter);

        Button installedListUpdate = (Button) findViewById(R.id.activity_main_app_installed_list_update);
        installedListUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
                mAdapter.addAll(installedAppList());
                mAdapter.notifyDataSetChanged();
            }
        });

        Button installedPackageListUpdate = (Button) findViewById(R.id.activity_main_package_installed_list_update);
        installedPackageListUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
                mAdapter.addAll(installedPackageList());
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_view);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(false);
        if (!"".equals(mSearchWord)) {
            mSearchView.setQuery(mSearchWord, false);
        } else {
            String queryHint = getResources().getString(R.string.menu_search_view_query_hint);
            mSearchView.setQueryHint(queryHint);
        }
        mSearchView.setOnQueryTextListener(onQueryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return setSearchWord(query);
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mAdapter.getFilter().filter(newText);
            return false;
        }
    };

    boolean setSearchWord(String searchWord) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(searchWord);
        actionBar.setDisplayShowTitleEnabled(true);
        if (searchWord != null && !"".equals(searchWord)) {
            mSearchWord = searchWord;
        }
        mSearchView.setIconified(false);
        mSearchView.onActionViewCollapsed();
        mSearchView.clearFocus();
        return false;
    }

    ArrayList<String> installedPackageList() {
        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
        ArrayList<String> pkgList = new ArrayList<>(installedPackages.size());
        for (PackageInfo info : installedPackages) {
            pkgList.add(info.packageName);
        }
        return pkgList;
    }

    ArrayList<String> installedAppList() {
        List<ApplicationInfo> installedAppInfo = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> appList = new ArrayList<>(installedAppInfo.size());
        for (ApplicationInfo info : installedAppInfo) {
            appList.add((getPackageManager().getApplicationLabel(info)).toString());
        }
        return appList;
    }
}
