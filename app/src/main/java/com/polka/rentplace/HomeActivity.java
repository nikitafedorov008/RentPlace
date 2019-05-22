package com.polka.rentplace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.polka.rentplace.model.Products;
import com.polka.rentplace.prevalent.Prevalent;
import com.polka.rentplace.utility.Font;
import com.polka.rentplace.viewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FloatingToolbar.MorphListener, FloatingToolbar.ItemClickListener, MaterialSearchBar.OnSearchActionListener {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FloatingToolbar mFloatingToolbar;
    private FloatingActionButton fab;
    private AppBarLayout appBarLayout;
    private MaterialSearchBar searchBar;
    private TextView barTextView;
    private String SearchInput;
    AwesomeBar bar;
    DrawerLayout drawer;
    Font font = new Font();

    private TextView txtItemName,txtItemPrice,txtItemTime,txtItemDecription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtItemName = (TextView) findViewById(R.id.product_name);
        txtItemPrice = (TextView) findViewById(R.id.product_price);
        txtItemTime = (TextView) findViewById(R.id.product_time);
        txtItemDecription = (TextView) findViewById(R.id.product_description);
        Font font = new Font();
        //font.setFont(getApplicationContext(),txtItemName);
        //font.setFont(getApplicationContext(),txtItemPrice);
        //font.setFont(getApplicationContext(),txtItemDecription);

        int orient = getResources().getConfiguration().orientation;
        switch(orient) {
            case Configuration.ORIENTATION_LANDSCAPE:
                recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                break;
            default:
        }



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Paper.init(this);



        drawer = findViewById(R.id.drawer_layout);
        bar = (AwesomeBar) findViewById(R.id.toolbar);
        mFloatingToolbar = findViewById(R.id.floatingToolbar);
        mFloatingToolbar.addMorphListener(this);
        mFloatingToolbar.setClickListener(this);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);

        searchBar.setVisibility(View.GONE);

        searchBar.enableSearch();

        searchBar.setOnSearchActionListener(this);

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

        fab = findViewById(R.id.fab);

        mFloatingToolbar.attachFab(fab);

        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {



            }

            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });


//        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        barTextView = (TextView) findViewById(R.id.barTextView);
//        appBarLayout.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);
        barTextView.setVisibility(View.INVISIBLE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.ic_account_circle_dark_24dp).into(profileImageView);



        bar.addAction(R.drawable.ic_search_white_48dp, "search product");
        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
               bar.setVisibility(View.INVISIBLE);
               searchBar.setVisibility(View.VISIBLE);
            }
        });

        bar.setOverflowActionItemClickListener(new AwesomeBar.OverflowActionItemClickListener() {
            @Override
            public void onOverflowActionItemClicked(int position, String item) {
                Toasty.info(getBaseContext(), item+" clicked", Toast.LENGTH_LONG).show();
            }

        });

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });

        bar.displayHomeAsUpEnabled(false);


        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.itemCart) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }else if (id == R.id.itemSearch) {
                    Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                    startActivity(intent);
                }else if (id == R.id.itemAdd) {
                    Intent intent = new Intent(HomeActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                }
            }


            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });
        mFloatingToolbar.attachRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        appBarLayout.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.INVISIBLE);

        bar.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(ProductsRef, Products.class)
                            .build();
            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                            holder.txtProductName.setText(model.getPname());
                            holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText(" Price = " + model.getPrice() + "\u20BD ");
                            holder.txtProductTime.setText( " Time = " + model.getPtime() + " days ");
                            Picasso.get().load(model.getImage()).into(holder.imageView);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
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
            recyclerView.setAdapter(adapter);
            adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            onStart();
        }else if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_products) {
            myProducts();
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(HomeActivity.this, AdminNewOrdersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_rate_us) {

            Toasty.warning(HomeActivity.this, "Sorry, this page not ready(", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


       public void myProducts(){
         bar.setVisibility(View.INVISIBLE);
//         appBarLayout.setVisibility(View.GONE);
           barTextView.setVisibility(View.VISIBLE);

           DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

           String phone = Prevalent.currentOnlineUser.getPhone();

           FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                   .setQuery(reference.orderByChild("phone").startAt(phone), Products.class)
                   .build();

           final FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                   new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                       @Override
                       protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                           holder.txtProductName.setText(model.getPname());
                           holder.txtProductDescription.setText(model.getDescription());
                           holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                           holder.txtProductTime.setText("Time = " + model.getPtime() + " days");
                           Picasso.get().load(model.getImage()).into(holder.imageView);
                           holder.itemView.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
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
           recyclerView.setAdapter(adapter);
           adapter.startListening();
       }


       public void searchProducts(){
           bar.setVisibility(View.INVISIBLE);
           searchBar.setVisibility(View.VISIBLE);
           DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

           FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                   .setQuery(reference.orderByChild("phone").startAt(SearchInput), Products.class)
                   .build();

           final FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                   new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                       @Override
                       protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                           holder.txtProductName.setText(model.getPname());
                           holder.txtProductDescription.setText(model.getDescription());
                           holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                           holder.txtProductTime.setText("Time = " + model.getPtime() + " days");
                           Picasso.get().load(model.getImage()).into(holder.imageView);
                           holder.itemView.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
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
           recyclerView.setAdapter(adapter);
           adapter.startListening();
       }


    @Override
    public void onMorphEnd() {

    }

    @Override
    public void onMorphStart() {

    }

    @Override
    public void onUnmorphStart() {

    }

    @Override
    public void onUnmorphEnd() {

    }

    @Override
    public void onItemClick(MenuItem item) {

    }

    @Override
    public void onItemLongClick(MenuItem item) {

    }


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }



}
