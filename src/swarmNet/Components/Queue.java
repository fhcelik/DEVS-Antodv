package swarmNet.Components;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import java.util.LinkedList;

/**
 * Queue is a data structure which maintains the FIFO (First In First Out) order
 * of the objects inserted into it.
 */

public class Queue {

	private LinkedList<NETPacket> packets; // queue object to store packets

	private int max_length; // maximum length of the queue

	private int cur_length; // Current length of the queue

	private NETPacket packet;

	/**
	 * Construction: a new queue with the given maximum length (in bytes!). if
	 * the queue is infinite; length = 0
	 * 
	 * @param length
	 *            maximum length of queue(in bytes)
	 */
	public Queue(int length) {
		max_length = length;
		cur_length = 0;
		packets = new LinkedList<NETPacket>();

	}

	/**
	 * Return the maximum length of the queue in bytes.
	 * 
	 * @return maximum length of the queue
	 */
	public int getMaximumLength() {
		return max_length;
	}
	
	public int getCurLength() {
		return cur_length;
	}

	/**
	 * Check whether the queue is full if a given number of bytes is to be
	 * added.
	 * 
	 * @param bytes
	 *            value to be compared
	 * @return true if queue has enough capacity
	 */
	public boolean isFull(int bytes) {
		if (max_length == 0)
			return false;
		return cur_length + bytes >= max_length;
	}

	/**
	 * Check if the queue is empty
	 * 
	 * @return true if no packet in queue
	 */
	public boolean isEmpty() {
		return packets.size() == 0;
	}

	/**
	 * Returns the reference of the first object in this queue
	 * 
	 * @return packet if present and null otherwise.
	 */
	public NETPacket front() {
		if (!isEmpty()) {
			return (NETPacket) packets.getFirst();
		}
		return null;
	}

	/**
	 * Enqueue a packet to this queue
	 * 
	 * @param packet
	 *            packet to be enqueued
	 * @return true in success
	 */
	public boolean enqueue(NETPacket packet) {

		if (!isFull(packet.getLength())) {
			// if (packet.numHops >= 10){return true;}
			if (packet.getPriority() != 7) {
				packets.addLast(packet);
				cur_length += packet.getLength();
			} else {
				packets.addFirst(packet);
				cur_length += packet.getLength();
			}
			return true;
		}
		return false;
	}

	/**
	 * Dequeue a packet from this queue.
	 * 
	 * @return a dequeued packet that was removed from the queue
	 */
	public NETPacket dequeue() {

		if (packets.size() != 0) { // if (!isEmpty()){}

			// Dequeue a packet and decrease queue length
			packet = (NETPacket) packets.removeFirst();
			cur_length -= packet.getLength();
			return packet;
		} else
			return null;
	}

	/**
	 * returns queue size
	 * 
	 * @return number of packets in queue
	 */
	public int size() {
		return packets.size();
	}

	/**
	 * printouts the objects of the queue
	 */
	public void qPrint() {
		if (size() == 0) {
			System.out.println("Queue is empty!!!");
		} else {
			for (int i = 0; i < packets.size(); i++) {
				System.out.println(((NETPacket) packets.get(i)).pPrnt());
			}
		}
	}

}
