package com.janote.view.gui;

public enum DialogStatus {
	NOTHING_CHANGE (0),
	OBJECT_UPDATED (1),
	ERROR (2);
	
	private Integer value;

    DialogStatus(Integer value){
	    this.value = value;  
    }
    
    public Integer getValue() {
        return value;
    }
    
    public String toString(){
	    return value.toString();
    }
}
