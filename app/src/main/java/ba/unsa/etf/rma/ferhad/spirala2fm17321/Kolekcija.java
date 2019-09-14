package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.renderscript.Sampler;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Kolekcija {

    private ArrayList<Knjiga> knjige;
    private ArrayList<String> kategorije;


    public Kolekcija()
    {
        knjige = new ArrayList<Knjiga>();
        kategorije = new ArrayList<String>();
        /*
        kategorije.add("Prva Klasa");
        kategorije.add("Prije ponoci");
        kategorije.add("Poslije 2h");
        knjige.add(new Knjiga("Jovan Perisic", "Sunce se Radja", "Prva Klasa", null));
        knjige.add(new Knjiga("Bane", "Samo pijan mogu", "Prva Klasa", null));
        knjige.add(new Knjiga("Saban Saulic", "Ima pravde, ima Boga, ja opet ljubim, ti nemas koga", "Prva Klasa", null));
        knjige.add(new Knjiga("Mile Boooktee", "Kraljica Trotoara", "Prva Klasa", null));
        knjige.add(new Knjiga("Djani", "Jos te sanjam, jos me misli vuku, kako prsten stavljam ti na ruku", "Prva Klasa", null));
        knjige.add(new Knjiga("Dejan Matic", "Bitanga i Dama", "Prije ponoci", null));
        knjige.add(new Knjiga("GILE", "ZASTO SAM SAM", "Poslije 2h", null));
        knjige.add(new Knjiga("Kebica bebica", "Ti hodas sa njom", "Prije ponoci", null));
        knjige.add(new Knjiga("Kebica bebica", "ONA TO ZNA", "Prije ponoci", null));
        knjige.add(new Knjiga("Kebica bebica", "Poomoozi kukaaaviciii da umreee muuskii", "Poslije 2h", null));
        autori.add("Jovan Perisic");
        autori.add("Saban Saulic");
        autori.add("Mile Boooktee");
        autori.add("Djani");
        autori.add("DejanM");
        autori.add("GILE");
        autori.add("Kebica bebica");
        */
    }

    public void dodajKategoriju(String kategorija){
        kategorije.add(kategorija);
    }
    public String dajKategoriju(int kat){
        return kategorije.get(kat);
    }

    public void dodajKnjigu(Knjiga k){
        knjige.add(k);
    }

    public ArrayList<String> dajKategorije(){
        return kategorije;
    }
    public ArrayList<Knjiga> dajKnjige(){
        return knjige;
    }
    public ArrayList<String> dajAutore(){
        ArrayList<String> autori = new ArrayList<String>();
        for(int i = 0; i < knjige.size(); i++)
        {
            for(int j = 0; j < knjige.get(i).getAutori().size(); j++)
            {
                if (!autori.contains(knjige.get(i).getAutori().get(j).getImeiPrezime()))
                    autori.add(knjige.get(i).getAutori().get(j).getImeiPrezime());
            }
        }
        return autori;
    }

    public ArrayList<Knjiga> dajKnjigeIzKategorije(String kategorija){
        ArrayList<Knjiga> novi = new ArrayList<Knjiga>();
        for(int i = 0; i < knjige.size(); i++)
            if(knjige.get(i).getKategorija().equals(kategorija))
                novi.add(knjige.get(i));
        return novi;
    }

    public int dajIndeksSelektovaneKnjige(String kategorija){
        for(int i = 0; i < knjige.size(); i++){
            if(knjige.get(i).getKategorija().equals(kategorija) && knjige.get(i).daLiJeSelektovana())
                return i;
        }
        return 0;
    }

    public ArrayList<String> dajAutoreIBrojKnjiga(){

        ArrayList<String> vrati = new ArrayList<String>();

        ArrayList<String> iskoristeni = new ArrayList<String>();;

        for(int i = 0; i < knjige.size(); i++)
        {
            for(int j = 0; j < knjige.get(i).getAutori().size(); j++)
            {
                if(!iskoristeni.contains(knjige.get(i).getAutori().get(j).getImeiPrezime()))
                {
                    int brojKnjiga = dajBrojKjiga(knjige.get(i).getAutori().get(j));
                    String novi = knjige.get(i).getAutori().get(j).getImeiPrezime() + " - " + String.valueOf(brojKnjiga);
                    iskoristeni.add(knjige.get(i).getAutori().get(j).getImeiPrezime());
                    vrati.add(novi);
                }
            }
        }
        return vrati;
    }

    private int dajBrojKjiga(Autor a)
    {
        int autorNapisaoKnjiga = 0;
        for (int i = 0; i < knjige.size(); i++)
        {
            for(int j = 0; j < knjige.get(i).getAutori().size(); j++)
                if (knjige.get(i).getAutori().get(j).getImeiPrezime().equals(a.getImeiPrezime()))
                    autorNapisaoKnjiga++;
        }
        return autorNapisaoKnjiga;
    }

    public ArrayList<String> dajKnjigeAutora(String ime) {
        ArrayList<String> povratni = new ArrayList<String>();
        for (int i = 0; i < knjige.size(); i++)
        {
            for (int j = 0; j < knjige.get(i).getAutori().size(); j++)
                if (knjige.get(i).getAutori().get(j).getImeiPrezime().equals(ime))
                    povratni.add(knjige.get(i).getNaziv());
        }
        return povratni;
    }

        public String dajAutora(int i){
        return "Kolekcija.dajAutora() moze da sjebe cijeli koncept ove nove kolekcije jer su autori poredani i vraca i-tog a ovako to nece bas ici";
        //return autori.get(i);
    }

}
