package com.example.login_map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.login_map.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment {
    private FragmentMapsBinding binding;
    private GoogleMap google_map;
    private FirebaseDatabase mapsDatabase;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            google_map = googleMap;
            mapsDatabase = FirebaseDatabase.getInstance();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        binding.btnSite1.setOnClickListener(v -> changeLocation("Site1"));

        binding.btnSite2.setOnClickListener(v -> changeLocation("Site2"));

        binding.btnSite3.setOnClickListener(v -> changeLocation("Site3"));

        binding.btnSite4.setOnClickListener(v -> changeLocation("Site4"));
    }

    private void changeLocation(String key) {
        DatabaseReference siteRef = mapsDatabase.getReference(key);
        siteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LatLng newSite = new LatLng(dataSnapshot.child("lat").getValue(Double.class), dataSnapshot.child("lng").getValue(Double.class));
                google_map.addMarker(new MarkerOptions().position(newSite).title(dataSnapshot.child("name").getValue(String.class)));
                google_map.moveCamera(CameraUpdateFactory.newLatLng(newSite));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}