package com.andrescanales.pokedex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

//import android.app.Fragment;

/**
 * Created by andrescanales on 10/19/14.
 */
public class PokemonListFragment extends Fragment {

    // First we set the variables including one of PokemonAdapter class
    private PokemonAdapter pokemonAdapter;
    private TextView loadingText;
    private ProgressBar progressBar;
    private ListView listView;

    public PokemonListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        loadingText = (TextView)rootView.findViewById(R.id.textview_loading);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressbar_loading);
        listView = (ListView) rootView.findViewById(R.id.listview_pokemon);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Here we are going to listen when an list element being touched to launch detail activity

                //String pokemonStr = pokemonAdapter.getItem(position);
                //Intent intent = new Intent(getActivity(), PokemonDetail.class).putExtra(Intent.EXTRA_TEXT, pokemonStr);
                //startActivity(intent);
                //Parceable
            }
        });

        // Here we are going to check if the adapter comes empty, then we make a request
        if(pokemonAdapter == null){
            ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
            pokemonAdapter = new PokemonAdapter(pokemonList, getActivity());
            runTask();
        }
        listView.setAdapter(pokemonAdapter);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        // We override the method and what setRetainInstance does is controlling(retaining) that the
        // Fragment is not being created again each time the Activity is destroyed.
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }

    // In this method we just make the request to the Class
    public void runTask(){
        PokemonListApiTask apiTask = new PokemonListApiTask(pokemonAdapter, loadingText, listView, progressBar);
        apiTask.execute();
    }
}
