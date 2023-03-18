package com.example.practicaguiada10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ListaCentrosFragment extends ListFragment {
    ListView.OnItemClickListener listener;
    public ListaCentrosFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Centro> centros = List.of(
                new Centro("Doctor Fleming", "IES", "Oviedo",
                        R.drawable.iesdrfleming, R.drawable.flemingimg),
                new Centro("Monte Naranco", "IES", "Oviedo",
                        R.drawable.iesmontenaranco, R.drawable.narancoimg),
                new Centro("Avilés", "CIFP", "Avilés",
                        R.drawable.cifpaviles, R.drawable.avilesimg)
        );
        Activity activity = getActivity();
        setListAdapter(new CentrosAdapter(centros, activity));
        if (activity instanceof ListView.OnItemClickListener)
            listener = (AdapterView.OnItemClickListener) activity;
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v,
                                int position, long id) {
        if (listener != null)
            listener.onItemClick(l, v, position, id);
    }
}
