package com.wesoft.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wesoft.demo.databinding.FragmentListBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by james on 2018/7/23.
 */
class ListFragment : Fragment() {
    private lateinit var mBinding: FragmentListBinding
    private var dataList: ArrayList<ListDataDto> = ArrayList()
    private lateinit var adapter: ListAdapter
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    companion object {
        fun newInstance(title: String): ListFragment {
            val fragment = ListFragment()
            val arg = Bundle()
            arg.putString("title", title)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        setupViews()
        return mBinding.root
    }

    private fun setupViews() {
        val manager = GridLayoutManager(context, 2)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val data = dataList[position]
                var type = 1
                if (data.type == DATA_TYPE.BANNER) {
                    type = 2
                } else if (data.type == DATA_TYPE.FULL_ITEM) {
                    type = 2
                } else if(data.type == DATA_TYPE.HALF_ITEM) {
                    type = 1
                } else if (data.type == DATA_TYPE.HALF_BANNER_TITLE) {
                    type = 2
                } else if (data.type == DATA_TYPE.HALF_BANNER) {
                    type = 1
                }
                return type
            }
        }
        manager.spanSizeLookup = spanSizeLookup
        mBinding.rvList.layoutManager = manager
        createDataList()
        adapter = ListAdapter(dataList)
        mBinding.rvList.adapter = adapter
        setupTimer()
    }

    private fun createDataList() {
        var imageList = ArrayList<String>()
        imageList.add("http://img5.imgtn.bdimg.com/it/u=3252774957,880379066&fm=27&gp=0.jpg")
        imageList.add("http://f10.baidu.com/it/u=3250680277,1257668285&fm=72")
        imageList.add("http://img0.imgtn.bdimg.com/it/u=3093674761,1038215453&fm=27&gp=0.jpg")
        imageList.add("http://f10.baidu.com/it/u=3250680277,1257668285&fm=72")
        var model = ListDataDto(DATA_TYPE.BANNER, imageList)
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_ITEM, "http://img5.imgtn.bdimg.com/it/u=3559113226,124122343&fm=27&gp=0.jpg")
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_ITEM, "http://img5.imgtn.bdimg.com/it/u=81037530,1775446013&fm=27&gp=0.jpg")
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.FULL_ITEM, "http://img.taopic.com/uploads/allimg/121229/235059-12122921022065.jpg")
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.FULL_ITEM, "http://img3.imgtn.bdimg.com/it/u=2952989042,3699947507&fm=27&gp=0.jpg")
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER_TITLE, "优惠推介")
        dataList.add(model)
        imageList = ArrayList<String>()
        imageList.add("http://img5.imgtn.bdimg.com/it/u=3252774957,880379066&fm=27&gp=0.jpg")
        imageList.add("http://f10.baidu.com/it/u=3250680277,1257668285&fm=72")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2952989042,3699947507&fm=27&gp=0.jpg")
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("美味三文鱼", "$73", imageList))
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("水果小点心", "$40", imageList))
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("果味吐司", "$63", imageList))
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("果味吐司", "$23", imageList))
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("寿司", "$43", imageList))
        dataList.add(model)
        model = ListDataDto(DATA_TYPE.HALF_BANNER, HalfBannerDto("咖啡", "$23", imageList))
        dataList.add(model)

    }

    private fun setupTimer() {
        Log.d("test", "setupTimer")
        dataList.mapIndexed { index, listDataDto ->
            Log.d("test", "for each" + (listDataDto.type == DATA_TYPE.BANNER))
            if (listDataDto.type == DATA_TYPE.HALF_BANNER || listDataDto.type == DATA_TYPE.BANNER) {
                compositeDisposable.add(Observable.interval(0, 3, TimeUnit.SECONDS)
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe (
                                {
                                    var size = 0
                                    if (listDataDto.type == DATA_TYPE.HALF_BANNER) {
                                        size = (listDataDto.data as HalfBannerDto).images.size
                                    } else if (listDataDto.type == DATA_TYPE.BANNER) {
                                        size = (listDataDto.data as List<String>).size
                                    }
                                    val position = Random().nextInt(size)
                                    Log.d("test", "timer tick" + position)
                                    adapter.updateItemView(mBinding.rvList, index, position)
                                },
                                {
                                    Log.e("test", "error= " + it.printStackTrace())
                                }
                        )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}