//package com.vsnapnewschool.voicesnapmessenger.Adapters
//
//import Data
//import android.content.Context
//import android.graphics.Color
//import android.graphics.PorterDuff
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import androidx.recyclerview.widget.RecyclerView
//import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
//import com.smarteist.autoimageslider.SliderAnimations
//import com.smarteist.autoimageslider.SliderView
//import com.vsnapnewschool.voicesnapmessenger.Models.SliderItem
//import com.vsnapnewschool.voicesnapmessenger.R
//import com.vsnapnewschool.voicesnapmessenger.UtilCommon.ScrollTextView
//
//
//class ParentHomeAdapter(context: Context, list: ArrayList<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    var slideadapter: ImageSliderAdapter? = null
//    var sliderItemList: MutableList<SliderItem> = ArrayList()
//    companion object {
//        const val VIEW_TYPE_ONE = 1
//        const val VIEW_TYPE_TWO = 2
//    }
//
//    private val context: Context = context
//    private val  list: ArrayList<Data> = list
//
//
//
//    private inner class View1ViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//
//        fun bind(position: Int) {
//            val recyclerViewModel = list[position]
//
//            var sliderView = itemView.findViewById<SliderView>(R.id.imageSlider)
//            slideadapter =
//                ImageSliderAdapter(
//                    context
//                )
//            sliderView.setSliderAdapter(slideadapter!!)
//            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
//            sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
//            // sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
//            // sliderView!!.indicatorSelectedColor = Color.GREEN
//            sliderView.indicatorUnselectedColor = Color.GRAY
//            sliderView.scrollTimeInSec = 2 //set scroll delay in seconds :
//            sliderView.startAutoCycle()
//            renewItems()
//
//        }
//
//        private fun renewItems() {
//            sliderItemList.clear()
//            var sliderItem = SliderItem()
//            sliderItem.setDescription("Slider Item 00")
//            sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
//            sliderItemList.add(sliderItem)
//
//
//            sliderItem = SliderItem()
//            sliderItem.setDescription("Slider Item 01")
//            sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
//            sliderItemList.add(sliderItem)
//
//            sliderItem = SliderItem()
//            sliderItem.setDescription("Slider Item 02")
//            sliderItem.setImageUrl("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
//            sliderItemList.add(sliderItem)
//
//            sliderItem = SliderItem()
//            sliderItem.setDescription("Slider Item 03")
//            sliderItem.setImageUrl("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png")
//            sliderItemList.add(sliderItem)
//
//            sliderItem = SliderItem()
//            sliderItem.setDescription("Slider Item 04")
//            sliderItem.setImageUrl("https://homepages.cae.wisc.edu/~ece533/images/boat.png")
//            sliderItemList.add(sliderItem)
//
//            slideadapter!!.renewItems(sliderItemList)
//        }
//    }
//    private inner class View2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var message: ScrollTextView = itemView.findViewById(R.id.textView2)
//        var lnrTextScroll: LinearLayout = itemView.findViewById(R.id.lnrTextScroll)
//        var lnrMenu: LinearLayout = itemView.findViewById(R.id.lnrMenu)
//
//        fun bind(position: Int) {
//            val recyclerViewModel = list[position]
//           // message.text = recyclerViewModel.textData
//
//            (lnrMenu.getBackground()).setColorFilter(Color.parseColor(list[position].bgColor), PorterDuff.Mode.SRC_IN);
//            (lnrTextScroll.getBackground()).setColorFilter(Color.parseColor(list[position].bgColor), PorterDuff.Mode.SRC_IN);
//
//            message.text = "I use it as a sort of running log, displayed to the user so they can monitor progress of a task that takes about 3 minutes. However, once I go over 8 lines, the text goes off screen. This is unintuitive to the user because they" +
//                    "Method setMovementMethod() by parameter ScrollingMovementMethod() is the gimmick." +
//                    "Although you code in Xamarin Android, but as in Java in the xxxActivity.java for terminalOutput invoke must be like this"
//            message.startScroll()
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == VIEW_TYPE_ONE) {
//            return View1ViewHolder(
//                LayoutInflater.from(context).inflate(R.layout.image_slide_main, parent, false)
//            )
//        }
//        return View2ViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.view_type_two, parent, false)
//        )
//    }
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (list[position].viewType === VIEW_TYPE_ONE) {
//            (holder as View1ViewHolder).bind(position)
//        } else {
//            (holder as View2ViewHolder).bind(position)
//        }
//    }
//    override fun getItemViewType(position: Int): Int {
//        return list[position].viewType
//    }
//}