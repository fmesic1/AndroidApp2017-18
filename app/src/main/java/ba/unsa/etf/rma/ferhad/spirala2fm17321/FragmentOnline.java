package ba.unsa.etf.rma.ferhad.spirala2fm17321;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static ba.unsa.etf.rma.ferhad.spirala2fm17321.KategorijeAkt.moja;


public class FragmentOnline extends Fragment implements DohvatiKnjige.IDohvatiKnjigeDone, DohvatiNajnovije.IDohvatiNajnovijeDone, MojResulReceiver.Receiver{

    Intent intent;
    MojResulReceiver mReceiver;


    private Spinner spinnerKategorije;
    private EditText tekst;
    private Spinner spinnerRezultat;
    private Button dPretraga;
    private Button dDodajKnjigu;
    private Button dPovratak;

    public ArrayList<Knjiga> sveKnjige;

    ArrayAdapter<String> adapterRezultata;

    @Override
    public void onDohvatiDone(ArrayList<Knjiga> knjige) {
        for(int i = 0; i < knjige.size(); i++)
            sveKnjige.add(knjige.get(i));

        ArrayList<String> kkk = new ArrayList<String>();
        for (int i = 0; i < sveKnjige.size(); i++)
            kkk.add(sveKnjige.get(i).getNaziv());

        adapterRezultata = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kkk);
        adapterRezultata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRezultat.setAdapter(adapterRezultata);
    }

    @Override
    public void onNajnovijeDone(ArrayList<Knjiga> knjige) {
        for(int i = 0; i < knjige.size(); i++)
            sveKnjige.add(knjige.get(i));

        ArrayList<String> kkk = new ArrayList<String>();
        for (int i = 0; i < sveKnjige.size(); i++)
            kkk.add(sveKnjige.get(i).getNaziv());

        adapterRezultata = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kkk);
        adapterRezultata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRezultat.setAdapter(adapterRezultata);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case KnjigePoznanika.STATUS_START:
                Toast.makeText(getActivity(), "Servis startovan.", Toast.LENGTH_SHORT).show();
                break;
            case KnjigePoznanika.STATUS_ERROR:
                Toast.makeText(getActivity(), "Desila se greska.", Toast.LENGTH_SHORT).show();
                break;
            case KnjigePoznanika.STATUS_FINISH:
                sveKnjige = resultData.getParcelableArrayList("SVE_KNJIGE_POZNANIKA");

                ArrayList<String> kkk = new ArrayList<String>();
                for (int i = 0; i < sveKnjige.size(); i++)
                    kkk.add(sveKnjige.get(i).getNaziv());

                adapterRezultata = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kkk);
                adapterRezultata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRezultat.setAdapter(adapterRezultata);
                Toast.makeText(getActivity(), "Servis je gotov sa radom.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public FragmentOnline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online, container, false);

        dPretraga = (Button)view.findViewById(R.id.dRun);
        dDodajKnjigu = (Button)view.findViewById(R.id.dAdd);
        dPovratak = (Button)view.findViewById(R.id.dPovratak);
        spinnerKategorije = (Spinner)view.findViewById(R.id.sKategorije);
        spinnerRezultat = (Spinner)view.findViewById(R.id.sRezultat);
        tekst = (EditText)view.findViewById(R.id.tekstUpit);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, moja.dajKategorije());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategorije.setAdapter(adapter);

        dPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sveKnjige = new ArrayList<Knjiga>();

                ArrayList<String> kolicina = new ArrayList<String>();

                if (tekst.getText().toString().contains("autor:")) {
                    String ovaj = tekst.getText().toString();
                    String novi = ovaj.substring(ovaj.indexOf(":")+1, ovaj.length());
                    if(novi.isEmpty())
                        Toast.makeText(getActivity(), "Navedite ime autora.", Toast.LENGTH_SHORT).show();
                    else
                        new DohvatiNajnovije((DohvatiNajnovije.IDohvatiNajnovijeDone) FragmentOnline.this).execute(novi);
                }

                else if (tekst.getText().toString().contains("korisnik:")) {
                    String ovaj = tekst.getText().toString();
                    String novi = ovaj.substring(ovaj.indexOf(":")+1, ovaj.length());
                    if (novi.isEmpty())
                        Toast.makeText(getActivity(), "Navedite id korisnika.", Toast.LENGTH_SHORT).show();
                    else {

                        intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), KnjigePoznanika.class);
                        mReceiver = new MojResulReceiver(new Handler());
                        mReceiver.setmReceiver(FragmentOnline.this);

                        intent.putExtra("idKorisnika", novi);
                        intent.putExtra("receiver", mReceiver);
                        getActivity().startService(intent);
                    }
                }

                else {
                    String novi = "";
                    for (int i = 0; i < tekst.getText().toString().length(); i++) {
                        if (tekst.getText().toString().charAt(i) != ';')
                            novi += String.valueOf(tekst.getText().toString().charAt(i));
                        else {
                            kolicina.add(novi);
                            novi = "";
                        }
                        if (i == tekst.getText().toString().length() - 1)
                            kolicina.add(novi);
                    }

                    for (int i = 0; i < kolicina.size(); i++)
                        new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone) FragmentOnline.this).execute(kolicina.get(i));
                }
            }
        });

        dPovratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();

                ListeFragment novi = new ListeFragment();

                android.support.v4.app.FragmentTransaction tr = manager.beginTransaction();
                tr.add(R.id.fragmentContainer, novi);
                tr.addToBackStack(null);
                tr.commit();

            }
        });

        dDodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter.getCount() == 0)
                {
                    Toast.makeText(getActivity(), "Kategorije ne smiju biti prazne.", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if (spinnerRezultat.getSelectedItem() == null)
                {
                    Toast.makeText(getActivity(), "Mora biti odabrana knjiga u spineru za rezultate.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String nazivKnjigeIzSpinera = spinnerRezultat.getSelectedItem().toString();

                //ZASTO SE OVO DESI JBT, OVO SE NE SMIJE DESITI..
                if(sveKnjige == null)
                {
                    Toast.makeText(getActivity(), "Nisam to za tebe mogao, rokni me care al' znas da te volim", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < sveKnjige.size(); i++)
                {
                    if(sveKnjige.get(i).getNaziv().equals(nazivKnjigeIzSpinera))
                    {
                        Knjiga kopija = sveKnjige.get(i);
                        Knjiga k = new Knjiga(kopija.getId(), kopija.getNaziv(), kopija.getAutori(), kopija.getOpis(), kopija.getDatumObjavljivanja(), kopija.getSlika(), kopija.getBrojStranica(), spinnerKategorije.getSelectedItem().toString());
                        moja.dodajKnjigu(k);
                        Toast.makeText(getActivity(), "Knjiga '" + sveKnjige.get(i).getNaziv() + "' uspjesno dodana u kategoriju '" + k.getKategorija() + "'", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });

        return view;
    }
}
