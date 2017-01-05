package com.javahacks.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A set that empties out after a certain time duration. 
 * This is similar to the LoadingCache in Guava that evicts map entries after a certain time,
 * but this just clears out ALL the entries in the set after the time interval mentioned.
 * 
 * Currently, this collection is NOT thread-safe 
 * 
 * @author arjunrao87
 *
 * @param <T> : Type of item in ExpiringSet
 */
public class ExpiringSet<T> extends HashSet<T>{

    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unused") private final long expiryTimeInMilliseconds;
    private final Timer timer = new Timer(true);

    public ExpiringSet(){
        this( 600000 );
    }
    
	public ExpiringSet( Collection<? extends T> paramCollection ){
        this( paramCollection, 600000 );
    }
    
    public ExpiringSet( long expiryTimeInMilliseconds ){
        super();
        this.expiryTimeInMilliseconds = expiryTimeInMilliseconds;
        timer.scheduleAtFixedRate(getExpiryThread( this ), 0, expiryTimeInMilliseconds);
    }
    
    public ExpiringSet( Collection<? extends T> paramCollection, long expiryTimeInMilliseconds ){
        super( paramCollection );
        this.expiryTimeInMilliseconds = expiryTimeInMilliseconds;
        timer.scheduleAtFixedRate(getExpiryThread( this ), 0, expiryTimeInMilliseconds);
    }
    
    private TimerTask getExpiryThread(ExpiringSet<T> expiringSet) {
    	return new TimerTask() {
    		
    		@Override
    		public void run() {
    			expiringSet.clear();
    		}
    	};
	}
}