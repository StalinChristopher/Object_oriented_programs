package com.yml.queue;

import java.util.Iterator;
import com.yml.linkedlist.Node;

public class Queue<T> implements Iterable<Node<T>> {
    Node<T> front;
    Node<T> rear;
    int size;

    public Queue() {
        front = null;
        rear = front;
        size = 0;
    }

    public Node<T> getFront() {
		return front;
	}

	public void setFront(Node<T> front) {
		this.front = front;
	}

	public Node<T> getRear() {
		return rear;
	}

	public void setRear(Node<T> rear) {
		this.rear = rear;
	}

	public void enqueue(T data) {
        Node<T> newNode = new Node<T>(data);

        if (rear == null) {
            rear = newNode;
            front = rear;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;

    }

    public T dequeue()  {
        T dequed = null;

        if (front == null) {
            System.out.println("Queue is empty");
        }
        else if (front == rear) {
            dequed = front.getData();
            front = null;
            rear = front;
        }
        else {
            dequed = front.getData();
            front = front.getNext();
        }
        size--;
        return dequed;
    }
    
    public int size() {
    	return size;
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new QueueIterator<T>(front);
    }

}
