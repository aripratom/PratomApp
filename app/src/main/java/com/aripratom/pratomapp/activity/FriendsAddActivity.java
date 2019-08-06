package com.aripratom.pratomapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aripratom.pratomapp.R;
import com.aripratom.pratomapp.db.FriendsHelper;
import com.aripratom.pratomapp.entity.Friends;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public class FriendsAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etNim, etNama, etKelas, etTelepon, etEmail, etSosmed;
    private Button btnSubmit;

    public static final String EXTRA_FRIENDS = "extra_friends";
    public static final String EXTRA_POSITION= "extra_position";

    private boolean isEdit = false;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;


    private Friends friends;
    private int position;

    private FriendsHelper friendsHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_add);
        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etKelas = findViewById(R.id.et_kelas);
        etTelepon = findViewById(R.id.et_telepon);
        etEmail = findViewById(R.id.et_email);
        etSosmed = findViewById(R.id.et_sosmed);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        friendsHelper = FriendsHelper.getInstance(getApplicationContext());

        friends = getIntent().getParcelableExtra(EXTRA_FRIENDS);
        if (friends != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            friends = new Friends();
        }

        String actionBarTitle;
        String btnTitle;

        if(isEdit){
            actionBarTitle = "Ubah";
            btnTitle = "Update";

            if (friends != null){
                etNim.setText(friends.getNim());
                etNama.setText(friends.getNama());
                etKelas.setText(friends.getKelas());
                etTelepon.setText(friends.getTelepon());
                etEmail.setText(friends.getEmail());
                etSosmed.setText(friends.getSosmed());
            }
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){
            String nim = etNim.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();
            String telepon = etTelepon.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String sosmed = etSosmed.getText().toString().trim();

            if (TextUtils.isEmpty(nim)) {
                etNim.setError("Field can not be blank");
                return;
            }
            if (TextUtils.isEmpty(nama)) {
                etNama.setError("Field can not be blank");
                return;
            }
            if (TextUtils.isEmpty(kelas)) {
                etKelas.setError("Field can not be blank");
                return;
            }
            if (TextUtils.isEmpty(telepon)) {
                etTelepon.setError("Field can not be blank");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Field can not be blank");
                return;
            }
            if (TextUtils.isEmpty(sosmed)) {
                etSosmed.setError("Field can not be blank");
                return;
            }
            friends.setNim(nim);
            friends.setNama(nama);
            friends.setKelas(kelas);
            friends.setTelepon(telepon);
            friends.setEmail(email);
            friends.setSosmed(sosmed);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FRIENDS, friends);
            intent.putExtra(EXTRA_POSITION, position);

            if (isEdit){
                long result = friendsHelper.updateFriends(friends);
                if (result > 0){
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else {
                    Toast.makeText(FriendsAddActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            }else {

                long result = friendsHelper.insertFriends(friends);

                if (result > 0){
                    friends.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(FriendsAddActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else{
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Teman";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose){
                            finish();
                        } else {
                            long result = friendsHelper.deleteFriends(friends.getId());
                            if (result > 0){
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(FriendsAddActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
