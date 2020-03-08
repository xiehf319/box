package com.github.tool.normal;

import com.github.tool.normal.screen.Splash;
import com.github.tool.normal.view.LoginView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NormalApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(NormalApplication.class, LoginView.class, new Splash(), args);
    }
    
}
