package com.example.despertador;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class RingtonePlayingService extends Service {

    MediaPlayer media_tema;
    int startId;
    boolean sonando;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String estado = intent.getExtras().getString("extra");

        Integer eleccion_id = intent.getExtras().getInt("eleccion");

        Log.e("Ringtone estado es ", estado);
        Log.e("El tono elegido es ", eleccion_id.toString());

        NotificationManager notify_man = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent pending_intento_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        Notification notificacion_popup = new Notification.Builder(this)
                .setContentTitle("Alarma sonando")
                .setContentText("Clic aquí")
                .setContentIntent(pending_intento_main_activity)
                .setSmallIcon(R.drawable.notify)
                .setAutoCancel(true)
                .build();

        assert estado != null;
        switch (estado){
            case "on":
            startId = 1;
            break;
            case "off":
            startId = 0;
            break;
            default:
                startId =0;
                break;
        }



        if (!this.sonando && startId == 1){

            Log.e("No hay musica", "y apretaste encender");

            if (eleccion_id == 0){

            int min_n = 1;
            int max_n = 6;

            Random random_n = new Random();
            int tono_n = random_n.nextInt(max_n + min_n);
            Log.e("El numero al azar es ", String.valueOf(tono_n));

            if (tono_n == 1){
                media_tema = MediaPlayer.create(this,R.raw.boku_no_hero_polaris);
                media_tema.start();
            }
            else if (tono_n == 2){
                media_tema = MediaPlayer.create(this,R.raw.my_hero_academia_op3);
                media_tema.start();
            }
            else if (tono_n == 3){
                media_tema = MediaPlayer.create(this,R.raw.padoru);
                media_tema.start();

            }
            else if (tono_n == 4){
                media_tema = MediaPlayer.create(this,R.raw.true_sincerely);
                media_tema.start();
            }
            else if (tono_n == 5){
                media_tema = MediaPlayer.create(this,R.raw.clannad_dango);
                media_tema.start();
            }
            else {
                media_tema = MediaPlayer.create(this,R.raw.buenosdias);
                media_tema.start();
            }


            }
            else if (eleccion_id == 1){

                media_tema = MediaPlayer.create(this,R.raw.boku_no_hero_polaris);
                media_tema.start();

           }
            else if (eleccion_id == 2){

                media_tema = MediaPlayer.create(this,R.raw.my_hero_academia_op3);
                media_tema.start();

            }
            else if (eleccion_id == 3){

                media_tema = MediaPlayer.create(this,R.raw.padoru);
                media_tema.start();

            }
            else if (eleccion_id == 4){

                media_tema = MediaPlayer.create(this,R.raw.true_sincerely);
                media_tema.start();

            }
            else if (eleccion_id == 5){

                media_tema = MediaPlayer.create(this,R.raw.boku_no_hero_polaris);
                media_tema.start();

            }

            else if (eleccion_id == 6){

                media_tema = MediaPlayer.create(this,R.raw.clannad_dango);
                media_tema.start();

            }
            else{

                media_tema = MediaPlayer.create(this,R.raw.buenosdias);
                media_tema.start();
            }


            notify_man.notify(0, notificacion_popup);

            this.sonando = true;
            this.startId = 1;


        }

        else if (this.sonando && startId == 0){

            Log.e("Hay musica", "y apretaste apagar");

            media_tema.stop();
            media_tema.reset();

            this.sonando = false;
            this.startId = 0;

        }
        else if (!this.sonando && startId == 0){

            Log.e("No hay musica", "y apretaste apagar");

            this.sonando = false;
            this.startId = 0;


        }
        else if (this.sonando && startId == 1){

            Log.e("Hay musica", "y apretaste encender");

        }
        else{
            Log.e("Otro", "usted llego hasta aquí");
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("on Destroy llamado", "ta da");

        super.onDestroy();
        this.sonando = false;
    }
}
