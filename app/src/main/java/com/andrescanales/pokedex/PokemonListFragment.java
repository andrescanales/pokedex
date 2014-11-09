package com.andrescanales.pokedex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Callbacks mCallbacks = sDummyCallbacks;

    public interface Callbacks {
        public void onItemSelected(String nombre);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String nombre) {

        }
    };

    public void onAttach(Activity activity){
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)){
            throw new IllegalStateException(
                    "Activity Must implement callbacks"
            );
        }
        mCallbacks = (Callbacks) activity;
    }

    public PokemonListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        loadingText = (TextView)rootView.findViewById(R.id.textview_loading);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressbar_loading);
        listView = (ListView) rootView.findViewById(R.id.listview_pokemon);

        // Here we are going to check if the adapter comes empty, then we make a request
        if(pokemonAdapter == null){
            ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
            pokemonAdapter = new PokemonAdapter(pokemonList, getActivity());
            runTask();
        }
        listView.setAdapter(pokemonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pokemon pokemon = (Pokemon) listView.getAdapter().getItem(i);
                mCallbacks.onItemSelected(pokemon.getNombre());
            }
        });
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
        /*PokemonListApiTask apiTask = new PokemonListApiTask(pokemonAdapter,
                listView,progressBarLoading);
        apiTask.execute();*/
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        GsonRequest<Pokemon[]> getPersons =
                new GsonRequest<Pokemon[]>("http://mi-pokedex.herokuapp.com/api/v1/pokemons", Pokemon[].class,

                        new Response.Listener<Pokemon[]>() {
                            @Override
                            public void onResponse(Pokemon[] response) {
                                List<Pokemon> pokemons = Arrays.asList(response);
                                if(pokemons!=null){
                                    pokemonAdapter.clear();
                                    pokemonAdapter.addAll(pokemons);
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                    }
                });

        PokedexApplication.getInstance().addToRequestQueue(getPersons);
    }
}
