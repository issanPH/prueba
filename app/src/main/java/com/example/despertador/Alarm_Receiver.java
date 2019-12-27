package com.example.despertador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Receptor", "Yay!");

        String get_string = intent.getExtras().getString("extra");

        Log.e("Cúal es la llave?", get_string);

        Integer get_eleccion = intent.getExtras().getInt("eleccion");

        Log.e("El tono elegido es ", get_eleccion.toString());

        Intent service_intento = new Intent(context,RingtonePlayingService.class);

        service_intento.putExtra("extra", get_string);

        service_intento.putExtra("eleccion", get_eleccion);

        context.startService(service_intento);

    }
}
