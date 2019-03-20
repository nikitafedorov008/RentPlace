package com.polka.rentplace;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.polka.rentplace.model.Products;
import com.polka.rentplace.viewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class SearchProductsActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, PopupMenu.OnMenuItemClickListener {


    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    RecyclerView.LayoutManager layoutManager;
    private String SearchInput;
    private MaterialSearchBar searchBar;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_porducts);


        int orient = getResources().getConfiguration().orientation;
        switch(orient) {
            case Configuration.ORIENTATION_LANDSCAPE:
                searchList = (RecyclerView) findViewById(R.id.search_list);
                searchList.setHasFixedSize(true);
                searchList.setLayoutManager(new GridLayoutManager(this,2));
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                searchList = (RecyclerView) findViewById(R.id.search_list);
                searchList.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                searchList.setLayoutManager(layoutManager);
                break;
            default:
        }



        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);

        searchBar.enableSearch();

        searchBar.setOnSearchActionListener(this);

//        searchBar.setOnKeyListener(new MaterialSearchBar.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_ENTER:
//                        SearchInput = searchBar.getText();
//
//                              onStart();
//                        return true;
//                    default:
//                        return SearchProductsActivity.super.onKeyUp(keyCode, event);
//                }
//            }
//        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                SearchInput = searchBar.getText();


                onStart();
                searchBar.disableSearch();
                searchBar.setPlaceHolder(SearchInput);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        searchBar.setCardViewElevation(5);
        //Inflate menu and setup OnMenuItemClickListener

//        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
//            @Override
//            public void onActionMenuItemSelected(MenuItem item) {
//                SearchInput = mSearchView.getQuery();
//
//                onStart();
//            }
//        });


//
//        SearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SearchInput = inputText.getText().toString();
//
//                onStart();
//            }
//        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(SearchInput), Products.class)
                .build();

        final FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {


                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into( holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchProductsActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };

        searchList.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_BACK:
                Toast.makeText(this, "run", Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(this, "run", Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                Toast.makeText(this, "run", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_ENTER:
//                if (event.isShiftPressed()) {
//                    SearchInput = searchBar.getText();
//                onStart();
//                }
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }

}
