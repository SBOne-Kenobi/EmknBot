package ru.codegen.emknbot.domain

import ru.codegen.emknbot.domain.userinfo._

trait EventStorage[F[_]] {

    def add(userId: Id, event: EmknEvent): F[Unit]

    def remove(userId: Id, event: EmknEvent): F[Unit]

    def getAll: F[List[(Id, List[EmknEvent])]]

    def getById(id: Id): F[List[EmknEvent]]
}
