package secondTry

import collection.immutable
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.Color
import java.awt.Font
class Pithoi {
  
  case class pithos(name:String, var bool:Boolean, location:Rectangle)
  
  def drawPithBoxes(g:Graphics2D,pithoi:List[pithos]){
    for (p <- pithoi){
      if(p.bool == true){
        g.setColor(Color.WHITE)
        g.fill(new Rectangle(p.location.x-40,p.location.y-15,140,10))
        g.setColor(Color.BLACK)
        g.draw(new Rectangle(p.location.x-40,p.location.y-15,140,10))
        val font = new Font("Arial", Font.PLAIN, 8)
        g.setFont(font)
        g.drawString("Pithos " + p.name + ": Right Click for Contents" ,p.location.x-38,p.location.y-7)
      }
    }
    
  }
}