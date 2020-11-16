package com.ajsb.rat;

/**
 * Programación Multimedia y de Dispositivos Móviles
 * Antonio J.Sánchez
 * Race Against Time
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    // Variables de juego
    private boolean jugando = false ;

    private int resto ;
    private int limite_tiempo = 20000 ; // 20 segundos (expresamos en milisegundos)

    private MutableLiveData<Integer> puntuacion ;

    private CountDownTimer timer ;
    private Config config;
    //
    @BindView(R.id.button)
    public Button button ;

    @BindView(R.id.score)
    public TextView score ;

    Animation anim;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////// Antes de esta línea no añadir código.

        // Vinculamos la clase MainActivity con la librería ButterKnife
        ButterKnife.bind(this) ;
        //adquirimos la configuracion
        ViewModelProvider.Factory vmf = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        config = new ViewModelProvider(this,vmf).get(Config.class);

        // Instanciamos los elementos del layout
        //button = findViewById(R.id.button) ;
        //score  = findViewById(R.id.score) ;

        // Creamos la variable LiveData
        puntuacion = new MutableLiveData<>() ;

        // Inicializamos el valor de la puntuación
        puntuacion.setValue(0);

        // Definimos el observador
        puntuacion.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer)
            {
                score.setText(String.valueOf(integer)) ;
            }
        }) ;

        // Asignamos funcionalidad al botón
        button.setOnClickListener((v) ->
        {
            if (!jugando) iniciarJuego(v) ;
            else          detenerJuego(v) ;

            anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
            button.startAnimation(anim);
        });


    }

    /**
     * @param v
     */
    private void detenerJuego(View v)
    {
        // Cambiamos el flag
        jugando = false ;

        // Detener el contador
        timer.cancel() ;

        // Calculamos la puntuación
        puntuacion.setValue(puntuacion.getValue() + 100 -(int)((((double)(resto))/((double)(limite_tiempo/1000)))*100));
        // puntuacion +=  100 ;
        //Log.i("RATIME", resto + ", " + limite_tiempo + ", " + ((limite_tiempo / resto) * 100)) ;

        // Mostramos mensaje de inicio de juego
        Snackbar.make(v,getString(R.string.msg_resto, resto), Snackbar.LENGTH_LONG)
                .show() ;

        // Mostramos la puntuación
        //score.setText(String.valueOf(puntuacion)) ;
    }

    /**
     * @param v
     */
    private void iniciarJuego(View v)
    {
        // Cambiamos el flag
        jugando = true ;

        // Mostramos mensaje de inicio de juego
        Snackbar.make(v, R.string.msg_start, Snackbar.LENGTH_LONG)
                .show() ;

        // Creamos el contador de tiempo
        timer = new CountDownTimer(limite_tiempo, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Guardamos los segundos que restan para acabar
                resto = (int) (millisUntilFinished / 1000) ;

                //
                //Log.i("RATIME", String.valueOf(millisUntilFinished)) ;
            }

            /**
             * El tiempo ha terminado; el jugador ha perdido.
             * Mostramos mensaje y restamos puntos.
             */
            @Override
            public void onFinish()
            {
                // Mostramos mensaje en ventana flotante
                MaterialAlertDialogBuilder madb ;
                madb = new MaterialAlertDialogBuilder(MainActivity.this) ;
                madb.setTitle(R.string.app_name) ;
                madb.setMessage(R.string.msg_fail) ;
                madb.setCancelable(false) ;
                madb.setPositiveButton("Aceptar", (d, v) -> { }) ;
                madb.create() ;
                madb.show() ;

                // Restamos puntos
                //puntuacion -= 200 ;
                puntuacion.setValue(puntuacion.getValue()-200);

                // Mostrar puntuación
                //score.setText(String.valueOf(puntuacion)) ;
            }
        } ;

        timer.start() ;
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Solicitar el inflador
        MenuInflater inflador = getMenuInflater() ;

        // Ahora que tengo el inflador, inflamos
        inflador.inflate(R.menu.main_menu, menu) ;

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        // La opción elegida es la correspondiente al menú Información
        if(item.getItemId()==R.id.menuInfo)
        {

            //emviamos valor a info
            Bundle dic = new Bundle();
            dic.putInt("tiempo", limite_tiempo);
            // Lanzar la nueva actividad
            Intent intent = new Intent(this, InfoActivity.class) ;
            intent.putExtra("info", dic);
            startActivity(intent) ;

            //
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {

        outState.putInt("puntos",puntuacion.getValue());
        outState.putString("jugador","yo");
        super.onSaveInstanceState(outState, outPersistentState);
    }
}