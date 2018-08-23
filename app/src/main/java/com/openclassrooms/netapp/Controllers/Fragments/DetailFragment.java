package com.openclassrooms.netapp.Controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.netapp.Models.GithubUserInfo;
import com.openclassrooms.netapp.R;
import com.openclassrooms.netapp.Utils.GithubStreams;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";

    // FOR DESIGN
    @BindView(R.id.textView_detail_nbFollowed) TextView textView_detail_nbFollowed;
    @BindView(R.id.textView_detail_nbFollow) TextView textView_detail_nbFollow;
    @BindView(R.id.textView_detail_nbRepo) TextView textView_detail_nbRepo;
    @BindView(R.id.textView_detail_name) TextView textView_detail_name;
    @BindView(R.id.imageView_detail_photo) ImageView imageView_detail_photo;

    //FOR DATA
    private Disposable disposableFirst;
    private Disposable disposableDeuz;

    public DetailFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the layout of MainFragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String login = bundle.getString("login");
            this.executeHttpRequestWithRetrofit(login);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(String login){
        this.disposableFirst = GithubStreams.streamFetchUserInfos(login).subscribeWith(new DisposableObserver<GithubUserInfo>() {
            @Override
            public void onNext(GithubUserInfo githubUserInfo) {
                //Log.i(TAG, "welcome user : "+ githubUserInfo.getLogin());

                try{
                    Log.i(TAG, "nb follow user : "+ githubUserInfo.getFollowers());
                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }

                updateUi(githubUserInfo);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposableFirst != null && !this.disposableFirst.isDisposed()) this.disposableFirst.dispose();
    }

    private void updateUi(GithubUserInfo githubUserInfo){
        this.textView_detail_name.setText(githubUserInfo.getLogin());
        this.textView_detail_nbFollow.setText(githubUserInfo.getFollowers().toString());
        this.textView_detail_nbFollowed.setText(githubUserInfo.getFollowing().toString());
        this.textView_detail_nbRepo.setText(githubUserInfo.getPublicRepos().toString());

        Glide.with(this).load(githubUserInfo.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(imageView_detail_photo);
    }

}
