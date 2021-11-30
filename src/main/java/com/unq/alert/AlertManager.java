package com.unq.alert;

import java.util.*;

public class AlertManager {

    private Map<AlertType, List<AlertListener>> listeners;

    public AlertManager(AlertType... events) {
        this.listeners = new HashMap<>();
        Arrays.stream(events).forEach(e -> {
            this.listeners.put(e, new ArrayList<>());
        });
    }

    public void subscribe(AlertType alertType, AlertListener listener) {
        List<AlertListener> users = listeners.get(alertType);
        users.add(listener);
    }

    public void unsubscribe(AlertType alertType, AlertListener listener) {
        List<AlertListener> users = listeners.get(alertType);
        users.remove(listener);
    }

    public void notify(AlertType alertType, String data) {
        List<AlertListener> notificationListeners = listeners.get(alertType);
        notificationListeners.forEach(l -> l.update(alertType, data));
    }

    public Map<AlertType, List<AlertListener>> getListeners() {
        return listeners;
    }
}
