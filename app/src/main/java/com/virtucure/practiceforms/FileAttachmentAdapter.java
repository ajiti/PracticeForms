package com.virtucure.practiceforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virtucare.practiceforms.dto.FileAttachmentDTO;

import java.util.List;

/**
 * Created by AJITI on 12-Jul-16.
 */
public class FileAttachmentAdapter extends BaseAdapter {

    private static final String TAG = FileAttachmentAdapter.class.getSimpleName();
    private List<FileAttachmentDTO> attachments;
    private Context context;

    public FileAttachmentAdapter(Context context, List<FileAttachmentDTO> attachments) {
        this.context = context;
        this.attachments = attachments;
    }

    @Override
    public int getCount() {
        return (attachments != null) ? attachments.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return attachments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            if(convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.file_attachment, null);
            }
            TextView uploadFile = (TextView) convertView.findViewById(R.id.attachmentTitle);
            TextView labelNumber = (TextView) convertView.findViewById(R.id.labelNumber);
            ImageView download = (ImageView) convertView.findViewById(R.id.download);
            FileAttachmentDTO attachment = (FileAttachmentDTO) getItem(position);
            if(attachment != null) {
                labelNumber.setText((position + 1) + ") ");
                uploadFile.setText(attachment.getOrgfilename());
                String imagePath = attachment.getFullimagepath();
                final String image = imagePath.substring(imagePath.lastIndexOf("/")+1);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ServerUtil.serverUrl + "VCRegionalAPP/rest/caserecordinsert/download;f=" + image));
                        context.startActivity(fileIntent);
                    }
                });
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }
        return convertView;
    }
}
