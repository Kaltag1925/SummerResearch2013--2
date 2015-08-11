package secondTry

import javax.swing.JInternalFrame
import javax.swing.JDesktopPane
import javax.swing.JFrame
import scala.swing.MainFrame

object internalPaneTrial {

  val innerFrame = new JInternalFrame("Hello", true, true, true, true)
  val desktopPane = new JDesktopPane()
  desktopPane.add(innerFrame)

  innerFrame.setBounds(0, 0, 500, 500)
  desktopPane.setSize(500, 500)

  innerFrame.setVisible(true)
  desktopPane.setVisible(true)

  val frame = new JFrame
  frame.setSize(500, 500)

  frame.add(desktopPane)

  frame.setVisible(true)
  def main(args: Array[String]) {
  }

}