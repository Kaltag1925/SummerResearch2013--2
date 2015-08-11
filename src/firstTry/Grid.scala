package firstTry

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Rectangle2D
import scala.swing.MainFrame
import scala.swing.Panel
import scala.swing.ScrollPane
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import scala.swing.event.KeyReleased
import scala.swing.BorderPanel
import scala.swing.CheckBox
import scala.swing.GridPanel
import collection.mutable
import java.awt.BasicStroke
import scala.swing.event.MousePressed
import java.awt.event.ItemListener
import java.awt.event.ItemEvent

//An Object that will hold the code for all of the GUIS for my Summer project.
object Grid {

  var gridScale = 1.4
  var controlBool = false

  val xml = new projectXMLCode
  val lamps = xml.getLamps

  //Main panel where all of the Graphing takes place.
  val graphPanel = new Panel {
    override def paint(g: Graphics2D) {
      g.setStroke(new BasicStroke(.5f))
      g.scale(gridScale, gridScale)
      //background rectangle
      g.setPaint(Color.white)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      g.setPaint(Color.black)

      //drawing the grid
      for (i <- 1 to 20)
        g.drawLine(36, i * 36, 504, i * 36)
      for (j <- 1 to 14)
        g.drawLine(j * 36, 36, j * 36, 720)

      //font for label  
      val font = new Font("Arial", Font.PLAIN, 20);
      g.setFont(font)
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      //labeling chart with +letters
      var startX = 49
      val lettArr = Array("E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q")
      for (x <- lettArr) {
        g.drawString(x, startX, 34)
        startX += 36
      }

      //labeling chart with numbers. 9 is the special case because it needs to be closer to the y axis
      var startY = 97
      for (a <- 10 to 27) {
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

      xml.drawArtifacts(lamps,g)

      preferredSize = new Dimension((600 * gridScale).toInt, (800 * gridScale).toInt)
      pane.revalidate
    }

    //implementing the zoom function with control-enter and control-shift as the zoom in and out keys
    listenTo(keys, mouse.clicks)
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
          if (controlBool && gridScale > 1) {
            gridScale -= .2
            repaint
          }
        }
      case e: KeyReleased =>
        if (e.key == Key.Control) controlBool = false
      case e: MousePressed =>
        requestFocus
    }
  }

  //ScrollPane that houses the main panel
  val pane: ScrollPane = new ScrollPane(graphPanel) {
    preferredSize = new Dimension(720, 720)
  }

  //Creates a list with the names of the various lamps and then creates check boxes for those lamps.
  var lampNames = lamps.map(_.name).toList
  lampNames = lampNames.sortWith(_ < _)
  var checkList = mutable.Buffer[CheckBox]()
  
  for (l <- lampNames)
    checkList += (new CheckBox(l))
    
  val checkPanel = new GridPanel(200, 1) {
    for (a <- checkList) contents += a
  }

  //ScrollPane that houses the check boxes
  val checkPane = new ScrollPane(checkPanel) {
    preferredSize = new Dimension(150, 720)
  }

  //BorderPanel that houses all of the GUIs
  val border = new BorderPanel {
    layout += pane -> BorderPanel.Position.Center
    layout += checkPane -> BorderPanel.Position.East
  }

  val frame = new MainFrame {
    contents = border
    visible = true
    centerOnScreen
    maximize
  }

  def main(args: Array[String]) {
    graphPanel.requestFocus()
  }
}

