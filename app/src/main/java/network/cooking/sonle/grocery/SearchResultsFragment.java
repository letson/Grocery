package network.cooking.sonle.grocery;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sonle on 4/6/16.
 */
public class SearchResultsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreferences sharedPref;
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
        final View view = inflater.inflate(R.layout.search_result_layout, container, false);
        // set action bar title for login fragment
        ((MainActivity)mActivity).setActionBarTitle("Grocery Nearby");
        Bundle bundle = getArguments();
        //here is your list array
        String query = bundle.getString("query");
        // use the query to search your data somehow
        new FetchingLocationsTask(getActivity(), new FetchingLocationsTask.AsyncResponse() {
            @Override
            public void processFinish(String result) {
                ArrayList<Shop> locationList = GroceryListFragment.populateLocationResults(result);
                mAdapter = new SearchResultAdapter(getActivity(),locationList);

                mRecyclerView =  (RecyclerView)view.findViewById(R.id.rv_search_result_list);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(llm);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mRecyclerView.setAdapter(mAdapter);
            }
        }).execute(query);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("SearchResultFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("SearchResultFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("SearchResultFragment", "onStop");
    }
}
