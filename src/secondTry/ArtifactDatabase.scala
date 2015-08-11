package secondTry

import javax.swing.UIManager
import scala.swing._
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
import java.awt.image.BufferedImage

class artifactDatabase {

  val xml = new projectXMLCode
  val artifacts = xml.getArtifacts

  UIManager.setLookAndFeel(new NimbusLookAndFeel)
 
  val recipeList = new ListView(List("Pop Tarts"))
  val ingredientList = new ListView(List("Pop Tarts"))
  //val directionsArea = new TextArea("Toast the poptarts ..\nor don't/")
  val ingredientNameField = new TextField("Pop Tart")
  val amountField = new TextField("1 packet")
  
  val ingredientListPanel = new BorderPanel {
    layout += (new GridPanel(1,2) {
      contents += Button("Add") (println("ADD"))
      contents += Button("Remove") (println("REMOVE"))
    } -> BorderPanel.Position.North)
    layout += (new ScrollPane(ingredientList) -> BorderPanel.Position.Center)
  }
  
  val ingredientDataPanel = new BorderPanel {
    layout += (new GridPanel(2,1){
      contents += new Label("Name")
      contents += new Label("Amount")
    } -> BorderPanel.Position.West)
    layout+= (new GridPanel(2,1) {
      contents += ingredientNameField
      contents += amountField
    } -> BorderPanel.Position.Center)
  }
  
  val frame = new MainFrame {
    contents = new BorderPanel {
      layout += (ingredientDataPanel -> BorderPanel.Position.Center)
      layout += (ingredientListPanel -> BorderPanel.Position.West)
    }
    maximize
    visible = true 
  }
  
}

object artifactDatabase {
  val artifactDatabase = new artifactDatabase
  def main(args: Array[String]) {
  }
}