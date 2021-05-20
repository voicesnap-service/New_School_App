package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.schoolHomeMenuClicklistener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ABSENTEES_REPORT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ATTEDANCE_MARKING
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_CONFERENCE_CALL
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_EMERGENCY
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_EVENTS
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_FEEDBACK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_IMGAE_PDF
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_IMPORTANT_INFO
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_INTRACTION_WITH_STUDENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_LEAVE_REQUESTS
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_MESSAGES_FROM_MANAGEMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_NOTICEBOARD
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ONLINE_TEXT_BOOK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_OTHER_SERVICES
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_PDF_UPLOAD
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_SCHEDULE_EXAM
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_SCHOOL_STRENGTH
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VIDEO
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE_HOMEWORK
import kotlinx.android.synthetic.main.recycle_cardview.view.*

class SchoolHomeGridAdapter(private val context: Context, private val mMenusList: List<MenuListData>, private val mRowLayout: Int, private val listener: schoolHomeMenuClicklistener) : RecyclerView.Adapter<SchoolHomeGridAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return QuestionViewHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.lblMenuName?.text=mMenusList[position].name
        listener.onClick(holder,mMenusList[position])


        if(mMenusList[position].id.equals(MENU_EMERGENCY.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.call)
        }
        else if(mMenusList[position].id?.equals(MENU_VOICE.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.call)
        }
        else if(mMenusList[position].id?.equals(MENU_TEXT.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_text)
        }
        else if(mMenusList[position].id?.equals(MENU_VOICE_HOMEWORK.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_homework)
        }
        else if(mMenusList[position].id?.equals(MENU_TEXT_HOMEWORK.toString())) {
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_homework)
        }
        else if(mMenusList[position].id?.equals(MENU_ATTEDANCE_MARKING.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_attedance)
        }
        else if(mMenusList[position].id?.equals(MENU_ABSENTEES_REPORT.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_absentees)
        }
        else if(mMenusList[position].id?.equals(MENU_IMGAE_PDF.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_image)
        }
        else if(mMenusList[position].id?.equals(MENU_SCHEDULE_EXAM.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_exam)
        }
        else if(mMenusList[position].id?.equals(MENU_NOTICEBOARD.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_absentees)
        }
        else if(mMenusList[position].id?.equals(MENU_EVENTS.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_events)
        }
        else if(mMenusList[position].id?.equals(MENU_SCHOOL_STRENGTH.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_absentees)
        }
        else if(mMenusList[position].id?.equals(MENU_FEEDBACK.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_attedance)
        }
        else if(mMenusList[position].id?.equals(MENU_IMPORTANT_INFO.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.call)
        }
        else if(mMenusList[position].id?.equals(MENU_ASSIGNMENT.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_assignment)
        }
        else if(mMenusList[position].id?.equals(MENU_VIDEO.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }

        else if(mMenusList[position].id?.equals(MENU_MESSAGES_FROM_MANAGEMENT.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }
        else if(mMenusList[position].id?.equals(MENU_OTHER_SERVICES.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }
        else if(mMenusList[position].id?.equals(MENU_INTRACTION_WITH_STUDENT.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_video)
        }
        else if(mMenusList[position].id?.equals(MENU_LEAVE_REQUESTS.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_attedance)
        }
        else if(mMenusList[position].id?.equals(MENU_ONLINE_TEXT_BOOK.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_library)
        }

        else if(mMenusList[position].id?.equals(MENU_PDF_UPLOAD.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.home_calendar)
        }
        else if(mMenusList[position].id?.equals(MENU_CONFERENCE_CALL.toString())){
            holder.rytCardMenu?.setBackgroundResource(R.drawable.rect_white_bg)
            setRectangaleShape(holder,mMenusList[position].color_code)
            holder.imgIcon?.setImageResource(R.drawable.call)
        }
    }

    private fun setRectangaleShape(holder: QuestionViewHolder, colorCode: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            holder.rytCardMenu?.background?.colorFilter = BlendModeColorFilter(Color.parseColor(colorCode), BlendMode.SRC_ATOP)
        }
        else{
            holder.rytCardMenu?.background?.setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.SRC_ATOP)
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
