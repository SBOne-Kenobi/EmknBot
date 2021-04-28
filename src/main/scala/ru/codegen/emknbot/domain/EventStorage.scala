package ru.codegen.emknbot.domain

import ru.codegen.emknbot.domain.userinfo._

trait EventStorage[F[_]] {

    def add(userId: Id, event: EmknEvent): F[Unit]

    def remove(userId: Id, event: EmknEvent): F[Unit]

    def getByUserId(id: Id): F[List[EmknEvent]]
}
