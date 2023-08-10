package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {
    private ArrayList<String> suggestions;
    private LayoutInflater inflater;
    private ArrayList<String> filteredSuggestions;

    public SearchAdapter(Context context, ArrayList<String> suggestions) {
        this.suggestions = suggestions;
        inflater = LayoutInflater.from(context);
        this.filteredSuggestions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return filteredSuggestions.size();
    }

    @Override
    public String getItem(int position) {
        return filteredSuggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_suggestion, parent, false);

            holder = new ViewHolder();
            holder.tv_item_search = convertView.findViewById(R.id.tv_item_search);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String suggestion = getItem(position);
        holder.tv_item_search.setText(suggestion);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                filteredSuggestions.clear();

                if (constraint != null) {
                    // Lọc dữ liệu dựa trên giá trị nhập vào (constraint)
                    for (String suggestion : suggestions) {
                        if (suggestion.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredSuggestions.add(suggestion);
                        }
                    }

                    results.values = filteredSuggestions;
                    results.count = filteredSuggestions.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // Cập nhật danh sách gợi ý mới dựa trên từ tìm kiếm mới
                    notifyDataSetChanged();
                } else {
                    // Xóa danh sách gợi ý nếu không có kết quả
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private static class ViewHolder {
        TextView tv_item_search;
    }

    //
}
