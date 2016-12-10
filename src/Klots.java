import javafx.scene.shape.Rectangle;

public class Klots extends Rectangle{
    private static final int deltaX = 15;

    public Klots() {
        super.setX(450);
        super.setY(760);
        super.setWidth(150);
        super.setHeight(25);
        super.setArcWidth(20);
        super.setArcHeight(20);
    }

    public void liigu(double platsiLaius, int suund){ //suund: 0-paremale,1-vasakule
        double x = this.getX();
        if (suund == 0) {
            if (x > platsiLaius - this.getWidth()) {
                this.setX(x);
            } else {this.setX(x + deltaX);}
        }
        else if (suund == 1){
            if (x < 0) {
                this.setX(x);
            } else {this.setX(x - deltaX);}
        }
    }

}