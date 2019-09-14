package ba.unsa.etf.rma.ferhad.spirala2fm17321;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static ba.unsa.etf.rma.ferhad.spirala2fm17321.KategorijeAkt.moja;

public class KnjigeFragment extends Fragment {

    private Button povratak;
    private ListView lista;
    private ListAdapter adapter;
    private String kategorija;
    //Ovo ce mi pomoci da znam koja je lista stigla, ako je to lista da se izlistaju kategorije = 1
    //Ako je lista da se izlistaju knjige autora = 2;
    private Integer izlistaj;

    public KnjigeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_knjige, container, false);

        Bundle prekoGrane = getArguments();
        //kategorija u slucaju izlistaj = 1 je ime kategorije
        //u slucaju izlistaj = 2 je ime autora
        kategorija = prekoGrane.getString("kat");
        izlistaj = prekoGrane.getInt("lista");

        povratak = (Button)view.findViewById(R.id.dPovratak);
        lista = (ListView)view.findViewById(R.id.listaKnjiga);

        if(izlistaj == 1)
            adapter = new AdapterZaListaKnjigaAkt(getContext(), moja.dajKnjigeIzKategorije(kategorija));
        else
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, moja.dajKnjigeAutora(kategorija));
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(izlistaj == 1) {
                    Knjiga kliknuta = (Knjiga) adapterView.getItemAtPosition(i);
                    kliknuta.setSelektovana(true);
                }
                view.setBackgroundColor(getResources().getColor(R.color.plava));
            }
        });



        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });


        return view;
    }

}
