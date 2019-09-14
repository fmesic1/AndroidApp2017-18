package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class KnjigePoznanika extends IntentService{

    static final int STATUS_START = 0;
    static final int STATUS_FINISH = 1;
    static final int STATUS_ERROR = 2;

    public  KnjigePoznanika() {
        super(null);
    }

    public KnjigePoznanika(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<Knjiga> knjige = new ArrayList<Knjiga>();

        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String idKorisnika = intent.getStringExtra("idKorisnika");

        Bundle bundle = new Bundle();

        receiver.send(STATUS_START, bundle.EMPTY);

        try {
            String url = "https://www.googleapis.com/books/v1/users/" + URLEncoder.encode(idKorisnika, "utf-8") + "/bookshelves";
            URL urll = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);

            ArrayList<String> bookshelves = new ArrayList<String>();

            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");

            for(int i = 0; i < items.length(); i++) {
                JSONObject polica = items.getJSONObject(i);
                if(polica.has("selfLink"))
                    bookshelves.add(polica.getString("selfLink"));
            }

            for(int i = 0; i < bookshelves.size(); i++) {
                url = bookshelves.get(i) + "/volumes";
                urll = new URL(url);
                urlConnection = (HttpURLConnection)urll.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                rezultat = convertStreamToString(in);

                jo = new JSONObject(rezultat);
                items = jo.getJSONArray("items");

                for(int k = 0; k < items.length(); k++) {
                    JSONObject knjiga = items.getJSONObject(k);

                    String id;
                    if(knjiga.has("id"))
                        id = knjiga.getString("id");
                    else id = null;

                    JSONObject volumeInfo = knjiga.getJSONObject("volumeInfo");

                    String naziv;
                    if(volumeInfo.has("title"))
                        naziv = volumeInfo.getString("title");
                    else naziv = "unknown";


                    ArrayList<Autor> autori = new ArrayList<Autor>();
                    if(volumeInfo.has("authors")) {
                        JSONArray authors = volumeInfo.getJSONArray("authors");
                        for (int j = 0; j < authors.length(); j++) {
                            String nazivAutora = authors.getString(j);
                            autori.add(new Autor(nazivAutora, id));
                        }
                    }
                    else
                        autori.add(new Autor("nepoznat autor", id));


                    String opis;
                    if(volumeInfo.has("description"))
                        opis = volumeInfo.getString("description");
                    else
                        opis = "No value for description";

                    String datumObjavljivanja;
                    if(volumeInfo.has("publishedDate"))
                        datumObjavljivanja = volumeInfo.getString("publishedDate");
                    else
                        datumObjavljivanja = "unknown";


                    URL urlSlika;
                    String slika = "https://i.imgur.com/YSATyE8.jpg";
                    if(volumeInfo.has("imageLinks")) {
                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        slika = imageLinks.getString("thumbnail");
                    }
                    urlSlika = new URL(slika);


                    int brojStranica;
                    if(volumeInfo.has("pageCount"))
                        brojStranica = volumeInfo.getInt("pageCount");
                    else
                        brojStranica = 0;

                    Knjiga BOOK = new Knjiga(id, naziv, autori, opis, datumObjavljivanja, urlSlika, brojStranica);
                    knjige.add(BOOK);
                }
            }

        } catch (JSONException e) {
            receiver.send(STATUS_ERROR, bundle.EMPTY);
        } catch (IOException e) {
            receiver.send(STATUS_ERROR, bundle.EMPTY);
        }

        bundle.putParcelableArrayList("SVE_KNJIGE_POZNANIKA", knjige);
        receiver.send(STATUS_FINISH, bundle);
    }



    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

}
