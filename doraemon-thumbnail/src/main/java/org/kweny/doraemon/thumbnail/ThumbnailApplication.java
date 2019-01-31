/*
 * Copyright (C) 2018 Apenk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kweny.doraemon.thumbnail;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * TODO-Kweny ThumbnailApplication
 *
 * @author Kweny
 * @since TODO-Kweny version
 */
public class ThumbnailApplication extends Application {

    public static void main(String[] args) {
        launch(ThumbnailApplication.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);
        Button btn = new Button();
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("Hello World");
        btn.setOnAction( actionEvent -> System.out.println("Hello World"));
        root.getChildren().add(btn);
        stage.setScene(scene); stage.show();
    }
}