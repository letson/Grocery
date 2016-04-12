package network.cooking.sonle.grocery;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sonle on 4/3/16.
 */
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder>  {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private String[] mDataset;
    private Activity context;
    private OnItemLongClickListener mOnItemLongClickListener;
    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }
    public void setmDataset(String[] mDataset) {
        this.mDataset = mDataset;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public CardView cv;
        public TextView mTvName;
        public ViewHolder(View view) {
            super(view);
            cv = (CardView)itemView.findViewById(R.id.cv_grocery_item);
            mTvName = (TextView)cv.findViewById(R.id.tv_grocery_item);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public GroceryListAdapter(Activity context, String[] myDataset, OnItemLongClickListener onItemLongClickListener) {
        this.context = context;
        this.mDataset = myDataset;
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public GroceryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_item_layout, parent, false);

        ViewHolder pvh = new ViewHolder(v);

        return pvh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClick(position);
                return true;
            }
        });
        holder.mTvName.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
