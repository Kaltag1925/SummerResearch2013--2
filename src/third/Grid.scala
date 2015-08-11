package third

import javax.swing.JPanel
import java.awt.Graphics2D
import javax.swing.JFrame
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.Graphics
import java.awt.Dimension

object Grid {
  val graphPanel = new JPanel {
    setPreferredSize(new Dimension(500, 500))
    override def paint(g: Graphics)= {
      g.setColor(Color.BLACK)
      g.drawRect(50, 50, 100, 100)
      g.fillRect(50, 50, 100, 100)
    }
    setVisible(true)
    setSize(500,500)
  }
  val frame = new JFrame{
    add(graphPanel)
    setVisible(true)
    setSize(500,500)
  }
  def main(args: Array[String]) {
    //graphPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));S
  }
}