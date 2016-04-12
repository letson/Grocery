package network.cooking.sonle.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sonle on 4/3/16.
 */
public class GroceryListFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private GroceryListAdapter mAdapter;
    private SharedPreferences sharedPref;
    private Activity mActivity;
    private String[] groceryList;
    private SimpleCursorAdapter mLocationAdapter;

    @Override
    public void onAttach(Activity act)
    {
        super.onAttach(act);
        this.mActivity = act;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // enable menu options
        final String[] from = new String[] {"locationName"};
        final int[] to = new int[] {android.R.id.text1};
        mLocationAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.grocery_list_layout, container, false);

        // set action bar title for login fragment
        ((MainActivity)mActivity).setActionBarTitle("Grocery list");
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_grocery_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonText = sharedPref.getString("groceryList", null);

        if(jsonText != null) {
            Gson gson = new Gson();
            groceryList = gson.fromJson(jsonText, String[].class);

            mAdapter = new GroceryListAdapter(getActivity(), groceryList, new GroceryListAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final int position) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setMessage("Are you sure you want to remove this grocery?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Gson gson = new Gson();
                                    String jsonGroceryListText = sharedPref.getString("groceryList", null);
                                    String[] jsonGroceryListArr = gson.fromJson(jsonGroceryListText, String[].class);

                                    List<String> groceryList =new ArrayList<String>(Arrays.asList(jsonGroceryListArr));
                                    String removeGroceryItem = groceryList.get(position);
                                    groceryList.remove(position);// remove this grocery
                                    String newGroceryList  = gson.toJson(groceryList);
                                    // update grocery list
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("groceryList",newGroceryList);
                                    editor.commit();
                                    // add/update purchased list
                                    String jsonPurchased = sharedPref.getString("purchasedList", null);
                                    if(jsonPurchased != null) {
                                        String[] jsonPurchasedArr = gson.fromJson(jsonPurchased, String[].class);
                                        List<String> purchaseList =new ArrayList<String>(Arrays.asList(jsonPurchasedArr));
                                        purchaseList.add(removeGroceryItem);
                                        jsonPurchased  = gson.toJson(purchaseList);
                                    }
                                    else {
                                        List<String> purchaseList = new ArrayList<String>();
                                        purchaseList.add(removeGroceryItem);
                                        jsonPurchased  = gson.toJson(purchaseList);
                                    }
                                    editor.putString("purchasedList", jsonPurchased);
                                    editor.commit();
                                    // update grocery adater
                                    mAdapter.setmDataset(groceryList.toArray(new String[groceryList.size()]));
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // Create the AlertDialog object
                    alert.show();
                    return true;
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            Toast.makeText(getActivity(),"You do not have any grocery item",Toast.LENGTH_LONG).show();

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));
        searchView.setSuggestionsAdapter(mLocationAdapter);
        searchView.setIconifiedByDefault(false);
        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {

                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SearchResultsFragment searchResultFragment = new SearchResultsFragment();
                searchResultFragment.setArguments(bundle);
                Fragment fragment_add = fm.findFragmentById(R.id.fragment_container);
                if(fragment_add == null)
                {
                    ft.add(R.id.fragment_container, searchResultFragment, "searchResultFragment");
                } else {
                    ft.replace(R.id.fragment_container, searchResultFragment, "searchResultFragment");
                }
                ft.addToBackStack(null);
                ft.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                /*new FetchingLocationsTask(getActivity(), new FetchingLocationsTask.AsyncResponse() {
                    @Override
                    public void processFinish(String result) {
                        ArrayList<Shop> locationList = populateLocationResults(result);
                        populateAdapter(query, locationList);
                    }
                }).execute(query);*/
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_add:
                AddGroceryFragment addGroceryFragment = new AddGroceryFragment();

                Fragment fragment_add = fm.findFragmentById(R.id.fragment_container);
                if(fragment_add == null)
                {
                    ft.add(R.id.fragment_container, addGroceryFragment, "addGroceryFragment");
                } else {
                    ft.replace(R.id.fragment_container, addGroceryFragment, "addGroceryFragment");
                }
                ft.addToBackStack(null);
                ft.commit();
                return true;
            case R.id.action_view_purchase_list:
                PurchasedListFragment purchasedListFragment = new PurchasedListFragment();
                Fragment fragment_list = fm.findFragmentById(R.id.fragment_container);
                if(fragment_list == null)
                {
                    ft.add(R.id.fragment_container, purchasedListFragment, "purchasedListFragment");
                } else {
                    ft.replace(R.id.fragment_container, purchasedListFragment, "purchasedListFragment");
                }
                ft.addToBackStack(null); // optional
                ft.commit();
                return true;
            default:
                break;
        }
        return false;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("GroceryListFragment","onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("GroceryListFragment", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i("GroceryListFragment", "onStop");
    }

    public static ArrayList<Shop> populateLocationResults(String response) {
        String serverMessage = "";
        ArrayList<Shop> locationList = new ArrayList<Shop>();
        System.out.println("response:" + response);
        if(response != null) {
            JSONObject serverResponse = null;
            try {
                serverResponse = new JSONObject(response);
                serverMessage = serverResponse.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (serverMessage.equals("ZERO_RESULTS")) {
            } else {
                try {
                    JSONArray locations = serverResponse.getJSONArray("results");
                    for (int i = 0; i < locations.length(); i++) {
                        JSONObject location = locations.getJSONObject(i);

                        String name = location.getString("name");
                        String address = location.getString("vicinity");
                        String lat = location.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        String lng = location.getJSONObject("geometry").getJSONObject("location").getString("lng");
                        Shop shop = new Shop(name,address,Double.parseDouble(lat),Double.parseDouble(lng));

                        locationList.add(shop);
                    }
                    return locationList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return locationList;
    }
    // You must implements your logic to get data using OrmLite
    private void populateAdapter(String query,ArrayList<Shop> locationResults) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "locationName" });
        for (int i=0; i<locationResults.size(); i++) {
            if (locationResults.get(i).getName().toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, locationResults.get(i).getName()});
        }
        mLocationAdapter.changeCursor(c);
        mLocationAdapter.notifyDataSetChanged();
    }
}
