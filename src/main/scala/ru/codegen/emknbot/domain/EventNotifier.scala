package ru.codegen.emknbot.domain

import scala.collection.mutable

trait Subscriber {

    def update(event: EmknEvent): Unit
}

class c() {

    private val subscribers: mutable.Set[(Subscriber, EventType)] =
        mutable.Set.empty

    def subscribe(subscriber: Subscriber, eventType: EventType): Unit =
        subscribers.add(subscriber, eventType)

    def unsubscribe(subscriber: Subscriber, eventType: EventType): Unit =
        subscribers.remove(subscriber, eventType)

    def notifySubscribers(event: EmknEvent): Unit =
        subscribers.collect {
            case (subscriber, event.eventType) => subscriber
        }.foreach { subscriber =>
            subscriber.update(event)
        }
}
