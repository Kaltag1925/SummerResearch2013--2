package secondTry
import collection.immutable
import java.awt.Graphics2D
import java.awt.Color
import scala.util.Random
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class projectXMLCode {

  val uluburunXML = xml.XML.loadFile("Artifacts/artifacts2.xml")

  case class artifact(Type: String, name: String, location: List[(Int, Int)], description: String, var showFirstHitOnly: Boolean, var img: BufferedImage)

  def artifactFromXML(elem: xml.Node): artifact = artifact((elem \ "@type").toString, (elem \ "@name").toString, (elem \ "location").map(e => locToInts(e.text)).toList, (elem \ "description").text.toString, false, null)

  def getArtifacts: List[artifact] = return ((uluburunXML \ "artifact").map(artifactFromXML)).toList

  def getArtifactLocation(a: artifact): List[(Int, Int)] = a.location

  val xLocMap = immutable.Map(("A", 52), ("B", 88), ("C", 124), ("D", 160), ("E", 196), ("F", 232), ("G", 268), ("H", 304), ("I", 340), ("J", 376), ("K", 412), ("L", 448), ("M", 484),("N", 520),("O", 556),("P", 592),("Q", 628) )

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
      if (split(1) == "1/2Left") x -= 9
      else if (split(1) == "MoreLeft") x -= 13
      else if (split(1) == "1/4Left") x -= 4
      else if (split(1) == "Ri") x += 4
      else if (split(1) == "LeftEdge") x -= 18
      else if (split(1) == "Bottom") y += 18
      else if (split(1) == "Bottom3/4Right") {
        y += 18
        x += 13
      } else if (split(1) == "3/4Bottom") y += 13
      else if (split(1) == "UpperBottom") y += 4
      else if (split(1) == "BottomEdgeHalfRight") {
        y += 18
        x += 9
      } else {
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
        } else if (split(1).length == 5) {
          if (split(1).substring(2, 5) == "1/3") x -= 4
          else if (split(1).substring(2, 5) == "3/4") y += 4
          else if (split(1).substring(2, 5) == "1/2") y -= 4
          else if (split(1).substring(2, 5) == "2/3") x += 4
        }
      }
    }
    (x, y)
  }

  def drawArtifacts(a: artifact, g: Graphics2D, lineBool: Boolean) {
    if (a.Type == "Lamp") g.setColor(new Color(0, 0, 204))
    else if (a.Type == "Base-ring Bowl") g.setColor(new Color(204, 0, 0))
    else if (a.Type == "White Shaved Juglet") g.setColor(new Color(0, 204, 0))
    else if (a.Type == "White Slip Milkbowl") g.setColor(new Color(255, 127, 80))
    else if (a.Type == "Wall Bracket") g.setColor(new Color(153,0,153))
    if (getArtifactLocation(a).length == 1 || a.showFirstHitOnly) {
      val (x, y) = getArtifactLocation(a)(0)
      g.fillOval(x, y, 4, 4)
    } else if (getArtifactLocation(a).length > 1) {
      var ct = 0
      while (ct < getArtifactLocation(a).length) {
        if (ct == getArtifactLocation(a).length - 1) {
          val (x1, y1) = getArtifactLocation(a)(ct)
          g.fillOval(x1, y1, 4, 4)
          val (x2, y2) = getArtifactLocation(a)(0)
          if (lineBool == true) g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
        } else {
          val (x1, y1) = getArtifactLocation(a)(ct)
          val (x2, y2) = getArtifactLocation(a)(ct + 1)
          g.fillOval(x1, y1, 4, 4)
          if (lineBool == true) g.drawLine(x1 + 2, y1 + 2, x2 + 2, y2 + 2)
        }
        ct += 1
      }
    }
    g.setColor(Color.BLACK)
  }

  def setArtifactsImage(a: artifact) {
    val files = new File("Images").listFiles()
    for (file <- files) {
      if (file.getName.split("_").length > 1) {
        if (a.name.split(" ")(1) == file.getName.substring(5, 9)) {
          a.img = ImageIO.read(file)
        }
      }
    }
  }
}

object projectXMLCode {
  def main(args: Array[String]) {
    val xml = new projectXMLCode
    val files = new File("Images").listFiles();
    showFiles(files);
  }
  def showFiles(files: Array[File]) {
    for (file <- files) {
      if (file.isDirectory()) {
        System.out.println("Directory: " + file.getName());
        showFiles(file.listFiles()); // Calls same method again.
      } else {
        System.out.println("File: " + file.getName());
      }
    }
  }
}