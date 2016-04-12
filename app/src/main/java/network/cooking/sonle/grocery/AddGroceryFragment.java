package network.cooking.sonle.grocery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sonle on 4/3/16.
 */
public class AddGroceryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.add_grocery_layout, container, false);
        Button btn_add = (Button)view.findViewById(R.id.btn_add_grocery);
        final EditText txt_grocery_name = (EditText)view.findViewById(R.id.txt_grocery_name);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                //Set the values
                String jsonText = sharedPref.getString("groceryList", null);
                Gson gson = new Gson();
                if(jsonText != null) {
                    String[] jsonArr = gson.fromJson(jsonText, String[].class);
                    List<String> groceryList =new ArrayList<String> (Arrays.asList(jsonArr));
                    groceryList.add(txt_grocery_name.getText().toString());
                    jsonText  = gson.toJson(groceryList);
                }
                else {
                    List<String> groceryList = new ArrayList<String>();
                    groceryList.add(txt_grocery_name.getText().toString());
                    jsonText  = gson.toJson(groceryList);
                }
                editor.putString("groceryList",jsonText);
                editor.commit();
                // return to grocery list then reload grocery list
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack(); // pop last fragment on Stack
            }
        });
        return view;
    }
}
