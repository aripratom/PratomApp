package com.aripratom.pratomapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public class Friends implements Parcelable {
    private int id;
    private String nim;
    private String nama;
    private String kelas;
    private String telepon;
    private String email;
    private String sosmed;

    public Friends(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSosmed() {
        return sosmed;
    }

    public void setSosmed(String sosmed) {
        this.sosmed = sosmed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nim);
        dest.writeString(nama);
        dest.writeString(kelas);
        dest.writeString(telepon);
        dest.writeString(email);
        dest.writeString(sosmed);
    }

    protected Friends(Parcel in) {
        id = in.readInt();
        nim = in.readString();
        nama = in.readString();
        kelas = in.readString();
        telepon = in.readString();
        email = in.readString();
        sosmed = in.readString();
    }

    public static final Creator<Friends> CREATOR = new Creator<Friends>() {
        @Override
        public Friends createFromParcel(Parcel in) {
            return new Friends(in);
        }

        @Override
        public Friends[] newArray(int size) {
            return new Friends[size];
        }
    };

}
