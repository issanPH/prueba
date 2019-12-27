package com.example.despertador;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.despertador.dal.RelogDAL;
import com.example.despertador.dto.Relog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AlarmManager alarm_man;
    TimePicker alarm_tp;
    TextView estado_alarma;
    Context context;
    PendingIntent pending_intent;
    int elegir_tono;
    Relog relog;
    RelogDAL relogDAL;
    String getAMPMValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relog = new Relog();
        relogDAL= new RelogDAL(getApplicationContext());
        this.context = this;

        //Iniciar alarm manager
        alarm_man = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Iniciar timepicker
        alarm_tp = (TimePicker) findViewById(R.id.timePicker1);

        estado_alarma = (TextView) findViewById(R.id.estado_alarma);

        final Calendar calendar = Calendar.getInstance();

        final Intent intento = new Intent(this.context, Alarm_Receiver.class);

        Spinner spinner = (Spinner) findViewById(R.id.tonos_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tonos_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);



        Button encender_alarma = (Button) findViewById(R.id.inicio_alarma);

        encender_alarma.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                relog.setEstado("Activa");
                calendar.set(Calendar.HOUR_OF_DAY, alarm_tp.getHour());
                calendar.set(Calendar.MINUTE, alarm_tp.getMinute());

                int horas = alarm_tp.getHour();
                int minutos = alarm_tp.getMinute();

                String horasS = String.valueOf(horas);
                String minutosS = String.valueOf(minutos);

                if (horas > 12 ){
                    horasS = String.valueOf(horas-12);
                }

                if (minutos < 10) {
                    minutosS = "0"+ String.valueOf(minutos);
                }

                enviar_estado("Alarma encendida a las "+horasS+":"+minutosS);

                intento.putExtra("extra", "on");

                intento.putExtra("eleccion", elegir_tono);

                Log.e("El tono elegido es ", String.valueOf(elegir_tono));



                pending_intent = PendingIntent.getBroadcast(MainActivity.this,0,
                        intento,PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_man.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }



        });

        Button apagar_alarma = (Button) findViewById(R.id.fin_alarma);

        apagar_alarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relog.setEstado("Inactiva");

                enviar_estado("Alarma apagada!");

                alarm_man.cancel(pending_intent);

                intento.putExtra("extra", "off");

                intento.putExtra("eleccion", elegir_tono);

                sendBroadcast(intento);

            }
        });

        Button btnLista = findViewById(R.id.btnLista);


        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this, AlarmsActivity.class);
                intento.putExtra("AWA", "UWU");
                intento.putExtra("EWE", "OWO");
                intento.putExtra("tono", elegir_tono);

                startActivityForResult(intento, 100);
            }
        });

        Button btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                relogDAL.insertar(alarm_tp.getHour(),alarm_tp.getMinute(),getAMPMValue,"true");;
                relog.setAMPM(getAMPMValue);
                relog.setHora(alarm_tp.getHour());
                relog.setMinutos(alarm_tp.getMinute());
                Toast.makeText(getApplicationContext(),
                        "Alarma para Las: "+relog.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });







        alarm_tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                getAMPMValue ="AM";
                if(hourOfDay>11){
                    getAMPMValue ="PM";
                }
            }

        });




    }

    private void enviar_estado(String s) {
        estado_alarma.setText(s);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        elegir_tono = (int) id;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
