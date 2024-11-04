package it.unibo.inner.impl;
import java.util.Iterator;


import it.unibo.inner.api.Predicate;

import it.unibo.inner.api.*;
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private final T array[];
    private  Predicate<T> pred;
    public IterableWithPolicyImpl(T[] array) {
        this(array, new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return true;
            }
        });
    }


    public IterableWithPolicyImpl (T array[], Predicate<T> pred) {
        this.array = array;
        this.pred = pred;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iter = new InnerIterator<T>(this.array, this.pred);
        return iter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.pred = filter;
    }

    public  String toString() {
        String str = "[";
        for( T elem : this.array){
            str = str + " " + elem;
        }
        str = str + "]";
        return str;
    }

    public static class InnerIterator<T> implements java.util.Iterator<T> {
        private int arrayCounter = 0;
        private final T array[];
        private final Predicate<T> pred;
        public InnerIterator(T array[], Predicate<T> pred) {
            this.array = array;
            this.pred = pred;
        }
        public T next() {
            if(this.array[arrayCounter] == null) {
                throw new IllegalArgumentException("Out of bound selection");
            }
            if(hasNext()) {
                return this.array[arrayCounter++];
            }
            
            throw new IllegalArgumentException("Out of bound selection");
            
        } 

        public boolean hasNext() {
            while( arrayCounter < this.array.length) {
                if(this.pred.test(this.array[arrayCounter])) {
                    return true;
                }
                arrayCounter ++;
            }
            
            return false;
        }
    }

    

    
    
}
