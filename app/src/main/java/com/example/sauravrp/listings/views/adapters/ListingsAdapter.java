package com.example.sauravrp.listings.views.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.sauravrp.listings.BR;
import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;

import java.util.List;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {

    private final List<ListingsUiModel> data;
    private final ListingsViewModel viewModel;

    public ListingsAdapter(ListingsViewModel dataModel, List<ListingsUiModel> aData) {
        viewModel = dataModel;
        data = aData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding itemBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),
                       R.layout.result_details_list_item, parent, false);
        itemBinding.setVariable(BR.viewModel, viewModel);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListingsUiModel result = data.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

       private final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ListingsUiModel item) {
            binding.setVariable(BR.uiModel, item);
            binding.executePendingBindings();
        }
    }
}
