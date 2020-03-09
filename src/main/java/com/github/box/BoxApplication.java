package com.github.box;

import com.github.box.screen.Splash;
import com.github.box.view.LoginView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoxApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(BoxApplication.class, LoginView.class, new Splash(), args);
    }
    
}
