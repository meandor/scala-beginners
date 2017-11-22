import org.scalatest.{FeatureSpec, Matchers}

class LibrarySuite extends FeatureSpec with Matchers {
  feature("someLibraryMethod that does something") {
    scenario("always true") {
      def library = new Library()

      library.someLibraryMethod() shouldBe true
    }
  }

  feature("add a constant number to a list of numbers") {
    scenario("add 42 to a list of 10 numbers") {
      def library = new Library()

      library.addConstantNumber(42, List(1, 2, 42)) shouldBe List(43, 44, 84)
    }

    scenario("add 42 to a list of 3000000 numbers") {
      def library = new Library()

      library.addConstantNumber(42, (1 to 3000000).toList) shouldBe (43 to 3000042).toList
    }
  }

  feature("parallelly add a constant number to a list of numbers") {
    scenario("add 42 to a list of 10 numbers") {
      def library = new Library()

      library.addConstantNumberParallel(42, List(1, 2, 42)) shouldBe List(43, 44, 84)
    }

    scenario("add 42 to a list of 30000000 numbers") {
      def library = new Library()

      library.addConstantNumberParallel(42, (1 to 3000000).toList) shouldBe (43 to 3000042).toList
    }
  }
}
