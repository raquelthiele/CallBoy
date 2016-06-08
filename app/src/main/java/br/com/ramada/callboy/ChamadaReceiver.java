package br.com.ramada.callboy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

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

                Contato contato = CallBoy.BD.contatoDAO.getContato(incomingNumber);

                Log.d("msg", "" + contato.getId());
                Log.d("msg", contato.getNome());
                Log.d("msg", contato.getNumeroTelefone());

                if(contato != null){
                    contato.setConfiguracao(CallBoy.BD.agendaDAO.getConfiguracao(contato.getId()));
                }

                Log.d("msg", String.valueOf(contato.getConfiguracao().isBloqueioChamada()));

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        Log.d("MyLittleDebugger", "I'm in " + state + " and the number is " + incomingNumber);
                        Toast.makeText(context.getApplicationContext(), incomingNumber,
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(context.getApplicationContext(), "CALL_STATE_RINGING",
                                Toast.LENGTH_LONG).show();
                        if(contato.getConfiguracao().isBloqueioChamada()){
                            //Bloquear a chamada
                            Class c = null;
                            try {
                                c = Class.forName(mtelephony.getClass().getName());
                                Method m = c.getDeclaredMethod("getITelephony");
                                m.setAccessible(true);
                                ITelephony telephonyService = (ITelephony) m.invoke(mtelephony);

                                telephonyService.endCall();
                            } catch (Exception e) {
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
