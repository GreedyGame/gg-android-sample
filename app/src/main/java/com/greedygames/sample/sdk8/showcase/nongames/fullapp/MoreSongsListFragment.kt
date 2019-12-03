package com.greedygames.sample.sdk8.showcase.nongames.fullapp
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.greedygames.sample.sdk8.BaseActivity
//import com.greedygames.sample.sdk8.R
//import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
//import com.greedygames.sample.sdk8.showcase.nongames.adapters.MoreSongsData
//import com.greedygames.sample.sdk8.showcase.nongames.loadAd
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.fragment_more_songs_list.*
//
//private const val SONGS_ITEM = "SONGS_ITEM"
//
//class MoreSongsListFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: MoreSongsData? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getParcelable(SONGS_ITEM)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_more_songs_list, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        if(param1?.listItem == ItemTypes.CONTENT) {
//            title.text = param1?.title
//            Picasso.with(context).load(param1?.cover).into(coverImage)
//        }
//        else{
//            container.visibility = View.GONE
//            context?.let {
//                adUnit.loadAd(param1?.adUnitId?:"",BaseActivity.mGreedyGameAgent,it,(it as Activity),false)
//            }
//
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: MoreSongsData) =
//            MoreSongsListFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(SONGS_ITEM, param1)
//                }
//            }
//    }
//}
