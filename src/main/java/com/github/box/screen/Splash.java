package com.github.box.screen;


import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;


public class Splash extends SplashScreen {

    public Parent getParent() {
        Group group = new Group();
        ImageView imageView = new ImageView(this.getClass().getResource(this.getImagePath()).toExternalForm());
        group.getChildren().add(imageView);
        return group;
    }
}
