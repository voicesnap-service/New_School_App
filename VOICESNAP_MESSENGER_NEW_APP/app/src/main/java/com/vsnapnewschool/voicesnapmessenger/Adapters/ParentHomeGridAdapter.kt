package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentHomeMenuClickListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData
import kotlinx.android.synthetic.main.recycle_cardview.view.*

class ParentHomeGridAdapter(private val context: Context, private val mMenusList: List<MenuListData>, private val mRowLayout: Int, private val listener: parentHomeMenuClickListener) : RecyclerView.Adapter<ParentHomeGridAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return QuestionViewHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.lblMenuName?.text=mMenusList[position].name
        listener.onClick(holder,mMenusList[position])


        if(mMenusList[position].id.equals("200")){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_one)
            holder.imgIcon?.setImageResource(R.drawable.call)
        }
        else if(mMenusList[position].id?.equals("201")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_two)
            holder.imgIcon?.setImageResource(R.drawable.home_text)
        }
        else if(mMenusList[position].id?.equals("202")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_three)
            holder.imgIcon?.setImageResource(R.drawable.home_homework)
        }
        else if(mMenusList[position].id?.equals("203")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_four)
            holder.imgIcon?.setImageResource(R.drawable.home_exam)
        }
        else if(mMenusList[position].id?.equals("204")!!) {
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_five)
            holder.imgIcon?.setImageResource(R.drawable.home_exam)
        }
        else if(mMenusList[position].id?.equals("205")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_six)
            holder.imgIcon?.setImageResource(R.drawable.home_events)
        }
        else if(mMenusList[position].id?.equals("206")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_seven)
            holder.imgIcon?.setImageResource(R.drawable.home_absentees)
        }
        else if(mMenusList[position].id?.equals("207")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_eight)
            holder.imgIcon?.setImageResource(R.drawable.home_events)
        }
        else if(mMenusList[position].id?.equals("208")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_nine)
            holder.imgIcon?.setImageResource(R.drawable.home_absentees)
        }
        else if(mMenusList[position].id?.equals("209")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_ten)
            holder.imgIcon?.setImageResource(R.drawable.home_attedance)
        }
        else if(mMenusList[position].id?.equals("210")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_leven)
            holder.imgIcon?.setImageResource(R.drawable.home_image)
        }
        else if(mMenusList[position].id?.equals("211")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_twele)
            holder.imgIcon?.setImageResource(R.drawable.home_fee)
        }
        else if(mMenusList[position].id?.equals("212")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_thirteen)
            holder.imgIcon?.setImageResource(R.drawable.home_attedance)
        }
        else if(mMenusList[position].id?.equals("213")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_fourteen)
            holder.imgIcon?.setImageResource(R.drawable.home_assignment)
        }
        else if(mMenusList[position].id?.equals("214")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_fifteen)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }
        else if(mMenusList[position].id?.equals("215")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_sixteen)
            holder.imgIcon?.setImageResource(R.drawable.home_library)
        }

        else if(mMenusList[position].id?.equals("216")!!){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_menu_two)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }
    }
    override fun getItemCount(): Int {
        return mMenusList.size
    }

    class QuestionViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val lblMenuName = containerView.lblMenuName
        val rytCardMenu = containerView.ConstParent
        val imgIcon = containerView.imgIcon
    }
}
