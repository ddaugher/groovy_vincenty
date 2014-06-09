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

    then: "the value of the numberOfVertices should be set"
    3 == result.numberOfVertices
  }

  def "should be able to set number of vertices on the PolygonResult via the constructor"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult(numberOfVertices: 3)

    expect: "the value of the numberOfVertices should be set"
    3 == result.numberOfVertices
  }

  def "should be able to set perimeter on the PolygonResult"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult()

    when: "set the value of the perimeter on the result"
    result.perimeter = 3.0d

    then: "the value of the perimeter should be set"
    3.0d == result.perimeter
  }

  def "should be able to set perimeter value on the PolygonResult via the constructor"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult(perimeter: 3.0d)

    expect: "the value of the perimeter should be set"
    3.0d == result.perimeter
  }

  def "should be able to set area of the polygon on the polygon result"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult()

    when: "set the area of the polygon on the result"
    result.area = 12.0d

    then: "the value of the area should be set"
    12.0d == result.area
  }

  def "should be able to set area on the PolygonResult via the constructor"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult(area: 12.0d)

    expect: "the value of area should be"
    12.0d == result.area
  }
}

