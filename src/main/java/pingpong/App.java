package pingpong;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.util.Random;


public final class App extends Application {

    private static Stage stage;
    private static Group root;

    private static final Rectangle player1 = new Rectangle();
    private static final Rectangle player2 = new Rectangle();
    private static final Circle ball = new Circle();

    private static Label player1Score;
    private static Label player2Score;
    private static Label info;

    private static final double playerSpeed = 3;

    private static byte player1Direction = 1;
    private static byte player2Direction = 1;

    private static final double ballBaseXSpeed = 2;
    private static final double ballBaseYSpeed = 2;
    private static double ballXSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];
    private static double ballYSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];

    // private static final Label player1Score = new Label("0");
    // private static final Label player2Score = new Label("0");
    // private static final Label info = new Label("Left player - space, right player - left mouse button");


    private static Group createBasicRoot() {
        App.root = new Group(App.player1, App.player2, App.ball); return App.root;
    }

    private static Scene createBasicScene() {
        return new Scene(App.createBasicRoot(), 1200, 800, true, SceneAntialiasing.BALANCED);
    }

    private static void setup(Stage stage) {
        App.stage = stage;
        App.stage.setTitle("PingPong [v1.0.0]");
        App.stage.setScene(App.createBasicScene());
        App.stage.getScene().setFill(Color.web("#111111ff"));
        App.stage.getScene().setCamera(new ParallelCamera());
        App.stage.setFullScreen(false);
        App.stage.setAlwaysOnTop(false);
        App.stage.setResizable(false);
        App.stage.setMinWidth(500);
        App.stage.setMinHeight(400);
        App.stage.getScene().setCursor(Cursor.DEFAULT);

        App.player1.setFill(Color.web("#eeeeeeff"));
        App.player1.setWidth(5);
        App.player1.setHeight(90);
        App.player1.setX(0);
        App.player1.setY(App.stage.getScene().getHeight() * 0.5 - App.player1.getHeight() * 0.5);

        App.player2.setFill(Color.web("#eeeeeeff"));
        App.player2.setWidth(5);
        App.player2.setHeight(90);
        App.player2.setX(App.stage.getScene().getWidth() - App.player2.getWidth());
        App.player2.setY(App.stage.getScene().getHeight() * 0.5 - App.player2.getHeight() * 0.5);

        App.ball.setFill(Color.web("#eeeeeeff"));
        App.ball.setRadius(7);
        App.ball.setCenterX(App.stage.getScene().getWidth() * 0.5);
        App.ball.setCenterY(App.stage.getScene().getHeight() * 0.5);

        App.player1Score = new Label("0");
        App.root.getChildren().add(App.player1Score);
        App.player1Score.setTranslateX(100 + App.player1Score.getWidth() * 0.5);
        App.player1Score.setTranslateY(50);
        App.player1Score.setFont(Font.font(40));
        App.player1Score.setTextFill(Color.web("#aaaaaaff"));

        App.player2Score = new Label("0");
        App.root.getChildren().add(App.player2Score);
        App.player2Score.setTranslateX(App.stage.getScene().getWidth() - 100 - 40 * 0.5);
        App.player2Score.setTranslateY(50);
        App.player2Score.setFont(Font.font(40));
        App.player2Score.setTextFill(Color.web("#aaaaaaff"));

        App.info = new Label("Left player - space, right player - left mouse button");
        App.root.getChildren().add(App.info);
        App.info.setTranslateX(App.stage.getScene().getWidth() * 0.5 - App.info.getText().length() * 4.2);
        App.info.setTranslateY(50);
        App.info.setFont(Font.font(20));
        App.info.setTextFill(Color.web("#aaaaaaff"));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> Platform.runLater(() -> {
            if (App.player1.getY() > 0 && App.player1Direction == -1) {
                App.player1.setY(App.player1.getY() - App.playerSpeed);
            } else if (App.player1.getY() < App.stage.getScene().getHeight() -
                    App.player1.getHeight() && App.player1Direction == 1) {
                App.player1.setY(App.player1.getY() + App.playerSpeed);
            }

            if (App.player2.getY() > 0 && App.player2Direction == -1) {
                App.player2.setY(App.player2.getY() - App.playerSpeed);
            } else if (App.player2.getY() < App.stage.getScene().getHeight() -
                    App.player2.getHeight() && App.player2Direction == 1) {
                App.player2.setY(App.player2.getY() + App.playerSpeed);
            }

            if (App.ball.getCenterX() - App.ball.getRadius() < 0) {
                App.ballXSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];
                App.ballYSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];
                App.ball.setCenterX(App.stage.getScene().getWidth() * 0.5);
                App.ball.setCenterY(App.stage.getScene().getHeight() * 0.5);
                App.player1.setY(App.stage.getScene().getHeight() * 0.5 - App.player1.getHeight() * 0.5);
                App.player2.setY(App.stage.getScene().getHeight() * 0.5 - App.player2.getHeight() * 0.5);
                App.player2Score.setText(String.valueOf((Integer.parseInt(App.player2Score.getText()) + 1)));
            } else if (App.ball.getCenterX() + App.ball.getRadius() > App.root.getScene().getWidth()) {
                App.ballXSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];
                App.ballYSpeed = ballBaseXSpeed * new byte[] {-1, 1}[new Random().nextInt(2)];
                App.ball.setCenterX(App.stage.getScene().getWidth() * 0.5);
                App.ball.setCenterY(App.stage.getScene().getHeight() * 0.5);
                App.player1.setY(App.stage.getScene().getHeight() * 0.5 - App.player1.getHeight() * 0.5);
                App.player2.setY(App.stage.getScene().getHeight() * 0.5 - App.player2.getHeight() * 0.5);
                App.player1Score.setText(String.valueOf((Integer.parseInt(App.player1Score.getText()) + 1)));
            }

            if (App.ball.getCenterY() - App.ball.getRadius() < 0) {
                App.ballYSpeed *= -1;
            } else if (App.ball.getCenterY() + App.ball.getRadius() > App.root.getScene().getHeight()) {
                App.ballYSpeed *= -1;
            }

            if (
                    App.ball.getCenterY() - App.ball.getRadius() >= App.player1.getY() &&
                    App.ball.getCenterY() + App.ball.getRadius() <= App.player1.getY() + App.player1.getHeight() &&
                    App.ball.getCenterX() - App.ball.getRadius() <= App.player1.getX() + App.player1.getWidth() &&
                    App.ballXSpeed < 0
            ) {
                App.ballXSpeed *= -1;
                // App.ballYSpeed = (App.ball.getCenterY() - App.player1.getY() - App.player1.getHeight() * 0.5) *
                //         App.ballBaseYSpeed / (App.player1.getHeight() * 0.5) * 2;
                // App.ballXSpeed = Math.max(App.ballBaseXSpeed + App.ballBaseYSpeed - App.ballYSpeed, 1);
                // System.out.println(App.ballYSpeed);
            }
            if (
                    App.ball.getCenterY() - App.ball.getRadius() >= App.player2.getY() &&
                    App.ball.getCenterY() + App.ball.getRadius() <= App.player2.getY() + App.player2.getHeight() &&
                    App.ball.getCenterX() + App.ball.getRadius() >= App.player2.getX() &&
                    App.ballXSpeed > 0
            ) {
                App.ballXSpeed *= -1;
                // App.ballYSpeed = (App.ball.getCenterY() - App.player2.getY() - App.player2.getHeight() * 0.5) *
                //         App.ballBaseYSpeed / (App.player2.getHeight() * 0.5) * 2;
                // App.ballXSpeed = Math.max(Math.min(App.ballYSpeed - App.ballBaseYSpeed - App.ballBaseXSpeed, -3), -1);
                // System.out.println(App.ballYSpeed);
            }

            App.ball.setCenterX(App.ball.getCenterX() + App.ballXSpeed);
            App.ball.setCenterY(App.ball.getCenterY() + App.ballYSpeed);
        }))); timeline.setCycleCount(-1); timeline.play();

        App.root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.SPACE)) {App.player1Direction *= -1;}
        });
        App.root.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {App.player2Direction *= -1;}
        });

        App.stage.show();
    }

    @Override
    public void start(Stage stage) {
        // Platform.startup(() -> {
        //     App.setup(stage);
        // });
        new Pane();
        App.setup(stage);
    }

    @Override
    public void stop() {
        App.stage.close();
    }

    public static void main(String[] args) {
        App.launch(args);
    }

}
