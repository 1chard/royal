package com.royal;

/**
 *
 * @author suporte
 */
public class StringMust {
    private final String target;
    public StringMust(String target){
	this.target = target;
    }
    
    public StringMust notNull(){
	if(target == null){
	    throw new MustHasFailedException("Target is null.");
	}
	return this;
    }
    
    public StringMust notBlank(){
	if(target.isBlank()){
	    throw new MustHasFailedException("Target <" + target + "> is blank.");
	}
	return this;
    }
    
    public StringMust sizeLessThan(int maxLength){
	if(target.length() > maxLength){
	    throw new MustHasFailedException("Target <" + target + "> has more than " + maxLength + " characters.");
	}
	return this;
    }
    
    public String get(){
	return target;
    }
}
