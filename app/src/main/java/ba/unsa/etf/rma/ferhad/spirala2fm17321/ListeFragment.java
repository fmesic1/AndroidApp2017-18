package ba.unsa.etf.rma.ferhad.spirala2fm17321;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import static ba.unsa.etf.rma.ferhad.spirala2fm17321.KategorijeAkt.moja;

public class ListeFragment extends Fragment {
    private Button dAutori;
    private Button dKategorije;
    private Button dPretraga;
    private Button dDodajKategoriju;
    private Button dDodajKnjigu;
    private EditText tekstPretraga;
    private ListView lista;
    private ArrayAdapter<String> adapter;
    private Boolean dugmeAutoriPritisnuto = false;

    public ListeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste, container, false);

        if(dugmeAutoriPritisnuto){
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, moja.dajAutoreIBrojKnjiga());
            lista.setAdapter(adapter);
        }


        dAutori = (Button)view.findViewById(R.id.dAutori);
        tekstPretraga = (EditText)view.findViewById(R.id.tekstPretraga);
        dPretraga = (Button)view.findViewById(R.id.dPretraga);
        dDodajKategoriju = (Button)view.findViewById(R.id.dDodajKategoriju);
        dKategorije = (Button)view.findViewById(R.id.dKategorije);
        lista = (ListView)view.findViewById(R.id.listaKategorija);
        dDodajKnjigu = (Button)view.findViewById(R.id.dDodajKnjigu);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, moja.dajKategorije());
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle prekoGrane = new Bundle();
                if(!dugmeAutoriPritisnuto) {
                    prekoGrane.putInt("lista", 1);
                    prekoGrane.putString("kat", moja.dajKategoriju(i));
                }
                else {
                    String autor = lista.getItemAtPosition(i).toString();
                    String[] separated = autor.split(" - ");
                    prekoGrane.putInt("lista", 2);
                    prekoGrane.putString("kat", separated[0]);
                }

                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
                FragmentTransaction tr = manager.beginTransaction();
                tr.add(R.id.fragmentContainer, new ListeFragment());
                tr.addToBackStack(null);
                tr.commit();
                FragmentTransaction n = manager.beginTransaction();
                KnjigeFragment kf = new KnjigeFragment();
                kf.setArguments(prekoGrane);
                n.replace(R.id.fragmentContainer, kf);
                n.addToBackStack(null);
                n.commit();
            }
        });

        dDodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();

                DodavanjeKnjigeFragment novi = new DodavanjeKnjigeFragment();

                //NEKIM LUDIM CUDOM OVO RADI KAKO TREBA AAAAAAAAAAAAAAAAAAA
                FragmentTransaction tr = manager.beginTransaction();
                tr.add(R.id.fragmentContainer, new ListeFragment());
                tr.addToBackStack(null);
                tr.commit();
                FragmentTransaction n = manager.beginTransaction();
                n.replace(R.id.fragmentContainer, novi);
                n.addToBackStack(null);
                n.commit();
            }
        });


        dAutori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dugmeAutoriPritisnuto = true;
                    tekstPretraga.setVisibility(View.GONE);
                    dPretraga.setVisibility(View.GONE);
                    dDodajKategoriju.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, moja.dajAutoreIBrojKnjiga());
                    lista.setAdapter(adapter);
            }
        });

        dKategorije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dugmeAutoriPritisnuto = false;
                dDodajKategoriju.setVisibility(View.VISIBLE);
                dPretraga.setVisibility(View.VISIBLE);
                tekstPretraga.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, moja.dajKategorije());
                lista.setAdapter(adapter);
            }
        });


        dPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getFilter().filter(tekstPretraga.getText().toString().trim(), new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        if (i == 0 && !tekstPretraga.getText().toString().isEmpty()) {
                            dDodajKategoriju.setEnabled(true);
                        }
                        else
                        {
                            dDodajKategoriju.setEnabled(false);
                            tekstPretraga.getText().clear();
                        }
                    }
                });
            }
        });

        dDodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.add(tekstPretraga.getText().toString());
                adapter.getFilter().filter(tekstPretraga.getText());
                moja.dodajKategoriju(tekstPretraga.getText().toString());
                dDodajKategoriju.setEnabled(false);
                tekstPretraga.setText("");
                Toast.makeText(getActivity(), "Kategorija uspjesno dodana", Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter("");
            }
        });

        tekstPretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dDodajKategoriju.setEnabled(false);
            }
        });

        return view;
    }

}
