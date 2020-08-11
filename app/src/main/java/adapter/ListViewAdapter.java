package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tablayoutdemo.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    Context context;

    private List<String> list;
    private LayoutInflater inflater;

//    public interface OnItemCliclLister {
//        void onClick(View view, int i);
//    }
//
//    private OnItemCliclLister onItemCliclLister;
//
//    public void SetOnItemCliclLister(OnItemCliclLister onItemCliclLister) {
//        this.onItemCliclLister = onItemCliclLister;
//    }


    public ListViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.recyclervew_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.textview);
            view.setTag(viewHolder);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onItemCliclLister != null) {
//                        onItemCliclLister.onClick(view, i);
//                    }
//                }
//            });
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(list.get(i));
        return view;
    }

    static class ViewHolder {
        TextView textView;
    }
}
