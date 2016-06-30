package br.com.ramada.callboy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.com.ramada.callboy.CallBoy;
import br.com.ramada.callboy.service.SpellingBee;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Grupo;
import com.android.internal.telephony.ITelephony;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.ramada.callboy.model.Contato;

/**
 * Created by RAMADA on 04/06/2016.
 */
public class ChamadaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final TelephonyManager mtelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

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
                    contato.setConfiguracao(configDominante(contato));
                }
                else {
                    contato = new Contato();
                    contato.setConfiguracao(new Configuracao(false,false,false,false));
                }

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        if(contato.getConfiguracao().isBloqueioChamada()){
                            //Bloquear a chamada
                            Class c = null;
                            try {
                                c = Class.forName(mtelephony.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                ITelephony telephonyService = (ITelephony) m.invoke(mtelephony);

                                AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
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

    private Configuracao configDominante(Contato contato){
        Configuracao config = new Configuracao(false, false, false, false);

        List<Grupo> grupos = CallBoy.BD.grupoDAO.getAllGroups();
        ArrayList<Configuracao> configs = new ArrayList<Configuracao>();
        for(Grupo grupo : grupos){
            configs.add(CallBoy.BD.agendaDAO.getConfiguracao(contato, grupo));
        }

        for(Configuracao configSalva : configs){
            if(!config.isBloqueioChamada() && configSalva.isBloqueioChamada() ){
                config.setBloqueioChamada(configSalva.isBloqueioChamada());
            }
            if(!config.isAnuncioChamada() && configSalva.isAnuncioChamada() ){
                config.setAnuncioChamada(configSalva.isAnuncioChamada());
            }
            if(!config.isBloqueioSms() && configSalva.isBloqueioSms() ){
                config.setBloqueioSms(configSalva.isBloqueioSms());
            }
            if(!config.isAnuncioSms() && configSalva.isAnuncioSms() ){
                config.setAnuncioSms(configSalva.isAnuncioSms());
            }
        }

        if(config.isBloqueioChamada() && config.isAnuncioChamada()){
            config.setAnuncioChamada(false);
        }
        if(config.isAnuncioSms() && config.isBloqueioSms()){
            config.setAnuncioSms(false);
        }

        return config;
    }

}
