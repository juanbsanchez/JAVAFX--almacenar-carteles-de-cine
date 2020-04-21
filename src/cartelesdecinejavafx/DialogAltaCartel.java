/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartelesdecinejavafx;

import java.io.File;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Juan José Burgos Sánchez
 */
public class DialogAltaCartel extends Stage{
    
     /**{@value #ESTILOS_BOTONES}  Estilos de los botones del formulario para añadir el cartel*/
    private static final String ESTILOS_BOTONES = "-fx-border-color: #b5b5b5;"
            + "-fx-background-color: #f9e0c5;"
            + "-fx-border-radius: 4px;";
    
     /**{@value #ESTILOS_FORMULARIO}  Estilos de los botones del formulario para añadir el cartel*/
    private static final String ESTILOS_FORMULARIO ="-fx-background-color: #f9e0c5 ";
    
    /** 
     * Variable que almacena el título del cartel
     */
    private TextField inputTituloCartel = new TextField();
    
    /** 
     *Variable que almacen el año del cartel
     */
    private TextField inputAnyoCartel = new TextField();
    
    /** 
     * Variable que almacen la ruta de la imagen del cartel
     */
    private TextField inputRutaCartel = new TextField();
    
    /** 
     * Variable para comprobar si el formulario ha sido cerrado con el botón cancelar
     */
    private boolean cancelado = true;
    
    /** 
     * Objeto de la clase cartel que almacen el cartel a guardar
     */
    private Cartel cartel;
      
     /**
     * Constructor de la clase que genera el formulario para añadir el cartel
     * @param parentStage Ventana padre 
     */
    public DialogAltaCartel (Stage parentStage)  {        
       
        //Modalidad "MODAL": bloquear eventos para que no vayan a otras venanas
        initModality(Modality.APPLICATION_MODAL);
        // Establecemos que el propietario es la clase padre
        initOwner(parentStage.getOwner());        
        // Establecer modo de ventana no redimensionable
        resizableProperty().setValue(false);
        // Establecer título de la ventana
        this.setTitle("Añadir cartel");
        
        //Creamos un contenedor para el nuevo formulario
        GridPane addCartelFormulario = new GridPane();
        addCartelFormulario.setStyle("-fx-background-color: #ffefdc");
        
        //Establecemos un padding para el formulario
        addCartelFormulario.setPadding(new Insets(10));
        
        //Establecemos la separación de los elementos del formulario
        addCartelFormulario.setHgap(5);
        addCartelFormulario.setVgap(10);

        //Titulo cartel
        Label tituloCartelEtiqueta = new Label("Título:");
        inputTituloCartel.setPrefWidth(580);

        //Año cartel
        Label anyoCartelEtiqueta = new Label("Año:");
        inputAnyoCartel.setMaxWidth(80);

        //Ruta imagen
        Label imagenCartelEtiqueta = new Label("Ruta imagen:");

        //Usamos la clase FileChooser para abrir una ventana y seleccionar una imagen
        FileChooser elegirImagen = new FileChooser();
        //Título de la ventana
        elegirImagen.setTitle("Seleccionar imagen");
        //Variable donde guardaremos la ruta del usuario del sistema
        String userDirectoryString = System.getProperty("user.home");
        //Sino obtenemos la ruta, establecemos la ruta a partir de la raiz de la aplicacion
         if(userDirectoryString == null){
            userDirectoryString = ".";
        }
        //Concatenamos la ruta del sistema con la carpeta donde se encuentran las imágenes por defecto en Windows
        String path = Paths.get(userDirectoryString.concat("/Pictures")).toAbsolutePath().normalize().toString();
        elegirImagen.setInitialDirectory(new File(path));
        
        //Botón para que el usuario pueda añadir una imagen
        Button addCartelImagen = new Button("Elegir imagen");
        
        //Aplicamos los estilos al formulario
        addCartelImagen.setStyle(ESTILOS_FORMULARIO);
        
        //Establecemos un año para el input de la ruta del cartel
        inputRutaCartel.setMaxWidth(300);
        
        //Aplicamos estilos al botón de añadir imagen
        addCartelImagen.setStyle(ESTILOS_BOTONES);

        //Evento para el botón añadir cartel
        addCartelImagen.setOnAction((final ActionEvent e) -> {
            File file = elegirImagen.showOpenDialog(this);
            if (file != null) {
                //Obtenemos la ruta y la añadimos al campo de texto
                inputRutaCartel.setText(file.getPath());
            }
        });

        //Botones aceptar y cancelar del formulario
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        //Aplicamos estilos a los botones
        aceptar.setStyle(ESTILOS_BOTONES);
        cancelar.setStyle(ESTILOS_BOTONES);

        //Añadimos evento al botón cancelar para que cuando hagamos click se cierre la ventana del formulario
        cancelar.setOnAction((final ActionEvent e) -> {
            this.close();
        });

        //Añadimos evento al botón aceptar
        aceptar.setOnAction(a ->this.aceptar()); 

        //Añadimos los elementos del formulario al contenedor
        addCartelFormulario.add(tituloCartelEtiqueta, 0, 1);
        addCartelFormulario.add(inputTituloCartel, 1, 1);
        addCartelFormulario.add(anyoCartelEtiqueta, 0, 2);
        addCartelFormulario.add(inputAnyoCartel, 1, 2);
        addCartelFormulario.add(imagenCartelEtiqueta, 0, 3);
        addCartelFormulario.add(inputRutaCartel, 1, 3);
        addCartelFormulario.add(addCartelImagen, 2, 3);
        addCartelFormulario.add(aceptar, 0, 4);
        addCartelFormulario.add(cancelar, 1, 4);
        
        Scene addCartelFormularioWindow = new Scene(addCartelFormulario, 780, 160);
        setScene(addCartelFormularioWindow);

    }

     /**
     * Acciones a efectuar si se pulsa en el botón aceptar.
     */
    private void aceptar() {

        try {
            File fileImagen = new File(inputRutaCartel.getText());
            String nombreCartel = inputTituloCartel.getText();
            int anyo = Integer.parseInt(inputAnyoCartel.getText());
            String rutaCartel = inputRutaCartel.getText();
            if (nombreCartel.length()==0 || nombreCartel.isEmpty())  {
                mostrarErrorFormulario("El título no puede estar vacío");
            } else if (anyo<=0) {
                mostrarErrorFormulario("El año debe ser un número");
            }
            else if(!fileImagen.isFile()){
            mostrarErrorFormulario("La ruta no es correcta");
            }
            else if(rutaCartel.length()==0){
            mostrarErrorFormulario("La ruta no es correcta");
            }

            else  {
                // Creamos el cartel y asignamos los datos introducidos por el usuario
                cartel = new Cartel() ;
                cartel.setNombreCartel(nombreCartel);
                cartel.setAnyoCartel(anyo);
                cartel.setRutaCartel(rutaCartel);
                // Se han aceptado correctamente los datos introducidos
                cancelado = false ;
                // Cerrar esta ventana de alta
                close() ;
            }
        } catch (NumberFormatException e) {
            mostrarErrorFormulario("El año debe ser un número");
        }
    }    
    
    
     /**
     * Genera ventana para mostrar error en el formulario al intentar cargar el
     * cartel
     *
     * @param error mensaje de error
     */
    private void mostrarErrorFormulario(String error) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error en los datos.");
        alert.setContentText(error);
        alert.showAndWait();
    }
    
    /**
     * Este método se usa en la clase Main para saber si se aceptó y por tanto
     * se dio a aceptar en esta ventana de añadir un cartel o bien si se dio
     * a cancelar.
     * 
     * @return false si no se añadió el cartel, true si se añadió el cartel.
     */
    public boolean esCancelado()  {
        return cancelado;
    }
    
    /** 
     * Método que devuelve el objeto de tipo Cartel para añadir a la lista de carteles
     * @return objeto de tipo Cartel
     */
    public Cartel getCartel()  {
        return cartel;
    }
    
}
