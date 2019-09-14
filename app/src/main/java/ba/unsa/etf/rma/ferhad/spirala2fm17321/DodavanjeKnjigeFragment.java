package ba.unsa.etf.rma.ferhad.spirala2fm17321;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static ba.unsa.etf.rma.ferhad.spirala2fm17321.KategorijeAkt.moja;

public class DodavanjeKnjigeFragment extends Fragment {
    private Button dUpisiKnjigu;
    private Button dPonisti;
    private Button nadjiSliku;
    private Bitmap slika;
    private ImageView naslovna;

    private String selectedImage;
    public static int GALLERY_REQUEST = 1;


    public DodavanjeKnjigeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);

        dUpisiKnjigu = (Button)view.findViewById(R.id.dUpisiKnjigu);
        dPonisti = (Button)view.findViewById(R.id.dPonisti);
        nadjiSliku = (Button)view.findViewById(R.id.dNadjiSliku);
        naslovna = (ImageView)view.findViewById(R.id.naslovnaStr);

        final EditText autor = (EditText)view.findViewById(R.id.imeAutora);
        final EditText knjiga = (EditText)view.findViewById(R.id.nazivKnjige);
        final Spinner spiner = (Spinner)view.findViewById(R.id.sKategorijaKnjige);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, moja.dajKategorije());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);

        dUpisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autor.getText().toString().isEmpty() && !knjiga.getText().toString().isEmpty() && !spiner.getSelectedItem().toString().isEmpty()) {
                    final Knjiga k = new Knjiga(autor.getText().toString(), knjiga.getText().toString(), spiner.getSelectedItem().toString(), slika);
                    moja.dodajKnjigu(k);
                    Toast.makeText(getActivity(), "Knjiga " + knjiga.getText().toString() + " je uspjesno dodana!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Ne smije biti praznih polja.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dPonisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });

        nadjiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && GALLERY_REQUEST == requestCode){
            if(data.getData() != null && !data.getData().equals(Uri.EMPTY)){
                try {
                    Uri uri = data.getData();
                    slika = getBitmapFromUri(uri);
                    naslovna.setImageBitmap(slika);
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Ne mmoze je pretvorit u Bitmap", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(getActivity(), "Prazan URI", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getActivity(), "Naslovna nije odabrana", Toast.LENGTH_SHORT).show();
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
