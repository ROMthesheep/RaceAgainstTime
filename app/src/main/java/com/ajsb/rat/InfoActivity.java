package com.ajsb.rat;

/**
 * Programación Multimedia y de Dispositivos Móviles
 * Race Against Time
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity
{
    @BindView(R.id.butBack)
    public Button button ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this) ;

        // Regresamos a la actividad anterior al pulsar el botón
        button.setOnClickListener((v) ->
        {
            setResult(66) ;
            finish() ;
            return ;
        }) ;
    }

    /**
     */
    @Override
    public void onBackPressed() { }
}