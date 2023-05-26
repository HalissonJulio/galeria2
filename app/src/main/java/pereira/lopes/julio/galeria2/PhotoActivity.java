package pereira.lopes.julio.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PhotoActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    String currentPhotoPath;

    private void dispathTakePictureIntent() {
        File f = null;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_SHORT).show();
            return;
        }

        currentPhotoPath = f.getAbsolutePath();

        if (f != null) {
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "pereira.lopes.julio.galeria2.fileprovider", f);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
        }
    }
    private File createImageFile() throws  IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Data());
        String imageFilmeName = "JPEG_" + timestamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, requestCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                photos.add(currentPhotoPath);

                mainAdapter.notifyItemInserted(photos.size() - 1);
            } else {
                File f = new File(currentPhotoPath);
                f.delete();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path");

        Bitmap bitmap = Utils.getBitmap(photoPath);
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater1 = getMenuInflater();
        return true;
    }

    @Override
    public boolean onptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opShare:
                sharePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sharePhoto() {

    }
}