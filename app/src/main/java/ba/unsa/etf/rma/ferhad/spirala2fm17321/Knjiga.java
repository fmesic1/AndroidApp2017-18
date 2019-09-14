package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Console;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Knjiga implements Parcelable{
    private String id;
    private String naziv;
    private ArrayList<Autor> autori;
    private String opis;
    private String datumObjavljivanja;
    private URL slika;
    private int brojStranica;
    private boolean selektovana;
    private String kategorija;
    private Bitmap naslovna;

    protected Knjiga(Parcel in) {
        id = in.readString();
        naziv = in.readString();
        if (in.readByte() == 0x01) {
            autori = new ArrayList<Autor>();
            in.readList(autori, Autor.class.getClassLoader());
        } else {
            autori = null;
        }
        opis = in.readString();
        datumObjavljivanja = in.readString();
        slika = (URL) in.readValue(URL.class.getClassLoader());
        brojStranica = in.readInt();
        selektovana = in.readByte() != 0x00;
        kategorija = in.readString();
        naslovna = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    public Knjiga(String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja, URL slika, int brojStranica){
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
        this.opis = opis;
        this.datumObjavljivanja = datumObjavljivanja;
        this.slika = slika;
        this.brojStranica = brojStranica;
        this.selektovana = false;
        this.kategorija = null;
        this.naslovna = null;
    }

    public Knjiga(String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja, URL slika, int brojStranica, String kategorija){
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
        this.opis = opis;
        this.datumObjavljivanja = datumObjavljivanja;
        this.slika = slika;
        this.brojStranica = brojStranica;
        this.selektovana = false;
        this.kategorija = kategorija;
        this.naslovna = null;
    }

    public Knjiga(String imeAutora, String nazivKnjige, String kategorija, Bitmap slika){
        Random r = new Random();
        int Low = 1;
        int High = 10000;
        int Result = r.nextInt(High-Low) + Low;

        id = nazivKnjige + Integer.toString(Result);
        this.autori = new ArrayList<Autor>();
        autori.add(new Autor(imeAutora, id));
        this.naziv = nazivKnjige;
        this.opis = "No value for description";
        this.datumObjavljivanja = "unknown";
        this.slika = null;
        this.brojStranica = 0;
        this.kategorija = kategorija;
        this.selektovana = false;
        this.naslovna = slika;
    }

    public Bitmap getNaslovna(){return naslovna;}
    public void setNaslovna(Bitmap slika){this.naslovna = naslovna;}

    public String getKategorija() {return kategorija;}
    public void setKategorija(String kategorija) {this.kategorija = kategorija;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Autor> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autor> autori) {
        this.autori = autori;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(String datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public URL getSlika() {
        return slika;
    }

    public void setSlika(URL slika) {
        this.slika = slika;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }



    public boolean daLiJeSelektovana(){
        if (selektovana) return true;
        return false;
    }
    public void setSelektovana(boolean selektovana){
        this.selektovana = selektovana;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(naziv);
        if (autori == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(autori);
        }
        dest.writeString(opis);
        dest.writeString(datumObjavljivanja);
        dest.writeValue(slika);
        dest.writeInt(brojStranica);
        dest.writeByte((byte) (selektovana ? 0x01 : 0x00));
        dest.writeString(kategorija);
        dest.writeValue(naslovna);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Knjiga> CREATOR = new Parcelable.Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel in) {
            return new Knjiga(in);
        }

        @Override
        public Knjiga[] newArray(int size) {
            return new Knjiga[size];
        }
    };
}

