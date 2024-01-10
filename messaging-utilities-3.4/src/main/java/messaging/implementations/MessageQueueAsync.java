package messaging.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import messaging.Event;
import messaging.MessageQueue;

public class MessageQueueAsync implements MessageQueue {

	private Map<String, TopicQueue> queuesByTopic = new HashMap<>();;

	@Override
	public void publish(Event event) {
		try {
			getQueue(event.getType()).publish(event);
		} catch (InterruptedException e) {
			throw new Error(e);
		}
	}

	@Override
	public void addHandler(String topic, Consumer<Event> handler) {
		getQueue(topic).addHandler(handler);
	}

	private TopicQueue getQueue(String topic) {
		if (!queuesByTopic.containsKey(topic)) {
			queuesByTopic.put(topic, new TopicQueue());
		}
		return queuesByTopic.get(topic);
	}


}

class TopicQueue {
	private final List<Consumer<Event>> handlers = new ArrayList<>();
	private final BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	private Thread notificationThread = null;

	TopicQueue() {
		notificationThread = new Thread(() -> {
			executeHandlers();
		});
		notificationThread.start();
	}

	public void addHandler(Consumer<Event> s) {
		handlers.add(s);
	}

	private void executeHandlers() {
		while (true) {
			Event event;
			try {
				event = queue.take();
			} catch (InterruptedException e) {
				throw new Error(e);
			}
			handlers.stream().forEach(handler -> handler.accept(event));
		}
	}

	public void publish(Event m) throws InterruptedException {
		queue.put(m);
	}
}
