package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

    private final int LENGTH = 60;
    private static boolean leftFormat = true;

    @FXML
    private Button buttonSend;

    @FXML
    private TextField textMessage;

    @FXML
    private FlowPane flowChat;

    @FXML
    private ScrollPane scrollMes;

    @FXML
    void addMessage(ActionEvent event) {

        String icon_admin = "src/img/admin-user-icon-48.png";
        String blank_admin = "src/img/blank-icon-164.png";
        String icon_user = "src/img/male-user-icon-48.png";
        String blank_user = "src/img/blank-icon-16.png";
        String style_user = "left-area";
        String style_admin = "right-area";
        String avatar;
        String blank;
        String style;
        String name;

        if (leftFormat){
            avatar = icon_user;
            blank = blank_user;
            style = style_user;
            name = "User";
        }else{
            avatar = icon_admin;
            blank = blank_admin;
            style = style_admin;
            name = "Admin";
        }

        TextArea area = new TextArea();
        area.setEditable(false);

        area.setText(makeMes(textMessage.getText(),name));
        area.getStyleClass().add(style);

        area.setPrefHeight(26.0 * ((1.0*textMessage.getText().length() + 6)/LENGTH + 5));
        area.setPrefWidth(445);

        FileInputStream input = null;
        try {
            input = new FileInputStream(blank);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        flowChat.getChildren().add(imageView);

        try {
            input = new FileInputStream(avatar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image2 = new Image(input);
        ImageView imageView2 = new ImageView(image2);
        flowChat.getChildren().add(imageView2);
        flowChat.getChildren().add(area);

        textMessage.clear();
        textMessage.requestFocus();

        flowChat.layout();
        scrollMes.layout();
        scrollMes.setVvalue(1.0d);

        leftFormat = !leftFormat;
    }

    public String makeMes(String line, String name){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date data = new Date();
        StringBuilder builder = new StringBuilder(line);
        String time = "\t\t\t\t\t\t\t" + format.format(data) + "\n\r\n\r";
        String user = "\n\r\t\t\t\t\t\t\t\t\t\t" + name;
        int n = LENGTH-3;
        int num = 0;
        int numCur = 0;
        int delta = 0;
        int len = line.length();
        while((len - numCur) >= LENGTH) {
            do{
                numCur = num;
                num = builder.indexOf(" ", num + 1);
            }while (num < n - delta && num > numCur);
            delta = n - numCur;
            builder.insert(numCur, System.lineSeparator());
            n += LENGTH;
            len += 2;
        }
        return time + "      " + builder.toString() + user;
    }
}
