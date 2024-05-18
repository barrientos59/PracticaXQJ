import javax.xml.xquery.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer la conexión con la base de datos eXistDB
            XQDataSource dataSource = new net.xqj.exist.ExistXQDataSource();
            dataSource.setProperty("serverName", "localhost");
            dataSource.setProperty("port", "8080");
            XQConnection connection = dataSource.getConnection("admin", "");

            // Crear un objeto Scanner para leer la entrada del usuario
            Scanner scanner = new Scanner(System.in);

            // Crear una instancia de GestorDB pasando la conexión y el scanner
            GestorDB gestor = new GestorDB(connection, scanner);

            // Crear una instancia de Menu para mostrar el menú
            Menu menu = new Menu();

            boolean salir = false;

            while (!salir) {
                menu.mostrarMenu();
                System.out.print("Ingrese una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        gestor.mostrarDatos();
                        break;
                    case 2:
                        gestor.insertarJugador();
                        break;
                    case 3:
                        gestor.insertarEquipo();
                        break;
                    case 4:
                        gestor.modificarDatos();
                        break;
                    case 5:
                        gestor.borrarDatos();
                        break;
                    case 6:
                        gestor.consultarJugadoresPorEquipo();
                        break;
                    case 7:
                        gestor.consultarJugadoresPorSalarioMayor();
                        break;
                    case 8:
                        gestor.consultarEquipos();
                        break;
                    case 9:
                        gestor.consultarJugadoresPorPosicion();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            }

            // Cerrar el scanner y la conexión
            scanner.close();
            connection.close();
        } catch (XQException e) {
            e.printStackTrace();
        }
    }
}
