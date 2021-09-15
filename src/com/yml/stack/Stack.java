package com.yml.stack;

import java.util.Iterator;
import com.yml.linkedlist.Node;

public class Stack<T> implements Iterable<Node<T>>{
    Node<T> top;

    public Stack() {
        top = null;
    }

    public void push(T data) {
        Node<T> newNode = new Node<T>(data);

        if (top == null) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
    }

    public T pop() {
        T popped = null;
        if (top == null) {
            System.out.println("Stack is empty");
            return null;
        } else {
            popped = top.getData();
            top = top.getNext();
        }
        return popped;
    }

    public T peek() {
        if (top == null) {
            System.out.println("Stack is empty");
            return null;
        } else {
            return top.getData();
        }
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new StackIterator<T>(top);
    }
}
