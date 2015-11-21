package services;

import actors.EventActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class ActorService {
    public static ActorSystem actorSystem;
    public static ActorRef eventRef;

    public static void initActors() {
        actorSystem = ActorSystem.create("contacts");
        eventRef = actorSystem.actorOf(Props.create(EventActor.class), "eventActor");
    }
}
