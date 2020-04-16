package com.chetan.allinone;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShayariFragment extends Fragment {

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    Bitmap bitmap = null;
    ImageView imageView1;

    public ShayariFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shayari, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageView1 = view.findViewById(R.id.imageView);
        mRef = FirebaseDatabase.getInstance().getReference().child("Shayari");
        AdView adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(mRef, Model.class)
                .build();
        FirebaseRecyclerAdapter<Model, ShayariFragment.DataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ShayariFragment.DataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ShayariFragment.DataViewHolder holder, final int i, @NonNull Model model) {

                final String imgUrl = model.getImage();
                Picasso.get().load(imgUrl).into(holder.imageView);
                holder.wallImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("image", imgUrl);
                        startActivity(intent);
                    }
                });
                //save image
                holder.saveImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                            } else {

                                try {
                                    bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
                                    if (bitmap != null) {

                                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

                                        File path = Environment.getExternalStorageDirectory();

                                        File dir = new File(path + "/Cartely/");

                                        dir.mkdirs();

                                        String imageName = timeStamp + ".PNG";
                                        File file = new File(dir, imageName);

                                        OutputStream out;

                                        try {

                                            out = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                            out.flush();
                                            out.close();

                                            Toast.makeText(getActivity(), imageName + "saved to" + dir, Toast.LENGTH_SHORT).show();

                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Can not load the image", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            {

                                try {
                                    bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
                                    if (bitmap != null) {

                                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

                                        File path = Environment.getExternalStorageDirectory();

                                        File dir = new File(path + "/Cartely/");

                                        dir.mkdirs();

                                        String imageName = timeStamp + ".PNG";
                                        File file = new File(dir, imageName);

                                        OutputStream out;

                                        try {

                                            out = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                            out.flush();
                                            out.close();

                                            Toast.makeText(getActivity(), imageName + "saved to" + dir, Toast.LENGTH_SHORT).show();

                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Can not load the image", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                holder.shareImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (bitmap != null) {
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());
                                bitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
                                File file = new File(getActivity().getExternalCacheDir(), "sample.png");
                                FileOutputStream fOut = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                                file.setReadable(true, false);

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                                intent.setType("image/png");
                                startActivity(Intent.createChooser(intent, "Share Via"));
                            }


                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.wallImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("image", imgUrl);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ShayariFragment.DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
                return new ShayariFragment.DataViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    return true;

                }

                return false;
            }
        });
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, saveImage, shareImage, wallImage;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            saveImage = itemView.findViewById(R.id.saveImage);
            shareImage = itemView.findViewById(R.id.shareImage);
            wallImage = itemView.findViewById(R.id.wallImage);
        }
    }
}

