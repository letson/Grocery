package network.cooking.sonle.grocery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sonle on 4/6/16.
 */
public class GroceryMapFragment extends  Fragment{
    private SupportMapFragment fragment;
    private GoogleMap map;
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
        View view = inflater.inflate(R.layout.map_layout, container, false);
        // set action bar title for login fragment
        ((MainActivity)mActivity).setActionBarTitle("Shop location");
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }

    }
    /***at this time google play services are not initialize so get map
     * and add what ever you want to it in onResume() or onStart() **/
    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            Bundle bundle = getArguments();
            //here is your list array
            double lat = bundle.getDouble("lat");
            double lng = bundle.getDouble("lng");
            String name  = bundle.getString("name");
            map = fragment.getMap();
            LatLng latLng = new LatLng(lat, lng);
            map.addMarker(new MarkerOptions().position(latLng).title(name)).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}