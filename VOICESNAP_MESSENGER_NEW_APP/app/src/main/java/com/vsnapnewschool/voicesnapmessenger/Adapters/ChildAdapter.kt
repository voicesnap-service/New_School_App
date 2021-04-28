package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.ParentHomeScreen
import com.vsnapnewschool.voicesnapmessenger.Models.ChildDetails
import kotlinx.android.synthetic.main.child_list_item.view.*
class ChildAdapter(
    private val context: Context,
    private val mMenusList: List<ChildDetails>,
    private val mRowLayout: Int
) : RecyclerView.Adapter<ChildAdapter.QuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.lblChildName?.text = mMenusList[position].ChildName
        holder.lblClassName?.text = mMenusList[position].ClassName
        holder.lblRollNoName?.text = mMenusList[position].RollNo
        holder.lblSchoolName?.text = mMenusList[position].SchoolName
        holder.lblSchoolAddress?.text = mMenusList[position].SchoolAddress
        (holder.rytBackround.getBackground()).setColorFilter(Color.parseColor(mMenusList[position].BackgroundColor), PorterDuff.Mode.SRC_IN);
        holder.cardParent?.setOnClickListener {
            val changePage = Intent(context, ParentHomeScreen::class.java)
            context.startActivity(changePage)
        }
    }
    override fun getItemCount(): Int {
        return mMenusList.size
    }
    class QuestionViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val lblChildName = containerView.lblChildName
        val lblClassName = containerView.lblClassName
        val lblRollNoName = containerView.lblRollNoName
        val lblSchoolName = containerView.lblSchoolName
        val lblSchoolAddress = containerView.lblSchoolAddress
        val rytBackround = containerView.rytBackround
        val elasticView = containerView.imageElasticView
        val cardParent = containerView.cardParent
    }
}
