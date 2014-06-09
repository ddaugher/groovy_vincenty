class PolygonResultTests extends spock.lang.Specification{
  def "should construct default class"() {
    given: "a new PolygonResult class"
    def result = new PolygonResult()

    expect: "a properly constructed PolygonResult is constructed"
    null != result
  }
}

