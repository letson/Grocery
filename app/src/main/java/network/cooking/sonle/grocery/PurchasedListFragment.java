package network.cooking.sonle.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by sonle on 4/3/16.
 */
public class PurchasedListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;

    private String[] groceryList;
    private Activity mActivity;

    @Override
    public void onAttach(Activity act)
    {
        super.onAttach(act);
        this.mActivity = act;

        /*Initialize whatever you need here*/
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchased_list_layout, container, false);
        ((MainActivity)mActivity).setActionBarTitle("Purchased list");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_purchased_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonText = sharedPref.getString("purchasedList", null);

        if (jsonText != null) {
            Gson gson = new Gson();
            groceryList = gson.fromJson(jsonText, String[].class);

            mAdapter = new PurchasedListAdapter(getActivity(), groceryList);
            mRecyclerView.setAdapter(mAdapter);
        } else
            Toast.makeText(getActivity(), "You have not purchased any grocery item", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("PurchasedListFragment", "onResume PurchasedListFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("PurchasedListFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("PurchasedListFragment", "onStop");
    }
}