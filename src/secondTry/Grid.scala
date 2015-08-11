
package secondTry

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import scala.swing.Panel
import java.awt.RenderingHints
import java.awt.Font
import java.awt.image.BufferedImage
import collection.immutable
import scala.swing.MainFrame
import java.awt.Dimension
import scala.swing.event.KeyPressed
import scala.swing.event.Key
import scala.swing.event.KeyReleased
import scala.swing.event.MousePressed
import scala.swing.ScrollPane
import scala.swing.BorderPanel
import scala.swing.GridPanel
import collection.mutable
import scala.swing.CheckBox
import scala.swing.event.ButtonClicked
import firstTry.random
import scala.util.Random
import javax.swing.JDesktopPane
import java.awt.Component
import javax.swing.JInternalFrame
import javax.swing.JComponent
import java.awt.Rectangle
import scala.swing.Frame
import scala.swing.event.MouseClicked
import javax.imageio.ImageIO
import java.io.File
import scala.swing.Dialog
import scala.swing.event.MouseDragged
import scala.swing.event.MouseMoved
import collection.mutable
import scala.swing.TextArea
import scala.swing.TextField
import scala.swing.TextComponent
import scala.swing.Label
import java.awt.Toolkit
import java.awt.Image
import java.awt.image.FilteredImageSource
import java.awt.image.RGBImageFilter

class Grid {

  val closeButton = ImageIO.read(new File("Images/closeButton.jpg"))
  var gridScale = 1.0
  var controlBool = false
  val xml = new projectXMLCode
  val pith = new Pithoi
  val artifacts = xml.getArtifacts
  var lineBool = true
  //val font = new Font("Arial", Font.PLAIN, 20);
  val p251Img = ImageIO.read(new File("Images/pithoi251.jpg"))
  val p250 = new Rectangle(356, 85, 20, 25)
  val p251 = new Rectangle(479, 265, 45, 50)
  val p252 = new Rectangle(412, 275, 40, 30)
  val p253 = new Rectangle(327, 290, 34, 33)
  val p254 = new Rectangle(246, 452, 45, 30)
  val p255 = new Rectangle(196, 543, 50, 31)
  val notSure = new Rectangle(318,865,50,50)
  val alsoNotSure = new Rectangle(217,305,40,40)
  var pithoiList = List[pith.pithos](pith.pithos("250", false, p250), pith.pithos("251", false, p251), pith.pithos("252", false, p252), pith.pithos("253", false, p253),
    pith.pithos("254", false, p254), pith.pithos("255", false, p255), pith.pithos("Not Sure",false,notSure), pith.pithos("Not Sure",false,alsoNotSure))

  for (a <- artifacts) {
    xml.setArtifactsImage(a)
  }

  val graphPanel = new Panel {
    override def paint(g: Graphics2D) {
      g.setStroke(new BasicStroke(.5f))
      g.scale(gridScale, gridScale)

      //all of the background drawing should probably be done somewhere else and we should create a background image 

      //drawing background white rectangle
      g.setPaint(Color.white)
      g.fill(new Rectangle2D.Double(0, 0, 10000, 10000))
      //g.drawImage(p251Img, p251.x-13, p251.y-5, (p251Img.getWidth()*.6).toInt, (p251Img.getHeight()*.6).toInt, null)
      //drawing the grid
      g.setPaint(Color.black)
      for (i <- 1 to 31)
        g.drawLine(36, i * 36, 648, i * 36)
      for (j <- 1 to 18)
        g.drawLine(j * 36, 36, j * 36, 1116)

      //labeling the chart with the letters
      val font = new Font("Arial", Font.PLAIN, 20);
      g.setFont(font)
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var startX = 49
      val lettArr = Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q")
      for (x <- lettArr) {
        g.drawString(x, startX, 34)
        startX += 36
      }

      //labeling chart with numbers. 9 is the special case because it needs to be closer to the y axis
      var startY = 97
      for (a <- 10 to 38) {
        g.drawString(a.toString, 10, startY)
        startY += 36
      }
      g.drawString("9", 17, 62)

      /*val dash1 = Array(10.0f);
      val dashed = new BasicStroke(1.0f,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        10.0f, dash1, 0.0f);
      g.setStroke(dashed);
      for(i <-36 until 504 by 6){
        g.drawLine(i, 36, i, 720)
      }
      for(j <-36 until 720 by 6)  g.drawLine(36,j, 504, j)*/
      val r = new Random
      for (c <- checkList) {
        if (c.selected) {
          for (a <- artifacts) {
            if (a.name == c.text) {
              //g.setPaint(new Color(r.nextFloat,r.nextFloat,r.nextFloat))
              xml.drawArtifacts(a, g, lineBool)
            }
          }
        }
      }

      g.drawOval(p250.x, p250.y, p250.width, p250.height)
      g.drawOval(p251.x, p251.y, p251.width, p251.height)
      g.drawOval(p252.x, p252.y, p252.width, p252.height)
      g.drawOval(p253.x, p253.y, p253.width, p253.height)
      g.drawOval(p254.x, p254.y, p254.width, p254.height)
      g.drawOval(p255.x, p255.y, p255.width, p255.height)
      g.drawOval(notSure.x,notSure.y,notSure.width,notSure.height)
      g.drawOval(alsoNotSure.x,alsoNotSure.y,alsoNotSure.width,alsoNotSure.height)
      pith.drawPithBoxes(g, pithoiList)

      preferredSize = new Dimension((750 * gridScale).toInt, (1200 * gridScale).toInt)
      pane.revalidate
    }
    listenTo(keys, mouse.clicks, mouse.moves)
    reactions += {
      case e: KeyPressed =>
        if (e.key == Key.Control) controlBool = true
        if (e.key == Key.Equals) {
          if (controlBool) {
            gridScale += .2
            repaint
          }
        }
        if (e.key == Key.Minus) {
          if (controlBool && gridScale > .7) {
            gridScale -= .2
            repaint
          }
        }
      case e: KeyReleased =>
        if (e.key == Key.Control) controlBool = false
      case e: MouseMoved =>
        for (p <- pithoiList) {
          val boundRect = new Rectangle((p.location.x * gridScale).toInt, (p.location.y * gridScale).toInt, (p.location.getWidth() * gridScale).toInt,
            (p.location.getHeight * gridScale).toInt)
          if (boundRect.contains(e.point)) {
            p.bool = true
          } else {
            p.bool = false
          }
        }
        repaint
      case e: MouseClicked =>
        requestFocus
        for (a <- artifacts) {
          for (l <- xml.getArtifactLocation(a)) {
            val boundRect = new Rectangle((l._1 * gridScale).toInt, (l._2 * gridScale).toInt, (4 * gridScale).toInt, (4 * gridScale).toInt)
            if (boundRect.contains(e.point.x, e.point.y)) {
              gridPanel.contents += new Panel {
                val closeButtonLocation = new Rectangle(315, 0, 25, 25)
                override def paint(g: Graphics2D) {
                  g.setColor(Color.WHITE)
                  g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
                  g.setColor(Color.BLACK)
                  val font = new Font("Arial", Font.PLAIN, 15);
                  g.setFont(font)
                  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  g.drawString("Artifact Info", 125, 20)
                  g.drawString("Name: " + a.name, 10, 55)
                  g.drawString("Type: " + a.Type, 10, 90)
                  if (a.description.length < 35) g.drawString("Description: " + a.description, 10, 125)
                  else if (a.description.length < 78) {
                    g.drawString("Description: " + a.description.substring(0, a.description.length / 2), 10, 125)
                    g.drawString(a.description.substring(a.description.length / 2, a.description.length), 10, 145)
                  } else {
                    g.drawString("Description: " + a.description.substring(0, a.description.length / 3), 10, 125)
                    g.drawString(a.description.substring(a.description.length / 3, (a.description.length / 3) * 2), 10, 145)
                    g.drawString(a.description.substring((a.description.length / 3) * 2, a.description.length), 10, 165)
                  }
                  g.drawImage(closeButton, 315, 0, 25, 25, null)
                  if (a.img != null) g.drawImage(a.img, 30, 170, a.img.getWidth() / 6, a.img.getHeight() / 6, null)
                }
                listenTo(mouse.clicks)
                reactions += {
                  case e: MouseClicked =>
                    requestFocus
                    if (closeButtonLocation.contains(e.point)) {
                      gridPanel.contents -= this
                      gridPanel.rows -= 1
                      if (gridPanel.rows == 0) {
                        infoScroll.visible = false
                        //frame.centerOnScreen
                        //frame.maximize
                      }
                      frame.centerOnScreen
                      frame.maximize
                    }
                }
                preferredSize = new Dimension(350, 500)
              }
              gridPanel.rows += 1
              infoScroll.visible = true
              frame.centerOnScreen
              frame.maximize
            }
          }
        }
    }
  }

  val pane: ScrollPane = new ScrollPane(graphPanel) {
  }

  var artifactNames = artifacts.map(_.name).toList
  artifactNames = artifactNames.sortWith(_ < _)
  var checkList = mutable.Buffer[CheckBox]()
  var titleList = mutable.Buffer[CheckBox]()
  val lampBox = new CheckBox("Lamps");
  val brBox = new CheckBox("Base-ring Bowls")
  val whshBox = new CheckBox("White Shaved Juglets")
  val whiteSlipBox = new CheckBox("White Slip MilkBowls")
  val wbBox = new CheckBox("Wall Brackets")
  titleList += new CheckBox("Show Lines");
  titleList += lampBox
  titleList += brBox
  titleList += whshBox
  titleList += whiteSlipBox
  titleList += wbBox
  for (a <- artifactNames)
    checkList += (new CheckBox(a))
  for (c <- checkList)
    c.selected = true

  val checkPanel = new GridPanel(200, 1) {
    for (t <- titleList) {
      t.selected = true
      contents += t
      listenTo(t)
    }
    for (c <- checkList) {
      contents += c
      listenTo(c)
    }
    reactions += {
      case e: ButtonClicked =>
        if (e.source.text == "Show Lines") lineBool = e.source.selected
        else if (e.source.text == "Lamps") {
          for (h <- checkList)
            if (h.text.split(" ")(2) == "(L)") h.selected = e.source.selected
        } else if (e.source.text == "Base-ring Bowls") {
          for (h <- checkList)
            if (h.text.split(" ")(2) == "(BR)") h.selected = e.source.selected
        } else if (e.source.text == "White Shaved Juglets") {
          for (h <- checkList)
            if (h.text.split(" ")(2) == "(WhSh)") h.selected = e.source.selected
        } else if (e.source.text == "White Slip MilkBowls") {
          for (h <- checkList)
            if (h.text.split(" ")(2) == "(WS)") h.selected = e.source.selected
        } else if (e.source.text == "Wall Brackets") {
          for (h <- checkList)
            if (h.text.split(" ")(2) == "(WB)") h.selected = e.source.selected
        }
        graphPanel.repaint
    }
  }

  //ScrollPane that houses the check boxes
  val checkPane: ScrollPane = new ScrollPane(checkPanel) {
    preferredSize = new Dimension(150, 720)
  }

  /*val innerFrame = new JInternalFrame("Hello", true, true, true, true)
  val deskFrame = new JDesktopPane
  //deskFrame.add(border.)
  deskFrame.add(innerFrame)
  innerFrame.setBounds(0, 0, 0, 0)
  deskFrame.setSize(500, 500)
  innerFrame.setVisible(true)
  deskFrame.setVisible(true)
  //val desktop = swing.Component.wrap(deskFrame)
  val inFrame = swing.Component.wrap(innerFrame)*/

  val gridPanel = new GridPanel(0, 1) {
  }

  val infoScroll = new ScrollPane(gridPanel) {
    visible = false
  }

  val textGrid = new GridPanel(0, 3) {
    val myLabel = new Label
    myLabel.text = "Type Commands Here ------>"
    contents += myLabel
    contents += new TextField {
      listenTo(keys)
      reactions += {
        case e: KeyPressed =>
          if (e.key == Key.Enter) {
            readSearch(text)
            text = ""
          }
          graphPanel.repaint
      }
    }
    contents += new Label
  }
  def readSearch(search: String) {
    val s = search.split(" ")
    if (s(0).toLowerCase() == "show" && s.length > 1) show(s(1))
    else if (s(0).toLowerCase() == "hide" && s.length > 1) hide(s(1))
    else if ((s(0).toLowerCase() == "first-hit" || s(0).toLowerCase() == "fh") && s.length > 1) firstHit(s(1))
  }
  def show(group: String) {
    if (group.toLowerCase == "l") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(L)") {
          c.selected = true
        }
      }
      lampBox.selected = true
    } else if (group.toLowerCase == "br") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(BR)") {
          c.selected = true
        }
      }
      brBox.selected = true
    } else if (group.toLowerCase == "whsh") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WhSh)") {
          c.selected = true
        }
      }
      whshBox.selected = true
    } else if (group.toLowerCase == "ws") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WS)") {
          c.selected = true
        }
      }
      whiteSlipBox.selected = true
    } else if (group.toLowerCase == "wb") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WB)") {
          c.selected = true
        }
      }
      wbBox.selected = true
    } else if (group.toLowerCase == "all") {
      for (c <- checkList) c.selected = true
      for (t <- titleList) t.selected = true
    } else {
      for (c <- checkList) {
        if (group == c.text.split(" ")(1)) {
          c.selected = true
        }
      }
    }
  }
  def hide(group: String) {
    if (group.toLowerCase == "l") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(L)") {
          c.selected = false
        }
      }
      lampBox.selected = false
    } else if (group.toLowerCase == "br") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(BR)") {
          c.selected = false
        }
      }
      brBox.selected = false
    } else if (group.toLowerCase == "whsh") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WhSh)") {
          c.selected = false
        }
      }
      whshBox.selected = false
    } else if (group.toLowerCase == "ws") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WS)") {
          c.selected = false
        }
      }
      whiteSlipBox.selected = false
    } else if (group.toLowerCase == "wb") {
      for (c <- checkList) {
        if (c.text.split(" ")(2) == "(WB)") {
          c.selected = false
        }
      }
      wbBox.selected = false
    } else if (group.toLowerCase == "all") {
      for (c <- checkList) c.selected = false
      for (t <- titleList) t.selected = false
    }else {
      for (c <- checkList) {
        if (group == c.text.split(" ")(1)) {
          c.selected = false
        }
      }
    }
  }

  def firstHit(group: String) {
    if (group.toLowerCase == "l") {
      for (a <- artifacts) {
        if (a.Type == "Lamp") {
          a.showFirstHitOnly = !a.showFirstHitOnly
        }
      }
    } else if (group.toLowerCase == "br") {
      for (a <- artifacts) {
        if (a.Type == "Base-ring Bowl") {
          a.showFirstHitOnly = !a.showFirstHitOnly
        }
      }
    } else if (group.toLowerCase == "whsh") {
      for (a <- artifacts) {
        if (a.Type == "White Shaved Juglet") {
          a.showFirstHitOnly = !a.showFirstHitOnly
        }
      }
    } else if (group.toLowerCase == "ws") {
      for (a <- artifacts) {
        if (a.Type == "White Slip Milkbowl") {
          a.showFirstHitOnly = !a.showFirstHitOnly
        }
      }
    } else {
      for (a <- artifacts) {
        if (group == a.name.split(" ")(1)) {
          a.showFirstHitOnly = !a.showFirstHitOnly
        }
      }
    }
  }

  //BorderPanel that houses all of the GUIs
  val borderP = new BorderPanel {
    layout += pane -> BorderPanel.Position.Center
    layout += checkPane -> BorderPanel.Position.East
    layout += infoScroll -> BorderPanel.Position.West
    layout += textGrid -> BorderPanel.Position.North
  }

  val frame: MainFrame = new MainFrame {
    contents = borderP
    visible = true
    centerOnScreen;
    maximize
  }

}
object Grid {
  val grid = new Grid
  def main(args: Array[String]) {
    grid.graphPanel.requestFocus()
  }
}
