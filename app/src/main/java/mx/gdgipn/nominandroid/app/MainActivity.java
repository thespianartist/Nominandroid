package mx.gdgipn.nominandroid.app;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.gdgipn.nominandroid.app.adapters.CustomAdapter;
import mx.gdgipn.nominandroid.app.models.Persona;


public class MainActivity extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(colorDrawable);

        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "https://script.googleusercontent.com/macros/echo?user_content_key=RcHx6AaJv4vz_BHvaygpB-s-65ScCIX3NDqf9qb1fc_v5fMuMCJrd6Juw9qtJ8GEIUzWNu3VUrGUa-3JTzIP7u531AHsHcjgm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnA21ty4-NFgqoKc_ZhjFAX1ah6w8hoKrwU71K7lgPAwVmCE74_lR62bbtcQNKfQPMhdIug5zkO6q&lib=MuVcg4D5zOmfGpbFbxsUiP8v3COA06hUq";

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait ...", "Downloading Data ...", true);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {

            @Override
            public void onResponse(JSONArray response) {
                Log.e("respeusta", response.toString());
                ListView list = (ListView) findViewById(R.id.list_view);
                CustomAdapter adapter = new CustomAdapter(getBaseContext(), parser(response));
                list.setAdapter(adapter);

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                progressDialog.dismiss();

            }
        });

        queue.add(req);



    }


    public ArrayList<Persona> parser(JSONArray respuesta){
        ArrayList<Persona> personasAux = new ArrayList<Persona>();

        for(int i = 0; i < respuesta.length(); i++){
            JSONObject jsonObject;
            Persona persona = new Persona();

            try {
                jsonObject = (JSONObject) respuesta.get(i);
                persona.setNombre(jsonObject.getString("nombre"));
                persona.setSalarioDiario(jsonObject.getString("salarioDiario"));
                persona.setDiasLaborados(jsonObject.getString("diasLaborados"));
                persona.setSueldo(jsonObject.getString("sueldo"));
                personasAux.add(persona);

            } catch (JSONException e) { Log.e("peticion", "Error al parsear");}
        }

        return personasAux;
    }



}
