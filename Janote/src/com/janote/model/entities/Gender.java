/**
 * 
 */
package com.janote.model.entities;

/**
 * @author estelle
 *
 */
public enum Gender {
	BOY (1),
	GIRL (0);
	
	private Integer value;

    Gender(int value){
	    this.value = value;  
    }
    
    public int getValue() {
        return value;
    }
    
    public String toString(){
	    return value.toString();
    }
}
