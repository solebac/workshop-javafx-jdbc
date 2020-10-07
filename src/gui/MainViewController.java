package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import entities.service.DepartmentService;
import entities.service.SellerService;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController<T> implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartments;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setService(new SellerService());
			controller.updateTableView();});
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setService(new DepartmentService());
			controller.updateTableView();});
		//Teste loadView2("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		
	}

	@FXML
	private synchronized <T> void loadView(String path, Consumer<T> initializingAction) {
		try {
			//Load da nova View
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			VBox newBox = loader.load();
			//Para ter condiçao de trabalhar com o mainScene Main.java
			Scene mainScene = Main.getMainScene();
			//Hierarquia de Nodos
			//Pegando reference da janela principal
			VBox mainBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Preservar o MenuBar. .get(0) pega todo o menu
			Node mainMenu = mainBox.getChildren().get(0);
			// Limpar filhos do mainBox
			mainBox.getChildren().clear();
			//Adicionando novo Form para MainView
			mainBox.getChildren().add(mainMenu);
			mainBox.getChildren().addAll(newBox.getChildren());
			
			//Executa o Generics
			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IOExceptio", "Error loading view", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

	}
	
	//Recurso inativo
	@FXML
	private synchronized void loadView2(String path) {
		try {
			//Load da nova View
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			VBox newBox = loader.load();
			//Para ter condiçao de trabalhar com o mainScene Main.java
			Scene mainScene = Main.getMainScene();
			//Hierarquia de Nodos
			//Pegando reference da janela principal
			VBox mainBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//Preservar o MenuBar. .get(0) pega todo o menu
			Node mainMenu = mainBox.getChildren().get(0);
			// Limpar filhos do mainBox
			mainBox.getChildren().clear();
			//Adicionando novo Form para MainView
			mainBox.getChildren().add(mainMenu);
			mainBox.getChildren().addAll(newBox.getChildren());
			
			//TESTE PARA CARREGAR OS DADOS DO TABLEVIEW
			DepartmentListController controller = loader.getController();
			controller.setService(new DepartmentService());
			controller.updateTableView();
		} catch (IOException e) {
			Alerts.showAlert("IOExceptio", "Error loading view", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

	}

}
