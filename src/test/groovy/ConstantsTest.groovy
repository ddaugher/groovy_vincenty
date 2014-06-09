class ConstantsTest extends spock.lang.Specification{
  def "the constant value for WGS84_a should be returned properly"() {

    expect: "the proper value for WGS84_a should be returned"
    6378137 == Constants.WGS84_a

  }

  def "the constant value for WGS84_f should be returned properly"() {

    expect: "the proper value for the WGS84_f should be returned"
    1/298.257223563 == Constants.WGS84_f
  }
}

