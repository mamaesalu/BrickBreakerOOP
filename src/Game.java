import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.Iterator;


public class Game {
    public Pane plats;
    private Pall pall;
    Timeline animation;
    Klots klots;
    private Tellised tellised;
    int tellisteArv = 3;
    int ridadeArv = 2;
    Scene scene;
    int level = 1;
    Label tase;
    Label skoorHetkel;
    int skoor = 0;
    Image levelxtaust;
    Stage vajutaSpace;
    double platsiLaius = 1000;

    public Game() {
        gamestage();
        playlevel();
    }

    private void gamestage() {
        plats = new Pane();
        scene = new Scene(plats, platsiLaius, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreakerOOP");
        stage.setResizable(false);
        stage.show();
    }

    private void playlevel(){
        kuvaTaustJaTase();
        looLiikuvKlots();
        looTellised(tellisteArv, ridadeArv);
        looPall();
    }

    private void kuvaTaustJaTase() {
        switch (level) {
            case 1:
                levelxtaust = new Image("level1taust.png");
                break;
            case 2:
                levelxtaust = new Image("level2taust.png");
                break;
            case 3:
                levelxtaust = new Image("level3taust.png");
        }
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage leveltaust = new BackgroundImage(levelxtaust,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, size);
        plats.setBackground(new Background(leveltaust));
        tase = new Label("Tase " + level);
        tase.setFont(Font.font("Calibri", 25));
        tase.setTextFill(Color.GREY);
        tase.setLayoutY(plats.getHeight()-100);
        plats.getChildren().add(tase);
    }

    private void looLiikuvKlots() {
        klots = new Klots();
        plats.getChildren().add(klots);
        klots.setOnKeyPressed(e -> {
            if (e.getCode() == (KeyCode.RIGHT)) {
                int suund = 0;
                klots.liigu(plats.getWidth(), suund);
            } else if (e.getCode() == (KeyCode.LEFT)) {
                int suund = 1;
                klots.liigu(plats.getWidth(), suund);
            }
        });
        klots.setFocusTraversable(true);
    }

    public void looTellised(int tellisteArv, int ridadeArv) {
        tellised = new Tellised();
        int tellisteVahe = 8;
        double tellisteSuurusX = ((plats.getWidth() - ((tellisteArv + 1) * tellisteVahe)) / tellisteArv);
        int tellisteSuurusY = 25;

        for (int i = 0; i < ridadeArv; i++) {
            for (int j = 0; j < tellisteArv; j++) {
                Rectangle tellis = new Rectangle(tellisteSuurusX, tellisteSuurusY);
                double tellisX = tellisteVahe + j * (tellisteVahe + tellisteSuurusX);
                double tellisY = tellisteVahe + i * (tellisteVahe + tellisteSuurusY);
                tellis.setX(tellisX);
                tellis.setY(tellisY);
                tellis.setId(Integer.toString((i+1)*(j+1)));
                tellis.setFill(Color.DARKOLIVEGREEN);
                tellised.add(tellis);
            }
        }
        plats.getChildren().addAll(tellised);
        System.out.println(tellised);
        System.out.println(tellised.size() + " tellist platsil");
    }

    public void looPall() {
        pall = new Pall();
        plats.getChildren().add(pall);
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> liigutaPalli()));
        animation.setCycleCount(Animation.INDEFINITE);
        vajutaSpace();
    }

    private void vajutaSpace() {
        vajutaSpace = new Stage();
        VBox vbox = new VBox();
        Scene sceneVajutaSpace = new Scene(vbox, 300, 100);
        Label label = new Label("Tase " + level+ ".");
        Label label2 = new Label("Alustamiseks vajuta tühikut");
        Label label3 = new Label("Mängi vasak-parem nooleklahvidega");
        label.setFont(Font.font("Calibri", 24));
        vbox.getChildren().addAll(label, label2, label3);
        vajutaSpace.initStyle(StageStyle.UNDECORATED);
        vajutaSpace.setScene(sceneVajutaSpace);
        vajutaSpace.show();
        sceneVajutaSpace.setOnKeyPressed(e-> {
                        if (e.getCode()== KeyCode.SPACE) {
                            vajutaSpace.close();
                            animation.play();
                        }
                });
    }

    protected void liigutaPalli() {
        kontrolliTellised();
        kontrolliKokkop6rget();
        kuvaSkoor();
        pall.liigu();
        LevelOver();
    }

    private void kuvaSkoor() {
        plats.getChildren().remove(skoorHetkel);
        skoorHetkel = new Label("Skoor " + skoor);
        skoorHetkel.setFont(Font.font("Calibri", 25));
        skoorHetkel.setTextFill(Color.GREY);
        skoorHetkel.setLayoutY(plats.getHeight()-100);
        skoorHetkel.setLayoutX(plats.getWidth()-100);
        plats.getChildren().add(skoorHetkel);
    }

    private void LevelOver() {
        if (tellised.size() == 0) {
            System.out.println("tellised otsas");
            if (level == 3) {
                youWin();
            }
            else {
                level++;
                animation.stop();
                plats.getChildren().removeAll(pall, klots, tase);
                tellisteArv = tellisteArv + 2; //lisa telliseid ritta +2
                ridadeArv++;//ridade arv +1
                pall.dx = Math.abs(pall.dx)+ 1;//lisa palli liikumise kiirust iga leveli vahetusega
                pall.dy = Math.abs(pall.dy)+ 1;
                playlevel();
            }
        }
    }

    private void youWin() {
        StackPane stack = new StackPane();
        Label teade = new Label("Hästi tehtud! Sinu võit skooriga " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }

    private void kontrolliTellised() {
        Iterator<Rectangle> tellisteIter = tellised.iterator();
        while (tellisteIter.hasNext()) {
            Rectangle seetellis = tellisteIter.next();
            if (pall.intersects(seetellis.getLayoutBounds())) {
                pall.dy = pall.dy * -1;
                skoor++;
                tellisteIter.remove();
                plats.getChildren().remove(seetellis);
            }
        }
    }

    public void kontrolliKokkop6rget() {
        if (pall.getPallx() < pall.getPallr() || pall.getPallx() > plats.getWidth() - pall.getPallr()) {
            pall.dx = pall.dx * -1;
        }
        if (pall.getPally() < pall.getPallr() || pall.intersects(klots.getLayoutBounds())) {
            pall.dy = pall.dy * -1;
        }
        else if (pall.getPally() > plats.getHeight()) {
            youLose();
            animation.stop();
        }
    }

    private void youLose() {
        StackPane stack = new StackPane();
        Label teade = new Label("Mäng läbi! Sinu skoor: " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }
}