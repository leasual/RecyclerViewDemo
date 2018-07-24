package com.wesoft.demo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.youth.banner.Banner

/**
 * Created by james on 2018/7/23.
 */
class ListAdapter(val dataList: List<ListDataDto>): RecyclerView.Adapter<ListAdapter.ListHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListHolder {
        context = parent!!.context
        when(viewType) {
            DATA_TYPE.BANNER.ordinal -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_banner, parent, false))
            }
            DATA_TYPE.FULL_ITEM.ordinal -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_item, parent, false))
            }
            DATA_TYPE.HALF_ITEM.ordinal -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_item, parent, false))
            }
            DATA_TYPE.HALF_BANNER_TITLE.ordinal -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_half_banner_title, parent, false))
            }
            DATA_TYPE.HALF_BANNER.ordinal -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_half_banner, parent, false))
            }
            else -> {
                return ListHolder(LayoutInflater.from(context).inflate(R.layout.listitem_banner, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: ListHolder?, position: Int) {
        holder!!.bindItem(dataList[position], position)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        when(dataList[position].type) {
            DATA_TYPE.BANNER -> return DATA_TYPE.BANNER.ordinal
            DATA_TYPE.HALF_ITEM -> return DATA_TYPE.HALF_ITEM.ordinal
            DATA_TYPE.FULL_ITEM -> return DATA_TYPE.FULL_ITEM.ordinal
            DATA_TYPE.HALF_BANNER_TITLE -> return DATA_TYPE.HALF_BANNER_TITLE.ordinal
            DATA_TYPE.HALF_BANNER -> return DATA_TYPE.HALF_BANNER.ordinal
        }
    }

    public fun updateItemView(recyclerView: RecyclerView, position: Int, bannerPos: Int) {
        val holder = recyclerView.layoutManager.findViewByPosition(position)
        Log.d("test", "position= " + position +  " holder= " + holder)
        holder?.let { setData(recyclerView.getChildViewHolder(holder) as ListHolder, dataList[position], bannerPos) }

    }

    private fun setData(listHolder: ListHolder, dataDto: ListDataDto, bannerPos: Int) {
        if (dataDto.type == DATA_TYPE.BANNER || dataDto.type == DATA_TYPE.HALF_BANNER) {
            val bannerView = listHolder.itemView.findViewById<Banner>(R.id.banner)
            Log.d("test", "banner is null= " + bannerView)
            bannerView?.setCurrentPosition(bannerPos)
        }
    }

    inner class ListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItem(model: ListDataDto,  position: Int) {
            with(model) {
                when(model.type) {
                    DATA_TYPE.BANNER -> {
                        val dataModel = model.data as List<String>
                        val banner = itemView.findViewById<Banner>(R.id.banner)
                        banner.setImages(dataModel).setImageLoader(GlideImageLoader()).start()
                        banner.isAutoPlay(false)
                    }
                    DATA_TYPE.HALF_ITEM -> {
                        val imageView = itemView.findViewById<ImageView>(R.id.iv_item_image)
                        Glide.with(context).load(model.data as String).into(imageView)
                    }
                    DATA_TYPE.FULL_ITEM -> {
                        val imageView = itemView.findViewById<ImageView>(R.id.iv_item_image)
                        val titleView = itemView.findViewById<TextView>(R.id.tv_title)
                        Glide.with(context).load(model.data as String).into(imageView)
                        if (position == 0 || dataList[position -1].type != DATA_TYPE.FULL_ITEM) {
                            titleView.visibility = View.VISIBLE
                        }else {
                            titleView.visibility = View.GONE
                        }
                    }
                    DATA_TYPE.HALF_BANNER_TITLE -> {
                        val titleView = itemView.findViewById<TextView>(R.id.tv_half_banner_title)
                        titleView.setText(model.data as String)
                    }
                    DATA_TYPE.HALF_BANNER -> {
                        val contentView = itemView.findViewById<TextView>(R.id.tv_half_banner_content)
                        val priceView = itemView.findViewById<TextView>(R.id.tv_half_banner_price)
                        val banner = itemView.findViewById<Banner>(R.id.banner)
                        val dataModel = model.data as HalfBannerDto
                        contentView.text = dataModel.content
                        priceView.text = dataModel.price
                        banner.setImages(dataModel.images).setImageLoader(GlideImageLoader()).start()
                        banner.isAutoPlay(false)
                    }
                }
            }
        }
    }
}

data class ListDataDto(
        var type: DATA_TYPE,
        var data: Any
)

data class HalfBannerDto(
        var content: String,
        var price: String,
        var images: List<String>
)

enum class DATA_TYPE {
    BANNER,
    HALF_ITEM,
    FULL_ITEM,
    HALF_BANNER_TITLE,
    HALF_BANNER
}