package pasu.vadivasal.globalAdapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pasu.vadivasal.R;

/**
 * Created by Admin on 19-11-2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    private boolean check;
    private Context context;
    private LayoutInflater inflater;
    private int pos = 0;
    String[] searchList;

    private class ViewHolder {
        public ImageView img_line;
        public TextView txtArrow;
        public TextView txtName;

        private ViewHolder() {
        }
    }

    public SpinnerAdapter(Context context, String[] searchList) {
        this.context = context;
        this.searchList = searchList;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.searchList.length;
    }

    public Object getItem(int position) {
        return this.searchList[position];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = this.inflater.inflate(R.layout.toolbar_spinner_item_dropdown, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) view.findViewById(R.id.txt_name);
            holder.txtArrow = (TextView) view.findViewById(R.id.txt_arrow);
            holder.img_line = (ImageView) view.findViewById(R.id.img_line);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        System.out.println("dhoool"+this.searchList[position]);
        holder.txtName.setText(this.searchList[position]);
        holder.txtArrow.setVisibility(View.GONE);
        holder.img_line.setVisibility(View.GONE);
        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = this.inflater.inflate(R.layout.toolbar_spinner_item_dropdown, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) view.findViewById(R.id.txt_name);
            holder.txtArrow = (TextView) view.findViewById(R.id.txt_arrow);
            holder.img_line = (ImageView) view.findViewById(R.id.img_line);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.txtName.setText(this.searchList[position]);
        holder.txtArrow.setVisibility(View.GONE);
        holder.txtArrow.setTextColor(ContextCompat.getColor(this.context, R.color.gray_text));
        holder.img_line.setVisibility(View.GONE);
        this.pos = position;
        return view;
    }

    public void setCheck(boolean check) {
        this.check = check;
        notifyDataSetChanged();
    }

    public int getPos() {
        return this.pos;
    }
}
