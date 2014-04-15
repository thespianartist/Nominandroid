package mx.gdgipn.nominandroid.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mx.gdgipn.nominandroid.app.R;
import mx.gdgipn.nominandroid.app.models.Persona;

/**
 * Created by thespianartist on 4/12/14.
 */
public class CustomAdapter extends ArrayAdapter<Persona>{
    ArrayList<Persona> data;
    LayoutInflater inflater;


     public CustomAdapter(Context context, ArrayList<Persona> objects){
         super(context, -1, objects);

         this.data = objects;
         this.inflater = LayoutInflater.from(context);
     }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        Persona persona = data.get(position);
        int layout = R.layout.list_row;

        if (convertView == null) {

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.nombre = (TextView) convertView.findViewById(R.id.nombre);
            holder.salarioDiario = (TextView) convertView.findViewById(R.id.salario_diario);
            holder.diasLaborados = (TextView) convertView.findViewById(R.id.dias_laborados);
            holder.sueldo = (TextView) convertView.findViewById(R.id.sueldo);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nombre.setText(persona.getNombre());
        holder.sueldo.setText(persona.getSueldo());
        holder.salarioDiario.setText(persona.getSalarioDiario());
        holder.diasLaborados.setText(persona.getDiasLaborados());

       return convertView;

    }

    public static class ViewHolder{
        public TextView nombre;
        public TextView salarioDiario;
        public TextView diasLaborados;
        public TextView sueldo;
    }




}


