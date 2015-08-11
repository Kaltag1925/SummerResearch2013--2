package third
import collection.immutable
import java.awt.Graphics2D

class projectXMLCode {

  val uluburunXML = xml.XML.loadFile("Artifacts/artifacts2.xml")

  case class artifact(Type: String, name: String, location: List[(Int, Int)])

  def artifactFromXML(elem: xml.Node): artifact = artifact((elem \ "@type").toString, (elem \ "@name").toString, (elem \ "location").map(e => locToInts(e.text)).toList)

  def getArtifacts: List[artifact] = return ((uluburunXML \ "artifact").map(artifactFromXML)).toList

  def getArtifactLocation(a: artifact): List[(Int, Int)] = a.location

  val xLocMap = immutable.Map(("E", 52), ("F", 88), ("G", 124), ("H", 160), ("I", 196), ("J", 232), ("K", 268), ("L", 304), ("M", 340), ("N", 376), ("O", 412), ("P", 448), ("Q", 484))

  def locToInts(s: String): (Int, Int) = {
    var x = 0
    var y = 52
    for (xLoc <- xLocMap) {
      if (s(0).toString == xLoc._1) x = xLoc._2
    }
    val split = s.split(" ").toList
    if (split(0).length == 2) y += (s(1).toInt - 9) * 36
    if (split(0).length == 3) y += (s.substring(1, 3).toInt - 9) * 36
    if (split.length == 2) {
      if (split(1) == "Left") x -= 9
      else if (split(1) == "LeftEdge") x -= 18
      else {
        if (split(1)(0) == 'U') y -= 9
        if (split(1)(0) == 'L') y += 9
        if (split(1).length > 1) {
          if (split(1)(1) == 'L') x -= 9
          if (split(1)(1) == 'R') x += 9
        }
        if (split(1).length == 3) {
          if (split(1)(2) == '1') {
            x -= 4
            y -= 4
          } else if (split(1)(2) == '2') {
            x += 4
            y -= 4
          } else if (split(1)(2) == '3') {
            x -= 4
            y += 4
          } else if (split(1)(2) == '4') {
            x += 4
            y += 4
          }
        }
      }
    }
    (x, y)
  }

  def drawArtifacts(a: artifact, g: Graphics2D) {
    if (getArtifactLocation(a).length == 1) {
      val (x, y) = getArtifactLocation(a)(0)
      g.fillOval(x, y, 4, 4)
    } else if (getArtifactLocation(a).length > 1) {
      var ct = 0
      while (ct < getArtifactLocation(a).length) {
        if (ct == getArtifactLocation(a).length - 1) {
          val (x1, y1) = getArtifactLocation(a)(ct)
          g.fillOval(x1, y1, 4, 4)
          val (x2, y2) = getArtifactLocation(a)(0)
          g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
        } else {
          val (x1, y1) = getArtifactLocation(a)(ct)
          val (x2, y2) = getArtifactLocation(a)(ct + 1)
          g.fillOval(x1, y1, 4, 4)
          g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
        }
        ct += 1
      }
    }
  }
}

object projectXMLCode {
  def main(args: Array[String]) {
    val xml = new projectXMLCode
    println(xml.getArtifacts)
  }
}