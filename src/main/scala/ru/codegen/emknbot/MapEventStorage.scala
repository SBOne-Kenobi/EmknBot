package ru.codegen.emknbot

import ru.codegen.emknbot.domain.userinfo._
import ru.codegen.emknbot.domain.{EmknEvent, EventStorage}

import cats.Applicative
import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

class MapEventStorage[F[_]: Applicative] extends EventStorage[F] {

    private val F = Applicative[F]

    private val data = new ConcurrentHashMap[Id, List[EmknEvent]].asScala

    override def add(userId: Id, event: EmknEvent): F[Unit] = F.pure {
        data.updateWith(userId) {
            case None => Some(List(event))
            case Some(list) => Some(event :: list)
        }
    }

    override def remove(userId: Id, event: EmknEvent): F[Unit] = F.pure {
        data.updateWith(userId)(
            _.map(_.filter(_ != event))
        )
    }

    override def getAll: F[List[(Id, List[EmknEvent])]] = F.pure {
        data.toList
    }

    override def getById(id: Id): F[List[EmknEvent]] = F.pure {
        data.getOrElse(id, List.empty)
    }
}