import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClickerSimulator extends Application {
	private AnimationTimer gameTimer;
	private ClickerEngine gameEngine;
	private PlayerProfile currentPlayer;
	private Stage loadDialog;
	private Stage mainWindow;
	private Scene gameScene;
	private Scene loadScene;
	private Scene titleScene;
	
	public void victory() {
		gameTimer.stop();
		currentPlayer.setTotalActions(currentPlayer.getTotalActions() + gameEngine.getActions());
		currentPlayer.setTotalTime(currentPlayer.getTotalTime() + gameEngine.getTime());
		currentPlayer.setTotalGames(currentPlayer.getTotalGames() + 1);
		currentPlayer.saveProfile();
		mainWindow.setScene(titleScene);
		mainWindow.setTitle("Clicker Simulator - " + currentPlayer.toString());
	}
	
	@Override
	public void start(Stage primaryStage) {
		currentPlayer = PlayerProfile.loadProfile("Default");
		mainWindow = primaryStage;
		
		Text titleText = new Text("Clicker Simulator");
		titleText.setFont(new Font(36));
		
		ImageView titleImageView = new ImageView(new Image("cursors.png"));
		titleImageView.setPreserveRatio(true);
		titleImageView.setFitHeight(180);
		
		Button startButton = new Button("Start Game");
		startButton.setOnAction(new StartHandler());
		
		Button loadButton = new Button("Load Profile");
		loadButton.setOnAction(new LoadHandler());
		
		Button aboutButton = new Button("How to Play");
		aboutButton.setOnAction(new AboutHandler());
		
		Button exitButton = new Button("Exit to Desktop");
		exitButton.setOnAction(e -> {
			if(currentPlayer != null) {
				currentPlayer.saveProfile();
			}
			Platform.exit();
		});
		
		GridPane.setHalignment(titleText, HPos.CENTER);
		GridPane.setHalignment(titleImageView, HPos.CENTER);
		GridPane.setHalignment(startButton, HPos.CENTER);
		GridPane.setHalignment(loadButton, HPos.CENTER);
		GridPane.setHalignment(aboutButton, HPos.CENTER);
		GridPane.setHalignment(exitButton, HPos.CENTER);
		
		startButton.setMinWidth(100.0);
		loadButton.setMinWidth(100.0);
		aboutButton.setMinWidth(100.0);
		exitButton.setMinWidth(100.0);
		
		GridPane titlePane = new GridPane();
		titlePane.setAlignment(Pos.CENTER);
		titlePane.setVgap(10);
		
		titlePane.add(titleText, 0, 0);
		titlePane.add(titleImageView, 0, 1);
		titlePane.add(startButton, 0, 2);
		titlePane.add(loadButton, 0, 3);
		titlePane.add(aboutButton, 0, 4);
		titlePane.add(exitButton, 0, 5);
		
		titleScene = new Scene(titlePane, 640, 480);
		
		mainWindow.setResizable(false);
		mainWindow.setScene(titleScene);
		mainWindow.setTitle("Clicker Simulator - " + currentPlayer.toString());
		mainWindow.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	//Handler for 'Start Game' button.
	class StartHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			GridPane gamePane = new GridPane();
			gamePane.setVgap(20);
			gamePane.setHgap(40);
			gamePane.setAlignment(Pos.CENTER);
			
			gameEngine = new ClickerEngine();
			
			Text pointsText = new Text("Points: " + gameEngine.getPoints());
			Text timeText = new Text("Time: " + gameEngine.getTime());
			GridPane.setHalignment(timeText, HPos.RIGHT);
			
			pointsText.setFont(new Font(12));
			timeText.setFont(new Font(12));
			
			Button activeBtn = new Button("+" + gameEngine.getActiveMod() + " Points!");
			GridPane.setColumnSpan(activeBtn, 3);
			GridPane.setRowSpan(activeBtn, 3);
			
			Button upgradeActiveBtn = new Button("Active Upgrade (x2): " + (int)(Math.pow(1.2, gameEngine.getActiveMod())) + "points");
			Button t1PassiveBtn = new Button("Tier 1 Passive\n" + "Cost: 5 points\n" + "Generates 1 point/sec");
			Button t2PassiveBtn = new Button("Tier 2 Passive\n" + "Cost: 25 points\n" + "Generates 2 points/sec");
			Button t3PassiveBtn = new Button("Tier 3 Passive\n" + "Cost: 125 points\n" + "Generates 7 points/sec");
			Button t4PassiveBtn = new Button("Tier 4 Passive\n" + "Cost: 625 points\n" + "Generates 32 points/sec");
			Button t5PassiveBtn = new Button("Tier 5 Passive\n" + "Cost: 3125 points\n" + "Generates 157 points/sec");
			Button gambleBtn = new Button("$$$ Gamble Points $$$\n" +
										"50 / 50 odds of winning x2 points!");
			gambleBtn.setTextAlignment(TextAlignment.CENTER);
			
			activeBtn.setMinWidth(300);
			upgradeActiveBtn.setMinWidth(200);
			t1PassiveBtn.setMinWidth(200);
			t2PassiveBtn.setMinWidth(200);
			t3PassiveBtn.setMinWidth(200);
			t4PassiveBtn.setMinWidth(200);
			t5PassiveBtn.setMinWidth(200);
			gambleBtn.setMinWidth(100);
			
			activeBtn.setMinHeight(100);
			upgradeActiveBtn.setMinHeight(50);
			t1PassiveBtn.setMinHeight(50);
			t2PassiveBtn.setMinHeight(50);
			t3PassiveBtn.setMinHeight(50);
			t4PassiveBtn.setMinHeight(50);
			t5PassiveBtn.setMinHeight(50);			
			gambleBtn.setMinHeight(25);
			
			activeBtn.setOnAction(f -> {
				gameEngine.addPoints();
				pointsText.setText("Points: " + gameEngine.getPoints());
			});
			
			upgradeActiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(0);
				activeBtn.setText("+" + gameEngine.getActiveMod() + " Points!");
				upgradeActiveBtn.setText("Active Upgrade (x" + (gameEngine.getActiveMod() + 1) + "): " 
											+ (int)(Math.pow(1.2, gameEngine.getActiveMod())) + "points");
				pointsText.setText("Points: " + gameEngine.getPoints());
			});
			
			t1PassiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(1);
				if(gameEngine.hasPassive(1)) {
					t1PassiveBtn.setText("SOLD!!!");
					pointsText.setText("Points: " + gameEngine.getPoints());
				}
			});
			
			t2PassiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(2);
				if(gameEngine.hasPassive(2)) {
					t2PassiveBtn.setText("SOLD!!!");
					pointsText.setText("Points: " + gameEngine.getPoints());
				}
			});
			
			t3PassiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(3);
				if(gameEngine.hasPassive(3)) {
					t3PassiveBtn.setText("SOLD!!!");
					pointsText.setText("Points: " + gameEngine.getPoints());
				}
			});
			
			t4PassiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(4);
				if(gameEngine.hasPassive(4)) {
					t4PassiveBtn.setText("SOLD!!!");
					pointsText.setText("Points: " + gameEngine.getPoints());
				}
			});
			
			t5PassiveBtn.setOnAction(f -> {
				gameEngine.buyUpgrade(5);
				if(gameEngine.hasPassive(5)) {
					t5PassiveBtn.setText("SOLD!!!");
					pointsText.setText("Points: " + gameEngine.getPoints());
				}
			});
			
			gambleBtn.setOnAction(f -> {
				gameEngine.gamble();
				pointsText.setText("Points: " + gameEngine.getPoints());
			});
			
			gamePane.add(pointsText, 0, 0);
			gamePane.add(timeText, 4, 0);
			gamePane.add(activeBtn, 0, 3);
			gamePane.add(upgradeActiveBtn, 0, 2);
			gamePane.add(t1PassiveBtn, 4, 2);
			gamePane.add(t2PassiveBtn, 4, 3);
			gamePane.add(t3PassiveBtn, 4, 4);
			gamePane.add(t4PassiveBtn, 4, 5);
			gamePane.add(t5PassiveBtn, 4, 6);
			gamePane.add(gambleBtn, 0, 7);
			
			gameScene = new Scene(gamePane, 640, 480);
			
			mainWindow.setScene(gameScene);
			
			gameTimer = new AnimationTimer() {
				private long past = 0;
				
				@Override
				public void handle(long now) {
					if(past != 0) {
						if (now > past + 1_000_000_000) {
							gameEngine.step();
							if(gameEngine.getPoints() >= 10000) {
								victory();
							} else {
								pointsText.setText("Points: " + gameEngine.getPoints());
								timeText.setText("Time: " + gameEngine.getTime());
							}
		                    past = now;
		                }
					} else {
						past = now;
					}
				}
			};
			gameTimer.start();
		}
	}
	
	//Handler for 'Load Profile' button.
	class LoadHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			loadDialog = new Stage();
			
			GridPane loadPane = new GridPane();
			loadPane.setAlignment(Pos.CENTER);
			loadPane.setVgap(5);
			
			Text loadText1 = new Text("Please enter the name of the profile you wish to load.");
			Text loadText2 = new Text("If the profile name given does not exist, then it will create a new one with that name.");
			Text loadText3 = new Text("If a profile is already loaded, then it will be saved before loading the new one.");
			
			TextField loadTextField = new TextField();
			
			Button loadProfileBtn = new Button("Load/Create Profile");
			loadProfileBtn.setOnAction(f -> {
				currentPlayer = PlayerProfile.loadProfile(loadTextField.getText());
				mainWindow.setTitle("Clicker Simulator - " + currentPlayer.toString());
				loadDialog.close();
			});
			
			loadPane.add(loadText1, 0, 1);
			loadPane.add(loadText2, 0, 2);
			loadPane.add(loadText3, 0, 3);
			loadPane.add(loadTextField, 0, 4);
			loadPane.add(loadProfileBtn, 0, 5);
			
			loadScene = new Scene(loadPane, 480, 160);
			
			loadDialog.setTitle("Load Profile");
			loadDialog.setResizable(false);
			loadDialog.setScene(loadScene);
			loadDialog.show();
		}
	}
	
	//Handler for 'How to Play' button.
	class AboutHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			FlowPane aboutPane = new FlowPane();
			
			Text howToPlayText = new Text("How to Play: "
					+ "Click on the '+1 Point' button and use collected points to purchase upgrades until you have 10,000. "
					+ "You can also gamble your points if you are feeling lucky and want to push for lower times. "
					+ "Your average time (in seconds) and average actions per minute are recorded to your profile which can be loaded in a later session. "
					+ "Try to get the lowest time you can and prove that you are a master of clicker games!\n");
			
			Text tipText = new Text("Some helpful tips: \n");
			Text tip1 = new Text("*  Your most important upgrade is your active point modifier. Some people can click as fast as 20x per second!\n");
			Text tip2 = new Text("*  Don't ignore passive point generators though. The lowest tier returns a point profit after 5 seconds!\n");
			Text tip3 = new Text("*  At some point, going straight for the 10,000 point goal is faster than purchasing more upgrades. Don't overproduce!");
			
			Button returnBtn = new Button("Main Menu");
			returnBtn.setMinWidth(100);
			FlowPane.setMargin(returnBtn, new Insets(240, 0, 0, 270));
			returnBtn.setOnAction(new ReturnHandler());
			
			howToPlayText.setWrappingWidth(640);
			tipText.setWrappingWidth(640);
			tip1.setWrappingWidth(640);
			tip2.setWrappingWidth(640);
			tip3.setWrappingWidth(640);
			
			howToPlayText.setFont(new Font(12));
			tipText.setFont(new Font(12));
			tip1.setFont(new Font(12));
			tip2.setFont(new Font(12));
			tip3.setFont(new Font(12));
			
			aboutPane.getChildren().addAll(howToPlayText, tipText, tip1, tip2, tip3, returnBtn);
						
			Scene aboutScene = new Scene(aboutPane, 640, 480);
			
			mainWindow.setScene(aboutScene);
		}
	}
	
	//Handler for 'Main Menu' button.
	class ReturnHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			mainWindow.setScene(titleScene);
		}
	}
}




