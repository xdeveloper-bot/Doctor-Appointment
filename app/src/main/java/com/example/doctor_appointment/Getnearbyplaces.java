package com.example.doctor_appointment;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Getnearbyplaces extends AsyncTask<Object, String, String> {
    String googlePlaces;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaces = downloadUrl.readurl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaces;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList =parser.parse(s);
        showNearByPlaces(nearbyPlaceList);
    }

    private void showNearByPlaces(List<HashMap<String, String>> nearbyPlacesList){
        for(int i=0; i<nearbyPlacesList.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleplace = nearbyPlacesList.get(i);

            String placename = googleplace.get("Places");
            String vicinity = googleplace.get("Vicinity");
            double lat = Double.parseDouble(googleplace.get("lat"));
            double lng = Double.parseDouble(googleplace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placename+" : "+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
