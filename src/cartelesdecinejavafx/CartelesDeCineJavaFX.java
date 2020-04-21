
package cartelesdecinejavafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



/**
 *
 * @author Juan José Burgos Sánchez
 * 
 * Aplicación de interfaz gráfica para archivar carteles de cine digitalmente.
 */


public class CartelesDeCineJavaFX extends Application {
    

    // CONSTANTES
    /**
     * {@value #NOMBRE_ARCHIVO_ENTRADA_BIN_DEFAULT} Constante que almacena la
     * ruta donde se guarda la información de los carteles
     */
    private static final String NOMBRE_ARCHIVO_ENTRADA_BIN_DEFAULT = "carteles.dat";

    /**
     * Lista que contiene la información de los carteles para cargarlos y
     * guardarlos
     */
    List<Cartel> listaDeCarteles = new ArrayList();

    /**
     * Lista que contiene la información de los carteles de las películas
     * añadidas
     */
    ListView<String> carteles = new ListView();

    /**
     * Variable que usaremos para saber si se han producido cambios en la aplicación
     */
    boolean cambios = false;

    /**
     * Actualiza el layout que muestra la lista de carteles, borra el layout y
     * añade los datos de la lista.
     */
    private void actualizarLista() {
        // Limpiar el layout
        this.carteles.getItems().clear();
        // Añadir los objetos carteles al layout
        this.listaDeCarteles.forEach((p) -> {
            carteles.getItems().add(p.toString());
        });
    }

    //Contenedor principal
    FlowPane contenedor = new FlowPane(Orientation.HORIZONTAL);
    

    //Contenedor para la lista de carteles
    FlowPane flow = new FlowPane(Orientation.HORIZONTAL);

    //Contenedor para la imagen del cartel
    FlowPane imagenCartel = new FlowPane(Orientation.VERTICAL);

    //Creamos una etiqueta que usaremos si la imagen a mostrar no se ha encontrado o no es válida
    Label mensajeError = new Label("No se ha encontrado la imagen o la imagen no es válida");
    
    @Override
    public void start(Stage primaryStage){
        
        //Establecemos para que no se pueda cambiar el tamaño de la pantalla que vamos a predefinir
        primaryStage.setResizable(false);

        //Medidas de la ventana de la aplicación
        final int anchoVentana = 800;
        final int altoVentana = 580;

        //Medidas de la lista de carteles
        final int anchoLista = 420;
        final int altoLista = 540;

        //Aplicamos las medidas al layout de la aplicación
        contenedor.setPrefWidth(anchoVentana);
        contenedor.setPrefHeight(altoVentana);
        

        //Creamos la barra de menú
        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);
        

        // Crear un menú llamado Archivo
        Menu archivo = new Menu("Archivo");

        // Creamos dos items para el menú Archivo      
        MenuItem guardar = new MenuItem("Guardar");
        MenuItem cerrar = new MenuItem("Cerrar");

        // Añadimos los items al menú Archivo
        archivo.getItems().add(guardar);
        archivo.getItems().add(cerrar);

        //Creamos un menú Edición
        Menu edicion = new Menu("Edición");

        //Creamos dos items para el menú edición
        MenuItem addCartel = new MenuItem("Añadir");
        MenuItem deleteCartel = new MenuItem("Borrar");

        //Añadimos los items al menú Edición
        edicion.getItems().add(addCartel);
        edicion.getItems().add(deleteCartel);

        //Creamos un botón para ver el cartel
        Button verCartel = new Button("Ver cartel");

        // Espacio entre filas y columnas del contenedor de la lista de carteles
        flow.setVgap(8);
        flow.setHgap(4);

        // Ancho preferido
        flow.setPrefWrapLength(300);

        // Añadimos los menús a la barra de menú
        menuBar.getMenus().add(archivo);
        menuBar.getMenus().add(edicion);

        //Establecemos el ancho preferido de la barra de menú
        menuBar.setPrefWidth(anchoVentana);

        //Creamos etiqueta para la lista de películas
        Label lblPeliculas = new Label("Película   -   Año   -   Ruta del cartel");

        // Establecemos tamaño para la lista de carteles
        carteles.setPrefWidth(anchoLista);
        carteles.setPrefHeight(altoLista);

        //Añadimos la lista y etiqueta al layout
        flow.getChildren().add(lblPeliculas);
        flow.getChildren().add(carteles);
        flow.getChildren().add(verCartel);

        //Establecemos un padding para el contenedor de la lista de carteles
        flow.setPadding(new Insets(20));

        //Añadimos al contenedor principal los demás contenedores
        contenedor.getChildren().add(vBox);
        contenedor.getChildren().add(flow);
        contenedor.getChildren().add(imagenCartel);

        // Establecemos propiedades del escenario
        primaryStage.setTitle("Lista de carteles de películas de cine");

        // Creamos escena
        Scene scene1 = new Scene(contenedor);
        
        //LISTENER
        //Añadimos un Listener a la lista de carteles para detectar si ha habido un cambio en el item seleccionado
        carteles.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Si ha habido un cambio, procedemos a borrar la imagen del cartel mostrado
                imagenCartel.getChildren().clear();
            }
        });


        // EVENTOS
        //Evento para el botón añadir
        addCartel.setOnAction((a) -> {
            DialogAltaCartel ventanaAlta = new DialogAltaCartel(primaryStage);
            ventanaAlta.showAndWait();

            //Si en la ventana de alta no se dio a cancelar
            if (!ventanaAlta.esCancelado()) {
                listaDeCarteles.add(ventanaAlta.getCartel());
                actualizarLista();
                cambios = true;
            }
        });

        //Evento para borrar un cartel
        deleteCartel.setOnAction((a) -> {
            if (!listaDeCarteles.isEmpty()) {
                //Guardamos el índice en una variable
                int index = carteles.getSelectionModel().getSelectedIndex();
                //Comprobamos que hay un elemento seleccionado si existe un índice mayor o igual que 0
                if (index >= 0) {
                    //Mostramos mensajes de confirmación para eliminar el elemento seleccionado
                    Alert aviso = new Alert(AlertType.CONFIRMATION, "¿Seguro que desea borrar la línea seleccionada?", ButtonType.OK, ButtonType.CANCEL);
                    aviso.setHeaderText(null);
                    Optional<ButtonType> result = aviso.showAndWait();
                    if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                        //Si existe un índice seleccionado, borramos el elemento que está en dicho índice
                        carteles.getItems().remove(index);
                        listaDeCarteles.remove(index);
                        imagenCartel.getChildren().clear();
                        cambios = true;
                    }
                }
            }
        });

        //Evento para ver el cartel
        verCartel.setOnAction((ActionEvent a) -> {
            int index = carteles.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                imagenCartel.getChildren().clear();
                String rutaImagen = listaDeCarteles.get(index).getRutaCartel();
                Image imagenCartelAMostrar = new Image(new File(rutaImagen).toURI().toString());
                ImageView imageView = new ImageView(imagenCartelAMostrar);

                if (imagenCartel.getChildren().isEmpty() && !imagenCartelAMostrar.errorProperty().get()) {
                    
                    //Establecemos el tamaño de la imagen para mostrar
                    imageView.setFitWidth(400);
                    imageView.setFitHeight(240);
                    
                    //Para que la imagen no se deforme
                    imageView.setPreserveRatio(true);
                    
                    //Establecemos un padding superior
                    imagenCartel.setPadding(new Insets(44, 0, 0, 0));

                    imagenCartel.setAlignment(Pos.TOP_CENTER);
                    imagenCartel.getChildren().add(imageView);

                } else {
                    mensajeError.setAlignment(Pos.CENTER);
                    imagenCartel.setPrefWidth(300);
                    imagenCartel.setAlignment(Pos.CENTER);
                    imagenCartel.getChildren().add(mensajeError);
                }
            }
        });

        //Botón para guardar
        guardar.setOnAction((final ActionEvent a) -> {
            guardarDocumento(listaDeCarteles, "carteles.dat");
            Alert aviso = new Alert(AlertType.INFORMATION, "Datos guardados correctamente");
            aviso.showAndWait();
            actualizarLista();
            cambios = false;
        });

        //Botón cerrar
        cerrar.setOnAction((a) -> {
            // Si ha habido cambios, mostramos alerta al usuario para informarle de si quiere salir sin guardar
            if (cambios) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.CANCEL);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.setTitle("Salir de la aplicación");
                alert.setContentText(String.format("¿Salir sin guardar los cambios?"));
                alert.initOwner(primaryStage.getOwner());
                Optional<ButtonType> res = alert.showAndWait();

                if (res.isPresent()) {
                    if (res.get().equals(ButtonType.CANCEL)) {
                        a.consume();
                    }
                    if (res.get().equals(ButtonType.YES)) {
                        primaryStage.close();
                    }
                }
            } else {
                primaryStage.close();
            }
        });

        // Intentamos cargar el documento con la información de los carteles
        FileInputStream fnew = null;
        try {
            // Creamos un nuevo objeto File con la ruta hacia el fichero
            fnew = new FileInputStream(NOMBRE_ARCHIVO_ENTRADA_BIN_DEFAULT);

            //Creamos el flujo de datos de entrada
            ObjectInputStream recuperando_fichero = new ObjectInputStream(fnew);

            //Leemos el objeto y lo cargamos en la lista de carteles
            listaDeCarteles = (List<Cartel>) recuperando_fichero.readObject();

            //Actualizamos la lista
            actualizarLista();

            //Mostramos la aplicación
            primaryStage.setScene(scene1);
            primaryStage.show();
        } // Si no existe el documento, capturamos la excepción
        catch (FileNotFoundException e) {

            // Mostramos ventana de aviso
            mostrarAvisoInicioAplicacion("No se cargaron datos previos en la aplicación. \n Fichero no encontrado o vacío");

            //Intentamos guardar el documento
            guardarDocumento(listaDeCarteles, "carteles.dat");

            // Cargamos la aplicación
            primaryStage.setTitle("Lista de carteles de películas de cine");
            primaryStage.setScene(scene1);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Ha ocurrido un error al intentar abrir el fichero. Es posible que no se encuentre el fichero o la unidad de almacenamiento a la que se intenta acceder no es accesible.");
        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido un error: La clase del fichero no es válida.");
        } finally {
            if (fnew != null) {
                try {
                    fnew.close();
                } catch (IOException e) {
                    System.err.println("Ha ocurrido un error al intentar abrir el fichero. Es posible que no se encuentre el fichero o la unidad de almacenamiento a la que se intenta acceder no es accesible.");
                }
            }
        }
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> this.closeWindowEvent(e, primaryStage));
    }

    /**
     * Genera ventana para mostrar aviso de que no hay datos cargados previos en
     * la aplicación o no se ha encontrado el fichero de datos a cargar
     *
     * @param error mensaje de error
     */
    private void mostrarAvisoInicioAplicacion(String error) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error en los datos.");
        alert.setContentText(error);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Función para guardar un objeto serializable
     *
     * @param serObj objeto serializable a guardar
     * @param path ruta donde se va a guardar el objeto
     */
    private void guardarDocumento(Object serObj, String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(serObj);
            }
            System.out.println("Fichero guardado correctamente");
        } catch (FileNotFoundException ex) {
            System.err.println("Ha ocurrido un error. No se ha podido guardar el fichero");
        } catch (IOException ex) {
            System.err.println("Ha ocurrido un error al intentar guardar el fichero. Es posible que no se encuentre el fichero o la unidad de almacenamiento a la que se intenta acceder no es accesible.");
        }
    }

    /**
     * Función para detectar si el usuario cierra la aplicación haciendo click
     * en la aspa de la ventana principal que aparece en la parte superior
     * derecha y no se han guardado los cambios.
     *
     * @event
     * @stage
     */
    private void closeWindowEvent(WindowEvent event, Stage stage) {
        // Si hay cambios en la clase, avisamos al usuario con una ventanita
        if (cambios) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("Salir de la aplicación");
            alert.setContentText(String.format("¿Salir sin guardar los cambios?"));
            alert.initOwner(stage.getOwner());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.isPresent()) {
                if (res.get().equals(ButtonType.CANCEL)) {
                    event.consume();
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
