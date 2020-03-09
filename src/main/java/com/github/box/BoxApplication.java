package com.github.box;

import com.github.box.screen.Splash;
import com.github.box.view.LoginView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class BoxApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(BoxApplication.class, LoginView.class, new Splash(), args);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        return super.loadDefaultIcons();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

    @Override
    public void beforeShowingSplash(Stage splashStage) {
    }

}
