class PairTests extends spock.lang.Specification{
  def "should allow for the proper construct of Pair"() {
    given: "a new Pair class is created with proper values"
    def pair = new Pair(3,4)

    expect: "a properly constructed Pair is constructed"
    3 == pair.first
    4 == pair.second
  }
}

