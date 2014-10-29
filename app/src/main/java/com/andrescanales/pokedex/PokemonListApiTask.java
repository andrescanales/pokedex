package com.andrescanales.pokedex;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by andrescanales on 10/19/14.
 */
public class PokemonListApiTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {

    private final String LOG_TAG = PokemonListApiTask.class.getSimpleName();

    private PokemonAdapter mPokemonAdapter;
    private TextView loadingText;
    private ListView pokemonList;
    private ProgressBar progressBarLoading;

    // This public method just make a set of the variables to use them later
    public PokemonListApiTask(PokemonAdapter mPokemonAdapter, TextView loadingText,
                              ListView pokemonList, ProgressBar progressBarLoading){
        this.mPokemonAdapter = mPokemonAdapter;
        this.loadingText = loadingText;
        this.pokemonList = pokemonList;
        this.progressBarLoading = progressBarLoading;
    }

    // In onPreExecute method we just play with the loading elements
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        this.pokemonList.setVisibility(View.INVISIBLE);
        this.loadingText.setVisibility(View.VISIBLE);
        this.progressBarLoading.setVisibility(View.VISIBLE);
    }

    // onPostExecute method is the one that communicates with the main thread
    @Override
    protected void onPostExecute(ArrayList<Pokemon> result) {
        if (result != null) {
            mPokemonAdapter.clear();
            for(Pokemon pokemon : result) {
                mPokemonAdapter.add(pokemon);
            }
        }
        this.loadingText.setVisibility(View.INVISIBLE);
        this.progressBarLoading.setVisibility(View.INVISIBLE);
        this.pokemonList.setVisibility(View.VISIBLE);
    }

    // doInBackground is the rambo one, this makes all the work in a second thread
    @Override
    protected ArrayList<Pokemon> doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String pokemonJsonStr = null;

        try {

            URL url = new URL("http://mi-pokedex.herokuapp.com/api/v1/pokemons");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // InputStream will take the data of the previous urlConnection opened and then
            // The buffer will be reading the content to make an appropriate treatment of data.
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            // Till here.

            pokemonJsonStr = buffer.toString();
            Log.i(LOG_TAG, pokemonJsonStr);

            JSONArray pokemonsArray = new JSONArray(pokemonJsonStr);
            ArrayList<Pokemon> results = new ArrayList<Pokemon>();

            for(int i = 0; i < pokemonsArray.length(); i++) {
                JSONObject pokemonJSON = pokemonsArray.getJSONObject(i);
                Pokemon pokemon = new Pokemon();
                pokemon.setNombre(pokemonJSON.getString("nombre"));
                pokemon.setUrlImage(pokemonJSON.getString("avatar"));
                results.add(pokemon);
            }
            return results;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;

        } catch (JSONException e) {
            e.printStackTrace();

        } finally{
            // In this part we just close all the connections generated
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        // We are returning null at the end of the method because in the other exceptions
        // we are not returning anything
        return null;
    }
}
