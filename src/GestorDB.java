import javax.xml.xquery.*;
import java.util.Scanner;

public class GestorDB {
    private XQConnection connection;
    private Scanner scanner;

    public GestorDB(XQConnection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void mostrarDatos() throws XQException {
        // Consulta para mostrar datos de jugadores y equipos
        String query = "for $element in (doc('basket/nba.xml')//jugadores/jugador, doc('basket/nba.xml')//equipos/equipo) return $element";

        // Ejecutar la consulta
        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        // Procesar el resultado
        boolean hasResults = false;
        while (result.next()) {
            hasResults = true;
            // Obtener el item actual como un valor XML
            XQItem item = result.getItem();

            // Imprimir el valor XML como una cadena
            System.out.println(item.getItemAsString(null));
        }

        if (!hasResults) {
            System.out.println("No se encontraron resultados.");
        }
    }

    public void insertarJugador() throws XQException {
        // Solicitar datos al usuario
        System.out.print("Ingrese el código del jugador: ");
        String codigo = scanner.next();
        System.out.print("Ingrese el equipo del jugador: ");
        String equipo = scanner.next();
        System.out.print("Ingrese el apellido del jugador: ");
        String apellido = scanner.next();
        System.out.print("Ingrese la posición del jugador: ");
        String posicion = scanner.next();
        System.out.print("Ingrese la fecha de nacimiento del jugador (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.next();
        System.out.print("Ingrese el salario del jugador: ");
        String salario = scanner.next();

        // Construir la consulta de inserción
        String query = "update insert <jugador codigo=\"" + codigo + "\" equipo=\"" + equipo + "\">" +
                "<apellido>" + apellido + "</apellido>" +
                "<posicion>" + posicion + "</posicion>" +
                "<fechaNacimiento>" + fechaNacimiento + "</fechaNacimiento>" +
                "<salario>" + salario + "</salario>" +
                "</jugador> into doc('basket/nba.xml')//jugadores";

        // Ejecutar la consulta de inserción
        XQExpression expression = connection.createExpression();
        expression.executeCommand(query);

        System.out.println("Datos insertados correctamente.");
    }
    public void insertarEquipo() throws XQException {
        // Solicitar datos al usuario
        System.out.print("Ingrese el código del equipo: ");
        String codigo = scanner.next();
        System.out.print("Ingrese el nombre del equipo: ");
        String nombre = scanner.next();
        System.out.print("Ingrese la ciudad del equipo: ");
        String ciudad = scanner.next();

        // Construir la consulta de inserción
        String query = "update insert <equipo codigo=\"" + codigo + "\">" +
                "<nombre>" + nombre + "</nombre>" +
                "<ciudad>" + ciudad + "</ciudad>" +
                "</equipo> into doc('basket/nba.xml')//equipos";

        // Ejecutar la consulta de inserción
        XQExpression expression = connection.createExpression();
        expression.executeCommand(query);

        System.out.println("Equipo insertado correctamente.");
    }
    public void modificarDatos() throws XQException {
        // Solicitar datos al usuario
        System.out.print("Ingrese el código del jugador que desea modificar: ");
        String codigo = scanner.next();
        System.out.print("Ingrese el nuevo equipo del jugador: ");
        String nuevoEquipo = scanner.next();
        System.out.print("Ingrese el nuevo apellido del jugador: ");
        String nuevoApellido = scanner.next();
        System.out.print("Ingrese la nueva posición del jugador: ");
        String nuevaPosicion = scanner.next();
        System.out.print("Ingrese la nueva fecha de nacimiento del jugador (YYYY-MM-DD): ");
        String nuevaFechaNacimiento = scanner.next();
        System.out.print("Ingrese el nuevo salario del jugador: ");
        String nuevoSalario = scanner.next();

        // Construir la consulta de modificación para cada campo
        String query =
                "let $jugador := doc('basket/nba.xml')//jugadores/jugador[@codigo='" + codigo + "'] " +
                        "return ( " +
                        "update value $jugador/@equipo with '" + nuevoEquipo + "', " +
                        "update value $jugador/apellido with '" + nuevoApellido + "', " +
                        "update value $jugador/posicion with '" + nuevaPosicion + "', " +
                        "update value $jugador/fechaNacimiento with '" + nuevaFechaNacimiento + "', " +
                        "update value $jugador/salario with '" + nuevoSalario + "' " +
                        ")";

        // Ejecutar la consulta de modificación
        XQExpression expression = connection.createExpression();
        expression.executeCommand(query);

        System.out.println("Datos modificados correctamente.");
    }

    public void borrarDatos() throws XQException {
        // Solicitar datos al usuario
        System.out.print("Ingrese el código del jugador que desea eliminar: ");
        String codigo = scanner.next();

        // Construir la consulta de borrado
        String query = "update delete doc('basket/nba.xml')//jugadores/jugador[@codigo='" + codigo + "']";

        // Ejecutar la consulta de borrado
        XQExpression expression = connection.createExpression();
        expression.executeCommand(query);

        System.out.println("Datos eliminados correctamente.");
    }

    // Consultas adicionales

    public void consultarJugadoresPorEquipo() throws XQException {
        // Solicitar el equipo al usuario
        System.out.print("Ingrese el equipo de los jugadores que desea consultar: ");
        String equipo = scanner.next();

        // Construir la consulta
        String query = "for $jugador in doc('basket/nba.xml')//jugadores/jugador[@equipo='" + equipo + "'] return $jugador";

        // Ejecutar la consulta
        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        // Procesar el resultado
        boolean hasResults = false;
        while (result.next()) {
            hasResults = true;
            XQItem item = result.getItem();
            System.out.println(item.getItemAsString(null));
        }

        if (!hasResults) {
            System.out.println("No se encontraron jugadores para el equipo especificado.");
        }
    }

    public void consultarJugadoresPorSalarioMayor() throws XQException {
        // Solicitar el salario mínimo al usuario
        System.out.print("Ingrese el salario mínimo de los jugadores que desea consultar: ");
        double salarioMinimo = scanner.nextDouble();

        // Construir la consulta
        String query = "for $jugador in doc('basket/nba.xml')//jugadores/jugador where $jugador/salario > " + salarioMinimo + " return $jugador";

        // Ejecutar la consulta
        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        // Procesar el resultado
        boolean hasResults = false;
        while (result.next()) {
            hasResults = true;
            XQItem item = result.getItem();
            System.out.println(item.getItemAsString(null));
        }

        if (!hasResults) {
            System.out.println("No se encontraron jugadores con un salario mayor al especificado.");
        }
    }

    public void consultarEquipos() throws XQException {
        // Construir la consulta
        String query = "for $equipo in doc('basket/nba.xml')//equipos/equipo return $equipo";

        // Ejecutar la consulta
        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        // Procesar el resultado
        boolean hasResults = false;
        while (result.next()) {
            hasResults = true;
            XQItem item = result.getItem();
            System.out.println(item.getItemAsString(null));
        }

        if (!hasResults) {
            System.out.println("No se encontraron equipos.");
        }
    }

    public void consultarJugadoresPorPosicion() throws XQException {
        // Solicitar la posición al usuario
        System.out.print("Ingrese la posición de los jugadores que desea consultar: ");
        String posicion = scanner.next();

        // Construir la consulta
        String query = "for $jugador in doc('basket/nba.xml')//jugadores/jugador[posicion='" + posicion + "'] return $jugador";

        // Ejecutar la consulta
        XQPreparedExpression expression = connection.prepareExpression(query);
        XQResultSequence result = expression.executeQuery();

        // Procesar el resultado
        boolean hasResults = false;
        while (result.next()) {
            hasResults = true;
            XQItem item = result.getItem();
            System.out.println(item.getItemAsString(null));
        }

        if (!hasResults) {
            System.out.println("No se encontraron jugadores en la posición especificada.");
        }
    }
}
