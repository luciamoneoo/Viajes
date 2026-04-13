// ventana principal de la interfaz gráfica
// hereda de JFrame, que es la clase de Java Swing que representa una ventana

package com.proyect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Menu extends JFrame {

    private GestorViajes gestor; // lógica de los viajes
    private ViajesGuardados guardado; // lectura/escritura en fichero

    private DefaultTableModel modeloTabla; // modelo de datos de la tabla (filas y columnas)
    private JTable tabla; // la tabla visual
    private JTextField campoBuscar; // campo de texto para el buscador


    public Menu(GestorViajes gestor, ViajesGuardados guardado) {
        //recibe el gestor y el objeto de guardado, configura la ventana y la rellena

        this.gestor = gestor;
        this.guardado = guardado;

        setTitle("Agencia de Viajes");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // no cerrar directamente
        setLocationRelativeTo(null); 

        construirVentana();
        cargarTabla(gestor.getListaViajes()); // mostrar los viajes al arrancar
        añadirWindowListener();
    }

    private void construirVentana() {
        // construimos todos los componentes visuales para colocarlos en la ventana
        
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel etiqueta = new JLabel("Buscar: ");
        campoBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelBuscar.add(etiqueta);
        panelBuscar.add(campoBuscar);
        panelBuscar.add(btnBuscar);

        // tabla
        String[] columnas = {"ID", "Nombre", "Destino", "Transporte", "Alojamiento", "Días", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; // que no se pueda editar clickando la celda
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla); // para que tenga scroll si hay muchos viajes

        // panel de abajo: botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAniadir = new JButton("Añadir viaje");
        JButton btnEliminar = new JButton("Eliminar viaje");
        JButton btnGuardar = new JButton("Guardar");
        panelBotones.add(btnAniadir);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);

        // añadimos todo a la ventana
        add(panelBuscar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscar());

        // buscar también al pulsar Enter en el campo
        campoBuscar.addActionListener(e -> buscar());

        
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                eliminarViaje();
            }
        });

        btnAniadir.addActionListener(e -> abrirDialogoNuevo());

        // al pulsar guardar, guardamos en ambos formatos y mostramos un mensaje de confirmación
        btnGuardar.addActionListener(e -> {
            guardado.toCSV(gestor.getListaViajes());
            guardado.toJSON(gestor.getListaViajes());
            JOptionPane.showMessageDialog(null, "Viajes guardados correctamente");
        });
    }

    private void cargarTabla(List<Viajes> lista) {
        // carga la tabla con la lista de viajes que se le pase

        modeloTabla.setRowCount(0); // limpiamos la tabla
        for (Viajes v : lista) {
            modeloTabla.addRow(new Object[]{
                v.getId(),
                v.getNombre(),
                v.getDestino(),
                v.getTransporte(),
                v.getAlojamiento(),
                v.getDias(),
                v.calcularPrecio() + "€"
            });
        }
    }

    private void buscar() {
        // busca por texto en nombre o destino y recarga la tabla con los resultados
        // si el campo de búsqueda está vacío, muestra todos los viajes
        String texto = campoBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarTabla(gestor.getListaViajes());
        } else {
            cargarTabla(gestor.buscarPorTexto(texto));
        }
    }

    private void eliminarViaje() {
        // elimina el viaje seleccionado en la tabla, con confirmación

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un viaje primero");
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        // confirmación con JOptionPane 
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que quieres eliminar el viaje " + nombre + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.eliminarViaje(id);
            cargarTabla(gestor.getListaViajes());
        }
    }

    private void abrirDialogoNuevo() {
        // abre un nuevo diálogo para añadir un viaje, y recarga la tabla al cerrar el diálogo

        NuevoViaje dialogo = new NuevoViaje(this, gestor);
        dialogo.setVisible(true);
        cargarTabla(gestor.getListaViajes()); // recargamos la tabla al cerrar el dialogo
    }

    // WindowListener que pregunta si guardar al cerrar 
    private void añadirWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Quieres guardar antes de salir?",
                    "Guardar",
                    JOptionPane.YES_NO_CANCEL_OPTION
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    guardado.toCSV(gestor.getListaViajes());
                    guardado.toJSON(gestor.getListaViajes());
                    System.exit(0);
                } else if (opcion == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                // si pulsa cancelar no hace nada y la ventana sigue abierta
            }
        });
    }
}