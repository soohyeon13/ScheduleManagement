package kr.ac.jejunu.rxpractice.ui.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoItemBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel.TodoListItemViewModel
import kr.ac.jejunu.rxpractice.util.OnItemClickListener
import kr.ac.jejunu.rxpractice.util.getValueAnimator

class TodoListAdapter(private val itemClickListener : OnItemClickListener<Schedule>) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    private lateinit var schedules: List<Schedule>
    private lateinit var binding : TodoItemBinding
    private var originHeight = -1
    private var expandedHeight = -1
    private var animationPlaybackSpeed = 0.8
    private val listItemExpandDuration :Long get() = (300L/animationPlaybackSpeed).toLong()
    private var expandModel : Schedule? = null
    private var isScaledDown = false
    private lateinit var recyclerView: RecyclerView
    inner class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TodoListItemViewModel()
        val image = binding.expandState
        val container = binding.contractContainer
        val expandContainer = binding.expandContainer
        fun onBind(schedule: Schedule,itemClickListener:OnItemClickListener<Schedule>) {
            viewModel.bind(schedule)
            binding.viewModel = viewModel
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.todo_item,parent,false)
        return TodoViewHolder(binding)
    }

    fun setSchedules(schedule: List<Schedule>?) {
        this.schedules = schedule!!
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(::schedules.isInitialized){
            schedules.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(schedules[position],itemClickListener)
        val model = schedules[position]
        expandItem(holder,model == expandModel,animat = false)
        scaleDownItem(holder,position,isScaledDown)

        holder.container.setOnClickListener {
            if (expandModel == null) {
                expandItem(holder,b = true,animat = true)
                expandModel = model
            }else if (expandModel == model){
                expandItem(holder,b = false,animat = true)
                expandModel = null
            } else {
                expandItem(holder, b = true, animat = true)
                expandModel = model
            }
        }
    }

    override fun onViewAttachedToWindow(holder: TodoViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (expandedHeight < 0) {
            expandedHeight = 0

            holder.container.doOnLayout { view ->
                originHeight = view.height
                holder.expandContainer.isVisible = true
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.expandContainer.isVisible = false
                }
            }
        }
    }

    private fun scaleDownItem(
        holder: TodoViewHolder,
        position: Int,
        scaledDown: Boolean
    ) {
        setScaleDownProgress(holder,position,if(scaledDown) 1f else 0f)
    }

    private fun setScaleDownProgress(
        holder: TodoViewHolder,
        position: Int,
        progress: Float
    ) {
        val itemExpanded = position >=0 && schedules[position] == expandModel
        holder.container.layoutParams.apply {
            height = ((if (itemExpanded) expandedHeight else originHeight) * (1 - 0.1f*progress).toInt())
        }
        holder.container.requestLayout()
        holder.container.scaleX = 1- 0.05f * progress
        holder.container.scaleY = 1- 0.05f * progress
    }

    private fun expandItem(holder: TodoViewHolder, b: Boolean, animat: Boolean) {
        if (animat) {
            val animator = getValueAnimator(
                b,listItemExpandDuration, AccelerateDecelerateInterpolator()
            ){ progress -> setExpandProgress(holder,progress) }

            if (b) animator.doOnStart { holder.expandContainer.isVisible = true }
            else { animator.doOnEnd { holder.expandContainer.isVisible = false } }
            animator.start()
        } else {
            holder.expandContainer.isVisible = b && expandedHeight > 0
            setExpandProgress(holder,if (b) 1f else 0f)
        }
    }

    private fun setExpandProgress(holder: TodoViewHolder, progress: Float) {
        if (expandedHeight>0 && originHeight > 0) {
            holder.container.layoutParams.height =
                (originHeight + (expandedHeight - originHeight) * progress).toInt()
        }
        holder.container.requestLayout()
        holder.image.rotation = -90*progress
    }
}