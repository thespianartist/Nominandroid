package mx.gdgipn.nominandroid.app;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
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

        //ActionBar de Color Negro
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(colorDrawable);

        //Creamos una instancia de RequestQueue que solo recibe el contexto
        RequestQueue queue = Volley.newRequestQueue(this);

        //URL del SpreadSheet
        String URL = "https://script.googleusercontent.com/macros/echo?user_content_key=RcHx6AaJv4vz_BHvaygpB-s-65ScCIX3NDqf9qb1fc_v5fMuMCJrd6Juw9qtJ8GEIUzWNu3VUrGUa-3JTzIP7u531AHsHcjgm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnA21ty4-NFgqoKc_ZhjFAX1ah6w8hoKrwU71K7lgPAwVmCE74_lR62bbtcQNKfQPMhdIug5zkO6q&lib=MuVcg4D5zOmfGpbFbxsUiP8v3COA06hUq";

        //Creamos una instancia de un ProgressDialog para que el usuario observe que los datos se estan obteniendo
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait ...", "Downloading Data ...", true);


        //Configuramos nuestro request, al intanciar JsonArrayRequest recibe la URL y un Listener de JSON Array, el cual
          //contiene una serie de metodos a implelentar, practicamente si resulto bien o existio un error, ademas
              // sirve como un singleton para mandarlo a llamar cuando sea necesario hacer de nuevo el request.
                // Recordar que este solo construye el llamado HTTP, se ejecuta mas adelante cuando lo agregamos como parametro del metodo add
                   //de RequestQueue

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {

            @Override
            public void onResponse(JSONArray response) {

                //dejamos en el Log un pequeño mensaje con el response como string para ver un preview de lo que recibimos
                Log.e("respeusta", response.toString());

                //buscamos el listview de main_activity
                ListView list = (ListView) findViewById(R.id.list_view);

                //Si vemos el CustomAdapter debemos pasar un contexto y un ArrayList de objetos Personas
                  //dicho ArrayList es parseao de la respuesta por medio de la funcion parser

                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), parser(response));

                //pasamos el adapter para que la lista se muestre en el UI
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

        // Ejecutamos Volley, solo aqui se manda a realizar
        queue.add(req);



    }


    // La funcion de abajo se encarga de parsear toda la informacion

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
