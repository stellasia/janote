/**
 * 
 */
package com.janote.observer;

/**
 * @author Estelle Scifo
 * @version 1.0
 */
public interface Observable {

	public void addObserver(Observer observer);

	public void removeObserver(Observer observer);

	public boolean observableUpdated(Observable observable);

	public boolean observableAdded(Observable observable);

	public boolean observableDeleted(Observable observable);

}
