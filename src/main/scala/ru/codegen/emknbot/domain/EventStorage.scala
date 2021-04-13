package ru.codegen.emknbot.domain

import ru.codegen.emknbot.domain.userinfo._

trait EventStorage[F[_]] {

    def add(event: EmknEvent): F[Unit]

    def remove(event: EmknEvent): F[Unit]

    def getByUserId(id: Id): F[List[EmknEvent]]
}
