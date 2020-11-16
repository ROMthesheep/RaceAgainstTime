package com.ajsb.rat;

/**
 * Programación Multimedia y de Dispositivos Móviles
 * Race Against Time
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity
{
    @BindView(R.id.butBack)
    public Button button ;

    @BindView(R.id.editTime)
    public EditText editTime;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this) ;

        //recuperamos el valor
        Bundle bundle = getIntent().getExtras().getBundle("info");
        editTime.setText(String.valueOf(bundle.getInt("tiempo")));
        // Regresamos a la actividad anterior al pulsar el botón
        button.setOnClickListener((v) ->
        {
            setResult(66) ;
            finish();
            return;
        }) ;

    }

    /**
     */
    @Override
    public void onBackPressed() { }
}