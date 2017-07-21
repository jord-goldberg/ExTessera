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
 *
 * Provides a basic mechanism for displaying lists of BaseViewModels. If the BaseViewModels are data
 * classes and have properly overridden isSameAs(...), the BaseViewModelDiffUtil should handle
 * animating list updates smoothly if used
 *
 * Note: can also display objects that don't extend BaseViewModel for convenience
 */

abstract class BaseViewModelAdapter(open val parent: BaseView? = null) :
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
        holder.bind(viewModel, parent)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    class ViewModelHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: Any, parent: BaseView?) {
            binding.setVariable(BR.viewModel, viewModel)
            parent.let { binding.setVariable(BR.parent, it) }
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
