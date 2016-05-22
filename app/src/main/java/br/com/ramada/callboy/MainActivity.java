
package br.com.ramada.callboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                criarContatoTeste();
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



}
