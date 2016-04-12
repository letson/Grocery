package network.cooking.sonle.grocery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnButtonLoginClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create an instance of ExampleFragment
            LoginFragment loginFragment = new LoginFragment();
            // Add the fragment to the 'fragment_container' FrameLayout

            setFragment(loginFragment,"loginFragment");
            // avoid back to blank page
            FragmentManager fm = getSupportFragmentManager();
            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setFragment(Fragment frag, String tag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null) // check there is fragment in this container, if no add
        {
            ft.add(R.id.fragment_container, frag, tag);
        } else { // else replace
            ft.replace(R.id.fragment_container, frag, tag);
        }

        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void loadNextFragment() {
        EditText txt_pin_number = (EditText)findViewById(R.id.txt_pin_number);
        if(txt_pin_number.getText().toString().equals("1234")) {
            GroceryListFragment groceryListFragment = new GroceryListFragment();
            setFragment(groceryListFragment, "GroceryListFragment");
        }
        else {
            Toast.makeText(MainActivity.this,"Wrong pin number. Please try again",Toast.LENGTH_LONG).show();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Main Activity", "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("Main Activity", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i("Main Activity", "onStop");
    }
}
