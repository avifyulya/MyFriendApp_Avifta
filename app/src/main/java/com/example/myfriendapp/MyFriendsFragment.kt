package com.example.myfriendapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MyFriendsFragment : Fragment() {
    private lateinit var fabAddFriend : FloatingActionButton
    private lateinit var listTeman : MutableList<MyFriend>
    private lateinit var listMyFriends: RecyclerView
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null
    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_fragment,
            container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }
    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }
    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)!!
        fabAddFriend.setOnClickListener { (activity as
                MainActivity).tampilMyFriendsAddFragment() }
        ambilDataTeman()
        simulasiDataTeman()
        tampilTeman()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    private fun ambilDataTeman(){
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity()){ r ->  listTeman = r as MutableList<MyFriend>
            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        }
    }
    private fun simulasiDataTeman(){
        listTeman = ArrayList()
        (listTeman as ArrayList<MyFriend>).add(MyFriend(1,"Avif","Perempuan", "avifyulya23@gmail.com", "082230422105", "Jalan Nganjuk"))
    }
    private fun tampilTeman(){
        listMyFriends = activity?.findViewById(R.id.listMyFriends)!!
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        listMyFriends.adapter = MyFriendAdapter(requireActivity(), listTeman!! as ArrayList<MyFriend>)
    }
    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message,
            Toast.LENGTH_SHORT).show()
    }
}