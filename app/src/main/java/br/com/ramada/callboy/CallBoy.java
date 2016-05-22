package br.com.ramada.callboy;

import android.app.Application;
import br.com.ramada.callboy.dao.Database;

/**
 * Created by Ramada on 22/05/2016.
 */
public class CallBoy extends Application {

    public static Database BD;


    @Override
    public void onCreate() {
        super.onCreate();
        BD = new Database(this);
    }

    @Override
    public void onTerminate() {
        BD.close();
        super.onTerminate();
    }
}
