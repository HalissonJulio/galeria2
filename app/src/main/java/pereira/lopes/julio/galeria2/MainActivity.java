package pereira.lopes.julio.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> photos = new ArrayList<>();

    MainAdapter mainAdapter;

    static int RESULT_TAKE_PICTURE = 1;
    static int RESULT_REQUEST_PERMISSION = 2;


    String currentPhotoPath;

    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions) {
            if( !hasPermission(permission) {
                permissionsNotGranted.add(permission);
            }
        }

        if(Build.VERSION.SDK_INT >= BuildVERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()],RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean hasPermission(String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return
                    ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void setResultRequestPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {

            for(String permission : permissions) {
                if(!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar essa app é conceder essas permissões").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }).create().show();

                        }
                    }
                }
            }
        }
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

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);

            checkForPermissions(permissions);
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

    public void startPhotoActivity(String photopath) {
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        i.putExtra("photo_path", photopath);
        startActivity(i);
    }
}