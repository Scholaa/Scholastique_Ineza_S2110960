//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<AppNotification> notifications;
    private LayoutInflater inflater;
    private OnNotificationDeletedListener deleteListener;



    public interface OnNotificationDeletedListener {
        void onDeleted(String notificationId);
    }

    public NotificationsAdapter(Context context, List<AppNotification> notifications, OnNotificationDeletedListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.notifications = notifications;
        this.deleteListener = listener;
    }

    public void updateNotifications(List<AppNotification> newNotifications) {
        this.notifications = newNotifications;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        AppNotification notification = notifications.get(position);
        holder.bind(notification, deleteListener);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, deleteButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            deleteButton = itemView.findViewById(R.id.deleteNotificationButton);
        }

        public void bind(AppNotification notification, OnNotificationDeletedListener deleteListener) {
            title.setText(notification.getTitle());
            message.setText(notification.getMessage());
            deleteButton.setOnClickListener(v -> deleteListener.onDeleted(notification.getId()));
        }
    }
}

