package jorgecastillovindas.avisosescuelahiguito;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ListaAvisos extends ActionBarActivity {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> empresaList;
    private static String url = "http://escuelahiguitoav.esy.es/AndroidApp/get_avisos_porCategoria.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_AVISOS = "avisos";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_HORA = "hora";
    private static final String TAG_FECHA = "fecha";
    private static final String TAG_DESCRIPCION = "descripcion";
    JSONArray avisosArray = null;
    LinkedList<Aviso> listaAvisos;
    private String categoriaSeleccionada;


    ListView lvwAvisos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_avisos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        listaAvisos= new LinkedList<>();
        Bundle bundle = getIntent().getExtras();
        categoriaSeleccionada = bundle.getString("categoriaAviso");
       TextView txvCategoriaSeleccionada = (TextView)findViewById(R.id.txvCategoriaSeleccionada);
        txvCategoriaSeleccionada.setText(categoriaSeleccionada);
        ImageButton btn = (ImageButton)(findViewById(R.id.ibtnInfoListaAvisos));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaAvisos.this, InfoListaAvisos.class));
            }
        });
        new LoadAllProducts().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_avisos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListaAvisos.this);
            pDialog.setMessage("Cargando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            List params = new ArrayList<NameValuePair>(1);
            params.add(new BasicNameValuePair("categoria",categoriaSeleccionada));
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);
            Log.d("All Products: ", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    avisosArray = json.getJSONArray(TAG_AVISOS);
                    for (int i = 0; i < avisosArray.length(); i++) {
                        JSONObject c = avisosArray.getJSONObject(i);
                        Aviso avisoAux = new Aviso();
                        avisoAux.setNombre(String.valueOf(c.get(TAG_NOMBRE)));
                        avisoAux.setDescripcion(String.valueOf(c.get(TAG_DESCRIPCION)));
                        avisoAux.setFecha(String.valueOf(c.get(TAG_FECHA)));
                        avisoAux.setHora(String.valueOf(c.get(TAG_HORA)));

                        listaAvisos.add(avisoAux);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    cargarListView();
                }
            });
        }
    }

    public void cargarListView(){
        String[] arregloNombresAvisos = new String [listaAvisos.size()];
        int[] arregloImagenAvisos = new int [listaAvisos.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        int contador=0;
        for (Aviso aviso : listaAvisos) {

            arregloNombresAvisos[contador]=aviso.getNombre();
            if(aviso.getFecha().equals(date)){
                arregloImagenAvisos[contador]= R.drawable.star2;
            }else{
                arregloImagenAvisos[contador]= R.drawable.star;
            }
            contador++;
        }

       lvwAvisos = (ListView)findViewById(R.id.lvwAvisos);
        ListViewAdapter adapter = new ListViewAdapter(this, arregloNombresAvisos, arregloImagenAvisos);
        lvwAvisos.setAdapter(adapter);
        lvwAvisos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long arg) {

               Aviso avisoAux =listaAvisos.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("nombreAviso", avisoAux.getNombre());
                bundle.putString("descripcionAviso", avisoAux.getDescripcion());
                bundle.putString("fechaAviso", avisoAux.getFecha());
                bundle.putString("horaAviso", avisoAux.getHora());
                Intent intent = new Intent(ListaAvisos.this, DescripcionAvisos.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
