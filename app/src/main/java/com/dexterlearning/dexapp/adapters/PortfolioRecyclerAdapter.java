/*List adapter code taken from:
http://www.technetexperts.com/mobile/custom-file-explorer-in-android-application-development
*/

package com.dexterlearning.dexapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexterlearning.dexapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PortfolioRecyclerAdapter extends RecyclerView.Adapter<PortfolioRecyclerAdapter.ViewHolder> {
    private List<String> m_item;
    private List<String> m_path;
    public ArrayList<Integer> m_selectedItem;
    Context m_context;
    Boolean m_isRoot;

    public PortfolioRecyclerAdapter(Context p_context, List<String> p_item, List<String> p_path, Boolean p_isRoot) {
        m_context=p_context;
        m_item=p_item;
        m_path=p_path;
        m_selectedItem=new ArrayList<Integer>();
        m_isRoot=p_isRoot;
    }

    @Override
    public int getItemCount() {
        return m_item.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PortfolioRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View m_view = null;
        ViewHolder m_viewHolder = null;

        LayoutInflater m_inflater = LayoutInflater.from(m_context);
        m_view = m_inflater.inflate(R.layout.item_file, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        m_view.setLayoutParams(lp);
        m_viewHolder = new ViewHolder(m_view);
        m_view.setTag(m_viewHolder);

        return m_viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents in the view holder with the element at position
        final int p_position = position;

        holder.m_tvFileName.setText(m_item.get(p_position));
        holder.m_ivIcon.setImageResource(setFileImageType(new File(m_path.get(p_position))));
        holder.m_tvDate.setText(getLastDate(p_position));
        holder.m_cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    m_selectedItem.add(p_position);
                }
                else
                {
                    m_selectedItem.remove(m_selectedItem.indexOf(p_position));
                }
            }
        });
    }

    private int setFileImageType(File m_file)
    {
        int m_lastIndex=m_file.getAbsolutePath().lastIndexOf(".");
        String m_filepath=m_file.getAbsolutePath();
        if (m_file.isDirectory())
            return R.drawable.folder;
        else
        {
            if(m_filepath.substring(m_lastIndex).equalsIgnoreCase(".png"))
            {
                return R.drawable.ic_png;
            }
            else if(m_filepath.substring(m_lastIndex).equalsIgnoreCase(".jpg"))
            {
                return R.drawable.ic_jpeg;
            }
            else
            {
                return R.drawable.file;
            }
        }
    }

    private String getLastDate(int p_pos)
    {
        File m_file=new File(m_path.get(p_pos));
        SimpleDateFormat m_dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return m_dateFormat.format(m_file.lastModified());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public CheckBox m_cbCheck;
        public ImageView m_ivIcon;
        public TextView m_tvFileName;
        public TextView m_tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            m_tvFileName = (TextView) itemView.findViewById(R.id.lr_tvFileName);
            m_tvDate = (TextView) itemView.findViewById(R.id.lr_tvdate);
            m_ivIcon = (ImageView) itemView.findViewById(R.id.lr_ivFileIcon);
            m_cbCheck = (CheckBox) itemView.findViewById(R.id.lr_cbCheck);

        }
    }
}