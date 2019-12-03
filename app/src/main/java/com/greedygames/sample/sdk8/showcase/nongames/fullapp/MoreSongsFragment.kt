package com.greedygames.sample.sdk8.showcase.nongames.fullapp
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.greedygames.sample.sdk8.R
//import com.greedygames.sample.sdk8.showcase.nongames.adapters.MoreSongsAdapter
//import kotlinx.android.synthetic.main.fragment_more_songs.*
//
//
//class MoreSongsFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_more_songs, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        moreSongsViewpager.adapter = MoreSongsAdapter(childFragmentManager)
//
//    }
//
//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MoreSongsFragment()
//    }
//}
