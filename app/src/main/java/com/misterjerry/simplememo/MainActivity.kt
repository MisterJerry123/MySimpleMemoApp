package com.misterjerry.simplememo

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.misterjerry.simplememo.databinding.ActivityMainBinding
import com.misterjerry.simplememo.db.Memo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: MemoSharedViewModel by viewModels()
    private var interstitialAd: InterstitialAd? = null
    private var appOpenAd: AppOpenAd? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //광고 초기화 및 사용
        initadv()
        loadAppOpeningAd()


        val intent = Intent(this, AddMemoActivity::class.java)
        val adapter = MemoAdapter()
        sharedViewModel.memoList.observe(this) { memos ->
            adapter.submitList(memos)
            if (memos.isEmpty()) {
                binding.floTop.visibility = View.INVISIBLE
                binding.rcvMemo.visibility = View.INVISIBLE
                binding.tvMemoEmptyMsg.visibility = View.VISIBLE
            } else {
                binding.rcvMemo.visibility = View.VISIBLE
                binding.floTop.visibility = View.VISIBLE

                binding.tvMemoEmptyMsg.visibility = View.INVISIBLE
            }
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val memo = adapter.currentList[position]
                // ViewModel에 삭제 요청
                sharedViewModel.delete(memo)
            }

        }
        //

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvMemo)

        binding.btnAdd.setOnClickListener {
            startActivity(intent)
            finish()
        }

        val recyclerView = binding.rcvMemo

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(memo: Memo) {
                val detailMemoFragment = DetailMemoFragment.newInstance(memo)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fcv, detailMemoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        observDeleteSignal(adapter)

    }

    private fun observDeleteSignal(adapter: MemoAdapter) {
        sharedViewModel.deleteMemo.observe(this) { memo ->
            memo?.let {
                supportFragmentManager.popBackStack()
                adapter.deleteMemo(memo)
            }
        }
    }
    private fun initadv() {
        MobileAds.initialize(this) { initializationStatus ->
            // 초기화가 완료되었을 때 실행되는 콜백입니다.
            val statusMap = initializationStatus.adapterStatusMap
            for (adapterStatus in statusMap.values) {
                Log.d(
                    "AdMob",
                    "Adapter ${adapterStatus.description}: ${adapterStatus.initializationState}"
                )
            }
            Log.d("AdMob", "Initialization complete.")
        }
    }
    private fun taskcompleteAd() {
        InterstitialAd.load(
            this,
            BuildConfig.ADMOB_TASKCOMPLETEADV_UNIT_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    interstitialAd = ad
                    interstitialAd?.show(this@MainActivity)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    interstitialAd = null
                }
            },
        )
    }
    private fun loadAppOpeningAd() {
        AppOpenAd.load(
            this,
            BuildConfig.ADMOB_APPOPENINGADV_UNITID_UNIT_ID,
            AdRequest.Builder().build(),
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Log.d(TAG, "Ad was loaded.")
                    appOpenAd = ad
                    appOpenAd?.show(this@MainActivity)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    appOpenAd = null
                }
            },
        )
    }
}