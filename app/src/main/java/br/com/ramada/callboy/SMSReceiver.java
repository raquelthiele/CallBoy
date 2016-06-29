package br.com.ramada.callboy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            Log.d("msgBOLADO1", messages[0].getOriginatingAddress());
            String numeroContato = messages[0].getOriginatingAddress().substring(5);

            Contato contato = CallBoy.BD.contatoDAO.getContato(numeroContato);

            if(contato != null){
                contato.setConfiguracao(CallBoy.BD.agendaDAO.getConfiguracao(contato));
            }
            else {
                contato = new Contato();
                contato.setConfiguracao(new Configuracao(false,false,false,false));
            }

            Log.d("msgBOLADO", contato.getNome());
            Log.d("msgBOLADO", contato.getNumeroTelefone());
            Log.d("msgBOLADO", String.valueOf(contato.getConfiguracao().isAnuncioSms()));
            Log.d("msgBOLADO", String.valueOf(contato.getConfiguracao().isBloqueioSms()));

            if(contato.getConfiguracao().isBloqueioSms()){
                //abortBroadcast();
                this.abortBroadcast();
                //this.deleteSms(messages[0].ge, context);
            }
            else if(contato.getConfiguracao().isAnuncioSms()){
                //TODO: implementar o TTS
            }


        }

    }

    public boolean deleteSms(String smsId, Context context) {
        boolean isSmsDeleted = false;
        try {

            context.getContentResolver().delete(Uri.parse("content://sms/" + smsId), null, null);
            isSmsDeleted = true;

        } catch (Exception ex) {
            isSmsDeleted = false;
        }
        return isSmsDeleted;
    }

}
