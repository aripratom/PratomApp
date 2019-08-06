package com.aripratom.pratomapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aripratom.pratomapp.R;


/** 10116323 - Aria Pratomo
 * IF - 8
 * 02 - Agustus 2019 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        Button btn_telp =  view.findViewById(R.id.btn_telp);
        btn_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_telp:
                        String telp = "085779775977";
                        Intent telpintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+telp));
                        startActivity(telpintent);
                        break;
                }
            }
        });

        Button btn_gmail =  view.findViewById(R.id.btn_gmail);
        btn_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_gmail:
                        String gmail = "mailto:ariapratomo@gmail.com";
                        Intent gmailintent = new Intent(Intent.ACTION_VIEW, Uri.parse(gmail));
                        startActivity(gmailintent);
                        break;
                }
            }
        });

        Button btn_line =  view.findViewById(R.id.btn_line);
        btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_line:
                        String line = "https://line.me/ti/p/5rdvdfFMQS";
                        Intent lineintent = new Intent(Intent.ACTION_VIEW, Uri.parse(line));
                        startActivity(lineintent);
                        break;
                }
            }
        });

        Button btn_wa =  view.findViewById(R.id.btn_wa);
        btn_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wa:
                        String wa = "https://api.whatsapp.com/send?phone=6281320009730";
                        Intent waintent = new Intent(Intent.ACTION_VIEW, Uri.parse(wa));
                        startActivity(waintent);
                        break;
                }
            }
        });

        Button btn_twitter =  view.findViewById(R.id.btn_twitter);
        btn_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_twitter:
                        String twitter = "https://twitter.com/aripratom";
                        Intent twitterintent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                        startActivity(twitterintent);
                        break;
                }
            }
        });

        Button btn_ig =  view.findViewById(R.id.btn_ig);
        btn_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_ig:
                        String ig = "https://instagram.com/aripratom";
                        Intent igintent = new Intent(Intent.ACTION_VIEW, Uri.parse(ig));
                        startActivity(igintent);
                        break;
                }
            }
        });

        Button btn_fb =  view.findViewById(R.id.btn_fb);
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_fb:
                        String fb = "https://facebook.com/ariapratomo";
                        Intent fbintent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb));
                        startActivity(fbintent);
                        break;
                }
            }
        });


        return view;
    }

}
