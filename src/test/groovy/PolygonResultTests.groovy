class PolygonResultTests extends spock.lang.Specification{
  def "should construct default class"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult()

    expect: "a properly constructed PolygonResult is constructed"
    null != result
  }

  def "should be able to set number of vertices on the PolygonResult"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult()

    when: "set the value of the vertice on the result"
    result.numberOfVertices = 3

    then: "the value of the numberOfVertices to be set"
    3 == result.numberOfVertices
  }

  def "should be able to set number of vertices on the PolygonResult via the constructor"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult(numberOfVertices: 3)

    expect: "the value of the numberOfVertices to be set"
    3 == result.numberOfVertices
  }
}

