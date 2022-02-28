package com.shellytoo.dating.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import github.ankushsachdeva.emojicon.EmojiconTextView;
import com.shellytoo.dating.ProfileActivity;
import com.shellytoo.dating.R;
import com.shellytoo.dating.model.Chat;

public class DialogsListAdapter extends RecyclerView.Adapter<DialogsListAdapter.ViewHolder> {

    private Context ctx;
    private List<Chat> items;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(View view, Chat item, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, subtitle, count, time;
        public CircularImageView image, online, verified;
        public LinearLayout parent;
        public EmojiconTextView message;

        public ViewHolder(View view) {

            super(view);

            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            message = view.findViewById(R.id.message);
            count = view.findViewById(R.id.count);
            time = view.findViewById(R.id.time);
            image = view.findViewById(R.id.image);
            parent = view.findViewById(R.id.parent);

            online = view.findViewById(R.id.online);
            verified = view.findViewById(R.id.verified);
        }
    }

    public DialogsListAdapter(Context mContext, List<Chat> items) {

        this.ctx = mContext;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Chat item = items.get(position);

        holder.online.setVisibility(View.GONE);

        if (item.getWithUserVerify() != 0) {

            holder.verified.setVisibility(View.VISIBLE);

        } else {

            holder.verified.setVisibility(View.GONE);
        }

        if (item.getWithUserPhotoUrl().length() > 0) {

            try {

                Glide.with(ctx).load(item.getWithUserPhotoUrl())
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.image);

            } catch (Exception e) {

                Log.e("DialogsListAdapter", e.toString());
            }

        } else {

            holder.image.setImageResource(R.drawable.profile_default_photo);
        }

        holder.title.setText(item.getWithUserFullname());
        holder.subtitle.setVisibility(View.GONE);

        if (item.getLastMessage().length() != 0) {

            holder.message.setText(item.getLastMessage().replaceAll("<br>", " "));

        } else {

            holder.message.setText(ctx.getString(R.string.label_last_message_image));
        }

        if (item.getLastMessageAgo().length() != 0) {

            holder.time.setText(item.getLastMessageAgo());

        } else {

            holder.time.setText("");
        }

        if (item.getNewMessagesCount() == 0) {

            holder.count.setVisibility(View.GONE);
            holder.count.setText(Integer.toString(item.getNewMessagesCount()));

        } else {

            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText(Integer.toString(item.getNewMessagesCount()));
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick(v, items.get(position), position);
                }
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Chat chat = items.get(position);

                Intent intent = new Intent(ctx, ProfileActivity.class);
                intent.putExtra("profileId", chat.getWithUserId());
                ctx.startActivity(intent);
            }
        });
    }

    public Chat getItem(int position) {

        return items.get(position);
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public interface OnClickListener {

        void onItemClick(View view, Chat item, int pos);
    }
}