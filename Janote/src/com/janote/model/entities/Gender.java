/**
 * 
 */
package com.janote.model.entities;

/**
 * @author estelle
 *
 */
public enum Gender {
	BOY ("H"),
	GIRL ("F");
	
	private String value;

    Gender(String value){
	    this.value = value;  
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString(){
	    return value.toString();
    }
    
    public static Gender fromString(String value) {
        if (value != null) {
          for (Gender b : Gender.values()) {
            if (value.equalsIgnoreCase(b.value)) {
              return b;
            }
          }
        }
        throw new IllegalArgumentException("No constant with text " + value + " found");
    }
}
