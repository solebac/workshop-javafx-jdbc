package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DBException;
import entities.service.SellerService;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;

public class SellerFormController implements Initializable {

	private Seller seller;
	private SellerService service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		//Valida inject
		if (seller == null) {
			throw new IllegalStateException("Entities was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			seller = getFormData();
			service.saveOrUpdate(seller);
			notifyDataChangelistener();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErroMessage(e.getErrors());
		}
		catch (DBException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		//Test System.out.println("onBtSaveAction");
	}
	private void notifyDataChangelistener() {
		// Implements the interface onDataChangeListener
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
		
	}
	private Seller getFormData() {
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Validation error.");
		//1-Sem validation
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		//2-Validation
		if(txtName.getText() == null || txtName.getText().trim().equals("")) 
		{
			exception.addErrors("name", "Field can't be empty.");
		}
		obj.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
		//System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 20);
		
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void updateFormData() {
		if (seller == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(seller.getId()));
		txtName.setText(seller.getName());
	}
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	public void setErroMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
}
