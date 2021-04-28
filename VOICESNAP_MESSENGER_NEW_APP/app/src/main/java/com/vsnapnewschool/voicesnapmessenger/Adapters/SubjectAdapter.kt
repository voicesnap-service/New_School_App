package com.vsnapnewschool.voicesnapmessenger.Adapters



import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.SubjectsData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedSubjectID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedSubjectName
class SubjectAdapter(private val imagelist: ArrayList<SubjectsData>,
                     private val context: Context?
                   ) : RecyclerView.Adapter<SubjectAdapter.MyViewHolder>() {

    private  var row_index: Int = -1
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblSelectedSubject: TextView
        init {
            lblSelectedSubject = view.findViewById<View>(R.id.lblSelectedSubject) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.assignment_subject_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {
            lblSelectedSubject.text = text_info.subject_name

            holder.lblSelectedSubject.setOnClickListener(View.OnClickListener {
                (context as BaseActivity).HideKeyboard_Fragment(context)

                row_index = position
                notifyDataSetChanged()
            })
            if (row_index == position) {
                holder.lblSelectedSubject.setBackgroundResource(R.drawable.rectangle_orange)
                lblSelectedSubject.text = text_info.subject_name

                selectedSubjectID=text_info.subject_id
                selectedSubjectName=text_info.subject_name
                holder.lblSelectedSubject.setTextColor(Color.parseColor("#FFFEFE"))
//                Toast.makeText(context,text_info.subject_name , Toast.LENGTH_LONG).show();
            } else {
                holder.lblSelectedSubject.setBackgroundResource(R.drawable.greylight_bg)
                holder.lblSelectedSubject.setTextColor(Color.parseColor("#FF000000"))
            }
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
