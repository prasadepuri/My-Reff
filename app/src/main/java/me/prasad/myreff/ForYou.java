package me.prasad.myreff;

import android.app.Application;
import android.app.slice.SliceProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForYou extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView recyclerVieww;
    public ArrayList<AppData> appData = new ArrayList<>();
    //public ArrayList<NewData> newData=new ArrayList<>();
    RecyclerView.Adapter radapter;
    RecyclerView.LayoutManager layoutManager;
    Context contextt;
    public MyReffRoomRepository myReffRoomRepository;
    public int clicks=0;

    public ForYou() {
        ;

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_for_you, container, false);
        // FirebaseApp.initializeApp(getContext());
        myReffRoomRepository = new MyReffRoomRepository(getActivity().getApplication());
        recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
       appData.clear();
       clicks++;
        final SwipeRefreshLayout pullToRefresh =rootView.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int i=0;
                i++;
                Toast.makeText(getContext(),"pull count"+i,Toast.LENGTH_SHORT).show();
               // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        //loadRecycleView();
        setListnerForRecevingNewData();
        if(clicks<=1) {
            Toast.makeText(getContext(),"pull count click"+clicks,Toast.LENGTH_SHORT).show();
           // setListnerForRecevingNewData();
            //loadData();
        }

        return rootView;
    }

    private void setListnerForRecevingNewData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("newslinks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    if (doc.getType().equals(DocumentChange.Type.ADDED)) {

                        Log.d("info of process", doc.getDocument().getData().toString());
                        String title, category, url, imageurl;
                        long timestamp = (long) (doc.getDocument().getData().get("timestamp"));
                        title = (String) (doc.getDocument().getData().get("title"));
                      //  Toast.makeText(getContext(), title + "firebase", Toast.LENGTH_LONG).show();
                        category = (String) (doc.getDocument().getData().get("category"));
                        imageurl = (String) (doc.getDocument().getData().get("image url"));
                        url = (String) (doc.getDocument().getData().get("link"));
                           /* if (!(new DataFileHandling(MainActivity.this).checkIfRecordExist(url)))
                            {
                                new DataFileHandling(MainActivity.this).files(url,title,description);
                            }
                            else
                            {
                                Log.d("duplicate record",url);
                            }*/

                        formatDataAndWriteIntoSqliteDataBase(title, url, category, timestamp, imageurl);

                    }
                }
                loadDataToRecylceview();

            }
        });

    }

    private void loadDataToRecylceview() {
        readFromSqLiteDataBase();
    }

    private void readFromSqLiteDataBase() {
        List<MyReff> myReferences = new ArrayList<MyReff>();
        List<HashMap<String, Object>> myReferenceList = new ArrayList<>();
        try {
            myReferences = new GetUsersAsyncTask().execute().get();
            for (MyReff ref : myReferences) {
                String title = ref.getTitle();
                String link = ref.getUrl();
                String category = ref.getCategory();
                String date = (String) converToNormalTime(ref.getTimestamp());
                long timestamp = ref.getTimestamp();
               // Toast.makeText(getContext(), title + "sqlite", Toast.LENGTH_LONG).show();
                String imageurl = ref.getImageurl();
                /*Write data of sqlite database into RecycleView data content*/
                writeintoAppData(title, link, category, date, timestamp, imageurl);
            }


            //Finally we loaded the data into recycle view***


            loadRecycleView();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return myReferenceList;
    }

    private void writeintoAppData(String title, String link, String category, String date, long timestamp, String imageurl) {
        appData.add(new AppData(title, link, date, category, imageurl, timestamp));
    }

    private Object converToNormalTime(long timestamp) {
        //convert seconds to milliseconds
        Date date = new Date(timestamp);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        //jdf.setTimeZone(TimeZone.getDefault());
        String java_date = jdf.format(date);
        System.out.println("\n" + java_date + "\n");
        return java_date;
    }


    private void formatDataAndWriteIntoSqliteDataBase(String title, String url, String category, long timestamp, String imageurl) {
        MyReff myReff = new MyReff(title, url, category, timestamp, imageurl);
        myReffRoomRepository.insert(myReff);

        //Toast.makeText(getContext(), "Data inserted into table", Toast.LENGTH_LONG).show();
    }

    private class GetUsersAsyncTask extends AsyncTask<Void, Void, List<MyReff>> {
        @Override
        protected List<MyReff> doInBackground(Void... url) {
            return myReffRoomRepository.getAllData();

        }
    }

    private void loadRecycleView() {
        layoutManager = new LinearLayoutManager(getActivity());
        radapter = new Adapter(appData, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(radapter);

    }

    private void loadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("newslinks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title, category, url, imageurl;
                                long timestamp = (long) document.getData().get("timestamp");
                                title = (String) document.getData().get("title");
                                Toast.makeText(getContext(), title + "firebase", Toast.LENGTH_LONG).show();
                                category = (String) document.getData().get("category");
                                imageurl =(String)  document.getData().get("imageurl");
                                url = (String) document.getData().get("link");
                                formatDataAndWriteIntoSqliteDataBase(title, url, category, timestamp, imageurl);

                            }
                        loadDataToRecylceview();
                        } else {
                            Log.d("Status of firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
