package network.cooking.sonle.grocery;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sonle on 4/4/16.
 */
public class PurchasedListAdapter  extends RecyclerView.Adapter<PurchasedListAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cv;
        public TextView mTvName;
        public ViewHolder(View view) {
            super(view);
            cv = (CardView)itemView.findViewById(R.id.cv_purchased_item);
            mTvName = (TextView)cv.findViewById(R.id.tv_purchased_item);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public PurchasedListAdapter(Context context, String[] myDataset) {
        this.context = context;
        this.mDataset = myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public PurchasedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchased_item_layout, parent, false);

        ViewHolder pvh = new ViewHolder(v);

        return pvh;
    }
    // update content for element in holder
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvName.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
