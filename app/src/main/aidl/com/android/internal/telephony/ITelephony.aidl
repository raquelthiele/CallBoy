package com.android.internal.telephony;

/**
 * Created by PRUP-16 on 07/06/2016.
 */
interface ITelephony {

    boolean endCall();

    void answerRingingCall();

    void silenceRinger();
}
