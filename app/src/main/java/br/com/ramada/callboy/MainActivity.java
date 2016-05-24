
package br.com.ramada.callboy;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

import static br.com.ramada.callboy.CallBoy.BD;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "br.com.ramada.TESTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            Log.d("msg", "Não tenho permissão");
        }
        else{
            copiaAgenda();
            Log.d("msg", "Tenho permissão");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criarContatoTeste();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///criarContatoTeste();
                //redirecionarAdicaoContato();
                //List<Contato> contatos = BD.contatoDAO.getAllContacts();

                redirecionarAdicaoContato();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        populaListaContatos();
    }

    @Override
    protected void onResume(){
        super.onResume();
        populaListaContatos();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        populaListaContatos();
    }

    private void redirecionarAdicaoContato() {
        Intent intent = new Intent(this, ExibirAdicaoContatoActivity.class);
       // EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void criarContatoTeste(){

        Contato contato = new Contato("Raquel","992830552");
        BD.contatoDAO.salvarContato(contato);

        Grupo geral = new Grupo("Geral");
        BD.grupoDAO.salvarGrupo(geral);

        Horario permanente = new Horario("Permanente");
        BD.horarioDAO.salvarHorario(permanente);

    }

    private void populaListaContatos(){
        List<Contato> contatos = BD.contatoDAO.getAllContacts();

        List<String> nomesContatos = new ArrayList<>();
        for(Contato c : contatos){
            nomesContatos.add(c.getNome());
        }

        ListView listView = (ListView) findViewById(R.id.listView_contatos);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, nomesContatos));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


    private void copiaAgenda(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String nome = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String numeroTelefone = "";

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        numeroTelefone = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        break;
                    }
                    pCur.close();
                }

                Contato contato = new Contato(nome, numeroTelefone);
                BD.contatoDAO.salvarContato(contato);
            }
        }


    }


    // TODO: criar constantes globais para os requestCodes
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    copiaAgenda();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
