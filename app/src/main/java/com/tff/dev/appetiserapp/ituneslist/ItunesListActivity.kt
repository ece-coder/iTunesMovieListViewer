package com.tff.dev.appetiserapp.ituneslist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.tff.dev.appetiserapp.App
import com.tff.dev.appetiserapp.R
import com.tff.dev.appetiserapp.room.ItunesData
import com.tff.dev.appetiserapp.trackdetails.TrackDetailsActivity
import com.tff.dev.appetiserapp.util.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_itunes_list.*


//-------------------------------------------------------------------------------------
// For ItunesListActivity, MVVM architectural pattern is used to have a more efficient
// separation between UI and business logic. It also supports data binding to have a more
// flexibility in UI. In addition to that, aslo utilizes viewmodel that interacts between
// model layer and control the view by exposing the states of the data
//-------------------------------------------------------------------------------------

/**
 * Displays the list of iTunes Movies from database and/or API
 * with SearchView to search iTunes Movies according to their title
 */

class ItunesListActivity : AppCompatActivity() {

    private var itunesViewModel: ItunesListViewModel? = null
    val subscriptions = CompositeDisposable()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    private var adapter: ItunesListDataAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itunes_list)


        var actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)

        // Initializing recycler view
        layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        itunes_recycler.layoutManager = layoutManager

        // Intializing viewmodel
        itunesViewModel = ItunesListViewModel.create(this)
        App.appComponent.inject(itunesViewModel!!)

        adapter = ItunesListDataAdapter { track: ItunesData -> ituneItemClickListener(track) }
        itunes_recycler.adapter = adapter

        mHandler = Handler()


        //------------------------------------------------------------------------------
        // Using SwipeRefreshLayout, swiping down the screen will trigger API call.
        // By doing that, it minimizes the use of Http requests since the list is stored
        // in the database. It will avoid use of unnecessary mobile data for the app.
        // The iTunesAPi will be triggered 1 second after swiping.
        //------------------------------------------------------------------------------

        swipe_layout.setOnRefreshListener {
            mRunnable = Runnable {
                getItunesListUsingInternet()
                swipe_layout.isRefreshing = false
            }

            mHandler.postDelayed(
                mRunnable,
                (1000).toLong() // Delay 1 second
            )

        }

        //--------------------------------------------------------------------------------
        // Persistence Mechanism: (to restore the last screen)
        //
        // Using SharedPreference, the ref page will be stored to keep track of the screen
        // so it can be restored if the app closes accidentally. Every time the app will
        // start, it will check if there's a stored ref number, meaning that page must be
        // restored.
        //--------------------------------------------------------------------------------


        //--------------------------------------------------------------------------------
        // This is how the page reference number was used.
        // if ref  = 0, it means the last page before shutdown is ItunesListActivity page.
        // if ref = 1, it means the last page is the first item in the database and so on.
        //--------------------------------------------------------------------------------

        var ref = itunesViewModel!!.getPageRef()

        if(ref != 0){
            var track = itunesViewModel!!.getItuneTrack(ref)!!  // getting the iTunes details from the ref number
            ituneItemClickListener(track) //trigger loading the last page to restore the session
        }

    }


    private var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        this.menu = menu
        val search = menu!!.findItem(R.id.list_filter)
        val searchView = search.actionView as SearchView
        search(searchView)
        return true
    }

    /**
     * Method to use the string from SearchView in order to filter the iTunes list
     *
     * @param searchView the reference SearchView
     */

    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.list_filter){

        }
        return true
    }


    override fun onStart() {
        super.onStart()

        //--------------------------------------------------------------------------------
        // Persistence Mechanism: (to save data to be reused)
        //
        // Using Room persistence, once the API is called, it will get the data and store
        // it in a database. So when the user open the app, it will check if the databse
        // is empty and there is internet connection. If it's empty and have internet
        // connection, it will get the list using internet and store it to the databse.
        // Otherwise, it will just get the list from the database and display it.
        //
        // This persistence mechanism may support offline mode for the users in order to
        // have a better user experience. It may also save the use of mobile data for loading
        // same data again.
        //--------------------------------------------------------------------------------

        if((Helper.isNetworkAvailable(this)) && (itunesViewModel!!.checkIfDbIsEmpty()) ){
            getItunesListUsingInternet()
        }else{
            getItunesListUsingDB()
        }



    }


    private fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }

    //------------------------------------------------------
    //RxJava and Room are used to store data in the database
    //------------------------------------------------------


    /**
     * Call Api and store in a database and display in the
     * RecyclerView
     */

    fun getItunesListUsingInternet(){
        subscribe(itunesViewModel!!.getItunesListRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                subscribe(itunesViewModel!!.getItunesFinalList(it!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        adapter!!.setItunesList(it)
                        adapter!!.notifyDataSetChanged()
                    }
                )
            }, {
                Log.e("ERROR", "ERR-MESSAGE " + it.toString())
            }
            )
        )
    }

    /**
     * Get list of iTunes data from the database and
     * display in the RecyclerView
     */

    fun getItunesListUsingDB(){
        subscribe(itunesViewModel!!.getItunesFinalList(null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter!!.setItunesList(it)
                adapter!!.notifyDataSetChanged()
            },{
                Log.e("ERROR", "ERR-MESSAGE " + it.toString())
            }
            )
        )
    }

    //-------------------------------------------------------------------------------------------
    // It is the listener used when clicking the item to show detail view (TracKDetailsActivity).
    // The date and time is also stored in the database when clicking to track when the user last
    // visited the page.
    //-------------------------------------------------------------------------------------------


    /**
     * Gets the current date and time, and store in the database. Then get the ref number of the
     * parameter before opening the TrackDetailsActivity screen
     *
     * @param track iTunesData  from the item that was clicked
     */

    private fun ituneItemClickListener(track : ItunesData){
        var date  = Helper.getDate()
        itunesViewModel!!.updateDb(track, date)  //Stores date and time and update the corresponding item int the database
        adapter!!.notifyDataSetChanged()

        itunesViewModel!!.sotrePageRef(track.counter)  //Stores screen or page ref number using SharedPreference

        Helper.trackDetails = track
        val intent =   Intent(baseContext, TrackDetailsActivity::class.java)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        itunesViewModel!!.sotrePageRef(0)
        if(menu != null) {
            val search = menu?.findItem(R.id.list_filter)
            val searchView = search?.actionView as SearchView
            searchView.isIconified = true
        }
    }

}
