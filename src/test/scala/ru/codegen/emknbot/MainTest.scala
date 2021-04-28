package ru.codegen.emknbot

import ru.codegen.emknbot.domain._
import ru.codegen.emknbot.domain.userinfo.Password

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MainTest extends AnyFunSuite with Matchers {

    test("important test") {
        assert(0 != 1)
    }

    test("empty MapUserRepository") {
        val repo = new MapUserRepository[cats.Id]
        assert(repo.getAll.isEmpty)
        assert(repo.getById(0).isLeft)
    }

    private def pushUsers[F[_]](repo: UserRepository[F]) = new {
        val users = List(
            EmknUser("a", Password("1234"), 0),
            EmknUser("b", Password("1234"), 1),
            EmknUser("c", Password("1234"), 2),
        )
        users.foreach(repo.add)
    }

    test("simple adding to MapUserRepository") {
        val repo = new MapUserRepository[cats.Id]
        val fixture = pushUsers(repo)

        repo.getAll shouldBe fixture.users
        for (i <- fixture.users.indices) {
            repo.getById(i) shouldBe Right(fixture.users(i))
        }
        assert(repo.getById(fixture.users.length).isLeft)
    }

    test("simple removing from MapUserRepository") {
        val repo = new MapUserRepository[cats.Id]
        val fixture = pushUsers(repo)

        for (i <- 1 until fixture.users.length) {
            repo.remove(i)
        }
        repo.getAll.length shouldBe 1
        repo.getById(0) shouldBe Right(fixture.users.head)

        repo.remove(0)
        assert(repo.getAll.isEmpty)
        assert(repo.getById(fixture.users.length).isLeft)
    }

    test("empty MapEventStorage") {
        val storage = new MapEventStorage[cats.Id]
        assert(storage.getById(0).isEmpty)
    }

    case class EmknEventMock(eventId: Int) extends EmknEvent {
        override val eventType: EventType = EventType.LectureType
    }

    private def pushEvents[F[_]](storage: EventStorage[F]) = new {
        val events: List[EmknEvent] = (0 until 5).map(EmknEventMock).toList
        events.zipWithIndex.foreach { case (x, i) =>
            storage.add(i, x)
        }
    }

    test("simple adding to MapEventStorage") {
        val storage = new MapEventStorage[cats.Id]
        val fixture = pushEvents(storage)

        for (i <- fixture.events.indices) {
            storage.getById(i) shouldBe List(fixture.events(i))
        }
        storage.getById(fixture.events.length) shouldBe List.empty
    }

    test("simple removing from MapEventStorage") {
        val storage = new MapEventStorage[cats.Id]
        val fixture = pushEvents(storage)

        for (i <- 1 until fixture.events.length) {
            storage.remove(i, fixture.events(i))
        }
        storage.getById(1) shouldBe List.empty
        storage.getById(0) shouldBe List(fixture.events.head)

        storage.remove(0, fixture.events.head)
        assert(storage.getAll.forall(_._2.isEmpty))
    }
}
