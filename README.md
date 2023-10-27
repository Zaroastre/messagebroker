# Message Broker

## Usage

```java
// Configuration setup
final Configuration configuration = Configuration.builder()
        .maxQueueSize(100)
        .messageTTL(Duration.ofMinutes(1))
        .build();

// Topic/Channel name
final String queueName = "DEMO";

// Message Broker creation
final MessageBroker messageBroker = new MessageBrokerClient(configuration);

// Message Queue type
final QueueType queueType = QueueType.CHANNEL;

// Publish a message
messageBroker.producer().publish(queueType, queueName, "Hello World!");

// The callback to execute when receiving a new message
final Comsumer<Event<Message<?>>> callback = (event) -> {
    System.out.println(event.data().payload());
}

// Subscribe to a queue.
messageBroker.subscriber().subscribe(queueType, queueName, callback);

// Close the broker (mandatory)
messageBroker.close();
```