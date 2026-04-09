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

    private GestorViajes gestor;
    private ViajesGuardados guardado;

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField campoBuscar;

    public Menu(GestorViajes gestor, ViajesGuardados guardado) {
        this.gestor = gestor;
        this.guardado = guardado;

        setTitle("Agencia de Viajes");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        construirVentana();
        cargarTabla(gestor.getListaViajes());
        añadirWindowListener();
    }

    private void construirVentana() {
        // panel de arriba: buscador
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
        JScrollPane scroll = new JScrollPane(tabla);

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

        // --- ActionListener con LAMBDA (lo pide la rúbrica) ---
        btnBuscar.addActionListener(e -> buscar());

        // buscar también al pulsar Enter en el campo
        campoBuscar.addActionListener(e -> buscar());

        // --- ActionListener con CLASE ANÓNIMA (lo pide la rúbrica) ---
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                eliminarViaje();
            }
        });

        btnAniadir.addActionListener(e -> abrirDialogoNuevo());

        btnGuardar.addActionListener(e -> {
            guardado.toCSV(gestor.getListaViajes());
            guardado.toJSON(gestor.getListaViajes());
            JOptionPane.showMessageDialog(null, "Viajes guardados correctamente");
        });
    }

    private void cargarTabla(List<Viajes> lista) {
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
        String texto = campoBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarTabla(gestor.getListaViajes());
        } else {
            cargarTabla(gestor.buscarPorTexto(texto));
        }
    }

    private void eliminarViaje() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un viaje primero");
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        // confirmación con JOptionPane (lo pide la rúbrica)
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
        NuevoViaje dialogo = new NuevoViaje(this, gestor);
        dialogo.setVisible(true);
        cargarTabla(gestor.getListaViajes()); // recargamos la tabla al cerrar el dialogo
    }

    // WindowListener que pregunta si guardar al cerrar (lo pide la rúbrica)
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