package jorgecastillovindas.avisosescuelahiguito;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ListViewAdapter adapter;
    String[] titulo = new String[]{
            "Avisos Generales",
            "Kinder",
            "Primer grado",
            "Segundo grado",
            "Tercer grado",
            "Cuarto grado",
            "Quinto grado",
            "Sexto grado",
            "Banda",
            "Acerca de"
    };

    int[] imagenes = {
            R.drawable.report,
            R.drawable.icon_kinder,
            R.drawable.icon_primero,
            R.drawable.icon_segundo,
            R.drawable.icon_tercero,
            R.drawable.icon_cuarto,
            R.drawable.icon_quinto,
            R.drawable.icon_sexto,
            R.drawable.icon_musica,
            R.drawable.credit

    };
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        /*//Fill spinner
        Spinner spinner = (Spinner) findViewById(R.id.sprCategorias);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        //Spinner's event
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Bundle bundle = new Bundle();
                    switch (position){
                        case 1: bundle.putString("categoriaAviso", "Banda");
                            break;
                        case 2: bundle.putString("categoriaAviso", "Kinder");
                            break;
                        case 3: bundle.putString("categoriaAviso", "Primer grado");
                            break;
                        case 4: bundle.putString("categoriaAviso", "Segundo grado");
                            break;
                        case 5: bundle.putString("categoriaAviso", "Tercer grado");
                            break;
                        case 6: bundle.putString("categoriaAviso", "Cuarto grado");
                            break;
                        case 7: bundle.putString("categoriaAviso", "Quinto grado");
                            break;
                        case 8: bundle.putString("categoriaAviso", "Sexto grado");
                            break;

                        default:  bundle.putString("categoriaAviso", "General");;
                            break;
                    }
                    Intent intent = new Intent(MainActivity.this, ListaAvisos.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

         final ListView lista = (ListView) findViewById(R.id.lvwOpciones);
         adapter = new ListViewAdapter(this, titulo, imagenes);
         lista.setAdapter(adapter);

         lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                 Bundle bundle = new Bundle();
                 if (i == 0) {
                     bundle.putString("categoriaAviso", "General");
                 }else if(i==1){
                     bundle.putString("categoriaAviso", "Kinder");
                 }
                 else if(i==2){
                     bundle.putString("categoriaAviso", "Primer grado");
                 }
                 else if(i==3){
                     bundle.putString("categoriaAviso", "Segundo grado");
                 }
                 else if(i==4){
                     bundle.putString("categoriaAviso", "Tercer grado");
                 }
                 else if(i==5){
                     bundle.putString("categoriaAviso", "Cuarto grado");
                 }
                 else if(i==6){
                     bundle.putString("categoriaAviso", "Quinto grado");
                 }
                 else if(i==7){
                     bundle.putString("categoriaAviso", "Sexto grado");
                 }
                 else if(i==8){
                     bundle.putString("categoriaAviso", "Banda");
                 }
                 else if(i==9){
                     startActivity(new Intent(MainActivity.this, Creditos.class));
                     return;
                 }
                 Intent intent = new Intent(MainActivity.this, ListaAvisos.class);
                 intent.putExtras(bundle);
                 startActivity(intent);
             }
         });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
