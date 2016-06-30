package br.com.ramada.callboy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import br.com.ramada.callboy.CallBoy;
import br.com.ramada.callboy.service.SpellingBee;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;

import java.util.ArrayList;
import java.util.List;

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

            String numeroContato = messages[0].getOriginatingAddress().substring(5);

            Contato contato = CallBoy.BD.contatoDAO.getContato(numeroContato);

            if(contato != null){
                contato.setConfiguracao(configDominante(contato));
            }
            else {
                contato = new Contato();
                contato.setConfiguracao(new Configuracao(false,false,false,false));
            }

            if(contato.getConfiguracao().isBloqueioSms()){
                //abortBroadcast();
                this.abortBroadcast();
                //this.deleteSms(messages[0].ge, context);
            }
            else if(contato.getConfiguracao().isAnuncioSms()){
                //TODO: implementar o TTS
                Intent service = new Intent(context, SpellingBee.class);
                service.putExtra("msg", messages[0].getMessageBody());
                context.startService(service);

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
