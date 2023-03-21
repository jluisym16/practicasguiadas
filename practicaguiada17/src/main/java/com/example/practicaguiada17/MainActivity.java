package com.example.practicaguiada17;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.BreakIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {
    private EditText textoBusqueda;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textoBusqueda = findViewById(R.id.editText);
        resultado = findViewById(R.id.resultado);
        findViewById(R.id.google).setOnClickListener(this::buscarClick);
        findViewById(R.id.googleBooks).setOnClickListener(this::restClick);
    }

    private void restClick(View view) {
        new BusquedaAsyncTask("Búsqueda en Google Books", this::consultaRest)
                .execute(textoBusqueda.getText().toString());
    }

    private void buscarClick(View view) {
        new BusquedaAsyncTask("Búsqueda en Google Books", this::busquedaGoogle)
                .execute(textoBusqueda.getText().toString());

    }

    private String consultaRest(String textoBusqueda) {
        try {
            URL url = new URL("https://books.google.com/books/feeds/volumes?q=\"" +
                    URLEncoder.encode(textoBusqueda, "UTF-8") + "\"");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            ManejadorXML manejadorXML = new ManejadorXML();
            reader.setContentHandler(manejadorXML);
            reader.parse(new InputSource(url.openStream()));
            return manejadorXML.getResultado();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return e.getLocalizedMessage();
        }


    }

    private String busquedaGoogle(String textoBusqueda) {
        HttpsURLConnection con = null;
        StringBuilder resultado = new StringBuilder(textoBusqueda);
        resultado.append(": ");
        try {
            URL url = new URL("https://www.google.com/search?q=" +
                    URLEncoder.encode(textoBusqueda, "UTF-8") + "&num=10");
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(con.getInputStream())) {
                    scanner.findWithinHorizon("Aproximadamente", 0);
                    resultado.append(scanner.nextInt());
                    resultado.append(" resultados");
                } catch (NoSuchElementException e) {
                    resultado.append("no se han encontrado resultados");
                }
            } else {
                resultado.append(con.getResponseMessage());
            }
        } catch (IOException e) {
            resultado.append(e.getLocalizedMessage());
        } finally {
            if (con != null)
                con.disconnect();
        }
        return resultado.toString();
    }
}


class BusquedaAsyncTask extends AsyncTask<String, Void, String> {
    private final Function<String, String> tarea;
    private final ProgressDialog dialogo;

    public BusquedaAsyncTask(String progressMsg, Function<String, String> task) {
        this.tarea = task;
        dialogo = new ProgressDialog(this);
        dialogo.setCancelable(true);
        dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogo.setMessage(progressMsg);
    }

    @Override
    protected void onPreExecute() {
        dialogo.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        return tarea.apply(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        resultado.setText(s);
        dialogo.dismiss();
    }

    @Override
    protected void onCancelled() {
        dialogo.dismiss();
    }
}
}