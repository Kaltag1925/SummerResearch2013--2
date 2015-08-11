package firstTry

import scala.swing.MainFrame
import scala.swing.Panel
import java.awt.Graphics2D
import java.awt.Dimension
import java.awt.RenderingHints
import java.awt.Image
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Toolkit
import java.awt.image.FilteredImageSource
import java.awt.Color
import java.awt.image.RGBImageFilter

object random {

  val bimg = ImageIO.read(new File("Images/sqr.gif"))
  class CustomImageFilter(color: Color) extends RGBImageFilter {
    val markerRGB = color.getRGB() | 0xFFFFFFFF

    def filterRGB(x: Int, y: Int, rgb: Int): Int = {
      if ((rgb | 0xFF000000) == markerRGB) {
        // Mark the alpha bits as zero - transparent  
        return 0x00FFFFFF & rgb
      } else {
        // nothing to do  
        return rgb
      }
    }
  }

  def makeColorTransparent(im: BufferedImage, color: Color): Image = {
    def filter = new CustomImageFilter(color)
    def ip = new FilteredImageSource(im.getSource(), filter)
    return Toolkit.getDefaultToolkit().createImage(ip)
  }

  val panel = new Panel {
    override def paint(g: Graphics2D) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.drawArc(100, 100, 100, 100, 75, 30)
      g.drawArc(162, 51, 100, 100, 180, 15)
      g.drawArc(35, 61, 100, 100, 5, 15)
      g.drawImage(makeColorTransparent(bimg,Color.white),0,0,null)
    }
  }

  val frame = new MainFrame {
    contents = panel
    size = new Dimension(500, 500)
    centerOnScreen
    visible = true
  }

  def main(args: Array[String]) {
  }
}