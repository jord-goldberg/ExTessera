package ny.gelato.extessera.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import ny.gelato.extessera.BR


/**
 * Created by jord.goldberg on 4/30/17.
 */

abstract class BaseViewModelAdapter(open val presenter: BasePresenter<*>? = null) :
        RecyclerView.Adapter<BaseViewModelAdapter.ViewModelHolder>() {

    protected abstract fun getViewModelForPosition(position: Int): Any

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewModelHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewModelHolder, position: Int) {
        val viewModel = getViewModelForPosition(position)
        holder.bind(viewModel, presenter)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    class ViewModelHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: Any, presenter: BasePresenter<*>?) {
            binding.setVariable(BR.viewModel, viewModel)
            presenter.let {
                binding.setVariable(BR.presenter, it)
            }
            binding.executePendingBindings()
        }
    }

    class ModelLayoutException(modelName: String, adapterName: String) : RuntimeException(
            "Please create branch for destination $modelName in $adapterName.getLayoutIdForPosition()")

    class BaseViewModelDiffUtil(private val oldList: List<BaseViewModel>, private val newList: List<BaseViewModel>)
        : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].isSameAs(newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]
    }
}
