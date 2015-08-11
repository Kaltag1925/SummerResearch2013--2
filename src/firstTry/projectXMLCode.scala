package firstTry

import collection.immutable
import java.awt.Graphics2D
import java.awt.BasicStroke

class projectXMLCode {

  val ourXML = xml.XML.loadFile("Artifacts/artifacts.xml")

  case class lamp(name: String, location: List[String])

  def lampFromXML(elem: xml.Node): lamp = lamp((elem \ "@name").toString, (elem \ "location").map(_.text).toList)

  def getLamps: List[lamp] = return (ourXML \ "lamp").map(lampFromXML).toList

  val xLocMap = immutable.Map(("E", 52), ("F", 88), ("G", 124), ("H", 160), ("I", 196), ("J", 232), ("K", 268), ("L", 304), ("M", 340), ("N", 376), ("O", 412), ("P", 448), ("Q", 484))

  def getLampLoc(l: lamp): List[String] = return l.location

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
      else if(split(1) == "LeftEdge") x-=18
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

  def drawArtifacts(lamps: List[lamp], g: Graphics2D) {
    for (l <- lamps) {
      if (getLampLoc(l).length == 1) {
        val (x, y) = locToInts(getLampLoc(l)(0))
        g.fillOval(x, y, 4, 4)
      } else if (getLampLoc(l).length > 1) {
        var ct = 0
        while (ct < getLampLoc(l).length) {
          if (ct == getLampLoc(l).length - 1) {
            val (x1, y1) = locToInts(getLampLoc(l)(ct))
            g.fillOval(x1, y1, 4, 4)
            val (x2, y2) = locToInts(getLampLoc(l)(0))
            g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
          } else {
            val (x1, y1) = locToInts(getLampLoc(l)(ct))
            val (x2, y2) = locToInts(getLampLoc(l)(ct + 1))
            g.fillOval(x1, y1, 4, 4)
            g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
          }
          ct += 1
        }
      }
    }
  }
}

object projectXMLCode {
  def main(args: Array[String]) {
    val xml = new projectXMLCode
    val lamps = xml.getLamps
    for (l <- lamps)
      println(xml.getLampLoc(l))

    /*val l = xml.g
    for(a<-l)
      println(xml.getLam(a))*/
  }
}