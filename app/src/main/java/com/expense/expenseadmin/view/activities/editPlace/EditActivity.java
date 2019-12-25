package com.expense.expenseadmin.view.activities.editPlace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.expense.expenseadmin.R;
import com.expense.expenseadmin.Utilities.AppUtils;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.view.adapters.AddLocationsRecAdapter;
import com.expense.expenseadmin.view.adapters.EditPhotosRecAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.common.util.IOUtils.copyStream;

public class EditActivity extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMyLocationChangeListener, EditPlaceView, EditActivityListener {

    private static final String IMAGE_FOLDER_NAME = "Expense";
    @BindView(R.id.edit_activity_place_add_location_fab)
    FloatingActionButton addLocationFab;

    @BindView(R.id.edit_activity_place_add_photo_fab)
    FloatingActionButton addPhoto;

    @BindView(R.id.edit_activity_place_select_category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.edit_activity_place_title_edit_text)
    EditText nameET;

    @BindView(R.id.edit_activity_place_phone_number_text)
    EditText phoneNoET;

    @BindView(R.id.edit_activity_place_description_edit_text)
    EditText descriptionET;

    @BindView(R.id.edit_activity_place_website_edit_text)
    EditText websiteET;

    @BindView(R.id.edit_activity_place_facebook_url_et)
    EditText facebookUrlET;

    @BindView(R.id.edit_activity_place_twitter_edit_text)
    EditText twitterUrlET;

    @BindView(R.id.edit_activity_place_instagram_edit_text)
    EditText instagramUrlET;

    @BindView(R.id.edit_activity_place_photos_rv)
    RecyclerView mPhotosRV;

    @BindView(R.id.edit_activity_place_locations_rv)
    RecyclerView mLocationsRV;

    @BindView(R.id.edit_activity_place_post_ad_button)
    Button mCreatePlaceButton;

    @BindView(R.id.add_place_locations_bottom_sheet)
    CardView mBottomSheetLayout;

    @BindView(R.id.map_activity_add_location)
    Button mConfirmLocation;

    @BindView(R.id.map_activity_undo_ic)
    ImageView mUndoMarker;

    @BindView(R.id.map_activity_close_ic)
    ImageView mCloseBottomSheet;

    @BindView(R.id.map_activity_city_edit_text)
    EditText mCityET;

    @BindView(R.id.map_activity_street_edit_text)
    EditText mStreetET;

    @BindView(R.id.map_activity_countries_spinner)
    Spinner mCountrySpinner;

    @BindView(R.id.edit_activity_place_toolbar)
    Toolbar mToolbar;

    private static final String TAG = "AddPlaceFragment";
    private static final String PNG = ".png";
    private static final int ACCESS_LOCATION = 1;
    private static final int REQUEST_CODE_TAKE_PICTURE = 2;
    private static final int RESULT_LOAD_IMG = 3;
    private EditActivity mCurrent;
    private LatLng mSelectedLocation = null;
    private GoogleMap mMap;

    private BottomSheetBehavior bottomSheetBehavior;
    private EditPhotosRecAdapter mAddPhotosRecAdapter;
    private AddLocationsRecAdapter mAddLocationsRecAdapter;
    private ArrayList<LocationModel> locationModels;
    private ArrayList<File> images;
    private ArrayList<String> imageUrls;
    private EditPlacePresenter mPresenter;

    private File mFileTemp;
    private PlaceModel placeModel;

    private MaterialDialog mProgressDlg;
    private Location mCurrentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private String oldCategory;

    @Override
    protected void onStart() {
        super.onStart();
        try {
            initLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        try {
            mCurrent = EditActivity.this;
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {

            readPlaceFromIntent();

            ButterKnife.bind(this);

            initToolbar();
            initPhotosRV();

            initLocationsRV();

            initMap();

            initBottomSheet();

            setViewsWithData();

            mPresenter = new EditPlacePresenter(mCurrent, this);
            addPhoto.setOnClickListener(this);
            addLocationFab.setOnClickListener(this);
            mConfirmLocation.setOnClickListener(this);
            mUndoMarker.setOnClickListener(this);
            mCloseBottomSheet.setOnClickListener(this);
            mCreatePlaceButton.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToolbar() {
        try {
            String title = getString(R.string.edit_place_nav_lbl).concat(" ").concat(placeModel.getName());
            mToolbar.setTitle(title);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViewsWithData() {
        try {
            setSpinnerWithSelectedCategory();

            nameET.setText(placeModel.getName());
            phoneNoET.setText(placeModel.getPhoneNumber());

            if (placeModel.getFacebookUrl() != null && !placeModel.getFacebookUrl().matches("none")) {
                facebookUrlET.setText(placeModel.getFacebookUrl());
            }
            if (placeModel.getInstagramUrl() != null && !placeModel.getInstagramUrl().matches("none")) {
                instagramUrlET.setText(placeModel.getInstagramUrl());
            }

            if (placeModel.getTwitterUrl() != null && !placeModel.getTwitterUrl().matches("none")) {
                twitterUrlET.setText(placeModel.getTwitterUrl());
            }

            if (placeModel.getWebsiteUrl() != null && !placeModel.getWebsiteUrl().matches("none")) {
                websiteET.setText(placeModel.getWebsiteUrl());
            }

            descriptionET.setText(placeModel.getDescription());
            locationModels.addAll(placeModel.getLocationModels());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerWithSelectedCategory() {
        try {
            String[] categories = getResources().getStringArray(R.array.categories);

            for (int i = 0; i < categories.length; i++) {
                if (placeModel.getCategory().matches(categories[i])) {
                    categorySpinner.setSelection(i);
                }
            }

            oldCategory = placeModel.getCategory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLocation() {
        try {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                mCurrentLocation = location;
                                zoomToCurrentLocation();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zoomToCurrentLocation() {
        try {
            if (mMap != null) {
                if (mCurrentLocation != null) {
                    Log.i(TAG, "onMapReady(): lat = " + mCurrentLocation.getLatitude() + " Lang = " + mCurrentLocation.getLongitude());
                    LatLng newLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 12));
                } else {
                    Log.i(TAG, "onMapReady(): location is null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readPlaceFromIntent() {
        try {
            placeModel = getIntent().getParcelableExtra(getString(R.string.place_intent_lbl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMap() {
        try {
            if (ActivityCompat.checkSelfPermission(mCurrent, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mCurrent, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mCurrent, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION);
            } else {
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
                } else {
                    Log.i(TAG, "initMap(): mapFragment = null");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBottomSheet() {
        try {

            bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLocationsRV() {
        try {
            locationModels = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(mCurrent, RecyclerView.HORIZONTAL, false);
            mAddLocationsRecAdapter = new AddLocationsRecAdapter(mCurrent, locationModels);
            mLocationsRV.setLayoutManager(layoutManager);
            mLocationsRV.setAdapter(mAddLocationsRecAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPhotosRV() {
        try {
            images = new ArrayList<>();
            imageUrls = placeModel.getImagesURL();
            LinearLayoutManager layoutManager = new LinearLayoutManager(mCurrent, RecyclerView.HORIZONTAL, false);
            mAddPhotosRecAdapter = new EditPhotosRecAdapter(images, imageUrls, mCurrent, this);
            mPhotosRV.setLayoutManager(layoutManager);
            mPhotosRV.setAdapter(mAddPhotosRecAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(addLocationFab)) {
            try {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.equals(mConfirmLocation)) {
            try {
                confirmAddLocation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.equals(mCloseBottomSheet)) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (v.equals(mUndoMarker)) {
            undoMarker();
        } else if (v.equals(addPhoto)) {
            if (ActivityCompat.checkSelfPermission(mCurrent, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mCurrent, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            } else if (ActivityCompat.checkSelfPermission(mCurrent, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mCurrent, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            } else {
                showImageChoiceDlg();
            }
        } else if (v.equals(mCreatePlaceButton)) {
            editPlace();
        }
    }

    private void showImageChoiceDlg() {
        try {
            MaterialDialog mChooseInputDlg = AppUtils.showAlertDialogWithCustomView(mCurrent, R.layout.image_input_dlg);
            View view = mChooseInputDlg.getCustomView();
            if (view != null) {
                Button cameraBtn = view.findViewById(R.id.image_input_dlg_camera);
                Button galleryBtn = view.findViewById(R.id.image_input_dlg_gallery);

                cameraBtn.setOnClickListener(e -> {
                    takePicture();
                    mChooseInputDlg.dismiss();
                });
                galleryBtn.setOnClickListener(e -> {
                    openGallery();
                    mChooseInputDlg.dismiss();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        try {
            createFile();
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFileTemp));
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPlace() {
        if (categorySpinner.getSelectedItemPosition() == 0) {
            AppUtils.showToast(mCurrent, getString(R.string.please_choose_category));
        } else if (nameET.getText() == null || nameET.getText().toString().isEmpty()) {
            nameET.setError(getString(R.string.required));
        } else if (phoneNoET.getText() == null || phoneNoET.getText().toString().isEmpty()) {
            phoneNoET.setError(getString(R.string.required));
        } else if (descriptionET.getText() == null || descriptionET.getText().toString().isEmpty()) {
            descriptionET.setError(getString(R.string.required));
        } else if (images.isEmpty() && imageUrls != null && imageUrls.isEmpty()) {
            AppUtils.showToast(mCurrent, getString(R.string.please_choose_images));
        } else if (locationModels.isEmpty()) {
            AppUtils.showToast(mCurrent, getString(R.string.select_location_first));
        } else {

            String category = categorySpinner.getSelectedItem().toString();
            String name = nameET.getText().toString();
            String phoneNo = phoneNoET.getText().toString();
            String description = descriptionET.getText().toString();
            String facebookUrl = "";
            String twitterUrl = "";
            String websiteUrl = "";

            if (facebookUrlET.getText() != null && !facebookUrlET.getText().toString().isEmpty()) {
                facebookUrl = facebookUrlET.getText().toString();
            }

            if (twitterUrlET.getText() != null && !twitterUrlET.getText().toString().isEmpty()) {
                twitterUrl = twitterUrlET.getText().toString();
            }

            if (websiteET.getText() != null && !websiteET.getText().toString().isEmpty()) {
                websiteUrl = websiteET.getText().toString();
            }

            placeModel.setCategory(category);
            placeModel.setName(name);
            placeModel.setPhoneNumber(phoneNo);
            placeModel.setDescription(description);
            placeModel.setFacebookUrl(facebookUrl);
            placeModel.setTwitterUrl(twitterUrl);
            placeModel.setWebsiteUrl(websiteUrl);
            placeModel.setLocationModels(locationModels);


            mProgressDlg = AppUtils.showProgressDialog(mCurrent, getString(R.string.saving));

            if (mPresenter != null) {
                mPresenter.editPlace(placeModel, images, imageUrls, oldCategory);
            }
        }
    }

    private void takePicture() {
        try {

            createFile();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoURI = FileProvider.getUriForFile(mCurrent, getString(R.string.app_name), mFileTemp);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PICTURE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFile() {
        try {
            Date d = new Date();
            String TEMP_PHOTO_FILE_NAME = "IMG_" + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.ENGLISH).format(d) + PNG;

            File rootFolder = new File(Environment.getExternalStorageDirectory(), IMAGE_FOLDER_NAME);

            if (!rootFolder.exists()) {
                if (rootFolder.mkdir()) {
                    Log.i(TAG, "createFile(): rootFolder created");
                } else {
                    Log.i(TAG, "createFile(): rootFolder not created");
                }
            }

            mFileTemp = new File(rootFolder, TEMP_PHOTO_FILE_NAME);
            Log.i(TAG, "createFile(): mFileTemp path = " + mFileTemp.getPath());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void undoMarker() {
        try {
            if (mSelectedLocation != null) {
                if (mMap != null) {
                    mMap.clear();
                    mUndoMarker.setVisibility(View.GONE);
                } else {
                    AppUtils.showToast(mCurrent, getString(R.string.select_location_first));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmAddLocation() {
        try {

            if (mCountrySpinner.getSelectedItemPosition() == 0) {
                AppUtils.showToast(mCurrent, getString(R.string.please_choose_country));
            } else if (mCityET.getText() == null || mCityET.getText().toString().isEmpty()) {
                mCityET.setError(getString(R.string.required));
            } else if (mStreetET.getText() == null || mStreetET.getText().toString().isEmpty()) {
                mStreetET.setError(getString(R.string.required));
            } else if (mSelectedLocation == null) {
                AppUtils.showToast(mCurrent, getString(R.string.select_location_first));
            } else {
                String country = mCountrySpinner.getSelectedItem().toString();
                String city = mCityET.getText().toString();
                String street = mStreetET.getText().toString();
                double lat = mSelectedLocation.latitude;
                double lang = mSelectedLocation.longitude;

                LocationModel locationModel = new LocationModel();
                locationModel.setCountry(country);
                locationModel.setCity(city);
                locationModel.setStreet(street);
                locationModel.setLatitude(lat);
                locationModel.setLongitude(lang);

                AppUtils.showConfirmationDialog(mCurrent, getString(R.string.are_you_sure_with_this_location), getString(R.string.yes), getString(R.string.cancel), new AppUtils.CallBack() {
                    @Override
                    public void OnPositiveClicked(MaterialDialog dlg) {
                        locationModels.add(locationModel);
                        mAddLocationsRecAdapter.notifyDataSetChanged();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        dlg.dismiss();
                    }

                    @Override
                    public void OnNegativeClicked(MaterialDialog dlg) {
                        dlg.dismiss();
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        try {
            mSelectedLocation = latLng;
            mUndoMarker.setVisibility(View.VISIBLE);
            mMap.clear();
            // Add a marker in Sydney and move the camera
            mMap.addMarker(new MarkerOptions().position(latLng).title("X = " + latLng.latitude + " Y = " + latLng.longitude));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;

//            // Add a marker in Sydney and move the camera
//            if (placeModel != null){
//                if (placeModel.getLocationModels() != null && !placeModel.getLocationModels().isEmpty()){
//                    if (placeModel.getLocationModels().get(0) != null){
//
//                    }
//                }
//            }
//            LatLng sydney = new LatLng(placeModel.getLocationModels().get(0).getLatitude(), placeModel.getLocationModels().get(0).getLongitude());
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(this);
            mMap.setOnMapClickListener(this);

            if (mMap.getMyLocation() != null)
                mCurrentLocation = mMap.getMyLocation();

            zoomToCurrentLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_LOCATION) {
            Log.i(TAG, "onRequestPermissionsResult(): Access Location Granted");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (mFileTemp != null) {
                File newFile = compressImage(mFileTemp.getPath(), mCurrent);
                images.add(newFile);
                mAddPhotosRecAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == RESULT_LOAD_IMG) {
            try {
                if (data != null) {
                    try {
                        InputStream inputStream = mCurrent.getContentResolver().openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);

                        copyStream(inputStream, fileOutputStream);

                        fileOutputStream.close();
                        inputStream.close();
                        File newFile = compressImage(mFileTemp.getPath(), mCurrent);

                        images.add(newFile);
                        mAddPhotosRecAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            writeBitmapInFile(bitmap);
//                            startCropImage();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void writeBitmapInFile(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mFileTemp);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_place_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.delete_place) {
            showDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        try {
            String message = getString(R.string.delete).concat(" ").concat(placeModel.getName()).concat(" ?");
            AppUtils.showConfirmationDialog(mCurrent, message, getString(R.string.delete), getString(R.string.cancel), new AppUtils.CallBack() {
                @Override
                public void OnPositiveClicked(MaterialDialog dlg) {
                    dlg.dismiss();
                    mProgressDlg = AppUtils.showProgressDialog(mCurrent, getString(R.string.deleting));
                    mPresenter.deletePlace(placeModel);
                }

                @Override
                public void OnNegativeClicked(MaterialDialog dlg) {
                    dlg.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    private static File compressImage(String filePath, Context context) {
        File mImageFile = null;

        try {

//        String filePath = getRealPathFromURI(imageUri, context);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }

//      setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
//          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            try {
                Canvas canvas = new Canvas(Objects.requireNonNull(scaledBitmap));
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
            } catch (Exception e) {
                e.printStackTrace();
            }
//      check the rotation of the image and display it properly
            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                        true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream out = null;

            File file = getImageFile();
            String filename = getFilename();

            mImageFile = new File(file.getPath(), filename);

            try {
                out = new FileOutputStream(mImageFile);

//          write the compressed bitmap at the destination specified by filename.
                Objects.requireNonNull(scaledBitmap).compress(Bitmap.CompressFormat.JPEG, 80, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mImageFile;
    }

    private static File getImageFile() {
        File mediaStorageDir = null;

        try {

            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), IMAGE_FOLDER_NAME);

            if (!mediaStorageDir.exists())
                mediaStorageDir.mkdir();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaStorageDir;
    }

    private static String getFilename() {
        String uriSting = null;


        try {
            Date d = new Date();
            uriSting = "IMG_Compressed_" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.ENGLISH).format(d) + ".png";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return uriSting;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = Math.min(heightRatio, widthRatio);
        }

        return inSampleSize;
    }

    @Override
    public void onEditPlace(boolean status) {

        if (mProgressDlg != null) {
            mProgressDlg.dismiss();
        }
        if (status) {
            AppUtils.showToast(mCurrent, getString(R.string.place_updated_successfully));
        } else {
            AppUtils.showToast(mCurrent, getString(R.string.error_adding_place));
        }

        finish();
    }

    @Override
    public void onDeletePlace(boolean status) {
        if (mProgressDlg != null) {
            mProgressDlg.dismiss();
        }

        if (status) {
            AppUtils.showToast(mCurrent, getString(R.string.place_deleted_successfully));
            finish();
        } else {
            AppUtils.showToast(mCurrent, getString(R.string.failed_to_delete_check_network));
        }
    }

    private void resetViews() {
        try {
            mCountrySpinner.setSelection(0);
            mCityET.setText("");
            mStreetET.setText("");
            nameET.setText("");
            phoneNoET.setText("");
            facebookUrlET.setText(getString(R.string.facebook_url));
            twitterUrlET.setText(getString(R.string.twitter_lbl));
            websiteET.setText(getString(R.string.website_lbl));
            descriptionET.setText("");
            images.clear();
            mAddPhotosRecAdapter.notifyDataSetChanged();
            locationModels.clear();
            mAddLocationsRecAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemovePhoto(int position) {

    }

    @Override
    public void onRemovePlace(int position) {

    }
}
