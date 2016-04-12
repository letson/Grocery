package network.cooking.sonle.grocery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sonle on 4/3/16.
 */
public class LoginFragment extends Fragment implements View.OnClickListener
{
    private Activity mActivity;

    @Override
    public void onAttach(Activity act)
    {
        super.onAttach(act);
        this.mActivity = act;

        /*Initialize whatever you need here*/
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_layout, container, false);

        Button button = (Button)v.findViewById(R.id.btn_log_in);
        button.setOnClickListener(this);
        // set action bar title for login fragment
        ((MainActivity)mActivity).setActionBarTitle("Login");
        return v;
    }

    @Override
    public void onClick(View v) {
        ((OnButtonLoginClickListener) mActivity).loadNextFragment();
    }


    public interface OnButtonLoginClickListener
    {
        void loadNextFragment();
    }
}
