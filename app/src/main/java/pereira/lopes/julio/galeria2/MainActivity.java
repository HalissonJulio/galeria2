package pereira.lopes.julio.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> photos = new ArrayList<>();

    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setContentView(R.layout.activity_main);

        File dir = getExternalFilesDir(Enviroment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles();
        for(int i = 0; i < files.length; i++) {
            photos.add(files[i].getAbsolutePath());
        }

        mainAdapter = new MainAdapter(MainActivity.this, photos);

        RecyclerView rvGallery = findViewById(R.id.rvGallery = findViewById(R.id.rvGallery));
        rvGallery.setAdapter(mainAdapter);

        float w = getResources().getDimension(R.dimen.itemWidth);
        int numberOFColumns =  Utils.calculateNoOfColums(MainActivity.this, w);
        GridLayoutManager(MainActivity.this, numberOFColumns);
        rvGallery.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater1 = getMenuInflater();
        return true;
    }
    @Override
    public boolean onOptionsItemsSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opCamera:
                dispatchTakePictureIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}