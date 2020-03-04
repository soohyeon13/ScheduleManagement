package kr.ac.jejunu.rxpractice.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.SearchItemBinding
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel.UserListItemViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(private val userList: MutableList<User>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable {
    private lateinit var binding: SearchItemBinding
    private val copyUserList = ArrayList(userList)
    private var onNothingFound: (() -> Unit)? = null

    class SearchViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = UserListItemViewModel()
        fun onBind(user: User) {
            viewModel.bind(user)
            binding.userItemViewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.search_item, parent, false)
        return SearchViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return userList.size
    }


    fun search(s: String?, onNothingFound: (() -> Unit)?) {
        this.onNothingFound = onNothingFound
        filter.filter(s)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                userList.clear()
                if (constraint.isNullOrBlank()) {
                    userList.addAll(copyUserList)
                } else {
                    val searchResults = copyUserList.filter { it.userName.contains(constraint) }
                    userList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = userList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (userList.isNullOrEmpty())
                    onNothingFound?.invoke()
                notifyDataSetChanged()
            }
        }
    }
}