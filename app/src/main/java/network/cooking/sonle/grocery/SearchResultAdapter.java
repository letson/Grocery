package network.cooking.sonle.grocery;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sonle on 4/6/16.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private ArrayList<Shop> mDataset;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cv;
        public TextView mTvName;
        public TextView mTvAddress;
        public ViewHolder(View view) {
            super(view);
            cv = (CardView)itemView.findViewById(R.id.cv_search_item);
            mTvName = (TextView)cv.findViewById(R.id.tv_seach_name);
            mTvAddress = (TextView)cv.findViewById(R.id.tv_seach_address);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchResultAdapter(Context context, ArrayList<Shop> myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item_layout, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }
    // update content for element in holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTvName.setText(mDataset.get(position).getName());
        holder.mTvAddress.setText(mDataset.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", mDataset.get(position).getLat());
                bundle.putDouble("lng", mDataset.get(position).getLng());
                bundle.putString("name", mDataset.get(position).getName());
                FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GroceryMapFragment groceryMapFragment = new GroceryMapFragment();
                groceryMapFragment.setArguments(bundle);
                Fragment fragment_add = fm.findFragmentById(R.id.fragment_container);
                if(fragment_add == null)
                {
                    ft.add(R.id.fragment_container, groceryMapFragment, "groceryMapFragment");
                } else {
                    ft.replace(R.id.fragment_container, groceryMapFragment, "groceryMapFragment");
                }
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
