package com.example.servicecategory;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTitle;
    private HashMap<String, List<String>> listDetail;
    CustomExpandableListAdapter(Context context, List<String> listTitle, HashMap<String, List<String>> listDetail) {
        this.context = context;
        this.listTitle = listTitle;
        this.listDetail = listDetail;
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return
                Objects.requireNonNull(this.listDetail.get(this.listTitle.get(listPosition))).get(expandedListPosition);
    }
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }
    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(layoutInflater).inflate(R.layout.list_row, null);
        }
        TextView textView = convertView.findViewById(R.id.listTitle);
        textView.setText(expandedListText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return
                this.listDetail.get(this.listTitle.get(listPosition)).size();
    }
    @Override
    public Object getGroup(int listPosition) {
        return this.listTitle.get(listPosition);
    }
    @Override
    public int getGroupCount() {
        return this.listTitle.size();
    }
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }
    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(layoutInflater).inflate(R.layout.list_row, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}