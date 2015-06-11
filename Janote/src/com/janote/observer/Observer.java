/**
 * 
 */
package com.janote.observer;

/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public interface Observer {

    public boolean add(Observable observable);

    public boolean update(Observable observable);

    public boolean delete(Observable observable);
}