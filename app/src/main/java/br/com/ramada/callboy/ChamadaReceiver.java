package br.com.ramada.callboy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by RAMADA on 04/06/2016.
 */
public class ChamadaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager mtelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mtelephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        Log.d("MyLittleDebugger", "I'm in " + state + " and the number is " + incomingNumber);
                        Toast.makeText(context.getApplicationContext(), incomingNumber,
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(context.getApplicationContext(), "CALL_STATE_RINGING",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
