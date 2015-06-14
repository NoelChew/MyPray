package noelc.mypray.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import noelc.mypray.R;

/**
 * Created by noelchew on 6/13/15.
 */
public class CreatePrayEventActivity extends ActionBarActivity {

    private ImageView ivImagePlaceholder;
    private EditText etTitle;
    private TextView tvDate, tvCategory;
    private Button btnCreateEvent;


    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pray_event);
        setToolbar();
        setView();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        ImageView ivLeft = (ImageView) toolbar.findViewById(R.id.image_view_left);
        ImageView ivRight = (ImageView) toolbar.findViewById(R.id.image_view_right);
        ivRight.setVisibility(View.GONE);

        tvTitle.setText(getString(R.string.app_name));

        ivLeft.setImageResource(R.drawable.mypray_back);
        ivLeft.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setSupportActionBar(toolbar);

    }

    private void setView() {
        ivImagePlaceholder = (ImageView) findViewById(R.id.image_view_create_pray_event);
        EditText etTitle = (EditText) findViewById(R.id.edit_text_title);
        tvDate = (TextView) findViewById(R.id.text_view_date);
        tvCategory = (TextView) findViewById(R.id.text_view_category);
        btnCreateEvent = (Button) findViewById(R.id.button_create);

        ivImagePlaceholder.setOnClickListener(ivPlaceHolderOnClickListener);
        tvDate.setOnClickListener(tvDateOnClickListener);
        tvCategory.setOnClickListener(tvCategoryOnClickListener);
        btnCreateEvent.setOnClickListener(btnCreateOnClickListener);
    }

    private View.OnClickListener ivPlaceHolderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startDialog();
        }
    };

    private View.OnClickListener tvDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int tmpMonth = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            final int month = tmpMonth - 1;

            // display calendar
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                   CreatePrayEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(year);
                    tvDate.setText(date);
                }
            }, year, month, day);
            datePickerDialog.show();

        }
    };

    private View.OnClickListener tvCategoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final CharSequence[] eventList = {"Birthday Anniversary", "Death Anniversary"};

            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePrayEventActivity.this);
            builder.setTitle("Select a category")
                    .setItems(eventList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvCategory.setText(eventList[which]);
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();

        }
    };

    private View.OnClickListener btnCreateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePrayEventActivity.this);
            builder.setTitle("Confirm Event Creation?")
                    .setMessage("The admin will assign a package and remind you before the event.\nPress CONFIRM to proceed.")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreatePrayEventActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(CreatePrayEventActivity.this, "Pray event submitted.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    };

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                Intent.ACTION_GET_CONTENT, null);
                        pictureActionIntent.setType("image/*");
                        pictureActionIntent.putExtra("return-data", true);
                        startActivityForResult(pictureActionIntent,
                                GALLERY_PICTURE);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(pictureActionIntent,
                                CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    // our BitmapDrawable for the thumbnail
                    BitmapDrawable bmpDrawable = null;
                    // try to retrieve the image using the data from the intent
                    Cursor cursor = getContentResolver().query(data.getData(),
                            null, null, null, null);
                    if (cursor != null) {

                        cursor.moveToFirst();

                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        String fileSrc = cursor.getString(idx);
                        bitmap = BitmapFactory.decodeFile(fileSrc); // load
                        // preview
                        // image
                        bitmap = Bitmap.createScaledBitmap(bitmap,
                                100, 100, false);
                        // bmpDrawable = new BitmapDrawable(bitmapPreview);
                        ivImagePlaceholder.setImageBitmap(bitmap);
                    } else {

                        bmpDrawable = new BitmapDrawable(getResources(), data
                                .getData().getPath());
                        ivImagePlaceholder.setImageDrawable(bmpDrawable);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("data")) {

                    // retrieve the bitmap from the intent
                    bitmap = (Bitmap) data.getExtras().get("data");


                    Cursor cursor = getContentResolver()
                            .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    new String[]{
                                            MediaStore.Images.Media.DATA,
                                            MediaStore.Images.Media.DATE_ADDED,
                                            MediaStore.Images.ImageColumns.ORIENTATION},
                                    MediaStore.Images.Media.DATE_ADDED, null, "date_added ASC");
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            Uri uri = Uri.parse(cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA)));
                            selectedImagePath = uri.toString();
                        } while (cursor.moveToNext());
                        cursor.close();
                    }

//                    Log.e("path of the image from camera ====> ",
//                            selectedImagePath);


                    bitmap = Bitmap.createScaledBitmap(bitmap, 100,
                            100, false);
                    // update the image view with the bitmap
                    ivImagePlaceholder.setImageBitmap(bitmap);
                } else if (data.getExtras() == null) {

                    Toast.makeText(getApplicationContext(),
                            "No extras to retrieve!", Toast.LENGTH_SHORT)
                            .show();

                    BitmapDrawable thumbnail = new BitmapDrawable(
                            getResources(), data.getData().getPath());

                    // update the image view with the newly created drawable
                    ivImagePlaceholder.setImageDrawable(thumbnail);

                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }


}
