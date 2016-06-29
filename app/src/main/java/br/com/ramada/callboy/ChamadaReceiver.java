package br.com.ramada.callboy;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import br.com.ramada.callboy.model.Configuracao;
import com.android.internal.telephony.ITelephony;


import java.lang.reflect.Method;
import java.util.Locale;

import br.com.ramada.callboy.model.Contato;

/**
 * Created by RAMADA on 04/06/2016.
 */
public class ChamadaReceiver extends BroadcastReceiver /*implements TextToSpeech.OnInitListener*/ {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final TelephonyManager mtelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //tts = new TextToSpeech(context, this);

        mtelephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                Contato contato = new Contato();

                if(incomingNumber.length() > 0){
                    contato = CallBoy.BD.contatoDAO.getContato(incomingNumber.substring(3));
                }
                else {
                    contato = CallBoy.BD.contatoDAO.getContato(incomingNumber);
                }

                if(contato != null){
                    contato.setConfiguracao(CallBoy.BD.agendaDAO.getConfiguracao(contato));
                }
                else {
                    contato = new Contato();
                    contato.setConfiguracao(new Configuracao(false,false,false,false));
                }

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        /*
                        Log.d("MyLittleDebugger", "I'm in " + state + " and the number is " + incomingNumber);
                        Toast.makeText(context.getApplicationContext(), incomingNumber,
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(context.getApplicationContext(), "CALL_STATE_RINGING",
                                Toast.LENGTH_LONG).show();
                        */

                        if(contato.getConfiguracao().isBloqueioChamada()){
                            //Bloquear a chamada
                            Class c = null;
                            try {
                                c = Class.forName(mtelephony.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                ITelephony telephonyService = (ITelephony) m.invoke(mtelephony);

                                AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                //manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                telephonyService.endCall();
                                wait(100);
                                manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            } catch (Exception e) {
                                Log.d("msgexception", e.getMessage());
                                e.printStackTrace();
                            }

                        }


                        if(contato.getConfiguracao().isAnuncioChamada()){
                            try {
                                AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                Intent service = new Intent(context, SpellingBee.class);
                                service.putExtra("msg", contato.getNome());
                                context.startService(service);
                                wait(100);
                                manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            } catch (Exception e) {
                                Log.d("msgexception", e.getMessage());
                                e.printStackTrace();
                            }


                        }


                        break;
                    default:
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }



}
