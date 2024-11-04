package it.unibo.inner.impl;
import java.util.Iterator;

import it.unibo.inner.api.*;
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private T array[];
    public IterableWithPolicyImpl (T array[]) {
        this.array = array;
    }
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iter = new InnerIterator<T>(this.array);
        return iter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
    
    }

    public static class InnerIterator<T> implements java.util.Iterator<T>{
        private int arrayCounter = 0;
        private T array[];
        public InnerIterator(T array[]) {
            this.array = array;
        }
        public T next() {
            return this.array[arrayCounter ++];
        } 

        public boolean hasNext() {
            return arrayCounter < this.array.length;
        }
    }
    
}
