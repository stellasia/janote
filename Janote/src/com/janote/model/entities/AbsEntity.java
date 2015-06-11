package com.janote.model.entities;

import java.util.ArrayList;

import com.janote.observer.Observable;
import com.janote.observer.Observer;

public class AbsEntity implements Observable {

    protected Integer id;
    protected boolean modified = false;

    private final ArrayList<Observer> observers;

    public AbsEntity() {
        this.observers = new ArrayList<Observer>();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param modified
     *            the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public void addObserver(Observer obsr) {
        this.observers.add(obsr);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public boolean observableUpdated(Observable observable) {
        for (Observer o : this.observers) {
            boolean status = o.update(observable);
            if (status == false)
                return status;
        }
        return true;
    }

    @Override
    public boolean observableAdded(Observable observable) {
        for (Observer o : this.observers) {
            boolean status = o.add(observable);
            if (status == false)
                return status;
        }
        return true;
    }

    @Override
    public boolean observableDeleted(Observable observable) {
        for (Observer o : this.observers) {
            boolean status = o.delete(observable);
            if (status == false)
                return status;
        }
        return true;
    }

    @Override
    public boolean equals(Object g) {
        if (g == null)
            return false;
        if (this.id == ((AbsEntity) g).getId()) {
            return true;
        }
        return false;
    }
}
