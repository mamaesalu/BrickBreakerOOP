import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pall extends Circle{
    private double r = 20;
    private double x = r+200;
    private double y = r+200;
    static int dx = 2;
    static int dy = 2;

    public Pall() {
        super.setCenterX(x);
        super.setCenterY(y);
        super.setRadius(r);
        super.setFill(Color.DARKBLUE);
        super.setStroke(Color.CORAL);
    }

    public void liigu(){
        x+=dx;
        super.setCenterX(x);
        y+=dy;
        super.setCenterY(y);
    }

    public double getPallr(){
        return r;
    }

    public double getPallx(){
        return x;
    }

    public double getPally(){ return y; }
}